package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.*;
import java.util.*;
import java.io.*;
import com.jagrosh.discordipc.entities.*;
import org.json.*;
import org.slf4j.*;

public class WindowsPipe extends Pipe
{
    private static final Logger LOGGER;
    private final RandomAccessFile file;
    
    WindowsPipe(final IPCClient ipcClient, final HashMap<String, Callback> callbacks, final String location) {
        super(ipcClient, callbacks);
        try {
            this.file = new RandomAccessFile(location, "rw");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void write(final byte[] b) throws IOException {
        this.file.write(b);
    }
    
    @Override
    public Packet read() throws IOException, JSONException {
        while (this.file.length() == 0L && this.status == PipeStatus.CONNECTED) {
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
        final Packet.OpCode op = Packet.OpCode.values()[Integer.reverseBytes(this.file.readInt())];
        final int len = Integer.reverseBytes(this.file.readInt());
        final byte[] d = new byte[len];
        this.file.readFully(d);
        final Packet p = new Packet(op, new JSONObject(new String(d)));
        WindowsPipe.LOGGER.debug(String.format("Received packet: %s", p.toString()));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, p);
        }
        return p;
    }
    
    @Override
    public void close() throws IOException {
        WindowsPipe.LOGGER.debug("Closing IPC pipe...");
        this.send(Packet.OpCode.CLOSE, new JSONObject(), null);
        this.status = PipeStatus.CLOSED;
        this.file.close();
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(WindowsPipe.class);
    }
}
