package net.nighthawkempires.core.util;

import com.google.common.collect.Lists;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;

import static org.bukkit.entity.EntityType.*;

public class EntityUtil {

    public static boolean isInteractable(Entity entity) {
        List<EntityType> entityTypes = Lists.newArrayList(
                ITEM_FRAME, ARMOR_STAND
        );

        return entityTypes.contains(entity.getType());
    }
}
