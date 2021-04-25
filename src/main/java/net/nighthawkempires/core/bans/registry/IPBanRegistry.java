package net.nighthawkempires.core.bans.registry;

import com.google.common.collect.ImmutableList;
import net.nighthawkempires.core.bans.IPBanModel;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Registry;
import net.nighthawkempires.core.user.UserModel;

import java.util.Map;
import java.util.UUID;

public interface IPBanRegistry extends Registry<IPBanModel> {
    String NAME = "ip-bans";

    default IPBanModel fromDataSection(String stringKey, DataSection data) {
        return new IPBanModel(stringKey, data);
    }

    default IPBanModel getBan(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return fromKey(uuid.toString()).orElse(null);
    }

    default IPBanModel getBan(String address) {
        for (IPBanModel model : loadAllFromDb().values()) {
            if (model.getBannedIp().equals(address)) {
                if (model.isBanActive()) {
                    return model;
                }
            }
        }
        return null;
    }

    default IPBanModel getBan(Map<String, Object> banMap) {
        if (banMap == null) {
            return null;
        }
        IPBanModel ipBanModel = new IPBanModel(banMap);
        register(ipBanModel);
        return ipBanModel;
    }

    @Deprecated
    Map<String, IPBanModel> getRegisteredData();

    default ImmutableList<IPBanModel> getUsers() {
        return ImmutableList.copyOf(loadAllFromDb().values());
    }

    default boolean banExists(UUID uuid) {
        return fromKey(uuid.toString()).isPresent();
    }

    default boolean isBanned(String ip) {
        for (IPBanModel ban : loadAllFromDb().values()) {
            if (ban.getBannedIp().equals(ip)) {
                return ban.isBanActive();
            }
        }

        return false;
    }

    default void unban(String ip) {
        for (IPBanModel model : loadAllFromDb().values()) {
            if (model.getBannedIp().equals(ip)) {
                if (model.isBanActive()) {
                    model.setBanActive(false);
                    
                }
            }
        };
    }
}