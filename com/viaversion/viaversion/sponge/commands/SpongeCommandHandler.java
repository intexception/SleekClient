package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.api.command.*;
import org.spongepowered.api.command.*;
import org.spongepowered.api.world.*;
import java.util.*;
import org.spongepowered.api.text.*;

public class SpongeCommandHandler extends ViaCommandHandler implements CommandCallable
{
    public CommandResult process(final CommandSource source, final String arguments) throws CommandException {
        final String[] args = (arguments.length() > 0) ? arguments.split(" ") : new String[0];
        this.onCommand(new SpongeCommandSender(source), args);
        return CommandResult.success();
    }
    
    public List<String> getSuggestions(final CommandSource commandSource, final String s, final Location<World> location) throws CommandException {
        return this.getSuggestions(commandSource, s);
    }
    
    public List<String> getSuggestions(final CommandSource source, final String arguments) throws CommandException {
        final String[] args = arguments.split(" ", -1);
        return this.onTabComplete(new SpongeCommandSender(source), args);
    }
    
    public boolean testPermission(final CommandSource source) {
        return source.hasPermission("viaversion.admin");
    }
    
    public Optional<Text> getShortDescription(final CommandSource source) {
        return Optional.of((Text)Text.of("Shows ViaVersion Version and more."));
    }
    
    public Optional<Text> getHelp(final CommandSource source) {
        return Optional.empty();
    }
    
    public Text getUsage(final CommandSource source) {
        return (Text)Text.of("Usage /viaversion");
    }
}
