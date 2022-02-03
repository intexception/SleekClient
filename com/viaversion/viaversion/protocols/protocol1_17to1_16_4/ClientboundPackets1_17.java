package com.viaversion.viaversion.protocols.protocol1_17to1_16_4;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ClientboundPackets1_17 implements ClientboundPacketType
{
    SPAWN_ENTITY, 
    SPAWN_EXPERIENCE_ORB, 
    SPAWN_MOB, 
    SPAWN_PAINTING, 
    SPAWN_PLAYER, 
    ADD_VIBRATION_SIGNAL, 
    ENTITY_ANIMATION, 
    STATISTICS, 
    ACKNOWLEDGE_PLAYER_DIGGING, 
    BLOCK_BREAK_ANIMATION, 
    BLOCK_ENTITY_DATA, 
    BLOCK_ACTION, 
    BLOCK_CHANGE, 
    BOSSBAR, 
    SERVER_DIFFICULTY, 
    CHAT_MESSAGE, 
    CLEAR_TITLES, 
    TAB_COMPLETE, 
    DECLARE_COMMANDS, 
    CLOSE_WINDOW, 
    WINDOW_ITEMS, 
    WINDOW_PROPERTY, 
    SET_SLOT, 
    COOLDOWN, 
    PLUGIN_MESSAGE, 
    NAMED_SOUND, 
    DISCONNECT, 
    ENTITY_STATUS, 
    EXPLOSION, 
    UNLOAD_CHUNK, 
    GAME_EVENT, 
    OPEN_HORSE_WINDOW, 
    WORLD_BORDER_INIT, 
    KEEP_ALIVE, 
    CHUNK_DATA, 
    EFFECT, 
    SPAWN_PARTICLE, 
    UPDATE_LIGHT, 
    JOIN_GAME, 
    MAP_DATA, 
    TRADE_LIST, 
    ENTITY_POSITION, 
    ENTITY_POSITION_AND_ROTATION, 
    ENTITY_ROTATION, 
    VEHICLE_MOVE, 
    OPEN_BOOK, 
    OPEN_WINDOW, 
    OPEN_SIGN_EDITOR, 
    PING, 
    CRAFT_RECIPE_RESPONSE, 
    PLAYER_ABILITIES, 
    COMBAT_END, 
    COMBAT_ENTER, 
    COMBAT_KILL, 
    PLAYER_INFO, 
    FACE_PLAYER, 
    PLAYER_POSITION, 
    UNLOCK_RECIPES, 
    REMOVE_ENTITY, 
    REMOVE_ENTITY_EFFECT, 
    RESOURCE_PACK, 
    RESPAWN, 
    ENTITY_HEAD_LOOK, 
    MULTI_BLOCK_CHANGE, 
    SELECT_ADVANCEMENTS_TAB, 
    ACTIONBAR, 
    WORLD_BORDER_CENTER, 
    WORLD_BORDER_LERP_SIZE, 
    WORLD_BORDER_SIZE, 
    WORLD_BORDER_WARNING_DELAY, 
    WORLD_BORDER_WARNING_DISTANCE, 
    CAMERA, 
    HELD_ITEM_CHANGE, 
    UPDATE_VIEW_POSITION, 
    UPDATE_VIEW_DISTANCE, 
    SPAWN_POSITION, 
    DISPLAY_SCOREBOARD, 
    ENTITY_METADATA, 
    ATTACH_ENTITY, 
    ENTITY_VELOCITY, 
    ENTITY_EQUIPMENT, 
    SET_EXPERIENCE, 
    UPDATE_HEALTH, 
    SCOREBOARD_OBJECTIVE, 
    SET_PASSENGERS, 
    TEAMS, 
    UPDATE_SCORE, 
    TITLE_SUBTITLE, 
    TIME_UPDATE, 
    TITLE_TEXT, 
    TITLE_TIMES, 
    ENTITY_SOUND, 
    SOUND, 
    STOP_SOUND, 
    TAB_LIST, 
    NBT_QUERY, 
    COLLECT_ITEM, 
    ENTITY_TELEPORT, 
    ADVANCEMENTS, 
    ENTITY_PROPERTIES, 
    ENTITY_EFFECT, 
    DECLARE_RECIPES, 
    TAGS;
    
    @Override
    public int getId() {
        return this.ordinal();
    }
    
    @Override
    public String getName() {
        return this.name();
    }
}
