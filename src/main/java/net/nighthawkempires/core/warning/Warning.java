package net.nighthawkempires.core.warning;

import com.google.common.collect.Maps;

import java.util.Map;
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

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("warned-by", this.warnedBy);

        map.put("warn-reason", this.warnReason);

        map.put("warn-issued", this.warnIssued);

        return map;
    }
}
