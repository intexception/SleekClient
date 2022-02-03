package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2BPacketChangeGameState implements Packet<INetHandlerPlayClient>
{
    public static final String[] MESSAGE_NAMES;
    private int state;
    private float field_149141_c;
    
    public S2BPacketChangeGameState() {
    }
    
    public S2BPacketChangeGameState(final int stateIn, final float p_i45194_2_) {
        this.state = stateIn;
        this.field_149141_c = p_i45194_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.state = buf.readUnsignedByte();
        this.field_149141_c = buf.readFloat();
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeByte(this.state);
        buf.writeFloat(this.field_149141_c);
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleChangeGameState(this);
    }
    
    public int getGameState() {
        return this.state;
    }
    
    public float func_149137_d() {
        return this.field_149141_c;
    }
    
    static {
        MESSAGE_NAMES = new String[] { "tile.bed.notValid" };
    }
}
