package net.nighthawkempires.core.settings.registry;

import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Registry;
import net.nighthawkempires.core.settings.AnnouncementsModel;
import net.nighthawkempires.core.settings.ConfigModel;
import net.nighthawkempires.core.settings.MessagesModel;
import net.nighthawkempires.core.settings.SettingsModel;

import java.util.Map;

public interface SettingsRegistry extends Registry<SettingsModel> {

    default SettingsModel fromDataSection(String key, DataSection data) {
        if (key.equalsIgnoreCase("config"))
            return new ConfigModel(key, data);
        else if (key.equalsIgnoreCase("messages"))
            return new MessagesModel(key, data);
        else if (key.equalsIgnoreCase("announcements"))
            return new AnnouncementsModel(key, data);

        return null; //new SettingsModel(stringKey, data);
    }

    default ConfigModel getConfig() {
        return (ConfigModel) fromKey("config").orElseGet(() -> register(new ConfigModel()));
    }

    default MessagesModel getMessages() {
        return (MessagesModel) fromKey("messages").orElseGet(() -> register(new MessagesModel()));
    }

    default AnnouncementsModel getAnnouncements() {
        return (AnnouncementsModel) fromKey("announcements").orElseGet(() -> register(new AnnouncementsModel()));
    }

    @Deprecated
    Map<String, SettingsModel> getRegisteredData();

    default boolean configExists() {
        return fromKey("config").isPresent();
    }
}
