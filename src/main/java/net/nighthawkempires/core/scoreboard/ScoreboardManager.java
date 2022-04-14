package net.nighthawkempires.core.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.user.UserModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import javax.swing.plaf.SplitPaneUI;
import java.util.*;

public class ScoreboardManager {

    private List<NEScoreboard> scoreboardList;
    //private HashMap<UUID, HashMap<String, Object>> playerScoreboardMap;
    private int currentBoard;

    public ScoreboardManager() {
        this.scoreboardList = Lists.newArrayList();
        //this.playerScoreboardMap = Maps.newHashMap();
        this.currentBoard = 0;
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
    public void startScoreboards() {
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(CorePlugin.getPlugin(), () -> {
            if (Bukkit.getOnlinePlayers().size() == 0) return;

            if (currentBoard == scoreboardList.size()) {
                currentBoard = 0;
            }

            if (scoreboardList.get(currentBoard) == null) {
                if (currentBoard == 0) return;
                currentBoard = 0;
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                UserModel userModel = CorePlugin.getUserRegistry().getUser(player.getUniqueId());
                if (userModel.isScoreboardEnabled()) {
                    player.setScoreboard(scoreboardList.get(currentBoard).getFor(player));
                }
            }
            currentBoard++;
        }, 0 , 300);
    }

    @SuppressWarnings("unchecked")
    public void stopScoreboards(Player player) {
        try {
            player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard());
        } catch (Exception ignored) {}
    }
}