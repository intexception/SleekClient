package me.kansio.client.value.value;

import me.kansio.client.value.*;
import me.kansio.client.modules.impl.*;

public class StringValue extends Value<String>
{
    /* synthetic */ String value;
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String llIIIllIIlI) {
        this.value = llIIIllIIlI;
    }
    
    public StringValue(final String llIIlIIlIIl, final Module llIIlIIIlll, final String llIIlIIIllI) {
        super(llIIlIIlIIl, llIIlIIIlll, llIIlIIIllI);
        this.value = llIIlIIIllI;
    }
}
