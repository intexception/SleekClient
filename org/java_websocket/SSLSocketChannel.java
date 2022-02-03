package org.java_websocket;

import org.java_websocket.interfaces.*;
import java.nio.*;
import java.util.concurrent.*;
import java.nio.channels.*;
import org.slf4j.*;
import java.io.*;
import org.java_websocket.util.*;
import javax.net.ssl.*;

public class SSLSocketChannel implements WrappedByteChannel, ByteChannel, ISSLChannel
{
    private final Logger log;
    private final SocketChannel socketChannel;
    private final SSLEngine engine;
    private ByteBuffer myAppData;
    private ByteBuffer myNetData;
    private ByteBuffer peerAppData;
    private ByteBuffer peerNetData;
    private ExecutorService executor;
    
    public SSLSocketChannel(final SocketChannel inputSocketChannel, final SSLEngine inputEngine, final ExecutorService inputExecutor, final SelectionKey key) throws IOException {
        this.log = LoggerFactory.getLogger(SSLSocketChannel.class);
        if (inputSocketChannel == null || inputEngine == null || this.executor == inputExecutor) {
            throw new IllegalArgumentException("parameter must not be null");
        }
        this.socketChannel = inputSocketChannel;
        this.engine = inputEngine;
        this.executor = inputExecutor;
        this.myNetData = ByteBuffer.allocate(this.engine.getSession().getPacketBufferSize());
        this.peerNetData = ByteBuffer.allocate(this.engine.getSession().getPacketBufferSize());
        this.engine.beginHandshake();
        if (this.doHandshake()) {
            if (key != null) {
                key.interestOps(key.interestOps() | 0x4);
            }
        }
        else {
            try {
                this.socketChannel.close();
            }
            catch (IOException e) {
                this.log.error("Exception during the closing of the channel", e);
            }
        }
    }
    
    @Override
    public synchronized int read(final ByteBuffer dst) throws IOException {
        if (!dst.hasRemaining()) {
            return 0;
        }
        if (this.peerAppData.hasRemaining()) {
            this.peerAppData.flip();
            return ByteBufferUtils.transferByteBuffer(this.peerAppData, dst);
        }
        this.peerNetData.compact();
        final int bytesRead = this.socketChannel.read(this.peerNetData);
        if (bytesRead > 0 || this.peerNetData.hasRemaining()) {
            this.peerNetData.flip();
            if (this.peerNetData.hasRemaining()) {
                this.peerAppData.compact();
                SSLEngineResult result;
                try {
                    result = this.engine.unwrap(this.peerNetData, this.peerAppData);
                }
                catch (SSLException e) {
                    this.log.error("SSLException during unwrap", e);
                    throw e;
                }
                switch (result.getStatus()) {
                    case OK: {
                        this.peerAppData.flip();
                        return ByteBufferUtils.transferByteBuffer(this.peerAppData, dst);
                    }
                    case BUFFER_UNDERFLOW: {
                        this.peerAppData.flip();
                        return ByteBufferUtils.transferByteBuffer(this.peerAppData, dst);
                    }
                    case BUFFER_OVERFLOW: {
                        this.peerAppData = this.enlargeApplicationBuffer(this.peerAppData);
                        return this.read(dst);
                    }
                    case CLOSED: {
                        this.closeConnection();
                        dst.clear();
                        return -1;
                    }
                    default: {
                        throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
                    }
                }
            }
        }
        else if (bytesRead < 0) {
            this.handleEndOfStream();
        }
        ByteBufferUtils.transferByteBuffer(this.peerAppData, dst);
        return bytesRead;
    }
    
    @Override
    public synchronized int write(final ByteBuffer output) throws IOException {
        int num = 0;
        while (output.hasRemaining()) {
            this.myNetData.clear();
            final SSLEngineResult result = this.engine.wrap(output, this.myNetData);
            switch (result.getStatus()) {
                case OK: {
                    this.myNetData.flip();
                    while (this.myNetData.hasRemaining()) {
                        num += this.socketChannel.write(this.myNetData);
                    }
                    continue;
                }
                case BUFFER_OVERFLOW: {
                    this.myNetData = this.enlargePacketBuffer(this.myNetData);
                    continue;
                }
                case BUFFER_UNDERFLOW: {
                    throw new SSLException("Buffer underflow occurred after a wrap. I don't think we should ever get here.");
                }
                case CLOSED: {
                    this.closeConnection();
                    return 0;
                }
                default: {
                    throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
                }
            }
        }
        return num;
    }
    
