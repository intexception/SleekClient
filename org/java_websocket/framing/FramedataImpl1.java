package org.java_websocket.framing;

import org.java_websocket.enums.*;
import java.nio.*;
import org.java_websocket.exceptions.*;
import org.java_websocket.util.*;

public abstract class FramedataImpl1 implements Framedata
{
    private boolean fin;
    private Opcode optcode;
    private ByteBuffer unmaskedpayload;
    private boolean transferemasked;
    private boolean rsv1;
    private boolean rsv2;
    private boolean rsv3;
    
    public abstract void isValid() throws InvalidDataException;
    
    public FramedataImpl1(final Opcode op) {
        this.optcode = op;
        this.unmaskedpayload = ByteBufferUtils.getEmptyByteBuffer();
        this.fin = true;
        this.transferemasked = false;
        this.rsv1 = false;
        this.rsv2 = false;
        this.rsv3 = false;
    }
    
    @Override
    public boolean isRSV1() {
        return this.rsv1;
    }
    
    @Override
    public boolean isRSV2() {
        return this.rsv2;
    }
    
    @Override
    public boolean isRSV3() {
        return this.rsv3;
    }
    
    @Override
    public boolean isFin() {
        return this.fin;
    }
    
    @Override
    public Opcode getOpcode() {
        return this.optcode;
    }
    
    @Override
    public boolean getTransfereMasked() {
        return this.transferemasked;
    }
    
    @Override
    public ByteBuffer getPayloadData() {
        return this.unmaskedpayload;
    }
    
    @Override
    public void append(final Framedata nextframe) {
        final ByteBuffer b = nextframe.getPayloadData();
        if (this.unmaskedpayload == null) {
            this.unmaskedpayload = ByteBuffer.allocate(b.remaining());
            b.mark();
            this.unmaskedpayload.put(b);
            b.reset();
        }
        else {
            b.mark();
            this.unmaskedpayload.position(this.unmaskedpayload.limit());
            this.unmaskedpayload.limit(this.unmaskedpayload.capacity());
            if (b.remaining() > this.unmaskedpayload.remaining()) {
                final ByteBuffer tmp = ByteBuffer.allocate(b.remaining() + this.unmaskedpayload.capacity());
                this.unmaskedpayload.flip();
                tmp.put(this.unmaskedpayload);
                tmp.put(b);
                this.unmaskedpayload = tmp;
            }
            else {
                this.unmaskedpayload.put(b);
            }
            this.unmaskedpayload.rewind();
            b.reset();
        }
        this.fin = nextframe.isFin();
    }
    
    @Override
    public String toString() {
        return "Framedata{ opcode:" + this.getOpcode() + ", fin:" + this.isFin() + ", rsv1:" + this.isRSV1() + ", rsv2:" + this.isRSV2() + ", rsv3:" + this.isRSV3() + ", payload length:[pos:" + this.unmaskedpayload.position() + ", len:" + this.unmaskedpayload.remaining() + "], payload:" + ((this.unmaskedpayload.remaining() > 1000) ? "(too big to display)" : new String(this.unmaskedpayload.array())) + '}';
    }
    
    public void setPayload(final ByteBuffer payload) {
        this.unmaskedpayload = payload;
    }
    
    public void setFin(final boolean fin) {
        this.fin = fin;
    }
    
    public void setRSV1(final boolean rsv1) {
        this.rsv1 = rsv1;
    }
    
    public void setRSV2(final boolean rsv2) {
        this.rsv2 = rsv2;
    }
    
    public void setRSV3(final boolean rsv3) {
        this.rsv3 = rsv3;
    }
    
    public void setTransferemasked(final boolean transferemasked) {
        this.transferemasked = transferemasked;
    }
    
    public static FramedataImpl1 get(final Opcode opcode) {
        if (opcode == null) {
            throw new IllegalArgumentException("Supplied opcode cannot be null");
        }
        switch (opcode) {
            case PING: {
                return new PingFrame();
            }
            case PONG: {
                return new PongFrame();
            }
            case TEXT: {
                return new TextFrame();
            }
            case BINARY: {
                return new BinaryFrame();
            }
            case CLOSING: {
                return new CloseFrame();
            }
            case CONTINUOUS: {
                return new ContinuousFrame();
            }
            default: {
                throw new IllegalArgumentException("Supplied opcode is invalid");
            }
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FramedataImpl1 that = (FramedataImpl1)o;
        return this.fin == that.fin && this.transferemasked == that.transferemasked && this.rsv1 == that.rsv1 && this.rsv2 == that.rsv2 && this.rsv3 == that.rsv3 && this.optcode == that.optcode && ((this.unmaskedpayload != null) ? this.unmaskedpayload.equals(that.unmaskedpayload) : (that.unmaskedpayload == null));
    }
    
    @Override
    public int hashCode() {
        int result = this.fin ? 1 : 0;
        result = 31 * result + this.optcode.hashCode();
        result = 31 * result + ((this.unmaskedpayload != null) ? this.unmaskedpayload.hashCode() : 0);
        result = 31 * result + (this.transferemasked ? 1 : 0);
        result = 31 * result + (this.rsv1 ? 1 : 0);
        result = 31 * result + (this.rsv2 ? 1 : 0);
        result = 31 * result + (this.rsv3 ? 1 : 0);
        return result;
    }
}
