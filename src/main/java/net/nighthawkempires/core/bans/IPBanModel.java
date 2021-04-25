package net.nighthawkempires.core.bans;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Model;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class IPBanModel implements Model {

    private String key;

    private UUID bannedBy;
    private String bannedIp;

    private BanType banType;

    private String banReason;

    private long banIssued;
    private long bannedUntil;

    private boolean banActive;

    public IPBanModel(Map<String, Object> banMap) {
        this.key = UUID.randomUUID().toString();

        this.bannedBy = UUID.fromString((String) banMap.get("banned-by"));
        this.bannedIp = (String) banMap.get("banned-ip");

        this.banActive = (Boolean) banMap.get("ban-active");

        this.banType = BanType.valueOf((String) banMap.get("ban-type"));

        this.banReason = (String) banMap.get("ban-reason");

        this.banIssued = Long.parseLong(banMap.get("ban-issued").toString());
        if (this.banType == BanType.TEMP && this.banActive) {
            this.bannedUntil = Long.parseLong(banMap.get("banned-until").toString());

            long duration = this.bannedUntil - System.currentTimeMillis();
            if (duration > 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getPlugin(), () -> {
                    this.banActive = false;
                    CorePlugin.getIpBanRegistry().register(this);
                }, TimeUnit.MILLISECONDS.toSeconds(duration) * 20);
            } else {
                Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getPlugin(), () -> {
                    this.banActive = false;
                    CorePlugin.getIpBanRegistry().register(this);
                }, 50);
            }
        }
    }

    public IPBanModel(String key, DataSection data) {
        this.key = UUID.randomUUID().toString();

        this.bannedBy = UUID.fromString(data.getString("banned-by"));
        this.bannedIp = data.getString("banned-ip");

        this.banActive = data.getBoolean("ban-active");

        this.banType = BanType.valueOf(data.getString("ban-type"));

        this.banReason = data.getString("ban-reason");

        this.banIssued = data.getLong("ban-issued");
        if (this.banType == BanType.TEMP && this.banActive) {
            this.bannedUntil = data.getLong("banned-until", 0);

            long duration = this.bannedUntil - System.currentTimeMillis();
            if (duration > 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getPlugin(), () -> {
                    this.banActive = false;
                    CorePlugin.getIpBanRegistry().register(this);
                }, TimeUnit.MILLISECONDS.toSeconds(duration) * 20);
            } else {
                Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getPlugin(), () -> {
                    this.banActive = false;
                    CorePlugin.getIpBanRegistry().register(this);
                }, 50);
            }
        }
    }

    public UUID getBannedBy() {
        return bannedBy;
    }

    public String getBannedIp() {
        return bannedIp;
    }

    public BanType getBanType() {
        return banType;
    }

    public String getBanReason() {
        return banReason;
    }

    public long getBanIssued() {
        return banIssued;
    }

    public String getBanIssuedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        return dateFormat.format(new Date(getBanIssued())) + " EST";
    }

    public long getBannedUntil() {
        return bannedUntil;
    }

    public String getBannedUntilDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        return dateFormat.format(new Date(getBannedUntil())) + " EST";
    }

    public boolean isBanActive() {
        if (this.banType == BanType.PERM) return this.banActive;

        if (this.banActive && this.banType == BanType.TEMP) {
            if (System.currentTimeMillis() < this.bannedUntil) return this.banActive;
            else this.banActive = false;
        }

        return false;
    }

    public void setBanActive(boolean banActive) {
        this.banActive = banActive;
        CorePlugin.getIpBanRegistry().register(this);
    }

    public String getKey() {
        return this.key;
    }

    public String getBanInfo() {
        return ChatColor
                .translateAlternateColorCodes('&',
                        "\n&4&l&oBANNED&7&l&o!\n   \n" +
                                "&8&l&oBy&7&l&o: &9&l&o" + Bukkit.getOfflinePlayer(this.bannedBy).getName() +
                                "&r\n&8&l&oReason&7&l&o: " + this.banReason +
                                "&r\n&8&l&oIssued&7&l&o: " + getBanIssuedDate() +
                                (this.banType == BanType.TEMP ? "&r\n&8&l&oUntil&7&l&o: " + getBannedUntilDate() : "") +
                                "&r\n&8&l&oRequest Unban&7&l&o: https://discord.gg/YevukF5");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("banned-by", this.bannedBy.toString());
        map.put("banned-ip", this.bannedIp.toString());

        map.put("ban-type", this.banType.toString());

        map.put("ban-reason", this.banReason);

        map.put("ban-issued", this.banIssued);
        if (this.banType == BanType.TEMP  ) {
            map.put("banned-until", this.bannedUntil);
        }

        map.put("ban-active", this.banActive);

        return map;
    }
}
