package me.kansio.client.commands;

import java.util.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.chat.*;
import com.google.common.eventbus.*;
import me.kansio.client.commands.impl.*;
import me.kansio.client.*;

public class CommandManager
{
    private /* synthetic */ ArrayList<Command> commands;
    
    @Subscribe
    public void callCommand(final ChatEvent lIIIIIIIIIllll) {
        if (!lIIIIIIIIIllll.getMessage().startsWith(".")) {
            return;
        }
        lIIIIIIIIIllll.setCancelled(true);
        final String lIIIIIIIIIlllI = lIIIIIIIIIllll.getMessage();
        final String[] lIIIIIIIIIllIl = lIIIIIIIIIlllI.split(" ");
        final String lIIIIIIIIIllII = lIIIIIIIIIllIl[0];
        final String lIIIIIIIIIlIll = lIIIIIIIIIlllI.substring(lIIIIIIIIIllII.length()).trim();
        for (final Command lIIIIIIIIlIIIl : this.commands) {
            final String lIIIIIIIIlIIlI = String.valueOf(new StringBuilder().append(".").append(lIIIIIIIIlIIIl.getClass().getAnnotation(CommandData.class).name()));
            if (lIIIIIIIIlIIlI.equalsIgnoreCase(lIIIIIIIIIllII)) {
                try {
                    lIIIIIIIIlIIIl.run(lIIIIIIIIIlIll.split(" "));
                }
                catch (Exception lIIIIIIIIlIllI) {
                    ChatUtil.log("Invalid command usage.");
                }
                break;
            }
            final String[] aliases = lIIIIIIIIlIIIl.getClass().getAnnotation(CommandData.class).aliases();
            final double lIIIIIIIIIIIII = aliases.length;
            for (final String lIIIIIIIIlIIll : aliases) {
                final String lIIIIIIIIlIlII = String.valueOf(new StringBuilder().append(".").append(lIIIIIIIIlIIll));
                if (lIIIIIIIIlIlII.equalsIgnoreCase(lIIIIIIIIIllII)) {
                    try {
                        lIIIIIIIIlIIIl.run(lIIIIIIIIIlIll.split(" "));
                    }
                    catch (Exception lIIIIIIIIlIlIl) {
                        ChatUtil.log("Invalid command usage.");
                    }
                    break;
                }
            }
        }
    }
    
    public void clearCommands() {
        this.commands.clear();
    }
    
    public ArrayList<Command> getCommands() {
        return this.commands;
    }
    
    public void registerCommands() {
        this.commands.add(new ToggleCommand());
        this.commands.add(new ReloadCommand());
        this.commands.add(new BindCommand());
        this.commands.add(new UsersCommand());
        this.commands.add(new BindsCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new VClipCommand());
        this.commands.add(new FriendCommand());
        this.commands.add(new FocusCommand());
        this.commands.add(new NameCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new HClipCommand());
    }
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
        this.registerCommands();
        Client.getInstance().getEventBus().register((Object)this);
    }
}
