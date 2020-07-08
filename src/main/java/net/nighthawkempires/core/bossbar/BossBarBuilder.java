package net.nighthawkempires.core.bossbar;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.Arrays;
import java.util.List;

public class BossBarBuilder {

    private String title;
    private double percent;
    private BarStyle barStyle;
    private BarColor barColor;
    private List<BarFlag> barFlags;

    public BossBarBuilder() {
        this.title = "";
        this.percent = 0.0d;
        this.barStyle = BarStyle.SOLID;
        this.barColor = BarColor.WHITE;
        this.barFlags = Lists.newArrayList();
    }

    public BossBarBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BossBarBuilder setPercent(double percent) {
        this.percent = percent;
        return this;
    }

    public BossBarBuilder setBarStyle(BarStyle barStyle) {
        this.barStyle = barStyle;
        return this;
    }

    public BossBarBuilder setBarColor(BarColor barColor) {
        this.barColor = barColor;
        return this;
    }

    public BossBarBuilder setFlags(BarFlag... barFlags) {
        this.barFlags.clear();
        this.barFlags.addAll(Arrays.asList(barFlags));
        return this;
    }

    public BossBarBuilder addFlag(BarFlag barFlag) {
        this.barFlags.add(barFlag);
        return this;
    }

    public BossBar build() {
        BarFlag[] flags = new BarFlag[this.barFlags.size()];
        flags = this.barFlags.toArray(flags);
        BossBar bossBar = Bukkit.createBossBar(this.title, this.barColor, this.barStyle, flags);
        bossBar.setProgress(percent);
        return bossBar;
    }
}
