package net.nighthawkempires.core.location.player.registry;

import com.google.common.collect.ImmutableList;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Registry;
import net.nighthawkempires.core.location.player.PlayerLocationModel;

import java.util.Map;
import java.util.UUID;

public interface PlayerLocationRegistry extends Registry<PlayerLocationModel> {

    default PlayerLocationModel fromDataSection(String stringKey, DataSection data) {
        return new PlayerLocationModel(stringKey, data);
    }

    default PlayerLocationModel getPlayerLocations(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return fromKey(uuid.toString()).orElseGet(() -> register(new PlayerLocationModel(uuid)));
    }

    @Deprecated
    Map<String, PlayerLocationModel> getRegisteredData();

    default boolean playerLocationsExist(UUID uuid) {
        return fromKey(uuid.toString()).isPresent();
    }
}
