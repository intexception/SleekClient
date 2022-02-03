package com.jagrosh.discordipc.entities;

import org.json.*;
import java.nio.*;

public class Packet
{
    private final OpCode op;
    private final JSONObject data;
    
    public Packet(final OpCode op, final JSONObject data) {
        this.op = op;
        this.data = data;
    }
    
    public byte[] toBytes() {
        final byte[] d = this.data.toString().getBytes();
        final ByteBuffer packet = ByteBuffer.allocate(d.length + 8);
        packet.putInt(Integer.reverseBytes(this.op.ordinal()));
        packet.putInt(Integer.reverseBytes(d.length));
        packet.put(d);
        return packet.array();
    }
    
    public OpCode getOp() {
        return this.op;
    }
    
    public JSONObject getJson() {
        return this.data;
    }
    
    @Override
    public String toString() {
        return "Pkt:" + this.getOp() + this.getJson().toString();
    }
    
    public enum OpCode
    {
        HANDSHAKE, 
        FRAME, 
        CLOSE, 
        PING, 
        PONG;
        
        private static /* synthetic */ OpCode[] $values() {
            return new OpCode[] { OpCode.HANDSHAKE, OpCode.FRAME, OpCode.CLOSE, OpCode.PING, OpCode.PONG };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
