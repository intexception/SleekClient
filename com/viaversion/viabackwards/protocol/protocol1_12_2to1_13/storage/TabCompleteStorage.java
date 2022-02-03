package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.*;

public class TabCompleteStorage implements StorableObject
{
    private final Map<UUID, String> usernames;
    private final Set<String> commands;
    private int lastId;
    private String lastRequest;
    private boolean lastAssumeCommand;
    
    public TabCompleteStorage() {
        this.usernames = new HashMap<UUID, String>();
        this.commands = new HashSet<String>();
    }
    
    public Map<UUID, String> usernames() {
        return this.usernames;
    }
    
    public Set<String> commands() {
        return this.commands;
    }
    
    public int lastId() {
        return this.lastId;
    }
    
    public void setLastId(final int lastId) {
        this.lastId = lastId;
    }
    
    public String lastRequest() {
        return this.lastRequest;
    }
    
    public void setLastRequest(final String lastRequest) {
        this.lastRequest = lastRequest;
    }
    
    public boolean isLastAssumeCommand() {
        return this.lastAssumeCommand;
    }
    
    public void setLastAssumeCommand(final boolean lastAssumeCommand) {
        this.lastAssumeCommand = lastAssumeCommand;
    }
}
