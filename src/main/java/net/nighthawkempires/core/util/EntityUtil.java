package net.nighthawkempires.core.util;

import com.google.common.collect.Lists;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;

import static org.bukkit.entity.EntityType.*;

public class EntityUtil {

    public static final List<EntityType> unholy = Lists.newArrayList(WITHER_SKELETON, STRAY, HUSK, ZOMBIE_VILLAGER, ZOMBIE, ZOMBIE_HORSE,
            CREEPER, SKELETON, SKELETON_HORSE, ENDERMAN, ENDERMITE, BLAZE, MAGMA_CUBE, ENDER_DRAGON, WITCH,
            GUARDIAN, DROWNED, HOGLIN, PIGLIN, PIGLIN_BRUTE, ZOMBIFIED_PIGLIN);

    public static boolean isUnholy(Entity entity) {
        return unholy.contains(entity.getType());
    }

    public static boolean isInteractable(Entity entity) {
        List<EntityType> entityTypes = Lists.newArrayList(
                ITEM_FRAME, ARMOR_STAND
        );

        return entityTypes.contains(entity.getType());
    }
}