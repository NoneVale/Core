package net.nighthawkempires.core.util;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Material.*;

public class ItemUtil {

    private static List<Material> interactable = Lists.newArrayList(
            CHEST, CRAFTING_TABLE, FURNACE, JUKEBOX, ENCHANTING_TABLE, ENDER_CHEST,
            ANVIL, CHIPPED_ANVIL, DAMAGED_ANVIL, SHULKER_BOX, WHITE_SHULKER_BOX, ORANGE_SHULKER_BOX,
            MAGENTA_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX, YELLOW_SHULKER_BOX, LIME_SHULKER_BOX, PINK_SHULKER_BOX,
            GRAY_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, CYAN_SHULKER_BOX, PURPLE_SHULKER_BOX, BLUE_SHULKER_BOX,
            BROWN_SHULKER_BOX, GREEN_SHULKER_BOX, RED_SHULKER_BOX, BLACK_SHULKER_BOX, ITEM_FRAME, ARMOR_STAND,
            FLOWER_POT, LOOM, BARREL, SMOKER, BLAST_FURNACE, CARTOGRAPHY_TABLE, FLETCHING_TABLE, GRINDSTONE,
            SMITHING_TABLE, STONECUTTER, BELL, CAMPFIRE, DISPENSER, NOTE_BLOCK, LEVER, STONE_PRESSURE_PLATE,
            OAK_PRESSURE_PLATE, SPRUCE_PRESSURE_PLATE, BIRCH_PRESSURE_PLATE, JUNGLE_PRESSURE_PLATE, ACACIA_PRESSURE_PLATE,
            DARK_OAK_PRESSURE_PLATE, OAK_TRAPDOOR, SPRUCE_TRAPDOOR, BIRCH_TRAPDOOR, JUNGLE_TRAPDOOR, ACACIA_TRAPDOOR,
            DARK_OAK_TRAPDOOR, OAK_FENCE_GATE, SPRUCE_FENCE_GATE, BIRCH_FENCE_GATE, JUNGLE_FENCE_GATE, ACACIA_FENCE_GATE,
            DARK_OAK_FENCE_GATE, OAK_BUTTON, SPRUCE_BUTTON, BIRCH_BUTTON, JUNGLE_BUTTON, ACACIA_BUTTON, DARK_OAK_BUTTON,
            LIGHT_WEIGHTED_PRESSURE_PLATE, HEAVY_WEIGHTED_PRESSURE_PLATE, HOPPER, DROPPER, OAK_DOOR, SPRUCE_DOOR,
            BIRCH_DOOR, JUNGLE_DOOR, ACACIA_DOOR, DARK_OAK_DOOR, REPEATER, COMPARATOR, LECTERN, CHEST_MINECART,
            FURNACE_MINECART, HOPPER_MINECART, COMPOSTER, BREWING_STAND, CAULDRON, TRAPPED_CHEST, TRIPWIRE_HOOK,
            TRIPWIRE
    );

    public static ItemStack getItemStack(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack getPlayerHead(UUID uuid) {
        ItemStack itemStack = new ItemStack(PLAYER_HEAD);

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public static ItemStack createSkull(String name, String url) {
        ItemStack head = new ItemStack(PLAYER_HEAD, 1, (short) 3);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }

        headMeta.setDisplayName(name);
        head.setItemMeta(headMeta);
        return head;
    }

    public boolean isTool(ItemStack itemStack) {
        List<Material> tools = Lists.newArrayList(
                WOODEN_SWORD, WOODEN_SHOVEL, WOODEN_AXE, WOODEN_HOE, WOODEN_PICKAXE,
                STONE_SWORD, STONE_SHOVEL, STONE_AXE, STONE_HOE, STONE_PICKAXE,
                IRON_SWORD, IRON_SHOVEL, IRON_AXE, IRON_HOE, IRON_PICKAXE,
                GOLDEN_SWORD, GOLDEN_SHOVEL, GOLDEN_AXE, GOLDEN_HOE, GOLDEN_PICKAXE,
                DIAMOND_SWORD, DIAMOND_SHOVEL, DIAMOND_AXE, DIAMOND_HOE, DIAMOND_PICKAXE,
                SHEARS, FISHING_ROD, FLINT_AND_STEEL, TURTLE_HELMET
        );

        return tools.contains(itemStack.getType());
    }

    public static boolean isInteractable(ItemStack itemStack) {
        return interactable.contains(itemStack.getType());
    }

    public static boolean isInteractable(Material material) {
        return interactable.contains(material);
    }

    public static boolean isInteractable(ItemStack itemStack, Material... exclude) {
        for (Material material : exclude) {
            if (interactable.contains(itemStack.getType()) && itemStack.getType() == material) return false;
        }
        return interactable.contains(itemStack.getType());
    }

    public static boolean isInteractable(Material material, Material... exclude) {
        for (Material materials : exclude) {
            if (interactable.contains(material) && material == materials) return false;
        }
        return interactable.contains(material);
    }


}