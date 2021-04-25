package net.nighthawkempires.core.settings.registry;

import net.nighthawkempires.core.datasection.AbstractFileRegistry;
import net.nighthawkempires.core.settings.SettingsModel;

import java.util.Map;

public class FSettingsRegistry extends AbstractFileRegistry<SettingsModel> implements SettingsRegistry {
    private static final boolean SAVE_PRETTY = true;

    public FSettingsRegistry() {
        super("empires", SAVE_PRETTY, -1);
    }

    public FSettingsRegistry(String path) {
        super(path, SAVE_PRETTY, -1);
    }

    @Override
    public Map<String, SettingsModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
