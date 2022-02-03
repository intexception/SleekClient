package org.java_websocket.util;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

public class NamedThreadFactory implements ThreadFactory
{
    private final ThreadFactory defaultThreadFactory;
    private final AtomicInteger threadNumber;
    private final String threadPrefix;
    
    public NamedThreadFactory(final String threadPrefix) {
        this.defaultThreadFactory = Executors.defaultThreadFactory();
        this.threadNumber = new AtomicInteger(1);
        this.threadPrefix = threadPrefix;
    }
    
    @Override
    public Thread newThread(final Runnable runnable) {
        final Thread thread = this.defaultThreadFactory.newThread(runnable);
        thread.setName(this.threadPrefix + "-" + this.threadNumber);
        return thread;
    }
}
