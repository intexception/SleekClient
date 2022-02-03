package io.netty.util.concurrent;

import java.util.concurrent.*;

final class DefaultEventExecutor extends SingleThreadEventExecutor
{
    DefaultEventExecutor(final DefaultEventExecutorGroup parent, final ThreadFactory threadFactory) {
        super(parent, threadFactory, true);
    }
    
    @Override
    protected void run() {
        do {
            final Runnable task = this.takeTask();
            if (task != null) {
                task.run();
                this.updateLastExecutionTime();
            }
        } while (!this.confirmShutdown());
    }
}
