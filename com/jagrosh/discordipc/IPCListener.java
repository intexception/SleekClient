package com.jagrosh.discordipc;

import com.jagrosh.discordipc.entities.*;
import org.json.*;

public interface IPCListener
{
    default void onPacketSent(final IPCClient client, final Packet packet) {
    }
    
    default void onPacketReceived(final IPCClient client, final Packet packet) {
    }
    
    default void onActivityJoin(final IPCClient client, final String secret) {
    }
    
    default void onActivitySpectate(final IPCClient client, final String secret) {
    }
    
    default void onActivityJoinRequest(final IPCClient client, final String secret, final User user) {
    }
    
    default void onReady(final IPCClient client) {
    }
    
    default void onClose(final IPCClient client, final JSONObject json) {
    }
    
    default void onDisconnect(final IPCClient client, final Throwable t) {
    }
}
