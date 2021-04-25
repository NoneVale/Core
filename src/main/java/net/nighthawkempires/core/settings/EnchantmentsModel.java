package net.nighthawkempires.core.settings;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static net.nighthawkempires.core.CorePlugin.getSettingsRegistry;
import static org.bukkit.enchantments.Enchantment.*;

public class EnchantmentsModel extends SettingsModel {

    private String key;

    private Map<String, Enchantment> enchantmentMap;
    private Map<Enchantment, String> enchantmentNames;
    private Map<Enchantment, Integer> enchantmentCostPerLevel;

    List<Enchantment> armorEnchants = Lists.newArrayList(PROTECTION_ENVIRONMENTAL, PROTECTION_EXPLOSIONS,
            PROTECTION_FIRE, PROTECTION_PROJECTILE, BINDING_CURSE, VANISHING_CURSE, MENDING, DURABILITY, THORNS);
    List<Enchantment> armorHelmetEnchants = Lists.newArrayList(OXYGEN, WATER_WORKER);
    List<Enchantment> armorBootsEnchants = Lists.newArrayList(DEPTH_STRIDER, PROTECTION_FALL, SOUL_SPEED, FROST_WALKER);
    List<Enchantment> swordEnchants = Lists.newArrayList(MENDING, DURABILITY, FIRE_ASPECT, LOOT_BONUS_MOBS, KNOCKBACK,
            SWEEPING_EDGE, DAMAGE_ALL, DAMAGE_ARTHROPODS, DAMAGE_UNDEAD, VANISHING_CURSE);
    List<Enchantment> axeEnchants = Lists.newArrayList(FIRE_ASPECT, LOOT_BONUS_MOBS, KNOCKBACK,
            DAMAGE_ALL, DAMAGE_ARTHROPODS, DAMAGE_UNDEAD);
    List<Enchantment> toolEnchants = Lists.newArrayList(MENDING, DURABILITY, LOOT_BONUS_BLOCKS, DIG_SPEED, SILK_TOUCH,
            VANISHING_CURSE);
    List<Enchantment> bowEnchants = Lists.newArrayList(ARROW_DAMAGE, ARROW_FIRE, ARROW_INFINITE, ARROW_KNOCKBACK,
            DURABILITY, MENDING, VANISHING_CURSE);
    List<Enchantment> fishingRodEnchants = Lists.newArrayList(MENDING, DURABILITY, LURE, LUCK, VANISHING_CURSE);
    List<Enchantment> tridentEnchants = Lists.newArrayList(MENDING, DURABILITY, IMPALING, CHANNELING, LOYALTY, RIPTIDE, DAMAGE_UNDEAD,
            DAMAGE_ARTHROPODS, DAMAGE_ALL, KNOCKBACK, FIRE_ASPECT, VANISHING_CURSE, LOOT_BONUS_MOBS);
    List<Enchantment> crossbowEnchants = Lists.newArrayList(MENDING, DURABILITY, QUICK_CHARGE, MULTISHOT, PIERCING, VANISHING_CURSE);
    List<Enchantment> shearEnchants = Lists.newArrayList(MENDING, DURABILITY, DIG_SPEED, VANISHING_CURSE);
    List<Enchantment> miscEnchants = Lists.newArrayList(MENDING, DURABILITY, VANISHING_CURSE);

