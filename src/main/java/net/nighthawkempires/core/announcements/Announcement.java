package net.nighthawkempires.core.announcements;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.FJsonSection;
import net.nighthawkempires.core.util.StringUtil;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Map;

public class Announcement {

    private String[] toStringArray(List<String> arr)
    {
        String str[] = new String[arr.size()];
        for (int j = 0; j < arr.size(); j++) {
            str[j] = ChatColor.GRAY + StringUtil.centeredMessage(ChatColor.translateAlternateColorCodes('&', arr.get(j)));
        }
        return str;
    }

    private String[] lines;

    public Announcement(Map<String, Object> map) {
        DataSection data = new FJsonSection(map);

        this.lines = toStringArray(data.getStringList("lines"));
    }

    public String[] getLines() {
        return this.lines;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("lines", this.lines);
        return map;
    }
}
