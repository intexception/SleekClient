package com.viaversion.viabackwards;

import net.md_5.bungee.api.plugin.*;
import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.*;

public class BungeePlugin extends Plugin implements ViaBackwardsPlatform
{
    public void onLoad() {
        Via.getManager().addEnableListener(() -> this.init(this.getDataFolder()));
    }
    
    public void disable() {
    }
}
