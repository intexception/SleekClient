package me.kansio.client.value.value;

import me.kansio.client.value.*;
import me.kansio.client.modules.impl.*;

public final class NumberValue<T extends Number> extends Value<T>
{
    private final /* synthetic */ T max;
    private final /* synthetic */ T min;
    private final /* synthetic */ T increment;
    
    public T getMin() {
        return this.min;
    }
    
    public T getCastedValue() {
        return this.value;
    }
    
    @Override
    public void setValueAutoSave(final T lIlIllllIIIII) {
        this.value = lIlIllllIIIII;
    }
    
    public T getIncrement() {
        return this.increment;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public NumberValue(final String lIllIIIlllIll, final Module lIllIIIlllIlI, final T lIllIIIlllIIl, final T lIllIIIlllIII, final T lIllIIIllIIII, final T lIllIIIlIllll) {
        super(lIllIIIlllIll, lIllIIIlllIlI, lIllIIIlllIIl);
        this.value = lIllIIIlllIIl;
        this.min = lIllIIIlllIII;
        this.max = lIllIIIllIIII;
        this.increment = lIllIIIlIllll;
    }
    
    public NumberValue(final String lIllIIIIIllII, final Module lIllIIIIIIIlI, final T lIllIIIIIIIIl, final T lIllIIIIIIIII, final T lIlIlllllllll, final T lIlIllllllllI, final ModeValue lIlIlllllllIl, final String... lIllIIIIIIlIl) {
        super(lIllIIIIIllII, lIllIIIIIIIlI, lIllIIIIIIIIl, lIlIlllllllIl, lIllIIIIIIlIl);
        this.value = lIllIIIIIIIIl;
        this.min = lIllIIIIIIIII;
        this.max = lIlIlllllllll;
        this.increment = lIlIllllllllI;
    }
    
    @Override
    public boolean equals(final Object lIlIlllIllIlI) {
        return super.equals(lIlIlllIllIlI) || this.getValue().equals(lIlIlllIllIlI);
    }
    
    public boolean isInteger() {
        return this.min instanceof Integer;
    }
    
    @Override
    public T getValue() {
        return this.value;
    }
    
    public NumberValue(final String lIllIIIlIIlIl, final Module lIllIIIIlllII, final T lIllIIIIllIll, final T lIllIIIIllIlI, final T lIllIIIIllIIl, final T lIllIIIlIIIII, final BooleanValue lIllIIIIlIlll) {
        super(lIllIIIlIIlIl, lIllIIIIlllII, lIllIIIIllIll, lIllIIIIlIlll);
        this.value = lIllIIIIllIll;
        this.min = lIllIIIIllIlI;
        this.max = lIllIIIIllIIl;
        this.increment = lIllIIIlIIIII;
    }
    
    private void checkRetardMoment(final T lIlIlllllIlIl) {
        if (lIlIlllllIlIl.doubleValue() < this.min.doubleValue()) {
            try {
                throw new Exception("Retard Exception: Default Value < Min Value");
            }
            catch (Exception lIlIllllllIII) {
                lIlIllllllIII.printStackTrace();
            }
        }
        if (this.min.doubleValue() >= 0.0) {
            if (this.max.doubleValue() >= 0.0) {
                return;
            }
        }
        try {
            throw new Exception("Retard Exception: Min or Max Value below zero!");
        }
        catch (Exception lIlIlllllIlll) {
            lIlIlllllIlll.printStackTrace();
        }
    }
}
