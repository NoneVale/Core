package net.nighthawkempires.core.warning;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.kick.Kick;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

public class Warning {

    private UUID warnedBy;

    private String warnReason;

    private long warnIssued;

    public Warning(Map<String, Object> warningMap) {
        this.warnedBy = UUID.fromString((String) warningMap.get("warned-by"));

        this.warnReason = (String) warningMap.get("warn-reason");

        this.warnIssued = (Long) warningMap.get("warn-issued");
    }

    public UUID getWarnedBy() {
        return warnedBy;
    }

    public String getWarnReason() {
        return warnReason;
    }

    public long getWarnIssued() {
        return warnIssued;
    }

    public String getWarnIssuedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        return dateFormat.format(new Date(getWarnIssued())) + " EST";
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("warned-by", this.warnedBy.toString());

        map.put("warn-reason", this.warnReason);

        map.put("warn-issued", this.warnIssued);

        return map;
    }

    public static Warning getWarning(UUID warnedBy, String warnReason, long warnIssued) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("warned-by", warnedBy.toString());

        map.put("warn-reason", warnReason);

        map.put("warn-issued", warnIssued);

        return new Warning(map);
    }
}
