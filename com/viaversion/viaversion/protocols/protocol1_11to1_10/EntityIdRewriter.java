package com.viaversion.viaversion.protocols.protocol1_11to1_10;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.google.common.collect.*;

public class EntityIdRewriter
{
    private static final BiMap<String, String> oldToNewNames;
    
    public static void toClient(final CompoundTag tag) {
        toClient(tag, false);
    }
    
    public static void toClient(final CompoundTag tag, final boolean backwards) {
        final Tag idTag = tag.get("id");
        if (idTag instanceof StringTag) {
            final StringTag id = (StringTag)idTag;
            final String newName = (String)(backwards ? EntityIdRewriter.oldToNewNames.inverse().get((Object)id.getValue()) : ((String)EntityIdRewriter.oldToNewNames.get((Object)id.getValue())));
            if (newName != null) {
                id.setValue(newName);
            }
        }
    }
    
    public static void toClientSpawner(final CompoundTag tag) {
        toClientSpawner(tag, false);
    }
    
    public static void toClientSpawner(final CompoundTag tag, final boolean backwards) {
        if (tag == null) {
            return;
        }
        final Tag spawnDataTag = tag.get("SpawnData");
        if (spawnDataTag != null) {
            toClient((CompoundTag)spawnDataTag, backwards);
        }
    }
    
    public static void toClientItem(final Item item) {
        toClientItem(item, false);
    }
    
    public static void toClientItem(final Item item, final boolean backwards) {
        if (hasEntityTag(item)) {
            toClient(item.tag().get("EntityTag"), backwards);
        }
        if (item != null && item.amount() <= 0) {
            item.setAmount(1);
        }
    }
    
    public static void toServerItem(final Item item) {
        toServerItem(item, false);
    }
    
    public static void toServerItem(final Item item, final boolean backwards) {
        if (!hasEntityTag(item)) {
            return;
        }
        final CompoundTag entityTag = item.tag().get("EntityTag");
        final Tag idTag = entityTag.get("id");
        if (idTag instanceof StringTag) {
            final StringTag id = (StringTag)idTag;
            final String newName = (String)(backwards ? EntityIdRewriter.oldToNewNames.get((Object)id.getValue()) : ((String)EntityIdRewriter.oldToNewNames.inverse().get((Object)id.getValue())));
            if (newName != null) {
                id.setValue(newName);
            }
        }
    }
    
    private static boolean hasEntityTag(final Item item) {
        if (item == null || item.identifier() != 383) {
            return false;
        }
        final CompoundTag tag = item.tag();
        if (tag == null) {
            return false;
        }
        final Tag entityTag = tag.get("EntityTag");
        return entityTag instanceof CompoundTag && ((CompoundTag)entityTag).get("id") instanceof StringTag;
    }
    
