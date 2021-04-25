package net.nighthawkempires.core.bans.registry;

import net.nighthawkempires.core.bans.IPBanModel;
import net.nighthawkempires.core.datasection.AbstractFileRegistry;
import net.nighthawkempires.core.user.UserModel;
import net.nighthawkempires.core.user.registry.UserRegistry;

import java.util.Map;

public class FIPBanRegistry extends AbstractFileRegistry<IPBanModel> implements IPBanRegistry {
    private static final boolean SAVE_PRETTY = true;

    public FIPBanRegistry(String path) {
        super(path, NAME, SAVE_PRETTY, 5);
    }

    @Override
    public Map<String, IPBanModel> getRegisteredData() {
        return REGISTERED_DATA.asMap();
    }
}
