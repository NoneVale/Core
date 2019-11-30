package net.nighthawkempires.core.mute;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class Mute {

    private UUID mutedBy;

    private MuteType muteType;

    private String muteReason;

    private long muteIssued;
    private long mutedUntil;

    private boolean muteActive;

    public Mute(Map<String, Object> muteMap) {
        this.mutedBy = UUID.fromString((String) muteMap.get("muted-by"));

        this.muteType = MuteType.valueOf((String) muteMap.get("mute-type"));

        this.muteReason = (String) muteMap.get("mute-reason");

        this.muteIssued = (Long) muteMap.get("mute-issued");
        this.mutedUntil = (Long) muteMap.get("muted-until");

        this.muteActive = (Boolean) muteMap.get("mute-active");
    }

    public UUID getMutedBy() {
        return mutedBy;
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

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("muted-by", this.mutedBy);

        map.put("mute-type", this.muteType.toString());

        map.put("mute-reason", this.muteReason);

        map.put("mute-issued", this.muteIssued);
        map.put("muted-until", this.mutedUntil);

        map.put("mute-active", this.muteActive);

        return map;
    }
}
