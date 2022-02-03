package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.*;
import me.kansio.client.modules.*;
import me.kansio.client.modules.impl.*;

@CommandData(name = "toggle", description = "Binds a module", aliases = { "t" })
public class ToggleCommand extends Command
{
    @Override
    public void run(final String[] llllIllIll) {
        if (llllIllIll.length != 1) {
            ChatUtil.log("Specify the module you'd like to toggle please.");
            return;
        }
        final String llllIllIlI = llllIllIll[0];
        final ModuleManager llllIllIIl = Client.getInstance().getModuleManager();
        final Module llllIllIII = llllIllIIl.getModuleByNameIgnoreSpace(llllIllIlI);
        if (llllIllIII == null) {
            ChatUtil.log("That module doesn't exist.");
            return;
        }
        ChatUtil.log(String.valueOf(new StringBuilder().append("You've toggled ").append(llllIllIlI)));
        llllIllIII.toggle();
    }
}
