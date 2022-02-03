package com.viaversion.viaversion.api.platform;

public interface PlatformTask<T>
{
    T getObject();
    
    void cancel();
}
