package net.nighthawkempires.core.location.player.registry;

import net.nighthawkempires.core.datasection.AbstractFileRegistry;
import net.nighthawkempires.core.location.player.PlayerLocationModel;

import java.util.Map;

public class FPlayerLocationRegistry extends AbstractFileRegistry<PlayerLocationModel> implements PlayerLocationRegistry {
    private static final boolean SAVE_PRETTY = true;

    public FPlayerLocationRegistry() {
        super("empires/homes", SAVE_PRETTY, -1);
    }

    @Override
    public Map<String, PlayerLocationModel  > getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