    public EnchantmentsModel() {
        this.key = "enchantments";

        this.enchantmentMap = Maps.newHashMap();
        for (Enchantment e : Enchantment.values()) {
            enchantmentMap.put(e.getName(), e);
        }

        this.enchantmentNames = Maps.newHashMap();
        this.enchantmentNames.put(Enchantment.ARROW_DAMAGE, "Power");
        this.enchantmentNames.put(Enchantment.ARROW_FIRE, "Flame");
        this.enchantmentNames.put(Enchantment.ARROW_INFINITE, "Infinity");
        this.enchantmentNames.put(Enchantment.ARROW_KNOCKBACK, "Punch");
        this.enchantmentNames.put(Enchantment.BINDING_CURSE, "Curse of Binding");
        this.enchantmentNames.put(Enchantment.CHANNELING, "Channeling");
        this.enchantmentNames.put(Enchantment.DAMAGE_ALL, "Sharpness");
        this.enchantmentNames.put(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods");
        this.enchantmentNames.put(Enchantment.DAMAGE_UNDEAD, "Smite");
        this.enchantmentNames.put(Enchantment.DEPTH_STRIDER, "Depth Strider");
        this.enchantmentNames.put(Enchantment.DIG_SPEED, "Efficiency");
        this.enchantmentNames.put(Enchantment.DURABILITY, "Unbreaking");
        this.enchantmentNames.put(Enchantment.FIRE_ASPECT, "Fire Aspect");
        this.enchantmentNames.put(Enchantment.FROST_WALKER, "Frost Walker");
        this.enchantmentNames.put(Enchantment.IMPALING, "Impaling");
        this.enchantmentNames.put(Enchantment.KNOCKBACK, "Knockback");
        this.enchantmentNames.put(Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
        this.enchantmentNames.put(Enchantment.LOOT_BONUS_MOBS, "Looting");
        this.enchantmentNames.put(Enchantment.LOYALTY, "Loyalty");
        this.enchantmentNames.put(Enchantment.LUCK, "Luck of the Sea");
        this.enchantmentNames.put(Enchantment.LURE, "Lure");
        this.enchantmentNames.put(Enchantment.MENDING, "Mending");
        this.enchantmentNames.put(Enchantment.MULTISHOT, "Multishot");
        this.enchantmentNames.put(Enchantment.OXYGEN, "Respiration");
        this.enchantmentNames.put(Enchantment.PIERCING, "Piercing");
        this.enchantmentNames.put(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection");
        this.enchantmentNames.put(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection");
        this.enchantmentNames.put(Enchantment.PROTECTION_FALL, "Feather Falling");
        this.enchantmentNames.put(Enchantment.PROTECTION_FIRE, "Fire Protection");
        this.enchantmentNames.put(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection");
        this.enchantmentNames.put(Enchantment.QUICK_CHARGE, "Quick Charge");
        this.enchantmentNames.put(Enchantment.RIPTIDE, "Riptide");
        this.enchantmentNames.put(Enchantment.SILK_TOUCH, "Silk Touch");
        this.enchantmentNames.put(Enchantment.SOUL_SPEED, "Soul Speed");
        this.enchantmentNames.put(Enchantment.SWEEPING_EDGE, "Sweeping Edge");
        this.enchantmentNames.put(Enchantment.THORNS, "Thorns");
        this.enchantmentNames.put(Enchantment.VANISHING_CURSE, "Curse of Vanishing");
        this.enchantmentNames.put(Enchantment.WATER_WORKER, "Aqua Affinity");

        this.enchantmentCostPerLevel = Maps.newHashMap();
        for (Enchantment enchantment : Enchantment.values()) {
            enchantmentCostPerLevel.put(enchantment, 4);
        }
    }

    public EnchantmentsModel(String key, DataSection data) {

        this.enchantmentMap = Maps.newHashMap();
        DataSection dataSection = data.getSectionNullable("enchantment_aliases");
        for (String s : dataSection.keySet())
            enchantmentMap.put(s, Enchantment.getByName(dataSection.getString(s)));

        this.enchantmentNames = Maps.newHashMap();
        if (!data.isSet("enchantment_names")) {
            this.enchantmentNames.put(Enchantment.ARROW_DAMAGE, "Power");
            this.enchantmentNames.put(Enchantment.ARROW_FIRE, "Flame");
            this.enchantmentNames.put(Enchantment.ARROW_INFINITE, "Infinity");
            this.enchantmentNames.put(Enchantment.ARROW_KNOCKBACK, "Punch");
            this.enchantmentNames.put(Enchantment.BINDING_CURSE, "Curse of Binding");
            this.enchantmentNames.put(Enchantment.CHANNELING, "Channeling");
            this.enchantmentNames.put(Enchantment.DAMAGE_ALL, "Sharpness");
            this.enchantmentNames.put(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods");
            this.enchantmentNames.put(Enchantment.DAMAGE_UNDEAD, "Smite");
            this.enchantmentNames.put(Enchantment.DEPTH_STRIDER, "Depth Strider");
            this.enchantmentNames.put(Enchantment.DIG_SPEED, "Efficiency");
            this.enchantmentNames.put(Enchantment.DURABILITY, "Unbreaking");
            this.enchantmentNames.put(Enchantment.FIRE_ASPECT, "Fire Aspect");
            this.enchantmentNames.put(Enchantment.FROST_WALKER, "Frost Walker");
            this.enchantmentNames.put(Enchantment.IMPALING, "Impaling");
            this.enchantmentNames.put(Enchantment.KNOCKBACK, "Knockback");
            this.enchantmentNames.put(Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
            this.enchantmentNames.put(Enchantment.LOOT_BONUS_MOBS, "Looting");
            this.enchantmentNames.put(Enchantment.LOYALTY, "Loyalty");
            this.enchantmentNames.put(Enchantment.LUCK, "Luck of the Sea");
            this.enchantmentNames.put(Enchantment.LURE, "Lure");
            this.enchantmentNames.put(Enchantment.MENDING, "Mending");
            this.enchantmentNames.put(Enchantment.MULTISHOT, "Multishot");
            this.enchantmentNames.put(Enchantment.OXYGEN, "Respiration");
            this.enchantmentNames.put(Enchantment.PIERCING, "Piercing");
            this.enchantmentNames.put(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection");
            this.enchantmentNames.put(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection");
            this.enchantmentNames.put(Enchantment.PROTECTION_FALL, "Feather Falling");
            this.enchantmentNames.put(Enchantment.PROTECTION_FIRE, "Fire Protection");
            this.enchantmentNames.put(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection");
            this.enchantmentNames.put(Enchantment.QUICK_CHARGE, "Quick Charge");
            this.enchantmentNames.put(Enchantment.RIPTIDE, "Riptide");
            this.enchantmentNames.put(Enchantment.SILK_TOUCH, "Silk Touch");
            this.enchantmentNames.put(Enchantment.SOUL_SPEED, "Soul Speed");
            this.enchantmentNames.put(Enchantment.SWEEPING_EDGE, "Sweeping Edge");
            this.enchantmentNames.put(Enchantment.THORNS, "Thorns");
            this.enchantmentNames.put(Enchantment.VANISHING_CURSE, "Curse of Vanishing");
            this.enchantmentNames.put(Enchantment.WATER_WORKER, "Aqua Affinity");
        } else {
            DataSection section = data.getSectionNullable("enchantment_names");
            for (String s : section.keySet())
                enchantmentNames.put(Enchantment.getByName(s), section.getString(s));
        }

        this.enchantmentCostPerLevel = Maps.newHashMap();
        DataSection costSection = data.getSectionNullable("enchantment_cost_per_level");
        for (String s : costSection.keySet())
            enchantmentCostPerLevel.put(Enchantment.getByName(s), costSection.getInt(s));
    }

    public boolean isEnchantment(String name) {
        return this.enchantmentMap.containsKey(name);
    }

    public Enchantment getEnchantment(String name) {
        if (!isEnchantment(name)) return null;
        return this.enchantmentMap.get(name);
    }

    public void addEnchantment(String name, Enchantment enchantment) {
        enchantmentMap.put(name, enchantment);
        getSettingsRegistry().register(this);
    }

    public void removeEnchantment(String name, Enchantment enchantment) {
        enchantmentMap.put(name, enchantment);
        getSettingsRegistry().register(this);
    }

    public String getName(Enchantment enchantment) {
        if (!this.enchantmentNames.containsKey(enchantment)) return "";

        return this.enchantmentNames.get(enchantment);
    }

    public Enchantment getByName(String name) {
        for (Enchantment e : enchantmentNames.keySet()) {
            if (enchantmentNames.get(e).equalsIgnoreCase(enumName(name))) {
                return e;
            }
        }
        return null;
    }

    public int getCost(Enchantment e, int level) {
        if (!this.enchantmentNames.containsKey(e)) return 0;

        int costPerLevel = this.enchantmentCostPerLevel.get(e);
        return costPerLevel * level;
    }

    public boolean canEnchant(ItemStack itemStack, Enchantment enchantment) {
        switch (itemStack.getType()) {
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case GOLDEN_HELMET:
            case DIAMOND_HELMET:
            case NETHERITE_HELMET:
            case TURTLE_HELMET:
                if (armorEnchants.contains(enchantment) || armorHelmetEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case LEATHER_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case IRON_CHESTPLATE:
            case GOLDEN_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case NETHERITE_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case IRON_LEGGINGS:
            case GOLDEN_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case NETHERITE_LEGGINGS:
                if (armorEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case GOLDEN_BOOTS:
            case DIAMOND_BOOTS:
            case NETHERITE_BOOTS:
                if (armorEnchants.contains(enchantment) || armorBootsEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case WOODEN_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLDEN_SWORD:
            case DIAMOND_SWORD:
            case NETHERITE_SWORD:
                if (swordEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                if (axeEnchants.contains(enchantment) || toolEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:
            case WOODEN_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLDEN_HOE:
            case DIAMOND_HOE:
            case NETHERITE_HOE:
            case WOODEN_SHOVEL:
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case GOLDEN_SHOVEL:
            case DIAMOND_SHOVEL:
            case NETHERITE_SHOVEL:
                if (toolEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case BOW:
                if (bowEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case FISHING_ROD:
                if (fishingRodEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case TRIDENT:
                if (tridentEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case CROSSBOW:
                if (crossbowEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case SHEARS:
                if (shearEnchants.contains(enchantment)) {
                    return true;
                }
                break;
            case SHIELD:
            case ELYTRA:
            case FLINT_AND_STEEL:
            case CARROT_ON_A_STICK:
            case WARPED_FUNGUS_ON_A_STICK:
                if (miscEnchants.contains(enchantment)) {
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        Map<String, Object> enchantmentAliases = Maps.newHashMap();
        for (String s : this.enchantmentMap.keySet()) {
            enchantmentAliases.put(s.toUpperCase().replaceAll(" ", "_"), this.enchantmentMap.get(s).getName());
        }
        map.put("enchantment_aliases", enchantmentAliases);

        Map<String, Object> enchantmentNames = Maps.newHashMap();
        for (Enchantment e : this.enchantmentNames.keySet()) {
            enchantmentNames.put(e.getName().toUpperCase().replaceAll(" ", "_"), this.enchantmentNames.get(e));
        }
        map.put("enchantment_names", enchantmentNames);

        Map<String, Object> enchantmentCost = Maps.newHashMap();
        for (Enchantment e : this.enchantmentCostPerLevel.keySet()) {
            enchantmentCost.put(e.getName().toUpperCase().replaceAll(" ", "_"), this.enchantmentCostPerLevel.get(e));
        }
        map.put("enchantment_cost_per_level", enchantmentCost);

        return map;
    }

    private String enumName(String s) {
        if (s.contains("_")) {
            String[] split = s.split("_");

            StringBuilder matName = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                matName.append(enumName(split[i]));

                if (i < split.length - 1) {
                    matName.append(" ");
                }
            }

            return matName.toString();
        }

        return s.toUpperCase().substring(0, 1) + s.substring(1).toLowerCase();
    }
}