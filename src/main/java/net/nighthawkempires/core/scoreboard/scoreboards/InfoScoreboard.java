package net.nighthawkempires.core.scoreboard.scoreboards;

import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.lang.Messages;
import net.nighthawkempires.core.scoreboard.NEScoreboard;
import net.nighthawkempires.core.scoreboard.ScoreboardManager;
import net.nighthawkempires.core.settings.ConfigModel;
import net.nighthawkempires.core.settings.SettingsModel;
import net.nighthawkempires.core.user.UserModel;
import net.nighthawkempires.core.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class InfoScoreboard extends NEScoreboard {

    private int taskId;

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getName() {
        return "Info";
    }

    @Override
    public int getTaskId() {
        return taskId;
    }

    @Override
    public Scoreboard getFor(Player player) {
        UserModel userModel = CorePlugin.getUserRegistry().getUser(player.getUniqueId());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(CorePlugin.getMessages().getMessage(Messages.SCOREBOARD_HEADER).replaceAll("%SERVER%",
                CorePlugin.getMessages().getServerTag(getConfig().getServerType())));
        Team top = scoreboard.registerNewTeam("top");
        top.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " ➛  " + ChatColor.BLUE);
        top.setPrefix("");
        top.setSuffix("");
        Team middle = scoreboard.registerNewTeam("middle");
        middle.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " ➛  " + ChatColor.GREEN);
        middle.setPrefix("");
        middle.setSuffix("");
        Team bottom = scoreboard.registerNewTeam("bottom");
        bottom.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " ➛  " + ChatColor.GOLD);
        bottom.setPrefix("");
        bottom.setSuffix("");

        objective.getScore(ChatColor.GRAY + " Name" + ChatColor.GRAY + ": ").setScore(9);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " ➛  " + ChatColor.BLUE).setScore(8);
        objective.getScore(ChatColor.DARK_PURPLE + " ").setScore(7);
        objective.getScore(ChatColor.GRAY + (getConfig().isEconomyBased() ? " Balance" : " Played Servers") + ChatColor.GRAY + ": ")
                .setScore(6);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " ➛  " + ChatColor.GREEN).setScore(5);
        objective.getScore(ChatColor.YELLOW + "  ").setScore(4);
        objective.getScore(ChatColor.GRAY + " Tokens" + ChatColor.GRAY + ": ").setScore(3);
        objective.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " ➛  " + ChatColor.GOLD).setScore(2);
        objective.getScore(ChatColor.DARK_GRAY + "  " + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "━━━━━━━━━━━━━━━━━━━━")
                .setScore(1);

        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(CorePlugin.getPlugin(), () -> {
            top.setSuffix(ChatColor.BLUE + player.getName());
            middle.setSuffix(ChatColor.GREEN + (getConfig().isEconomyBased() ? StringUtil.formatBalance(userModel.getServerBalance(getConfig().getServerType())) + "" : userModel.getPlayedServerList().size() + ""));
            bottom.setSuffix(ChatColor.GOLD + "" + userModel.getTokens());
        }, 0 , 10);
        Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getPlugin(), () -> {
            Bukkit.getScheduler().cancelTask(getTaskId());
        }, 295);
        return scoreboard;
    }

    private ConfigModel getConfig() {
        return CorePlugin.getConfigg();
    }
}