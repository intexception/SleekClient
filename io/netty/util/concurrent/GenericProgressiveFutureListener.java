package io.netty.util.concurrent;

public interface GenericProgressiveFutureListener<F extends ProgressiveFuture<?>> extends GenericFutureListener<F>
{
    void operationProgressed(final F p0, final long p1, final long p2) throws Exception;
}
