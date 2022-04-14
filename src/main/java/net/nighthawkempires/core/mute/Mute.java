package net.nighthawkempires.core.mute;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import org.apache.logging.log4j.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static net.nighthawkempires.core.CorePlugin.*;
import static org.bukkit.ChatColor.*;

public class Mute {

    private UUID mutedBy;
    private UUID mutedUser;

    private MuteType muteType;

    private String muteReason;

    private long muteIssued;
    private long mutedUntil;

    private boolean muteActive;

    public Mute(Map<String, Object> muteMap) {
        this.mutedBy = UUID.fromString((String) muteMap.get("muted-by"));
        this.mutedUser = UUID.fromString((String) muteMap.get("muted-user"));

        this.muteActive = (Boolean) muteMap.get("mute-active");

        this.muteType = MuteType.valueOf((String) muteMap.get("mute-type"));

        this.muteReason = (String) muteMap.get("mute-reason");

        this.muteIssued = Long.parseLong(muteMap.get("mute-issued").toString());
        if (this.muteType == MuteType.TEMP && this.muteActive) {
            this.mutedUntil = Long.parseLong(muteMap.get("muted-until").toString());

            long duration = this.mutedUntil - System.currentTimeMillis();
            if (duration > 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> {
                    this.muteActive = false;
                    if (Bukkit.getOfflinePlayer(this.mutedUser).isOnline()) {
                        Player player = Bukkit.getPlayer(this.mutedUser);
                        player.sendMessage(getMessages().getChatMessage(GRAY + "You are no longer muted, please be sure to abide by all server rules to avoid further punishment."));
                    }
                    getUserRegistry().register(getUserRegistry().getUser(this.mutedUser));
                }, TimeUnit.MILLISECONDS.toSeconds(duration) * 20);
            } else {
                Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> {
                    this.muteActive = false;
                    getUserRegistry().register(getUserRegistry().getUser(this.mutedUser));
                }, 50);
            }
        }
    }

    public UUID getMutedBy() {
        return mutedBy;
    }

    public UUID getMutedUser() {
        return mutedUser;
    }

    public MuteType getMuteType() {
        return muteType;
    }

    public String getMuteReason() {
        return muteReason;
    }

    public long getMuteIssued() {
        return muteIssued;
    }

    public long getMutedUntil() {
        return mutedUntil;
    }

    public String getMuteIssuedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        return dateFormat.format(new Date(getMuteIssued())) + " EST";
    }

    public String getMutedUntilDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        return dateFormat.format(new Date(getMutedUntil())) + " EST";
    }

    public boolean isMuteActive() {
        if (this.muteType == MuteType.PERM) return this.muteActive;

        if (this.muteActive && this.muteType == MuteType.TEMP) {
            if (System.currentTimeMillis() < this.mutedUntil) return this.muteActive;
            else this.muteActive = false;
        }

        return false;
    }

    public void setMuteActive(boolean muteActive) {
        this.muteActive = muteActive;
    }

    public String getMuteInfo() {
        if (isMuteActive()) {
            return getMessages().getChatMessage(GRAY + "You were muted by " + GREEN + Bukkit.getOfflinePlayer(this.getMutedBy()).getName() +
                    GRAY + " for " + YELLOW + getMuteReason() + GRAY + ". \n")
                    + getMessages().getChatMessage(GRAY +
                    "To request to be unmuted join our Discord and fill out your request accordingly https://discord.gg/ZR3qZfHA");
        }
        return null;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("muted-by", this.mutedBy.toString());
        map.put("muted-user", this.mutedUser.toString());

        map.put("mute-type", this.muteType.toString());

        map.put("mute-reason", this.muteReason);

        map.put("mute-issued", this.muteIssued);
        map.put("muted-until", this.mutedUntil);

        map.put("mute-active", this.muteActive);

        return map;
    }
}
