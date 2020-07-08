package net.nighthawkempires.core.enchantment;

import com.google.common.collect.Lists;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class EnchantmentManager {

    private List<Enchantment> enchants;

    public EnchantmentManager() {
        this.enchants = Lists.newArrayList();
    }

    public void registerEnchantment(Enchantment enchantment) {
        unregisterEnchantment(enchantment);
        boolean registered;
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);

            Enchantment.registerEnchantment(enchantment);
            registered = true;
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }

        if (registered)
            this.enchants.add(enchantment);
    }

    public void unregisterEnchantment(Enchantment enchantment) {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");
            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            byKey.remove(enchantment.getKey());

            Field nameField = Enchantment.class.getDeclaredField("byName");
            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            byName.remove(enchantment.getName());
        } catch (Exception ignored) {}
    }

    public void unregisterEnchants() {
        for (Enchantment enchantment : enchants) {
            unregisterEnchantment(enchantment);
        }
    }

    public Enchantment getEnchantment(String name) {
        for (Enchantment enchantment : enchants) {
            if (enchantment.getName().equalsIgnoreCase(name)) {
                return enchantment;
            }
        }
        return null;
    }

    public Enchantment getEnchantment(Plugin plugin, String name) {
        for (Enchantment enchantment : enchants) {
            if (enchantment.getKey().getNamespace().equals(plugin.getName().toLowerCase())) {
                if (enchantment.getKey().getKey().equals(name.toLowerCase())) {
                    return enchantment;
                }
            }
        }
        return null;
    }
}