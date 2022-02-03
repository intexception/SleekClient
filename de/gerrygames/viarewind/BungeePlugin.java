package de.gerrygames.viarewind;

import net.md_5.bungee.api.plugin.*;
import java.io.*;
import de.gerrygames.viarewind.api.*;

public class BungeePlugin extends Plugin implements ViaRewindPlatform
{
    public void onEnable() {
        final ViaRewindConfigImpl conf = new ViaRewindConfigImpl(new File(this.getDataFolder(), "config.yml"));
        conf.reloadConfig();
        this.init(conf);
    }
}