    static {
        (oldToNewNames = (BiMap)HashBiMap.create()).put((Object)"AreaEffectCloud", (Object)"minecraft:area_effect_cloud");
        EntityIdRewriter.oldToNewNames.put((Object)"ArmorStand", (Object)"minecraft:armor_stand");
        EntityIdRewriter.oldToNewNames.put((Object)"Arrow", (Object)"minecraft:arrow");
        EntityIdRewriter.oldToNewNames.put((Object)"Bat", (Object)"minecraft:bat");
        EntityIdRewriter.oldToNewNames.put((Object)"Blaze", (Object)"minecraft:blaze");
        EntityIdRewriter.oldToNewNames.put((Object)"Boat", (Object)"minecraft:boat");
        EntityIdRewriter.oldToNewNames.put((Object)"CaveSpider", (Object)"minecraft:cave_spider");
        EntityIdRewriter.oldToNewNames.put((Object)"Chicken", (Object)"minecraft:chicken");
        EntityIdRewriter.oldToNewNames.put((Object)"Cow", (Object)"minecraft:cow");
        EntityIdRewriter.oldToNewNames.put((Object)"Creeper", (Object)"minecraft:creeper");
        EntityIdRewriter.oldToNewNames.put((Object)"Donkey", (Object)"minecraft:donkey");
        EntityIdRewriter.oldToNewNames.put((Object)"DragonFireball", (Object)"minecraft:dragon_fireball");
        EntityIdRewriter.oldToNewNames.put((Object)"ElderGuardian", (Object)"minecraft:elder_guardian");
        EntityIdRewriter.oldToNewNames.put((Object)"EnderCrystal", (Object)"minecraft:ender_crystal");
        EntityIdRewriter.oldToNewNames.put((Object)"EnderDragon", (Object)"minecraft:ender_dragon");
        EntityIdRewriter.oldToNewNames.put((Object)"Enderman", (Object)"minecraft:enderman");
        EntityIdRewriter.oldToNewNames.put((Object)"Endermite", (Object)"minecraft:endermite");
        EntityIdRewriter.oldToNewNames.put((Object)"EntityHorse", (Object)"minecraft:horse");
        EntityIdRewriter.oldToNewNames.put((Object)"EyeOfEnderSignal", (Object)"minecraft:eye_of_ender_signal");
        EntityIdRewriter.oldToNewNames.put((Object)"FallingSand", (Object)"minecraft:falling_block");
        EntityIdRewriter.oldToNewNames.put((Object)"Fireball", (Object)"minecraft:fireball");
        EntityIdRewriter.oldToNewNames.put((Object)"FireworksRocketEntity", (Object)"minecraft:fireworks_rocket");
        EntityIdRewriter.oldToNewNames.put((Object)"Ghast", (Object)"minecraft:ghast");
        EntityIdRewriter.oldToNewNames.put((Object)"Giant", (Object)"minecraft:giant");
        EntityIdRewriter.oldToNewNames.put((Object)"Guardian", (Object)"minecraft:guardian");
        EntityIdRewriter.oldToNewNames.put((Object)"Husk", (Object)"minecraft:husk");
        EntityIdRewriter.oldToNewNames.put((Object)"Item", (Object)"minecraft:item");
        EntityIdRewriter.oldToNewNames.put((Object)"ItemFrame", (Object)"minecraft:item_frame");
        EntityIdRewriter.oldToNewNames.put((Object)"LavaSlime", (Object)"minecraft:magma_cube");
        EntityIdRewriter.oldToNewNames.put((Object)"LeashKnot", (Object)"minecraft:leash_knot");
        EntityIdRewriter.oldToNewNames.put((Object)"MinecartChest", (Object)"minecraft:chest_minecart");
        EntityIdRewriter.oldToNewNames.put((Object)"MinecartCommandBlock", (Object)"minecraft:commandblock_minecart");
        EntityIdRewriter.oldToNewNames.put((Object)"MinecartFurnace", (Object)"minecraft:furnace_minecart");
        EntityIdRewriter.oldToNewNames.put((Object)"MinecartHopper", (Object)"minecraft:hopper_minecart");
        EntityIdRewriter.oldToNewNames.put((Object)"MinecartRideable", (Object)"minecraft:minecart");
        EntityIdRewriter.oldToNewNames.put((Object)"MinecartSpawner", (Object)"minecraft:spawner_minecart");
        EntityIdRewriter.oldToNewNames.put((Object)"MinecartTNT", (Object)"minecraft:tnt_minecart");
        EntityIdRewriter.oldToNewNames.put((Object)"Mule", (Object)"minecraft:mule");
        EntityIdRewriter.oldToNewNames.put((Object)"MushroomCow", (Object)"minecraft:mooshroom");
        EntityIdRewriter.oldToNewNames.put((Object)"Ozelot", (Object)"minecraft:ocelot");
        EntityIdRewriter.oldToNewNames.put((Object)"Painting", (Object)"minecraft:painting");
        EntityIdRewriter.oldToNewNames.put((Object)"Pig", (Object)"minecraft:pig");
        EntityIdRewriter.oldToNewNames.put((Object)"PigZombie", (Object)"minecraft:zombie_pigman");
        EntityIdRewriter.oldToNewNames.put((Object)"PolarBear", (Object)"minecraft:polar_bear");
        EntityIdRewriter.oldToNewNames.put((Object)"PrimedTnt", (Object)"minecraft:tnt");
        EntityIdRewriter.oldToNewNames.put((Object)"Rabbit", (Object)"minecraft:rabbit");
        EntityIdRewriter.oldToNewNames.put((Object)"Sheep", (Object)"minecraft:sheep");
        EntityIdRewriter.oldToNewNames.put((Object)"Shulker", (Object)"minecraft:shulker");
        EntityIdRewriter.oldToNewNames.put((Object)"ShulkerBullet", (Object)"minecraft:shulker_bullet");
        EntityIdRewriter.oldToNewNames.put((Object)"Silverfish", (Object)"minecraft:silverfish");
        EntityIdRewriter.oldToNewNames.put((Object)"Skeleton", (Object)"minecraft:skeleton");
        EntityIdRewriter.oldToNewNames.put((Object)"SkeletonHorse", (Object)"minecraft:skeleton_horse");
        EntityIdRewriter.oldToNewNames.put((Object)"Slime", (Object)"minecraft:slime");
        EntityIdRewriter.oldToNewNames.put((Object)"SmallFireball", (Object)"minecraft:small_fireball");
        EntityIdRewriter.oldToNewNames.put((Object)"Snowball", (Object)"minecraft:snowball");
        EntityIdRewriter.oldToNewNames.put((Object)"SnowMan", (Object)"minecraft:snowman");
        EntityIdRewriter.oldToNewNames.put((Object)"SpectralArrow", (Object)"minecraft:spectral_arrow");
        EntityIdRewriter.oldToNewNames.put((Object)"Spider", (Object)"minecraft:spider");
        EntityIdRewriter.oldToNewNames.put((Object)"Squid", (Object)"minecraft:squid");
        EntityIdRewriter.oldToNewNames.put((Object)"Stray", (Object)"minecraft:stray");
        EntityIdRewriter.oldToNewNames.put((Object)"ThrownEgg", (Object)"minecraft:egg");
        EntityIdRewriter.oldToNewNames.put((Object)"ThrownEnderpearl", (Object)"minecraft:ender_pearl");
        EntityIdRewriter.oldToNewNames.put((Object)"ThrownExpBottle", (Object)"minecraft:xp_bottle");
        EntityIdRewriter.oldToNewNames.put((Object)"ThrownPotion", (Object)"minecraft:potion");
        EntityIdRewriter.oldToNewNames.put((Object)"Villager", (Object)"minecraft:villager");
        EntityIdRewriter.oldToNewNames.put((Object)"VillagerGolem", (Object)"minecraft:villager_golem");
        EntityIdRewriter.oldToNewNames.put((Object)"Witch", (Object)"minecraft:witch");
        EntityIdRewriter.oldToNewNames.put((Object)"WitherBoss", (Object)"minecraft:wither");
        EntityIdRewriter.oldToNewNames.put((Object)"WitherSkeleton", (Object)"minecraft:wither_skeleton");
        EntityIdRewriter.oldToNewNames.put((Object)"WitherSkull", (Object)"minecraft:wither_skull");
        EntityIdRewriter.oldToNewNames.put((Object)"Wolf", (Object)"minecraft:wolf");
        EntityIdRewriter.oldToNewNames.put((Object)"XPOrb", (Object)"minecraft:xp_orb");
        EntityIdRewriter.oldToNewNames.put((Object)"Zombie", (Object)"minecraft:zombie");
        EntityIdRewriter.oldToNewNames.put((Object)"ZombieHorse", (Object)"minecraft:zombie_horse");
        EntityIdRewriter.oldToNewNames.put((Object)"ZombieVillager", (Object)"minecraft:zombie_villager");
    }
}
