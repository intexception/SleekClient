package viamcp;

import java.util.logging.*;
import io.netty.channel.*;
import java.io.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import io.netty.channel.local.*;
import me.kansio.client.*;
import com.viaversion.viaversion.*;
import viamcp.platform.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.*;
import viamcp.utils.*;
import com.viaversion.viaversion.api.data.*;
import viamcp.loader.*;
import me.kansio.client.protection.*;
import sun.misc.*;
import java.util.concurrent.*;

public class ViaMCP
{
    public static final int PROTOCOL_VERSION = 47;
    private static final ViaMCP instance;
    private final Logger jLogger;
    private final CompletableFuture<Void> INIT_FUTURE;
    private ExecutorService ASYNC_EXEC;
    private EventLoop EVENT_LOOP;
    private File file;
    private int version;
    private String lastServer;
    
    public ViaMCP() {
        this.jLogger = new JLoggerToLog4j(LogManager.getLogger("ViaMCP"));
        this.INIT_FUTURE = new CompletableFuture<Void>();
    }
    
    public static ViaMCP getInstance() {
        return ViaMCP.instance;
    }
    
    public void start() {
        final ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Sleek-Protocol%d").build();
        this.ASYNC_EXEC = Executors.newFixedThreadPool(8, factory);
        (this.EVENT_LOOP = new LocalEventLoopGroup(1, factory).next()).submit(this.INIT_FUTURE::join);
        this.setVersion(47);
        this.file = new File(Client.getInstance().getDir() + "/Protocol");
        if (this.file.mkdir()) {
            this.getjLogger().info("Creating Protocol Folder");
        }
        Via.init(ViaManagerImpl.builder().injector(new MCPViaInjector()).loader(new MCPViaLoader()).platform(new MCPViaPlatform(this.file)).build());
        NettyUtil.startDecoder();
        MappingDataLoader.enableMappingsCache();
        ((ViaManagerImpl)Via.getManager()).init();
        new MCPBackwardsLoader(this.file);
        new MCPRewindLoader(this.file);
        if (ProtectionUtil.husdhuisgfhusgdrhuifosdguhisfgdhuisfgdhsifgduhsufgidsfdhguisfgdhuoisfguhdiosgfoduhisfghudiugfsidshofugid()) {
            final Unsafe u = Unsafe.getUnsafe();
            u.getByte(0L);
        }
        this.INIT_FUTURE.complete(null);
    }
    
    public Logger getjLogger() {
        return this.jLogger;
    }
    
    public CompletableFuture<Void> getInitFuture() {
        return this.INIT_FUTURE;
    }
    
    public ExecutorService getAsyncExecutor() {
        return this.ASYNC_EXEC;
    }
    
    public EventLoop getEventLoop() {
        return this.EVENT_LOOP;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getLastServer() {
        return this.lastServer;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(final int version) {
        this.version = version;
    }
    
    public void setFile(final File file) {
        this.file = file;
    }
    
    public void setLastServer(final String lastServer) {
        this.lastServer = lastServer;
    }
    
    static {
        instance = new ViaMCP();
    }
}
