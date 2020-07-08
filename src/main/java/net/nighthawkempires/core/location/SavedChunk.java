package net.nighthawkempires.core.location;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;
import java.util.UUID;

public class SavedChunk {

    private World world;

    private int x;
    private int z;

    public SavedChunk(Map<String, Object> map) {
        this.world = Bukkit.getWorld(UUID.fromString(map.get("world").toString()));

        this.x = Integer.parseInt(map.get("x").toString());
        this.z = Integer.parseInt(map.get("z").toString());
    }

    public Chunk toChunk() {
        return this.world.getChunkAt(this.x, this.z);
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("world", world.getUID().toString());

        map.put("x", x);
        map.put("z", z);

        return map;
    }

    public static SavedChunk fromLocation(Location location) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("world", location.getWorld().getUID().toString());

        Chunk chunk = location.getChunk();
        map.put("x", chunk.getX());
        map.put("z", chunk.getZ());

        return new SavedChunk(map);
    }

    public static SavedChunk fromChunk(Chunk chunk) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("world", chunk.getWorld().getUID().toString());

        map.put("x", chunk.getX());
        map.put("z", chunk.getZ());

        return new SavedChunk(map);
    }
}