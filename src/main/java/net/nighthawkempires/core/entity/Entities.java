package net.nighthawkempires.core.entity;

import org.bukkit.entity.EntityType;

public class Entities {

    public enum PassiveEntities {
        BEE(EntityType.BEE), BAT(EntityType.BAT), CAT(EntityType.CAT), CHICKEN(EntityType.CHICKEN), COD(EntityType.COD),
        COW(EntityType.COW), DOLPHIN(EntityType.DOLPHIN), DONKEY(EntityType.DONKEY), FOX(EntityType.FOX),
        HORSE(EntityType.HORSE), IRON_GOLEM(EntityType.IRON_GOLEM), LLAMA(EntityType.LLAMA), MUSHROOM_COW(EntityType.MUSHROOM_COW),
        MULE(EntityType.MULE), OCELOT(EntityType.OCELOT), PANDA(EntityType.PANDA), PARROT(EntityType.PARROT),
        PIG(EntityType.PIG), POLAR_BEAR(EntityType.POLAR_BEAR), PUFFERFISH(EntityType.PUFFERFISH), RABBIT(EntityType.RABBIT),
        SALMON(EntityType.SALMON), SHEEP(EntityType.SHEEP), SKELETON_HORSE(EntityType.SKELETON_HORSE),
        SNOWMAN(EntityType.SNOWBALL), SQUID(EntityType.SQUID), TRADER_LLAMA(EntityType.TRADER_LLAMA), TROPICAL_FISH(EntityType.TROPICAL_FISH),
        TURTLE(EntityType.TURTLE), VILLAGER(EntityType.VILLAGER), WANDERING_TRADER(EntityType.WANDERING_TRADER),
        WOLF(EntityType.WOLF);

        private EntityType entityType;

        PassiveEntities(EntityType entityType) {
            this.entityType = entityType;
        }

        public EntityType getEntityType() {
            return entityType;
        }
    }

    public enum HostileEntities {
        BLAZE(EntityType.BLAZE), CAVE_SPIDER(EntityType.CAVE_SPIDER), CREEPER(EntityType.CREEPER), DROWNED(EntityType.DROWNED),
        ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN), ENDERMAN(EntityType.ENDERMAN), ENDERMITE(EntityType.ENDERMITE),
        ENDER_DRAGON(EntityType.ENDER_DRAGON), EVOKER(EntityType.EVOKER), GHAST(EntityType.GHAST), GIANT(EntityType.GIANT),
        GUARDIAN(EntityType.GUARDIAN), HUSK(EntityType.HUSK), ILLUSIONER(EntityType.ILLUSIONER),
        MAGMA_CUBE(EntityType.MAGMA_CUBE), PHANTOM(EntityType.PHANTOM), PILLAGER(EntityType.PILLAGER),
        RAVAGER(EntityType.RAVAGER), SHULKER(EntityType.SHULKER), SILVERFISH(EntityType.SILVERFISH), SKELETON(EntityType.SKELETON),
        SLIME(EntityType.SLIME), STRAY(EntityType.STRAY), VEX(EntityType.VEX), VINDICATOR(EntityType.VINDICATOR),
        WITCH(EntityType.WITCH), WITHER(EntityType.WITHER), WITHER_SKELETON(EntityType.WITHER_SKELETON), ZOMBIE(EntityType.ZOMBIE),
        ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER), PIG_ZOMBIE(EntityType.PIG_ZOMBIE)
        ;

        private EntityType entityType;

        HostileEntities(EntityType entityType) {
            this.entityType = entityType;
        }

        public EntityType getEntityType() {
            return entityType;
        }
    }

    public static boolean isPassive(EntityType entityType) {
        for (PassiveEntities entity : PassiveEntities.values()) {
            if (entityType == entity.getEntityType())
                return true;
        }
        return false;
    }

    public static PassiveEntities getEntity(EntityType entityType) {
        for (PassiveEntities entity : PassiveEntities.values()) {
            if (entityType == entity.getEntityType()) {
                return entity;
            }
        }
        return null;
    }

    public static boolean isAggressive(EntityType entityType) {
        for (HostileEntities entity : HostileEntities.values()) {
            if (entityType == entity.getEntityType())
                return true;
        }
        return false;
    }
}
