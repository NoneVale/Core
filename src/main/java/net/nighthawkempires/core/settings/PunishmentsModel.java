package net.nighthawkempires.core.settings;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.bans.Ban;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Model;
import net.nighthawkempires.core.mute.Mute;

import java.util.List;
import java.util.Map;

public class PunishmentsModel extends SettingsModel {

    private String key;

    private List<Ban> tempBans;
    private List<Mute> tempMutes;

    private PunishmentsModel() {
        this.key = "punishments";

        this.tempBans = Lists.newArrayList();
        this.tempMutes = Lists.newArrayList();
    }

    public PunishmentsModel(String key, DataSection data) {
        this.key = key;

        this.tempBans = Lists.newArrayList();
        for (Map<String, Object> banMap : data.getMapList("temp-bans")) {
            this.tempBans.add(new Ban(banMap));
        }

        this.tempMutes = Lists.newArrayList();
        for (Map<String, Object> muteMap : data.getMapList("temp-mutes")) {
            this.tempMutes.add(new Mute(muteMap));
        }
    }

    public ImmutableList<Ban> getTempBans() {
        return ImmutableList.copyOf(this.tempBans);
    }

    public void addTempBan(Ban ban) {
        this.tempBans.add(ban);
        CorePlugin.getSettingsRegistry().register(this);
    }

    public void removeTempBan(Ban ban) {
        this.tempBans.remove(ban);
        CorePlugin.getSettingsRegistry().register(this);
    }

    public ImmutableList<Mute> getTempMutes() {
        return ImmutableList.copyOf(this.tempMutes);
    }

    public void addTempMute(Mute mute) {
        this.tempMutes.add(mute);
        CorePlugin.getSettingsRegistry().register(this);
    }

    public void removeTempMute(Mute mute) {
        this.tempMutes.remove(mute);
        CorePlugin.getSettingsRegistry().register(this);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
