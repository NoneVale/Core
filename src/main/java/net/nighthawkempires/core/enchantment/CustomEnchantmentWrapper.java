package net.nighthawkempires.core.enchantment;

import net.nighthawkempires.core.CorePlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;

public abstract class CustomEnchantmentWrapper extends Enchantment {

    public CustomEnchantmentWrapper(String name) {
        super(new NamespacedKey(CorePlugin.getPlugin(), name));
    }

    public CustomEnchantmentWrapper(Plugin plugin, String name) {
        super(new NamespacedKey(plugin, name));
    }
}