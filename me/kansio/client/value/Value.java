package me.kansio.client.value;

import me.kansio.client.modules.impl.*;
import me.kansio.client.value.value.*;
import java.util.*;

public abstract class Value<Type>
{
    protected /* synthetic */ Value<?> parent;
    private final /* synthetic */ String name;
    protected /* synthetic */ Type value;
    protected /* synthetic */ List<String> modes;
    private final /* synthetic */ Module owner;
    
    public Value(final String llIlllIIIIl, final Module llIlllIIlIl, final Type llIllIlllll, final BooleanValue llIllIllllI) {
        this(llIlllIIIIl, llIlllIIlIl, llIllIlllll);
        this.parent = llIllIllllI;
    }
    
    public List<String> getModes() {
        return this.modes;
    }
    
    public Value getParent() {
        return this.parent;
    }
    
    public Value(final String llIllllIIIl, final Module llIllllIIII, final Type llIllllIlIl, final ModeValue llIlllIlllI, final String... llIllllIIll) {
        this(llIllllIIIl, llIllllIIII, llIllllIlIl);
        this.parent = llIlllIlllI;
        Collections.addAll(this.modes, llIllllIIll);
    }
    
    public Type getValue() {
        return this.value;
    }
    
    public boolean hasParent() {
        return this.parent != null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValueAsString() {
        return String.valueOf(this.value);
    }
    
    public Value(final String lllIIIIIIIl, final Module lllIIIIIlII, final Type llIllllllll) {
        this.modes = new ArrayList<String>();
        this.name = lllIIIIIIIl;
        this.owner = lllIIIIIlII;
        this.value = llIllllllll;
    }
    
    public Module getOwner() {
        return this.owner;
    }
    
    public void setValue(final Type llIllIIlIIl) {
        this.value = llIllIIlIIl;
    }
    
    public void setValueAutoSave(final Type llIllIlIIIl) {
        this.value = llIllIlIIIl;
    }
}
