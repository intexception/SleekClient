package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.api.data.*;
import com.google.common.collect.*;
import com.viaversion.viaversion.libs.gson.*;

public class MappingData extends MappingDataBase
{
    private final BiMap<String, String> attributeMappings;
    
    public MappingData() {
        super("1.15", "1.16", true);
        this.attributeMappings = (BiMap<String, String>)HashBiMap.create();
    }
    
    @Override
    protected void loadExtras(final JsonObject oldMappings, final JsonObject newMappings, final JsonObject diffMappings) {
        this.attributeMappings.put((Object)"generic.maxHealth", (Object)"minecraft:generic.max_health");
        this.attributeMappings.put((Object)"zombie.spawnReinforcements", (Object)"minecraft:zombie.spawn_reinforcements");
        this.attributeMappings.put((Object)"horse.jumpStrength", (Object)"minecraft:horse.jump_strength");
        this.attributeMappings.put((Object)"generic.followRange", (Object)"minecraft:generic.follow_range");
        this.attributeMappings.put((Object)"generic.knockbackResistance", (Object)"minecraft:generic.knockback_resistance");
        this.attributeMappings.put((Object)"generic.movementSpeed", (Object)"minecraft:generic.movement_speed");
        this.attributeMappings.put((Object)"generic.flyingSpeed", (Object)"minecraft:generic.flying_speed");
        this.attributeMappings.put((Object)"generic.attackDamage", (Object)"minecraft:generic.attack_damage");
        this.attributeMappings.put((Object)"generic.attackKnockback", (Object)"minecraft:generic.attack_knockback");
        this.attributeMappings.put((Object)"generic.attackSpeed", (Object)"minecraft:generic.attack_speed");
        this.attributeMappings.put((Object)"generic.armorToughness", (Object)"minecraft:generic.armor_toughness");
    }
    
    public BiMap<String, String> getAttributeMappings() {
        return this.attributeMappings;
    }
}
