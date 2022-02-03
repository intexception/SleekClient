package org.java_websocket;

import org.java_websocket.interfaces.*;
import java.nio.*;
import org.slf4j.*;
import java.util.concurrent.*;
import java.util.*;
import javax.net.ssl.*;
import java.io.*;
import java.nio.channels.*;
import java.net.*;

public class SSLSocketChannel2 implements ByteChannel, WrappedByteChannel, ISSLChannel
{
    protected static ByteBuffer emptybuffer;
    private final Logger log;
    protected ExecutorService exec;
    protected List<Future<?>> tasks;
    protected ByteBuffer inData;
    protected ByteBuffer outCrypt;
    protected ByteBuffer inCrypt;
    protected SocketChannel socketChannel;
    protected SelectionKey selectionKey;
    protected SSLEngine sslEngine;
    protected SSLEngineResult readEngineResult;
    protected SSLEngineResult writeEngineResult;
    protected int bufferallocations;
    private byte[] saveCryptData;
    
    public SSLSocketChannel2(final SocketChannel channel, final SSLEngine sslEngine, final ExecutorService exec, final SelectionKey key) throws IOException {
        this.log = LoggerFactory.getLogger(SSLSocketChannel2.class);
        this.bufferallocations = 0;
        this.saveCryptData = null;
        if (channel == null || sslEngine == null || exec == null) {
            throw new IllegalArgumentException("parameter must not be null");
        }
        this.socketChannel = channel;
        this.sslEngine = sslEngine;
        this.exec = exec;
        final SSLEngineResult sslEngineResult = new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, sslEngine.getHandshakeStatus(), 0, 0);
        this.writeEngineResult = sslEngineResult;
        this.readEngineResult = sslEngineResult;
        this.tasks = new ArrayList<Future<?>>(3);
        if (key != null) {
            key.interestOps(key.interestOps() | 0x4);
            this.selectionKey = key;
        }
        this.createBuffers(sslEngine.getSession());
        this.socketChannel.write(this.wrap(SSLSocketChannel2.emptybuffer));
        this.processHandshake();
    }
    
    private void consumeFutureUninterruptible(final Future<?> f) {
        try {
            while (true) {
                try {
                    f.get();
                }
                catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                    continue;
                }
                break;
            }
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    private synchronized void processHandshake() throws IOException {
        if (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            return;
        }
        if (!this.tasks.isEmpty()) {
            final Iterator<Future<?>> it = this.tasks.iterator();
            while (it.hasNext()) {
                final Future<?> f = it.next();
                if (!f.isDone()) {
                    if (this.isBlocking()) {
                        this.consumeFutureUninterruptible(f);
                    }
                    return;
                }
                it.remove();
            }
        }
        if (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
            if (!this.isBlocking() || this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                this.inCrypt.compact();
                final int read = this.socketChannel.read(this.inCrypt);
                if (read == -1) {
                    throw new IOException("connection closed unexpectedly by peer");
                }
                this.inCrypt.flip();
            }
            this.inData.compact();
            this.unwrap();
            if (this.readEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                this.createBuffers(this.sslEngine.getSession());
                return;
            }
        }
        this.consumeDelegatedTasks();
        if (this.tasks.isEmpty() || this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
            this.socketChannel.write(this.wrap(SSLSocketChannel2.emptybuffer));
            if (this.writeEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                this.createBuffers(this.sslEngine.getSession());
                return;
            }
        }
        assert this.sslEngine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
        this.bufferallocations = 1;
    }
    
    private synchronized ByteBuffer wrap(final ByteBuffer b) throws SSLException {
        this.outCrypt.compact();
        this.writeEngineResult = this.sslEngine.wrap(b, this.outCrypt);
        this.outCrypt.flip();
        return this.outCrypt;
    }
    
    private synchronized ByteBuffer unwrap() throws SSLException {
        if (this.readEngineResult.getStatus() == SSLEngineResult.Status.CLOSED && this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            try {
                this.close();
            }
            catch (IOException ex) {}
        }
        int rem;
        do {
            rem = this.inData.remaining();
            this.readEngineResult = this.sslEngine.unwrap(this.inCrypt, this.inData);
        } while (this.readEngineResult.getStatus() == SSLEngineResult.Status.OK && (rem != this.inData.remaining() || this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP));
        this.inData.flip();
        return this.inData;
    }
    
    protected void consumeDelegatedTasks() {
        Runnable task;
        while ((task = this.sslEngine.getDelegatedTask()) != null) {
            this.tasks.add(this.exec.submit(task));
        }
    }
    
    protected void createBuffers(final SSLSession session) {
        this.saveCryptedData();
        final int netBufferMax = session.getPacketBufferSize();
        final int appBufferMax = Math.max(session.getApplicationBufferSize(), netBufferMax);
        if (this.inData == null) {
            this.inData = ByteBuffer.allocate(appBufferMax);
            this.outCrypt = ByteBuffer.allocate(netBufferMax);
            this.inCrypt = ByteBuffer.allocate(netBufferMax);
        }
        else {
            if (this.inData.capacity() != appBufferMax) {
                this.inData = ByteBuffer.allocate(appBufferMax);
            }
            if (this.outCrypt.capacity() != netBufferMax) {
                this.outCrypt = ByteBuffer.allocate(netBufferMax);
            }
            if (this.inCrypt.capacity() != netBufferMax) {
                this.inCrypt = ByteBuffer.allocate(netBufferMax);
            }
        }
        if (this.inData.remaining() != 0 && this.log.isTraceEnabled()) {
            this.log.trace(new String(this.inData.array(), this.inData.position(), this.inData.remaining()));
        }
        this.inData.rewind();
        this.inData.flip();
        if (this.inCrypt.remaining() != 0 && this.log.isTraceEnabled()) {
            this.log.trace(new String(this.inCrypt.array(), this.inCrypt.position(), this.inCrypt.remaining()));
        }
        this.inCrypt.rewind();
        this.inCrypt.flip();
        this.outCrypt.rewind();
        this.outCrypt.flip();
        ++this.bufferallocations;
    }
    
    @Override
    public int write(final ByteBuffer src) throws IOException {
        if (!this.isHandShakeComplete()) {
            this.processHandshake();
            return 0;
        }
        final int num = this.socketChannel.write(this.wrap(src));
        if (this.writeEngineResult.getStatus() == SSLEngineResult.Status.CLOSED) {
            throw new EOFException("Connection is closed");
        }
        return num;
    }
    
    @Override
    public int read(final ByteBuffer dst) throws IOException {
        this.tryRestoreCryptedData();
        while (dst.hasRemaining()) {
            if (!this.isHandShakeComplete()) {
                if (this.isBlocking()) {
                    while (!this.isHandShakeComplete()) {
                        this.processHandshake();
                    }
                }
                else {
                    this.processHandshake();
                    if (!this.isHandShakeComplete()) {
                        return 0;
                    }
                }
            }
            final int purged = this.readRemaining(dst);
            if (purged != 0) {
                return purged;
            }
            assert this.inData.position() == 0;
            this.inData.clear();
            if (!this.inCrypt.hasRemaining()) {
                this.inCrypt.clear();
            }
            else {
                this.inCrypt.compact();
            }
            if ((this.isBlocking() || this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW) && this.socketChannel.read(this.inCrypt) == -1) {
                return -1;
            }
            this.inCrypt.flip();
            this.unwrap();
            final int transferred = this.transfereTo(this.inData, dst);
            if (transferred == 0 && this.isBlocking()) {
                continue;
            }
            return transferred;
        }
        return 0;
    }
    
    private int readRemaining(final ByteBuffer dst) throws SSLException {
        if (this.inData.hasRemaining()) {
            return this.transfereTo(this.inData, dst);
        }
        if (!this.inData.hasRemaining()) {
            this.inData.clear();
        }
        this.tryRestoreCryptedData();
        if (this.inCrypt.hasRemaining()) {
            this.unwrap();
            final int amount = this.transfereTo(this.inData, dst);
            if (this.readEngineResult.getStatus() == SSLEngineResult.Status.CLOSED) {
                return -1;
            }
            if (amount > 0) {
                return amount;
            }
        }
        return 0;
    }
    
    public boolean isConnected() {
        return this.socketChannel.isConnected();
    }
    
    @Override
    public void close() throws IOException {
        this.sslEngine.closeOutbound();
        this.sslEngine.getSession().invalidate();
        if (this.socketChannel.isOpen()) {
            this.socketChannel.write(this.wrap(SSLSocketChannel2.emptybuffer));
        }
        this.socketChannel.close();
    }
    
    private boolean isHandShakeComplete() {
        final SSLEngineResult.HandshakeStatus status = this.sslEngine.getHandshakeStatus();
        return status == SSLEngineResult.HandshakeStatus.FINISHED || status == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }
    
    public SelectableChannel configureBlocking(final boolean b) throws IOException {
        return this.socketChannel.configureBlocking(b);
    }
    
    public boolean connect(final SocketAddress remote) throws IOException {
        return this.socketChannel.connect(remote);
    }
    
    public boolean finishConnect() throws IOException {
        return this.socketChannel.finishConnect();
    }
    
    public Socket socket() {
        return this.socketChannel.socket();
    }
    
    public boolean isInboundDone() {
        return this.sslEngine.isInboundDone();
    }
    
    @Override
    public boolean isOpen() {
        return this.socketChannel.isOpen();
    }
    
    @Override
    public boolean isNeedWrite() {
        return this.outCrypt.hasRemaining() || !this.isHandShakeComplete();
    }
    
    @Override
    public void writeMore() throws IOException {
        this.write(this.outCrypt);
    }
    
    @Override
    public boolean isNeedRead() {
        return this.saveCryptData != null || this.inData.hasRemaining() || (this.inCrypt.hasRemaining() && this.readEngineResult.getStatus() != SSLEngineResult.Status.BUFFER_UNDERFLOW && this.readEngineResult.getStatus() != SSLEngineResult.Status.CLOSED);
    }
    
    @Override
    public int readMore(final ByteBuffer dst) throws SSLException {
        return this.readRemaining(dst);
    }
    
    private int transfereTo(final ByteBuffer from, final ByteBuffer to) {
        final int fremain = from.remaining();
        final int toremain = to.remaining();
        if (fremain > toremain) {
            final int limit = Math.min(fremain, toremain);
            for (int i = 0; i < limit; ++i) {
                to.put(from.get());
            }
            return limit;
        }
        to.put(from);
        return fremain;
    }
    
    @Override
    public boolean isBlocking() {
        return this.socketChannel.isBlocking();
    }
    
    @Override
    public SSLEngine getSSLEngine() {
        return this.sslEngine;
    }
    
    private void saveCryptedData() {
        if (this.inCrypt != null && this.inCrypt.remaining() > 0) {
            final int saveCryptSize = this.inCrypt.remaining();
            this.saveCryptData = new byte[saveCryptSize];
            this.inCrypt.get(this.saveCryptData);
        }
    }
    
    private void tryRestoreCryptedData() {
        if (this.saveCryptData != null) {
            this.inCrypt.clear();
            this.inCrypt.put(this.saveCryptData);
            this.inCrypt.flip();
            this.saveCryptData = null;
        }
    }
    
    static {
        SSLSocketChannel2.emptybuffer = ByteBuffer.allocate(0);
    }
}
