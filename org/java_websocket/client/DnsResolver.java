package org.java_websocket.client;

import java.net.*;

public interface DnsResolver
{
    InetAddress resolve(final URI p0) throws UnknownHostException;
}
