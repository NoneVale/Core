package net.nighthawkempires.core.util;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import java.util.TreeMap;

public class StringUtil {

    private final static TreeMap<Integer, String> romanNumeralMap;

    static {
        romanNumeralMap = Maps.newTreeMap();

        romanNumeralMap.put(1000, "M");
        romanNumeralMap.put(900, "CM");
        romanNumeralMap.put(500, "D");
        romanNumeralMap.put(400, "CD");
        romanNumeralMap.put(100, "C");
        romanNumeralMap.put(90, "XC");
        romanNumeralMap.put(50, "L");
        romanNumeralMap.put(40, "XL");
        romanNumeralMap.put(10, "X");
        romanNumeralMap.put(9, "IX");
        romanNumeralMap.put(5, "V");
        romanNumeralMap.put(4, "IV");
        romanNumeralMap.put(1, "I");

    }

    public static String toRoman(int number) {
        int l =  romanNumeralMap.floorKey(number);
        if ( number == l ) {
            return romanNumeralMap.get(number);
        }
        return romanNumeralMap.get(l) + toRoman(number-l);
    }

    public static String beautify(String s) {
        if (s.contains("_")) {
            String[] split = s.split("_");

            StringBuilder string = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                string.append(beautify(split[i]));

                if (i < split.length - 1) {
                    string.append(" ");
                }
            }

            return string.toString();
        }

        return s.toUpperCase().substring(0, 1) + s.substring(1).toLowerCase();
    }

    public static final long THOU = 1000L;
    public static final long MILL = 1000000L;
    public static final long BILL = 1000000000L;
    public static final long TRIL = 1000000000000L;
    public static final long QUAD = 1000000000000000L;
    public static final long QUIN = 1000000000000000000L;

    public static String formatBalance(double val) {
        if (val < THOU) return Double.toString(val);
        if (val < MILL) return makeDecimal((long) val, THOU, "k");
        if (val < BILL) return makeDecimal((long) val, MILL, "M");
        if (val < TRIL) return makeDecimal((long) val, BILL, "B");
        if (val < QUAD) return makeDecimal((long) val, TRIL, "T");
        if (val < QUIN) return makeDecimal((long) val, QUAD, "Q");
        return makeDecimal((long) val, QUIN, "U");
    }

    public static String makeDecimal(long val, long div, String sfx) {
        val = val / (div / 10);
        long whole = val / 10;
        long tenths = val % 10;
        if ((tenths == 0) || (whole >= 10))
            return String.format("%d%s", whole, sfx);
        return String.format("%d.%d%s", whole, tenths, sfx);
    }


    private final static int centrePixels = 154;

    public static String centeredMessage(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        int messagePixelSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if(previousCode == true) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePixelSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePixelSize++;
            }
        }

        int halvedMessageSize = messagePixelSize / 2;
        int toCompensate = centrePixels - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();

        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb.toString() + message;
    }
}