package viamcp.loader;

import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.*;
import com.viaversion.viaversion.bungee.providers.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.protocols.base.*;
import com.viaversion.viaversion.api.connection.*;
import viamcp.*;

public class MCPViaLoader implements ViaPlatformLoader
{
    @Override
    public void load() {
        Via.getManager().getProviders().use((Class<BungeeMovementTransmitter>)MovementTransmitterProvider.class, new BungeeMovementTransmitter());
        Via.getManager().getProviders().use((Class<MCPViaLoader$1>)VersionProvider.class, new BaseVersionProvider() {
            @Override
            public int getClosestServerProtocol(final UserConnection connection) throws Exception {
                if (connection.isClientSide()) {
                    return ViaMCP.getInstance().getVersion();
                }
                return super.getClosestServerProtocol(connection);
            }
        });
    }
    
    @Override
    public void unload() {
    }
}
