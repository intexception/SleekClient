package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.*;
import java.util.*;
import org.newsclub.net.unix.*;
import java.net.*;
import com.jagrosh.discordipc.entities.*;
import java.nio.*;
import java.io.*;
import org.json.*;
import org.slf4j.*;

public class UnixPipe extends Pipe
{
    private static final Logger LOGGER;
    private final AFUNIXSocket socket;
    
    UnixPipe(final IPCClient ipcClient, final HashMap<String, Callback> callbacks, final String location) throws IOException {
        super(ipcClient, callbacks);
        (this.socket = AFUNIXSocket.newInstance()).connect((SocketAddress)new AFUNIXSocketAddress(new File(location)));
    }
    
    @Override
    public Packet read() throws IOException, JSONException {
        final InputStream is = this.socket.getInputStream();
        while (is.available() == 0 && this.status == PipeStatus.CONNECTED) {
            try {
                Thread.sleep(50L);
            }
            catch (InterruptedException ex) {}
        }
        if (this.status == PipeStatus.DISCONNECTED) {
            throw new IOException("Disconnected!");
        }
        if (this.status == PipeStatus.CLOSED) {
            return new Packet(Packet.OpCode.CLOSE, null);
        }
        byte[] d = new byte[8];
        is.read(d);
        final ByteBuffer bb = ByteBuffer.wrap(d);
        final Packet.OpCode op = Packet.OpCode.values()[Integer.reverseBytes(bb.getInt())];
        d = new byte[Integer.reverseBytes(bb.getInt())];
        is.read(d);
        final Packet p = new Packet(op, new JSONObject(new String(d)));
        UnixPipe.LOGGER.debug(String.format("Received packet: %s", p.toString()));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, p);
        }
        return p;
    }
    
    @Override
    public void write(final byte[] b) throws IOException {
        this.socket.getOutputStream().write(b);
    }
    
    @Override
    public void close() throws IOException {
        UnixPipe.LOGGER.debug("Closing IPC pipe...");
        this.send(Packet.OpCode.CLOSE, new JSONObject(), null);
        this.status = PipeStatus.CLOSED;
        this.socket.close();
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(UnixPipe.class);
    }
}
