package net.nighthawkempires.core.util;

import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static org.bukkit.potion.PotionEffectType.*;

public class PotionUtil {

    public static boolean isNegativeEffect(PotionEffectType type) {
        List<PotionEffectType> effectList = Lists.newArrayList(POISON, BAD_OMEN, BLINDNESS, SLOW, SLOW_DIGGING, HARM, CONFUSION, HUNGER, WEAKNESS, WITHER, UNLUCK);
        return effectList.contains(type);
    }

    public static boolean isNegativeEffect(PotionEffect effect) {
        return isNegativeEffect(effect.getType());
    }

    public static int getPotionDuration(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case POTION:
            case SPLASH_POTION:
            case LINGERING_POTION:
                PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                PotionData potionData = potionMeta.getBasePotionData();

                int amplifier = 0;
                switch (potionData.getType()) {
                    case NIGHT_VISION:
                    case WATER_BREATHING:
                    case FIRE_RESISTANCE:
                    case INVISIBILITY:
                    case JUMP:
                    case SPEED:
                    case STRENGTH:
                    case SLOWNESS:
                    case WEAKNESS:
                    case POISON:
                    case REGEN:
                    case LUCK:
                    case SLOW_FALLING:
                        if (potionData.getType().isExtendable() && potionData.isExtended()) {
                            amplifier += 1;
                        }
                        break;
                    case TURTLE_MASTER:
                        amplifier = 3;
                        if (potionData.getType().isExtendable() && potionData.isExtended()) {
                            amplifier = 5;
                        }
                        break;
                }
            default: return 0;
        }
    }

    public static int getPotionAmplifier(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case POTION:
            case SPLASH_POTION:
                PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                PotionData potionData = potionMeta.getBasePotionData();

                int duration = 0;
                switch (potionData.getType()) {
                    case NIGHT_VISION:
                    case WATER_BREATHING:
                    case FIRE_RESISTANCE:
                    case INVISIBILITY:
                        duration = 3600;
                        if (potionData.isExtended()) {
                            duration = 9600;
                        }
                        break;
                    case JUMP:
                    case SPEED:
                    case STRENGTH:
                        duration = 3600;
                        if (potionData.isExtended()) {
                            duration = 9600;
                        } else if (potionData.isUpgraded()) {
                            duration = 1800;
                        }
                        break;
                    case SLOWNESS:
                    case WEAKNESS:
                        duration = 1800;
                        if (potionData.isExtended()) {
                            duration = 4800;
                        } else if (potionData.isUpgraded()) {
                            duration = 400;
                        }
                        break;
                    case TURTLE_MASTER:
                        duration = 400;
                        if (potionData.isExtended()) {
                            duration = 800;
                        }
                        break;
                    case INSTANT_HEAL:
                    case INSTANT_DAMAGE:
                        break;
                    case POISON:
                    case REGEN:
                        duration = 900;
                        if (potionData.isExtended()) {
                            duration = 1800;
                        } else if (potionData.isUpgraded()) {
                            duration = 420;
                        }
                        break;
                    case LUCK:
                        duration = 6000;
                        break;
                    case SLOW_FALLING:
                        duration = 1800;
                        if (potionData.isExtended()) {
                            duration = 4800;
                        }
                        break;
                }
            case LINGERING_POTION:
                potionMeta = (PotionMeta) itemStack.getItemMeta();
                potionData = potionMeta.getBasePotionData();

                duration = 0;
                switch (potionData.getType()) {
                    case NIGHT_VISION:
                    case WATER_BREATHING:
                    case FIRE_RESISTANCE:
                    case INVISIBILITY:
                        duration = 900;
                        if (potionData.isExtended()) {
                            duration = 2400;
                        }
                        break;
                    case JUMP:
                    case SPEED:
                    case STRENGTH:
                        duration = 900;
                        if (potionData.isExtended()) {
                            duration = 2400;
                        } else if (potionData.isUpgraded()) {
                            duration = 440;
                        }
                        break;
                    case SLOWNESS:
                    case WEAKNESS:
                        duration = 440;
                        if (potionData.isExtended()) {
                            duration = 1200;
                        } else if (potionData.isUpgraded()) {
                            duration = 100;
                        }
                        break;
                    case TURTLE_MASTER:
                        duration = 100;
                        if (potionData.isExtended()) {
                            duration = 200;
                        }
                        break;
                    case POISON:
                    case REGEN:
                        duration = 220;
                        if (potionData.isExtended()) {
                            duration = 440;
                        } else if (potionData.isUpgraded()) {
                            duration = 100;
                        }
                        break;
                    case LUCK:
                        duration = 1500;
                        break;
                    case SLOW_FALLING:
                        duration = 440;
                        if (potionData.isExtended()) {
                            duration = 1200;
                        }
                        break;
                }
            default: return 0;
        }
    }
}
