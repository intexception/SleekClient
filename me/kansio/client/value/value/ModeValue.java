package me.kansio.client.value.value;

import me.kansio.client.value.*;
import me.kansio.client.modules.impl.*;
import java.util.*;

public final class ModeValue extends Value<String>
{
    private final /* synthetic */ List<String> choicesLowerCase;
    private final /* synthetic */ List<String> choices;
    
    public List<String> getChoices() {
        return this.choices;
    }
    
    public List<String> getChoicesLowerCase() {
        return this.choicesLowerCase;
    }
    
    public ModeValue(final String lIllIIIIIIlIIl, final Module lIllIIIIIIlIII, final ModeValue lIllIIIIIIIlll, final String[] lIllIIIIIIllII, final String... lIllIIIIIIlIll) {
        super(lIllIIIIIIlIIl, lIllIIIIIIlIII, lIllIIIIIIlIll[0], lIllIIIIIIIlll, lIllIIIIIIllII);
        this.choices = new ArrayList<String>();
        Collections.addAll(this.choices, lIllIIIIIIlIll);
        this.choicesLowerCase = new ArrayList<String>();
        final float lIllIIIIIIIlII = (Object)lIllIIIIIIlIll;
        final byte lIllIIIIIIIIll = (byte)lIllIIIIIIIlII.length;
        for (long lIllIIIIIIIIlI = 0; lIllIIIIIIIIlI < lIllIIIIIIIIll; ++lIllIIIIIIIIlI) {
            final String lIllIIIIIlIIIl = lIllIIIIIIIlII[lIllIIIIIIIIlI];
            this.choicesLowerCase.add(lIllIIIIIlIIIl.toLowerCase());
        }
    }
    
    @Override
    public void setValueAutoSave(final String lIlIlllllIIIll) {
        if (this.choicesLowerCase.contains(lIlIlllllIIIll.toLowerCase())) {
            super.setValueAutoSave(lIlIlllllIIIll);
        }
    }
    
    public ModeValue(final String lIlIllllllIIII, final Module lIlIlllllIllll, final BooleanValue lIlIlllllIlllI, final String... lIlIlllllIllIl) {
        super(lIlIllllllIIII, lIlIlllllIllll, lIlIlllllIllIl[0], lIlIlllllIlllI);
        this.choices = new ArrayList<String>();
        Collections.addAll(this.choices, lIlIlllllIllIl);
        this.choicesLowerCase = new ArrayList<String>();
        final long lIlIlllllIllII = (Object)lIlIlllllIllIl;
        final String lIlIlllllIlIll = (String)lIlIlllllIllII.length;
        for (byte lIlIlllllIlIlI = 0; lIlIlllllIlIlI < lIlIlllllIlIll; ++lIlIlllllIlIlI) {
            final String lIlIllllllIlll = lIlIlllllIllII[lIlIlllllIlIlI];
            this.choicesLowerCase.add(lIlIllllllIlll.toLowerCase());
        }
    }
    
    public void forceSetValue(final String lIlIllllIlllIl) {
        super.setValueAutoSave(lIlIllllIlllIl);
    }
    
    @Override
    public boolean equals(final Object lIlIllllIlIIll) {
        return super.equals(lIlIllllIlIIll) || this.getValue().equals(lIlIllllIlIIll);
    }
    
    public ModeValue(final String lIllIIIIlIIIlI, final Module lIllIIIIlIIIIl, final String... lIllIIIIlIIlII) {
        super(lIllIIIIlIIIlI, lIllIIIIlIIIIl, lIllIIIIlIIlII[0]);
        this.choices = Arrays.asList(lIllIIIIlIIlII);
        this.choicesLowerCase = new ArrayList<String>();
        final long lIllIIIIIlllll = (Object)lIllIIIIlIIlII;
        final short lIllIIIIIllllI = (short)lIllIIIIIlllll.length;
        for (byte lIllIIIIIlllIl = 0; lIllIIIIIlllIl < lIllIIIIIllllI; ++lIllIIIIIlllIl) {
            final String lIllIIIIlIlIII = lIllIIIIIlllll[lIllIIIIIlllIl];
            this.choicesLowerCase.add(lIllIIIIlIlIII.toLowerCase());
        }
    }
}
