package io.netty.buffer;

import java.nio.*;
import java.io.*;
import java.nio.channels.*;

public class DuplicatedByteBuf extends AbstractDerivedByteBuf
{
    private final ByteBuf buffer;
    
    public DuplicatedByteBuf(final ByteBuf buffer) {
        super(buffer.maxCapacity());
        if (buffer instanceof DuplicatedByteBuf) {
            this.buffer = ((DuplicatedByteBuf)buffer).buffer;
        }
        else {
            this.buffer = buffer;
        }
        this.setIndex(buffer.readerIndex(), buffer.writerIndex());
    }
    
    @Override
    public ByteBuf unwrap() {
        return this.buffer;
    }
    
    @Override
    public ByteBufAllocator alloc() {
        return this.buffer.alloc();
    }
    
    @Override
    public ByteOrder order() {
        return this.buffer.order();
    }
    
    @Override
    public boolean isDirect() {
        return this.buffer.isDirect();
    }
    
    @Override
    public int capacity() {
        return this.buffer.capacity();
    }
    
    @Override
    public ByteBuf capacity(final int newCapacity) {
        this.buffer.capacity(newCapacity);
        return this;
    }
    
    @Override
    public boolean hasArray() {
        return this.buffer.hasArray();
    }
    
    @Override
    public byte[] array() {
        return this.buffer.array();
    }
    
    @Override
    public int arrayOffset() {
        return this.buffer.arrayOffset();
    }
    
    @Override
    public boolean hasMemoryAddress() {
        return this.buffer.hasMemoryAddress();
    }
    
    @Override
    public long memoryAddress() {
        return this.buffer.memoryAddress();
    }
    
    @Override
    public byte getByte(final int index) {
        return this._getByte(index);
    }
    
    @Override
    protected byte _getByte(final int index) {
        return this.buffer.getByte(index);
    }
    
    @Override
    public short getShort(final int index) {
        return this._getShort(index);
    }
    
    @Override
    protected short _getShort(final int index) {
        return this.buffer.getShort(index);
    }
    
    @Override
    public int getUnsignedMedium(final int index) {
        return this._getUnsignedMedium(index);
    }
    
    @Override
    protected int _getUnsignedMedium(final int index) {
        return this.buffer.getUnsignedMedium(index);
    }
    
    @Override
    public int getInt(final int index) {
        return this._getInt(index);
    }
    
    @Override
    protected int _getInt(final int index) {
        return this.buffer.getInt(index);
    }
    
    @Override
    public long getLong(final int index) {
        return this._getLong(index);
    }
    
    @Override
    protected long _getLong(final int index) {
        return this.buffer.getLong(index);
    }
    
    @Override
    public ByteBuf copy(final int index, final int length) {
        return this.buffer.copy(index, length);
    }
    
    @Override
    public ByteBuf slice(final int index, final int length) {
        return this.buffer.slice(index, length);
    }
    
    @Override
    public ByteBuf getBytes(final int index, final ByteBuf dst, final int dstIndex, final int length) {
        this.buffer.getBytes(index, dst, dstIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final byte[] dst, final int dstIndex, final int length) {
        this.buffer.getBytes(index, dst, dstIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final ByteBuffer dst) {
        this.buffer.getBytes(index, dst);
        return this;
    }
    
    @Override
    public ByteBuf setByte(final int index, final int value) {
        this._setByte(index, value);
        return this;
    }
    
    @Override
    protected void _setByte(final int index, final int value) {
        this.buffer.setByte(index, value);
    }
    
    @Override
    public ByteBuf setShort(final int index, final int value) {
        this._setShort(index, value);
        return this;
    }
    
    @Override
    protected void _setShort(final int index, final int value) {
        this.buffer.setShort(index, value);
    }
    
    @Override
    public ByteBuf setMedium(final int index, final int value) {
        this._setMedium(index, value);
        return this;
    }
    
    @Override
    protected void _setMedium(final int index, final int value) {
        this.buffer.setMedium(index, value);
    }
    
    @Override
    public ByteBuf setInt(final int index, final int value) {
        this._setInt(index, value);
        return this;
    }
    
    @Override
    protected void _setInt(final int index, final int value) {
        this.buffer.setInt(index, value);
    }
    
    @Override
    public ByteBuf setLong(final int index, final long value) {
        this._setLong(index, value);
        return this;
    }
    
    @Override
    protected void _setLong(final int index, final long value) {
        this.buffer.setLong(index, value);
    }
    
    @Override
    public ByteBuf setBytes(final int index, final byte[] src, final int srcIndex, final int length) {
        this.buffer.setBytes(index, src, srcIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int index, final ByteBuf src, final int srcIndex, final int length) {
        this.buffer.setBytes(index, src, srcIndex, length);
        return this;
    }
    
    @Override
    public ByteBuf setBytes(final int index, final ByteBuffer src) {
        this.buffer.setBytes(index, src);
        return this;
    }
    
    @Override
    public ByteBuf getBytes(final int index, final OutputStream out, final int length) throws IOException {
        this.buffer.getBytes(index, out, length);
        return this;
    }
    
    @Override
    public int getBytes(final int index, final GatheringByteChannel out, final int length) throws IOException {
        return this.buffer.getBytes(index, out, length);
    }
    
    @Override
    public int setBytes(final int index, final InputStream in, final int length) throws IOException {
        return this.buffer.setBytes(index, in, length);
    }
    
    @Override
    public int setBytes(final int index, final ScatteringByteChannel in, final int length) throws IOException {
        return this.buffer.setBytes(index, in, length);
    }
    
    @Override
    public int nioBufferCount() {
        return this.buffer.nioBufferCount();
    }
    
    @Override
    public ByteBuffer[] nioBuffers(final int index, final int length) {
        return this.buffer.nioBuffers(index, length);
    }
    
    @Override
    public ByteBuffer internalNioBuffer(final int index, final int length) {
        return this.nioBuffer(index, length);
    }
    
    @Override
    public int forEachByte(final int index, final int length, final ByteBufProcessor processor) {
        return this.buffer.forEachByte(index, length, processor);
    }
    
    @Override
    public int forEachByteDesc(final int index, final int length, final ByteBufProcessor processor) {
        return this.buffer.forEachByteDesc(index, length, processor);
    }
}
