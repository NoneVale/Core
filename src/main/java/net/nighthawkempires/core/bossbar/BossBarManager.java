package net.nighthawkempires.core.bossbar;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class BossBarManager {

    private ConcurrentMap<UUID, BossBar> bossBarMap;

    public BossBarManager() {
        this.bossBarMap = Maps.newConcurrentMap();
    }

    public void setBossBar(Player player, BossBar bossBar) {
        removeBossBar(player);
        bossBar.addPlayer(player);
        bossBarMap.put(player.getUniqueId(), bossBar);
    }

    public void setBossBar(Player player, String title, double percent, BarColor color, BarStyle style) {
        BossBar bossBar = Bukkit.createBossBar(title, color, style);
        bossBar.setProgress(percent);
        setBossBar(player, bossBar);
    }

    public void removeBossBar(Player player) {
        if (bossBarMap.containsKey(player.getUniqueId())) {
            BossBar bossBar = bossBarMap.get(player.getUniqueId());

            bossBar.removePlayer(player);
            bossBarMap.remove(player.getUniqueId());
        }
    }
}
