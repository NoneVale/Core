package net.nighthawkempires.core.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public abstract class NEScoreboard {

    public abstract String getName();

    public abstract int getTaskId();

    public abstract Scoreboard getFor(Player player);

    public Scoreboard getScoreboard() {
        return Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public int getPriority() {
        return 999;
    }
}