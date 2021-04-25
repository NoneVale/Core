package net.nighthawkempires.core.bans.registry;

import com.mongodb.client.MongoDatabase;
import net.nighthawkempires.core.bans.IPBanModel;
import net.nighthawkempires.core.datasection.AbstractMongoRegistry;
import net.nighthawkempires.core.user.UserModel;
import net.nighthawkempires.core.user.registry.UserRegistry;

import java.util.Map;

public class MIPBanRegistry extends AbstractMongoRegistry<IPBanModel> implements IPBanRegistry {

    public MIPBanRegistry(MongoDatabase database) {
        super(database.getCollection(NAME), 5);
    }

    @Override
    public Map<String, IPBanModel> getRegisteredData()
    {
        return m_RegisteredData.asMap();
    }
}
