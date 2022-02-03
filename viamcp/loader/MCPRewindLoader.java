package viamcp.loader;

import java.io.*;
import de.gerrygames.viarewind.api.*;
import java.util.logging.*;
import com.viaversion.viaversion.api.*;

public class MCPRewindLoader implements ViaRewindPlatform
{
    public MCPRewindLoader(final File file) {
        final ViaRewindConfigImpl conf = new ViaRewindConfigImpl(file.toPath().resolve("ViaRewind").resolve("config.yml").toFile());
        conf.reloadConfig();
        this.init(conf);
    }
    
    @Override
    public Logger getLogger() {
        return Via.getPlatform().getLogger();
    }
}