    private boolean doHandshake() throws IOException {
        final int appBufferSize = this.engine.getSession().getApplicationBufferSize();
        this.myAppData = ByteBuffer.allocate(appBufferSize);
        this.peerAppData = ByteBuffer.allocate(appBufferSize);
        this.myNetData.clear();
        this.peerNetData.clear();
        SSLEngineResult.HandshakeStatus handshakeStatus = this.engine.getHandshakeStatus();
        boolean handshakeComplete = false;
        while (!handshakeComplete) {
            switch (handshakeStatus) {
                case FINISHED: {
                    handshakeComplete = !this.peerNetData.hasRemaining();
                    if (handshakeComplete) {
                        return true;
                    }
                    this.socketChannel.write(this.peerNetData);
                    continue;
                }
                case NEED_UNWRAP: {
                    if (this.socketChannel.read(this.peerNetData) < 0) {
                        if (this.engine.isInboundDone() && this.engine.isOutboundDone()) {
                            return false;
                        }
                        try {
                            this.engine.closeInbound();
                        }
                        catch (SSLException ex) {}
                        this.engine.closeOutbound();
                        handshakeStatus = this.engine.getHandshakeStatus();
                        continue;
                    }
                    else {
                        this.peerNetData.flip();
                        SSLEngineResult result;
                        try {
                            result = this.engine.unwrap(this.peerNetData, this.peerAppData);
                            this.peerNetData.compact();
                            handshakeStatus = result.getHandshakeStatus();
                        }
                        catch (SSLException sslException) {
                            this.engine.closeOutbound();
                            handshakeStatus = this.engine.getHandshakeStatus();
                            continue;
                        }
                        switch (result.getStatus()) {
                            case OK: {
                                continue;
                            }
                            case BUFFER_OVERFLOW: {
                                this.peerAppData = this.enlargeApplicationBuffer(this.peerAppData);
                                continue;
                            }
                            case BUFFER_UNDERFLOW: {
                                this.peerNetData = this.handleBufferUnderflow(this.peerNetData);
                                continue;
                            }
                            case CLOSED: {
                                if (this.engine.isOutboundDone()) {
                                    return false;
                                }
                                this.engine.closeOutbound();
                                handshakeStatus = this.engine.getHandshakeStatus();
                                continue;
                            }
                            default: {
                                throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
                            }
                        }
                    }
                    break;
                }
                case NEED_WRAP: {
                    this.myNetData.clear();
                    SSLEngineResult result;
                    try {
                        result = this.engine.wrap(this.myAppData, this.myNetData);
                        handshakeStatus = result.getHandshakeStatus();
                    }
                    catch (SSLException sslException) {
                        this.engine.closeOutbound();
                        handshakeStatus = this.engine.getHandshakeStatus();
                        continue;
                    }
                    switch (result.getStatus()) {
                        case OK: {
                            this.myNetData.flip();
                            while (this.myNetData.hasRemaining()) {
                                this.socketChannel.write(this.myNetData);
                            }
                            continue;
                        }
                        case BUFFER_OVERFLOW: {
                            this.myNetData = this.enlargePacketBuffer(this.myNetData);
                            continue;
                        }
                        case BUFFER_UNDERFLOW: {
                            throw new SSLException("Buffer underflow occurred after a wrap. I don't think we should ever get here.");
                        }
                        case CLOSED: {
                            try {
                                this.myNetData.flip();
                                while (this.myNetData.hasRemaining()) {
                                    this.socketChannel.write(this.myNetData);
                                }
                                this.peerNetData.clear();
                            }
                            catch (Exception e) {
                                handshakeStatus = this.engine.getHandshakeStatus();
                            }
                            continue;
                        }
                        default: {
                            throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
                        }
                    }
                    break;
                }
                case NEED_TASK: {
                    Runnable task;
                    while ((task = this.engine.getDelegatedTask()) != null) {
                        this.executor.execute(task);
                    }
                    handshakeStatus = this.engine.getHandshakeStatus();
                    continue;
                }
                case NOT_HANDSHAKING: {
                    continue;
                }
                default: {
                    throw new IllegalStateException("Invalid SSL status: " + handshakeStatus);
                }
            }
        }
        return true;
    }
    
    private ByteBuffer enlargePacketBuffer(final ByteBuffer buffer) {
        return this.enlargeBuffer(buffer, this.engine.getSession().getPacketBufferSize());
    }
    
    private ByteBuffer enlargeApplicationBuffer(final ByteBuffer buffer) {
        return this.enlargeBuffer(buffer, this.engine.getSession().getApplicationBufferSize());
    }
    
    private ByteBuffer enlargeBuffer(ByteBuffer buffer, final int sessionProposedCapacity) {
        if (sessionProposedCapacity > buffer.capacity()) {
            buffer = ByteBuffer.allocate(sessionProposedCapacity);
        }
        else {
            buffer = ByteBuffer.allocate(buffer.capacity() * 2);
        }
        return buffer;
    }
    
    private ByteBuffer handleBufferUnderflow(final ByteBuffer buffer) {
        if (this.engine.getSession().getPacketBufferSize() < buffer.limit()) {
            return buffer;
        }
        final ByteBuffer replaceBuffer = this.enlargePacketBuffer(buffer);
        buffer.flip();
        replaceBuffer.put(buffer);
        return replaceBuffer;
    }
    
    private void closeConnection() throws IOException {
        this.engine.closeOutbound();
        try {
            this.doHandshake();
        }
        catch (IOException ex) {}
        this.socketChannel.close();
    }
    
    private void handleEndOfStream() throws IOException {
        try {
            this.engine.closeInbound();
        }
        catch (Exception e) {
            this.log.error("This engine was forced to close inbound, without having received the proper SSL/TLS close notification message from the peer, due to end of stream.");
        }
        this.closeConnection();
    }
    
    @Override
    public boolean isNeedWrite() {
        return false;
    }
    
    @Override
    public void writeMore() throws IOException {
    }
    
    @Override
    public boolean isNeedRead() {
        return this.peerNetData.hasRemaining() || this.peerAppData.hasRemaining();
    }
    
    @Override
    public int readMore(final ByteBuffer dst) throws IOException {
        return this.read(dst);
    }
    
    @Override
    public boolean isBlocking() {
        return this.socketChannel.isBlocking();
    }
    
    @Override
    public boolean isOpen() {
        return this.socketChannel.isOpen();
    }
    
    @Override
    public void close() throws IOException {
        this.closeConnection();
    }
    
    @Override
    public SSLEngine getSSLEngine() {
        return this.engine;
    }
}
