package net.nighthawkempires.core.settings.registry;

import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Registry;
import net.nighthawkempires.core.settings.*;

import java.util.Map;

public interface SettingsRegistry extends Registry<SettingsModel> {

    default SettingsModel fromDataSection(String key, DataSection data) {
        if (key.equalsIgnoreCase("config"))
            return new ConfigModel(key, data);
        else if (key.equalsIgnoreCase("messages"))
            return new MessagesModel(key, data);
        else if (key.equalsIgnoreCase("announcements"))
            return new AnnouncementsModel(key, data);
        else if (key.equalsIgnoreCase("materials"))
            return new MaterialsModel(key, data);
        else if (key.equalsIgnoreCase("enchantments"))
            return new EnchantmentsModel(key, data);
        else if (key.equals("cooldowns"))
            return new CooldownModel(data);
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

    default EnchantmentsModel getEnchantments() {
        return (EnchantmentsModel) fromKey("enchantments").orElseGet(() -> register(new EnchantmentsModel()));
    }

    default MaterialsModel getMaterials() {
        return (MaterialsModel) fromKey("materials").orElseGet(() -> register(new MaterialsModel()));
    }

    default CooldownModel getCooldowns() {
        return (CooldownModel) fromKey("cooldowns").orElseGet(() -> register(new CooldownModel()));
    }

    @Deprecated
    Map<String, SettingsModel> getRegisteredData();

    default boolean configExists() {
        return fromKey("config").isPresent();
    }
}
