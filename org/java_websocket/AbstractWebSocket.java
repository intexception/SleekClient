package org.java_websocket;

import org.slf4j.*;
import java.util.*;
import org.java_websocket.util.*;
import java.util.concurrent.*;

public abstract class AbstractWebSocket extends WebSocketAdapter
{
    private final Logger log;
    private boolean tcpNoDelay;
    private boolean reuseAddr;
    private ScheduledExecutorService connectionLostCheckerService;
    private ScheduledFuture<?> connectionLostCheckerFuture;
    private long connectionLostTimeout;
    private boolean websocketRunning;
    private final Object syncConnectionLost;
    
    public AbstractWebSocket() {
        this.log = LoggerFactory.getLogger(AbstractWebSocket.class);
        this.connectionLostTimeout = TimeUnit.SECONDS.toNanos(60L);
        this.websocketRunning = false;
        this.syncConnectionLost = new Object();
    }
    
    public int getConnectionLostTimeout() {
        synchronized (this.syncConnectionLost) {
            return (int)TimeUnit.NANOSECONDS.toSeconds(this.connectionLostTimeout);
        }
    }
    
    public void setConnectionLostTimeout(final int connectionLostTimeout) {
        synchronized (this.syncConnectionLost) {
            this.connectionLostTimeout = TimeUnit.SECONDS.toNanos(connectionLostTimeout);
            if (this.connectionLostTimeout <= 0L) {
                this.log.trace("Connection lost timer stopped");
                this.cancelConnectionLostTimer();
                return;
            }
            if (this.websocketRunning) {
                this.log.trace("Connection lost timer restarted");
                try {
                    final ArrayList<WebSocket> connections = new ArrayList<WebSocket>(this.getConnections());
                    for (final WebSocket conn : connections) {
                        if (conn instanceof WebSocketImpl) {
                            final WebSocketImpl webSocketImpl = (WebSocketImpl)conn;
                            webSocketImpl.updateLastPong();
                        }
                    }
                }
                catch (Exception e) {
                    this.log.error("Exception during connection lost restart", e);
                }
                this.restartConnectionLostTimer();
            }
        }
    }
    
    protected void stopConnectionLostTimer() {
        synchronized (this.syncConnectionLost) {
            if (this.connectionLostCheckerService != null || this.connectionLostCheckerFuture != null) {
                this.websocketRunning = false;
                this.log.trace("Connection lost timer stopped");
                this.cancelConnectionLostTimer();
            }
        }
    }
    
    protected void startConnectionLostTimer() {
        synchronized (this.syncConnectionLost) {
            if (this.connectionLostTimeout <= 0L) {
                this.log.trace("Connection lost timer deactivated");
                return;
            }
            this.log.trace("Connection lost timer started");
            this.websocketRunning = true;
            this.restartConnectionLostTimer();
        }
    }
    
    private void restartConnectionLostTimer() {
        this.cancelConnectionLostTimer();
        this.connectionLostCheckerService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("connectionLostChecker"));
        final Runnable connectionLostChecker = new Runnable() {
            private ArrayList<WebSocket> connections = new ArrayList<WebSocket>();
            
            @Override
            public void run() {
                this.connections.clear();
                try {
                    this.connections.addAll(AbstractWebSocket.this.getConnections());
                    final long minimumPongTime;
                    synchronized (AbstractWebSocket.this.syncConnectionLost) {
                        minimumPongTime = (long)(System.nanoTime() - AbstractWebSocket.this.connectionLostTimeout * 1.5);
                    }
                    for (final WebSocket conn : this.connections) {
                        AbstractWebSocket.this.executeConnectionLostDetection(conn, minimumPongTime);
                    }
                }
                catch (Exception ex) {}
                this.connections.clear();
            }
        };
        this.connectionLostCheckerFuture = this.connectionLostCheckerService.scheduleAtFixedRate(connectionLostChecker, this.connectionLostTimeout, this.connectionLostTimeout, TimeUnit.NANOSECONDS);
    }
    
    private void executeConnectionLostDetection(final WebSocket webSocket, final long minimumPongTime) {
        if (!(webSocket instanceof WebSocketImpl)) {
            return;
        }
        final WebSocketImpl webSocketImpl = (WebSocketImpl)webSocket;
        if (webSocketImpl.getLastPong() < minimumPongTime) {
            this.log.trace("Closing connection due to no pong received: {}", webSocketImpl);
            webSocketImpl.closeConnection(1006, "The connection was closed because the other endpoint did not respond with a pong in time. For more information check: https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection");
        }
        else if (webSocketImpl.isOpen()) {
            webSocketImpl.sendPing();
        }
        else {
            this.log.trace("Trying to ping a non open connection: {}", webSocketImpl);
        }
    }
    
    protected abstract Collection<WebSocket> getConnections();
    
    private void cancelConnectionLostTimer() {
        if (this.connectionLostCheckerService != null) {
            this.connectionLostCheckerService.shutdownNow();
            this.connectionLostCheckerService = null;
        }
        if (this.connectionLostCheckerFuture != null) {
            this.connectionLostCheckerFuture.cancel(false);
            this.connectionLostCheckerFuture = null;
        }
    }
    
    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }
    
    public void setTcpNoDelay(final boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }
    
    public boolean isReuseAddr() {
        return this.reuseAddr;
    }
    
    public void setReuseAddr(final boolean reuseAddr) {
        this.reuseAddr = reuseAddr;
    }
}
