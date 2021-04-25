package net.nighthawkempires.core.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import javax.swing.plaf.SplitPaneUI;
import java.util.*;

public class ScoreboardManager {

    private List<NEScoreboard> scoreboardList;
    private HashMap<UUID, HashMap<String, Object>> playerScoreboardMap;

    public ScoreboardManager() {
        this.scoreboardList = Lists.newArrayList();
        this.playerScoreboardMap = Maps.newHashMap();
    }

    public ScoreboardManager addScoreboard(NEScoreboard scoreboard) {
        this.scoreboardList.add(scoreboard);
        this.scoreboardList.sort(Comparator.comparing(NEScoreboard::getPriority));

        return this;
    }

    public ScoreboardManager addAll(Collection<NEScoreboard> scoreboards) {
        scoreboards.stream().forEach(this::addScoreboard);
        return this;
    }

    public ScoreboardManager addAll(NEScoreboard... scoreboard) {
        Arrays.asList(scoreboard).stream().forEach(this::addScoreboard);
        return this;
    }

    @SuppressWarnings("unchecked")
    public void startScoreboards(Player player) {
        this.playerScoreboardMap.put(player.getUniqueId(), Maps.newHashMap());
        getPlayerMap(player).put("scoreboards", Lists.newArrayList(scoreboardList));
        getPlayerMap(player).put("current-board", 0);
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(CorePlugin.getPlugin(), () -> {
            if (!player.isOnline())return;

            int currentBoard = (int) getPlayerMap(player).get("current-board");
            if (currentBoard == ((List<NEScoreboard>) getPlayerMap(player).get("scoreboards")).size()) {
                currentBoard = 0;
            }

            if ((((List<NEScoreboard>) getPlayerMap(player).get("scoreboards"))).get(currentBoard) == null) {
                if (currentBoard == 0) return;
                currentBoard = 0;
            }

            /*int previousBoard = currentBoard - 1;
            if (previousBoard == -1) {
                previousBoard = ((List<NEScoreboard>) getPlayerMap(player).get("scoreboards")).size() - 1;
            }

            // Might not be needed due to canceling the event in the "Scoreboard" class
            try {
                Bukkit.getScheduler().cancelTask(((List<NEScoreboard>) getPlayerMap(player).get("scoreboards")).get(previousBoard).getTaskId());
            } catch (Exception ignored) {}*/

            player.setScoreboard(((List<NEScoreboard>) getPlayerMap(player).get("scoreboards")).get(currentBoard).getFor(player));
            getPlayerMap(player).put("current-board", currentBoard + 1);
        }, 0 , 300);
        getPlayerMap(player).put("taskId", taskId);
    }

    @SuppressWarnings("unchecked")
    public void stopScoreboards(Player player) {
        try {
            player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard());

            int currentBoard = (int) getPlayerMap(player).get("current-board");

            Bukkit.getScheduler().cancelTask(((List<NEScoreboard>) getPlayerMap(player).get("scoreboards")).get(currentBoard).getTaskId());
            Bukkit.getScheduler().cancelTask((int) getPlayerMap(player).get("taskId"));

            this.playerScoreboardMap.remove(player.getUniqueId());
        } catch (Exception ignored) {}
    }

    private HashMap<String, Object> getPlayerMap(Player player) {
        return playerScoreboardMap.get(player.getUniqueId());
    }

    public HashMap<UUID, HashMap<String, Object>> getPlayerScoreboardMap() {
        return this.playerScoreboardMap;
    }
}
