package net.nighthawkempires.core.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material type) {
        item = new ItemStack(type);
        meta = item.getItemMeta();
    }

    public ItemBuilder(MaterialData data) {
        item = new ItemStack(data.getItemType());
        item.setData(data);
        meta = item.getItemMeta();
    }

    public ItemBuilder type(Material type) {
        item.setType(type);
        return this;
    }

    public ItemBuilder data(MaterialData data) {
        item.setData(data);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder durability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchant, int level) {
        item.addEnchantment(enchant, level);
        return this;
    }

    public ItemBuilder unsafeEnchant(Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder displayName(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    // -- BUILD -- //

    public ItemStack build() {
        ItemStack item = this.item.clone();
        item.setItemMeta(meta);
        return item;
    }
}
