package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.*;
import me.kansio.client.modules.impl.*;
import org.lwjgl.input.*;

@CommandData(name = "binds", description = "Lists binds")
public class BindsCommand extends Command
{
    @Override
    public void run(final String[] lIlIlIIllIIllI) {
        ChatUtil.log("The Current Binds Are:");
        for (final Module lIlIlIIllIlIII : Client.getInstance().getModuleManager().getModules()) {
            if (lIlIlIIllIlIII.getKeyBind() != 0) {
                ChatUtil.log(String.valueOf(new StringBuilder().append(lIlIlIIllIlIII.getName()).append(" - ").append(Keyboard.getKeyName(lIlIlIIllIlIII.getKeyBind()))));
            }
        }
    }
}
