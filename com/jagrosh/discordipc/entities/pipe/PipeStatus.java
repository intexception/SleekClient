package com.jagrosh.discordipc.entities.pipe;

public enum PipeStatus
{
    UNINITIALIZED, 
    CONNECTING, 
    CONNECTED, 
    CLOSED, 
    DISCONNECTED;
    
    private static /* synthetic */ PipeStatus[] $values() {
        return new PipeStatus[] { PipeStatus.UNINITIALIZED, PipeStatus.CONNECTING, PipeStatus.CONNECTED, PipeStatus.CLOSED, PipeStatus.DISCONNECTED };
    }
    
    static {
        $VALUES = $values();
    }
}
