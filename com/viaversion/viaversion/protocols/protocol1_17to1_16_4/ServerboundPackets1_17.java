package com.viaversion.viaversion.protocols.protocol1_17to1_16_4;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundPackets1_17 implements ServerboundPacketType
{
    TELEPORT_CONFIRM, 
    QUERY_BLOCK_NBT, 
    SET_DIFFICULTY, 
    CHAT_MESSAGE, 
    CLIENT_STATUS, 
    CLIENT_SETTINGS, 
    TAB_COMPLETE, 
    CLICK_WINDOW_BUTTON, 
    CLICK_WINDOW, 
    CLOSE_WINDOW, 
    PLUGIN_MESSAGE, 
    EDIT_BOOK, 
    ENTITY_NBT_REQUEST, 
    INTERACT_ENTITY, 
    GENERATE_JIGSAW, 
    KEEP_ALIVE, 
    LOCK_DIFFICULTY, 
    PLAYER_POSITION, 
    PLAYER_POSITION_AND_ROTATION, 
    PLAYER_ROTATION, 
    PLAYER_MOVEMENT, 
    VEHICLE_MOVE, 
    STEER_BOAT, 
    PICK_ITEM, 
    CRAFT_RECIPE_REQUEST, 
    PLAYER_ABILITIES, 
    PLAYER_DIGGING, 
    ENTITY_ACTION, 
    STEER_VEHICLE, 
    PONG, 
    RECIPE_BOOK_DATA, 
    SEEN_RECIPE, 
    RENAME_ITEM, 
    RESOURCE_PACK_STATUS, 
    ADVANCEMENT_TAB, 
    SELECT_TRADE, 
    SET_BEACON_EFFECT, 
    HELD_ITEM_CHANGE, 
    UPDATE_COMMAND_BLOCK, 
    UPDATE_COMMAND_BLOCK_MINECART, 
    CREATIVE_INVENTORY_ACTION, 
    UPDATE_JIGSAW_BLOCK, 
    UPDATE_STRUCTURE_BLOCK, 
    UPDATE_SIGN, 
    ANIMATION, 
    SPECTATE, 
    PLAYER_BLOCK_PLACEMENT, 
    USE_ITEM;
    
    @Override
    public int getId() {
        return this.ordinal();
    }
    
    @Override
    public String getName() {
        return this.name();
    }
}
