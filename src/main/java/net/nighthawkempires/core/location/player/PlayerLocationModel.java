package net.nighthawkempires.core.location.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Model;
import net.nighthawkempires.core.location.SavedLocation;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerLocationModel implements Model {

    private String key;

    private Map<String, SavedLocation> homes;

    public PlayerLocationModel(UUID uuid) {
        this.key = uuid.toString();

        this.homes = Maps.newHashMap();
    }

    public PlayerLocationModel(String key, DataSection data) {
        this.key = key;

        this.homes = Maps.newHashMap();
        for (Map<String, Object> map : data.getMapList("homes")) {
            SavedLocation savedLocation = new SavedLocation(map);

            homes.put(savedLocation.getName(), savedLocation);
        }
    }

    public ImmutableList<String> getHomes() {
        return ImmutableList.copyOf(homes.keySet());
    }

    public Location getHome(String name) {
        if (homes.containsKey(name)) return homes.get(name).toLocation();
        return null;
    }

    public void setHome(String name, Location location) {
        homes.put(name, SavedLocation.fromLocation(location).setName(name));

        CorePlugin.getPlayerLocationRegistry().register(this);
    }

    public void removeHome(String name) {
        homes.remove(name);

        CorePlugin.getPlayerLocationRegistry().register(this);
    }

    public boolean homeExists(String name) {
        return homes.containsKey(name);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        List<Map<String, Object>> homeList = Lists.newArrayList();
        for (SavedLocation savedLocation : homes.values()) {
            homeList.add(savedLocation.serialize());
        }
        map.put("homes", homeList);

        return map;
    }
}