package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.*;

public class C0BPacketEntityAction implements Packet<INetHandlerPlayServer>
{
    private int entityID;
    private Action action;
    private int auxData;
    
    public C0BPacketEntityAction() {
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action action) {
        this(entity, action, 0);
    }
    
    public C0BPacketEntityAction(final Entity entity, final Action action, final int auxData) {
        this.entityID = entity.getEntityId();
        this.action = action;
        this.auxData = auxData;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.entityID = buf.readVarIntFromBuffer();
        this.action = buf.readEnumValue(Action.class);
        this.auxData = buf.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.entityID);
        buf.writeEnumValue(this.action);
        buf.writeVarIntToBuffer(this.auxData);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processEntityAction(this);
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public int getAuxData() {
        return this.auxData;
    }
    
    public enum Action
    {
        START_SNEAKING, 
        STOP_SNEAKING, 
        STOP_SLEEPING, 
        START_SPRINTING, 
        STOP_SPRINTING, 
        RIDING_JUMP, 
        OPEN_INVENTORY;
    }
}
