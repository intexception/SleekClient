package me.kansio.client.value.value;

import me.kansio.client.value.*;
import java.util.*;

public class SubSettings
{
    private /* synthetic */ ArrayList<Value<?>> subSettings;
    private /* synthetic */ String name;
    
    public ArrayList<Value<?>> getSubSettings() {
        return this.subSettings;
    }
    
    public void setName(final String lIllIlllIlIllI) {
        this.name = lIllIlllIlIllI;
    }
    
    public SubSettings(final String lIllIllllIIlII, final Value<?>... lIllIllllIIIll) {
        this.subSettings = new ArrayList<Value<?>>();
        this.name = lIllIllllIIlII;
        Collections.addAll(this.subSettings, lIllIllllIIIll);
    }
    
    public String getName() {
        return this.name;
    }
}
