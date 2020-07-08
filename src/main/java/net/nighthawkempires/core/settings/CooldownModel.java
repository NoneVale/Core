package net.nighthawkempires.core.settings;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.cooldown.Cooldown;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.FJsonSection;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CooldownModel extends SettingsModel {

    private List<Cooldown> cooldownList;

    public CooldownModel() {
        this.cooldownList = Lists.newArrayList();
    }

    public CooldownModel(DataSection data) {
        this.cooldownList = Lists.newArrayList();

        for (Map<String, Object> map : data.getMapList("cooldowns")) {
            DataSection dataSection = new FJsonSection(map);
            Cooldown cooldown = new Cooldown(dataSection);
            if (cooldown.isActive()) {
                cooldownList.add(cooldown);
            }
        }
    }

    public boolean hasActiveCooldown(UUID uuid, String id) {
        for (Cooldown cooldown : cooldownList) {
            if (cooldown.getUniqueId().equals(uuid) && cooldown.getId().equals(id)) {
                if (cooldown.isActive()) {
                    return true;
                } else {
                    this.cooldownList.remove(cooldown);
                    return false;
                }
            }
        }
        return false;
    }

    public Cooldown getActive(UUID uuid, String id) {
        for (Cooldown cooldown : cooldownList) {
            if (cooldown.getUniqueId().equals(uuid) && cooldown.getId().equals(id)) {
                if (cooldown.isActive()) {
                    return cooldown;
                }
            }
        }
        return null;
    }

    public void addCooldown(Cooldown cooldown) {
        cooldownList.add(cooldown);
        CorePlugin.getSettingsRegistry().register(this);
    }

    public void removeCooldown(Cooldown cooldown) {
        cooldownList.remove(cooldown);
        CorePlugin.getSettingsRegistry().register(this);
    }

    public String getKey() {
        return "cooldowns";
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        List<Map<String, Object>> cooldownList = Lists.newArrayList();
        for (Cooldown cooldown : this.cooldownList) {
            if (cooldown.isActive())
                cooldownList.add(cooldown.serialize());
        }

        map.put("cooldowns", cooldownList);
        return map;
    }
}
