package me.kansio.client.manager;

import me.kansio.client.value.*;
import java.util.*;

public class ValueManager extends Manager<Value>
{
    public List<Value> getValuesFromOwner(final Object llIlIIIIllIIIl) {
        final List<Value> llIlIIIIllIIII = new ArrayList<Value>();
        for (final Value llIlIIIIllIIll : this.getObjects()) {
            if (llIlIIIIllIIll.getOwner() == llIlIIIIllIIIl) {
                llIlIIIIllIIII.add(llIlIIIIllIIll);
            }
        }
        return llIlIIIIllIIII;
    }
    
    public ValueManager() {
        super(new ArrayList());
    }
    
    public <T extends Value> T getValueFromOwner(final Object llIlIIIIllllll, final String llIlIIIIlllIll) {
        for (final Value<?> llIlIIIlIIIIIl : this.getObjects()) {
            if (llIlIIIlIIIIIl.getOwner() == llIlIIIIllllll && llIlIIIlIIIIIl.getName().equalsIgnoreCase(llIlIIIIlllIll)) {
                return (T)llIlIIIlIIIIIl;
            }
        }
        return null;
    }
    
    public boolean hasValues(final Object llIlIIIIlIIlII) {
        for (final Value llIlIIIIlIIllI : this.getObjects()) {
            if (llIlIIIIlIIllI.getOwner() == llIlIIIIlIIlII) {
                return true;
            }
        }
        return false;
    }
}
