package net.nighthawkempires.core.cooldown;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Cooldown {

    private UUID uuid;
    private String id;
    private long expires;

    public Cooldown(UUID uuid, String id, long expires) {
        this.uuid = uuid;
        this.id = id;
        this.expires = expires;
    }

    public Cooldown(DataSection data) {
        this.uuid = UUID.fromString(data.getString("uuid"));
        this.id = data.getString("id");
        this.expires = data.getLong("expires");

        if (isActive()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getPlugin(), () -> {
                CorePlugin.getSettingsRegistry().getCooldowns().removeCooldown(this);
            }, TimeUnit.MILLISECONDS.toSeconds(getDuration()) * 20);
        }
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        long duration = this.expires - System.currentTimeMillis();
        return duration > 0;
    }

    public long getDuration() {
        return this.expires - System.currentTimeMillis();
    }

    public long getExpires() {
        return expires;
    }

    public String timeLeft() {
        long difference = getExpires() - System.currentTimeMillis();

        int seconds = (int) (difference / 1000) % 60 ;
        int minutes = (int) ((difference / (1000*60)) % 60);
        int hours   = (int) ((difference / (1000*60*60)) % 24);
        int days = (int) (difference / (1000*60*60*24));

        StringBuilder timeLeft = new StringBuilder();

        if (days > 0) {
            timeLeft.append(days).append(" days, ");
        }

        if (hours > 0) {
            timeLeft.append(hours).append(" hours, ");
        }

        if (minutes > 0) {
            timeLeft.append(minutes).append(" minutes, ");
        }

        if (seconds > 0) {
            timeLeft.append(seconds).append(" seconds");
        }

        String time = timeLeft.toString().trim();
        if (time.endsWith(","))
            time = time.substring(0, time.length() - 1);

        return time;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        map.put("uuid", uuid.toString());
        map.put("id", id);
        map.put("expires", expires);

        return map;
    }
}
