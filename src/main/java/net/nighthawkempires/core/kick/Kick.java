package net.nighthawkempires.core.kick;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.bans.BanType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

public class Kick {

    private UUID kickedBy;

    private String kickReason;

    private long kickIssued;

    public Kick(Map<String, Object> kickMap) {
        this.kickedBy = UUID.fromString((String) kickMap.get("kicked-by"));

        this.kickReason = (String) kickMap.get("kick-reason");

        this.kickIssued = (Long) kickMap.get("kick-issued");
    }

    public UUID getKickedBy() {
        return kickedBy;
    }

    public String getKickReason() {
        return kickReason;
    }

    public long getKickIssued() {
        return kickIssued;
    }

    public String getKickIssuedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        return dateFormat.format(new Date(getKickIssued())) + " EST";
    }

    public String getKickInfo() {
        return ChatColor
                .translateAlternateColorCodes('&',
                        "\n&c&l&oKICKED&7&l&o!\n   \n" +
                                "&8&l&oBy&7&l&o: &9&l&o" + Bukkit.getOfflinePlayer(this.kickedBy).getName() +
                                "&r\n&8&l&oReason&7&l&o: " + this.kickReason +
                                "&r\n&8&l&oIssued&7&l&o: " + getKickIssuedDate());
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("kicked-by", this.kickedBy.toString());

        map.put("kick-reason", this.kickReason);

        map.put("kick-issued", this.kickIssued);

        return map;
    }

    public static Kick getKick(UUID kickedBy, String kickReason, long kickIssued) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("kicked-by", kickedBy.toString());

        map.put("kick-reason", kickReason);

        map.put("kick-issued", kickIssued);

        return new Kick(map);
    }
}
