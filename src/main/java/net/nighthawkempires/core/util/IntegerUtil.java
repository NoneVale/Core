package net.nighthawkempires.core.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;

public class IntegerUtil {

    public static int extractInt(String string) {
        String newString = ChatColor.stripColor(string);
        boolean containsInt = false;
        for (char c : ChatColor.stripColor(string).toCharArray()) {
            String s = String.valueOf(c);

            if (NumberUtils.isDigits(s))
                containsInt = true;
            else
                newString = newString.replaceFirst(s, "");
        }

        return (containsInt ? Integer.parseInt(newString) : -1);
    }
}