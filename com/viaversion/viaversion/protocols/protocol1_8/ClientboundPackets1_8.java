package com.viaversion.viaversion.protocols.protocol1_8;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ClientboundPackets1_8 implements ClientboundPacketType
{
    KEEP_ALIVE, 
    JOIN_GAME, 
    CHAT_MESSAGE, 
    TIME_UPDATE, 
    ENTITY_EQUIPMENT, 
    SPAWN_POSITION, 
    UPDATE_HEALTH, 
    RESPAWN, 
    PLAYER_POSITION, 
    HELD_ITEM_CHANGE, 
    USE_BED, 
    ENTITY_ANIMATION, 
    SPAWN_PLAYER, 
    COLLECT_ITEM, 
    SPAWN_ENTITY, 
    SPAWN_MOB, 
    SPAWN_PAINTING, 
    SPAWN_EXPERIENCE_ORB, 
    ENTITY_VELOCITY, 
    DESTROY_ENTITIES, 
    ENTITY_MOVEMENT, 
    ENTITY_POSITION, 
    ENTITY_ROTATION, 
    ENTITY_POSITION_AND_ROTATION, 
    ENTITY_TELEPORT, 
    ENTITY_HEAD_LOOK, 
    ENTITY_STATUS, 
    ATTACH_ENTITY, 
    ENTITY_METADATA, 
    ENTITY_EFFECT, 
    REMOVE_ENTITY_EFFECT, 
    SET_EXPERIENCE, 
    ENTITY_PROPERTIES, 
    CHUNK_DATA, 
    MULTI_BLOCK_CHANGE, 
    BLOCK_CHANGE, 
    BLOCK_ACTION, 
    BLOCK_BREAK_ANIMATION, 
    MAP_BULK_CHUNK, 
    EXPLOSION, 
    EFFECT, 
    NAMED_SOUND, 
    SPAWN_PARTICLE, 
    GAME_EVENT, 
    SPAWN_GLOBAL_ENTITY, 
    OPEN_WINDOW, 
    CLOSE_WINDOW, 
    SET_SLOT, 
    WINDOW_ITEMS, 
    WINDOW_PROPERTY, 
    WINDOW_CONFIRMATION, 
    UPDATE_SIGN, 
    MAP_DATA, 
    BLOCK_ENTITY_DATA, 
    OPEN_SIGN_EDITOR, 
    STATISTICS, 
    PLAYER_INFO, 
    PLAYER_ABILITIES, 
    TAB_COMPLETE, 
    SCOREBOARD_OBJECTIVE, 
    UPDATE_SCORE, 
    DISPLAY_SCOREBOARD, 
    TEAMS, 
    PLUGIN_MESSAGE, 
    DISCONNECT, 
    SERVER_DIFFICULTY, 
    COMBAT_EVENT, 
    CAMERA, 
    WORLD_BORDER, 
    TITLE, 
    SET_COMPRESSION, 
    TAB_LIST, 
    RESOURCE_PACK, 
    UPDATE_ENTITY_NBT;
    
    @Override
    public int getId() {
        return this.ordinal();
    }
    
    @Override
    public String getName() {
        return this.name();
    }
}
