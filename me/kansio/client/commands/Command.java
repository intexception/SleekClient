package me.kansio.client.commands;

import net.minecraft.client.*;

public abstract class Command
{
    public abstract void run(final String[] p0);
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
