package me.kansio.client.value.value;

import me.kansio.client.value.*;
import me.kansio.client.modules.impl.*;

public final class BooleanValue extends Value<Boolean>
{
    @Override
    public boolean equals(final Object llllllllllllllllllllIIlllIlllllI) {
        return this.getValue() == llllllllllllllllllllIIlllIlllllI || super.equals(llllllllllllllllllllIIlllIlllllI);
    }
    
    public BooleanValue(final String llllllllllllllllllllIIlllllIlllI, final Module llllllllllllllllllllIIlllllIllIl, final Boolean llllllllllllllllllllIIlllllIlIII) {
        super(llllllllllllllllllllIIlllllIlllI, llllllllllllllllllllIIlllllIllIl, llllllllllllllllllllIIlllllIlIII);
    }
    
    public BooleanValue(final String llllllllllllllllllllIIlllllIIIIl, final Module llllllllllllllllllllIIlllllIIIII, final Boolean llllllllllllllllllllIIllllIlllll, final Value llllllllllllllllllllIIllllIllIIl) {
        super(llllllllllllllllllllIIlllllIIIIl, llllllllllllllllllllIIlllllIIIII, llllllllllllllllllllIIllllIlllll);
        this.parent = (Value<?>)llllllllllllllllllllIIllllIllIIl;
    }
    
    public BooleanValue(final String llllllllllllllllllllIIllllIlIIIl, final Module llllllllllllllllllllIIllllIlIIII, final Boolean llllllllllllllllllllIIllllIIlIIl, final ModeValue llllllllllllllllllllIIllllIIlIII, final String... llllllllllllllllllllIIllllIIllIl) {
        super(llllllllllllllllllllIIllllIlIIIl, llllllllllllllllllllIIllllIlIIII, llllllllllllllllllllIIllllIIlIIl, llllllllllllllllllllIIllllIIlIII, llllllllllllllllllllIIllllIIllIl);
    }
    
    @Override
    public Boolean getValue() {
        return super.getValue();
    }
}
