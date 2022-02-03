package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S3APacketTabComplete implements Packet<INetHandlerPlayClient>
{
    private String[] matches;
    
    public S3APacketTabComplete() {
    }
    
    public S3APacketTabComplete(final String[] matchesIn) {
        this.matches = matchesIn;
    }
    
    @Override
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.matches = new String[buf.readVarIntFromBuffer()];
        for (int i = 0; i < this.matches.length; ++i) {
            this.matches[i] = buf.readStringFromBuffer(32767);
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeVarIntToBuffer(this.matches.length);
        for (final String s : this.matches) {
            buf.writeString(s);
        }
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleTabComplete(this);
    }
    
    public String[] func_149630_c() {
        return this.matches;
    }
}
