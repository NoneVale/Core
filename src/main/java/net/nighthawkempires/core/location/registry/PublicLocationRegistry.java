package net.nighthawkempires.core.location.registry;

import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Registry;
import net.nighthawkempires.core.location.PublicLocationModel;
import net.nighthawkempires.core.user.UserModel;

import java.util.Map;
import java.util.UUID;

public interface PublicLocationRegistry extends Registry<PublicLocationModel> {
    default PublicLocationModel fromDataSection(String stringKey, DataSection data) {
        return new PublicLocationModel(stringKey, data);
    }

    default PublicLocationModel getPublicLocations() {
        return fromKey("locations").orElseGet(() -> register(new PublicLocationModel()));
    }

    @Deprecated
    Map<String, PublicLocationModel> getRegisteredData();
}
