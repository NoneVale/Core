package net.nighthawkempires.core.location.registry;

import net.nighthawkempires.core.datasection.AbstractFileRegistry;
import net.nighthawkempires.core.location.PublicLocationModel;
import net.nighthawkempires.core.settings.SettingsModel;
import net.nighthawkempires.core.settings.registry.SettingsRegistry;

import java.util.Map;

public class FPublicLocationRegistry extends AbstractFileRegistry<PublicLocationModel> implements PublicLocationRegistry {
    private static final boolean SAVE_PRETTY = true;

    public FPublicLocationRegistry() {
        super("empires", SAVE_PRETTY, -1);
    }

    @Override
    public Map<String, PublicLocationModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
