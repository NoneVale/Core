package net.nighthawkempires.core.location;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Model;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Map;

public class PublicLocationModel implements Model {

    private String key;

    private Map<World, SavedLocation> spawns;
    private Map<String, SavedLocation> warps;

    public PublicLocationModel() {
        this.key = "locations";

        this.spawns = Maps.newHashMap();
        this.warps = Maps.newHashMap();
    }

    public PublicLocationModel(String key, DataSection data) {
        this.key = key;

        this.spawns = Maps.newHashMap();
        for (Map<String, Object> map : data.getMapList("spawns")) {
            SavedLocation savedLocation = new SavedLocation(map);
            Location location = savedLocation.toLocation();

            spawns.put(location.getWorld(), savedLocation);
        }

        this.warps = Maps.newHashMap();
        for (Map<String, Object> map : data.getMapList("warps")) {
            SavedLocation savedLocation = new SavedLocation(map);

            warps.put(savedLocation.getName(), savedLocation);
        }
    }

    public Location getSpawn(World world) {
        if (spawns.containsKey(world)) return spawns.get(world).toLocation();
        return null;
    }

    public void setSpawn(Location location) {
        spawns.put(location.getWorld(), SavedLocation.fromLocation(location));

        CorePlugin.getPublicLocationRegistry().register(this);
    }

    public void removeSpawn(World world) {
        spawns.remove(world);

        CorePlugin.getPublicLocationRegistry().register(this);
    }

    public boolean hasSpawn(World world) {
        return spawns.containsKey(world);
    }

    public ImmutableList<String> getWarps() {
        return ImmutableList.copyOf(this.warps.keySet());
    }

    public Location getWarp(String name) {
        if (warps.containsKey(name)) return warps.get(name).toLocation();
        return null;
    }

    public void setWarp(String name, Location location) {
        warps.put(name, SavedLocation.fromLocation(location).setName(name));

        CorePlugin.getPublicLocationRegistry().register(this);
    }

    public void removeWarp(String name) {
        warps.remove(name);

        CorePlugin.getPublicLocationRegistry().register(this);
    }

    public boolean warpExists(String name) {
        return warps.containsKey(name);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        List<Map<String, Object>> spawnList = Lists.newArrayList();
        for (SavedLocation savedLocation : spawns.values()) {
            spawnList.add(savedLocation.serialize());
        }
        map.put("spawns", spawnList);

        List<Map<String, Object>> warpList = Lists.newArrayList();
        for (SavedLocation savedLocation : warps.values()) {
            warpList.add(savedLocation.serialize());
        }
        map.put("warps", warpList);

        return map;
    }
}