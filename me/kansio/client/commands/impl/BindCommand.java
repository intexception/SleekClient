package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.*;
import me.kansio.client.modules.impl.*;
import org.lwjgl.input.*;
import java.util.*;

@CommandData(name = "bind", description = "Binds a module")
public class BindCommand extends Command
{
    @Override
    public void run(final String[] lIlIllIIIIllIl) {
        try {
            if (lIlIllIIIIllIl[0].equalsIgnoreCase("list")) {
                ChatUtil.log("The Current Binds Are:");
                for (final Module lIlIllIIIlIlIl : Client.getInstance().getModuleManager().getModules()) {
                    if (lIlIllIIIlIlIl.getKeyBind() != 0) {
                        ChatUtil.log(String.valueOf(new StringBuilder().append(lIlIllIIIlIlIl.getName()).append(" - ").append(Keyboard.getKeyName(lIlIllIIIlIlIl.getKeyBind()))));
                    }
                }
            }
            else if (lIlIllIIIIllIl[0].equalsIgnoreCase("del")) {
                final Module lIlIllIIIlIlII = Client.getInstance().getModuleManager().getModuleByNameIgnoreSpace(lIlIllIIIIllIl[2]);
                ChatUtil.log("Deleted the bind.");
                lIlIllIIIlIlII.setKeyBind(0, true);
            }
            else {
                final Module lIlIllIIIlIIlI = Client.getInstance().getModuleManager().getModuleByNameIgnoreSpace(lIlIllIIIIllIl[0]);
                if (lIlIllIIIlIIlI != null) {
                    final int lIlIllIIIlIIll = Keyboard.getKeyIndex(lIlIllIIIIllIl[1].toUpperCase());
                    if (lIlIllIIIlIIll != -1) {
                        ChatUtil.log(String.valueOf(new StringBuilder().append("You've set the bind to ").append(Keyboard.getKeyName(lIlIllIIIlIIll)).append(".")));
                        lIlIllIIIlIIlI.setKeyBind(lIlIllIIIlIIll, true);
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException lIlIllIIIlIIIl) {
            ChatUtil.log("Usage: bind [module] [key]");
            ChatUtil.log("Usage: bind del [module]");
        }
        catch (Exception lIlIllIIIlIIII) {
            lIlIllIIIlIIII.printStackTrace();
        }
    }
}
