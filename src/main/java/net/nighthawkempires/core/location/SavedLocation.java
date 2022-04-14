package net.nighthawkempires.core.location;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;
import java.util.UUID;

public class SavedLocation {

    private World world;

    private double x;
    private double y;
    private double z;

    private boolean useYaw;
    private boolean usePitch;
    private float yaw;
    private float pitch;

    private String name;

    public SavedLocation(Map<String, Object> map) {
        this.world = Bukkit.getWorld(UUID.fromString(map.get("world").toString()));

        this.x = Double.parseDouble(map.get("x").toString());
        this.y = Double.parseDouble(map.get("y").toString());
        this.z = Double.parseDouble(map.get("z").toString());

        this.useYaw = map.containsKey("yaw");
        if (this.useYaw)
            this.yaw = Float.parseFloat(map.get("yaw").toString());

        this.usePitch = map.containsKey("pitch");
        if (this.usePitch)
            this.pitch = Float.parseFloat(map.get("pitch").toString());

        this.name = (map.containsKey("name") ? map.get("name").toString() : world.getName());
    }

    public String getName() {
        return this.name;
    }

    public SavedLocation setName(String name) {
        this.name = name;

        return this;
    }

    public World getWorld() {
        return world;
    }

    public Location toLocation() {
        return new Location(world, x, y, z, yaw, pitch);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        if (world != null) {
            map.put("world", world.getUID().toString());

            map.put("x", x);
            map.put("y", y);
            map.put("z", z);

            if (this.useYaw)
                map.put("yaw", yaw);
            if (this.usePitch)
                map.put("pitch", pitch);

            map.put("name", name);
        }
        return map;
    }

    public static SavedLocation fromLocation(Location location) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("world", location.getWorld().getUID().toString());

        map.put("x", location.getX());
        map.put("y", location.getY());
        map.put("z", location.getZ());

        map.put("yaw", location.getYaw());
        map.put("pitch", location.getPitch());

        return new SavedLocation(map);
    }

    public static SavedLocation fromLocation(Location location, boolean useYawPitch) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("world", location.getWorld().getUID().toString());

        map.put("x", location.getX());
        map.put("y", location.getY());
        map.put("z", location.getZ());

        if (useYawPitch) {
            map.put("yaw", location.getYaw());
            map.put("pitch", location.getPitch());
        }

        return new SavedLocation(map);
    }
}