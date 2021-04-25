package net.nighthawkempires.core.settings;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.datasection.DataSection;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static net.nighthawkempires.core.CorePlugin.*;
import static org.bukkit.Material.*;

public class MaterialsModel extends SettingsModel {

    private String key;

    private Map<String, Material> materialMap;

    public MaterialsModel() {
        this.key = "materials";

        this.materialMap = Maps.newHashMap();
        for (Material m : values()) {
            materialMap.put(m.name(), m);
        }
    }

    public MaterialsModel(String key, DataSection data) {
        this.materialMap = Maps.newHashMap();
        for (String s : data.keySet())
            materialMap.put(s, valueOf(data.getString(s).toUpperCase()));
    }

    public Material getMaterial(String name) {
        return materialMap.get(name.toUpperCase());
    }

    public void addMaterial(String name, Material material) {
        materialMap.put(name, material);
        getSettingsRegistry().register(this);
    }

    public void removeMaterial(String name) {
        materialMap.remove(name);
        getSettingsRegistry().register(this);
    }

    public boolean isButton(Material material) {
        List<Material> buttons = Lists.newArrayList(
                ACACIA_BUTTON, BIRCH_BUTTON, DARK_OAK_BUTTON,
                JUNGLE_BUTTON, OAK_BUTTON, SPRUCE_BUTTON, STONE_BUTTON
        );

        return buttons.contains(material);
    }

    public boolean isDoor(Material material) {
        List<Material> doors = Lists.newArrayList(
                ACACIA_DOOR, BIRCH_DOOR, DARK_OAK_DOOR,
                IRON_DOOR, JUNGLE_DOOR, OAK_DOOR, SPRUCE_DOOR,
                ACACIA_TRAPDOOR, BIRCH_TRAPDOOR, DARK_OAK_TRAPDOOR,
                IRON_TRAPDOOR, JUNGLE_TRAPDOOR, OAK_TRAPDOOR, SPRUCE_TRAPDOOR
        );

        return doors.contains(material);
    }

    public boolean isChest(Material material) {
        List<Material> chests = Lists.newArrayList(
                CHEST, CHEST_MINECART, SHULKER_BOX, BLACK_SHULKER_BOX, BLUE_SHULKER_BOX,
                BROWN_SHULKER_BOX, CYAN_SHULKER_BOX, GRAY_SHULKER_BOX, GREEN_SHULKER_BOX,
                LIGHT_BLUE_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX, LIME_SHULKER_BOX, MAGENTA_SHULKER_BOX,
                ORANGE_SHULKER_BOX, PINK_SHULKER_BOX, PURPLE_SHULKER_BOX, RED_SHULKER_BOX, WHITE_SHULKER_BOX,
                YELLOW_SHULKER_BOX
        );
        return chests.contains(material);
    }

    public boolean isSpawnEgg(Material material) {
        List<Material> spawnEggs = Lists.newArrayList(
                BAT_SPAWN_EGG, BEE_SPAWN_EGG, BLAZE_SPAWN_EGG, CAT_SPAWN_EGG, CAVE_SPIDER_SPAWN_EGG, CHICKEN_SPAWN_EGG,
                COD_SPAWN_EGG, COW_SPAWN_EGG, CREEPER_SPAWN_EGG, DOLPHIN_SPAWN_EGG, DONKEY_SPAWN_EGG, DROWNED_SPAWN_EGG,
                ELDER_GUARDIAN_SPAWN_EGG, ENDERMAN_SPAWN_EGG, ENDERMITE_SPAWN_EGG, EVOKER_SPAWN_EGG, FOX_SPAWN_EGG,
                GHAST_SPAWN_EGG, GUARDIAN_SPAWN_EGG, HORSE_SPAWN_EGG, HUSK_SPAWN_EGG, LLAMA_SPAWN_EGG, MAGMA_CUBE_SPAWN_EGG,
                MOOSHROOM_SPAWN_EGG, MULE_SPAWN_EGG, OCELOT_SPAWN_EGG, PANDA_SPAWN_EGG, PARROT_SPAWN_EGG, PHANTOM_SPAWN_EGG,
                PIG_SPAWN_EGG, PILLAGER_SPAWN_EGG, POLAR_BEAR_SPAWN_EGG, PUFFERFISH_SPAWN_EGG, RABBIT_SPAWN_EGG,
                RAVAGER_SPAWN_EGG, SALMON_SPAWN_EGG, SHEEP_SPAWN_EGG, SHULKER_SPAWN_EGG, SILVERFISH_SPAWN_EGG,
                SKELETON_HORSE_SPAWN_EGG, SKELETON_SPAWN_EGG, SLIME_SPAWN_EGG, SPIDER_SPAWN_EGG, SQUID_SPAWN_EGG,
                STRAY_SPAWN_EGG, TRADER_LLAMA_SPAWN_EGG, TROPICAL_FISH_SPAWN_EGG, TURTLE_SPAWN_EGG, VEX_SPAWN_EGG,
                VILLAGER_SPAWN_EGG, VINDICATOR_SPAWN_EGG, WANDERING_TRADER_SPAWN_EGG, WITCH_SPAWN_EGG, WITHER_SKELETON_SPAWN_EGG,
                WOLF_SPAWN_EGG, ZOMBIE_HORSE_SPAWN_EGG, ZOMBIFIED_PIGLIN_SPAWN_EGG, ZOMBIE_SPAWN_EGG, ZOMBIE_VILLAGER_SPAWN_EGG
        );

        return spawnEggs.contains(material);
    }

    public EntityType getEntitySpawned(Material material) {
        String name = material.name().replaceFirst("_SPAWN_EGG", "");

        return EntityType.valueOf(name);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        for (String s : this.materialMap.keySet()) {
            map.put(s.toUpperCase(), this.materialMap.get(s));
        }
        return map;
    }
}
