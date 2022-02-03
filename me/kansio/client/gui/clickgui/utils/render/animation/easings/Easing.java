package me.kansio.client.gui.clickgui.utils.render.animation.easings;

public interface Easing
{
    public static final /* synthetic */ Easing BOUNCE_IN = (llllllllllllllllllllllllIIIIIllI, llllllllllllllllllllllllIIIIIIIl, llllllllllllllllllllllllIIIIIlII, lllllllllllllllllllllllIllllllll) -> llllllllllllllllllllllllIIIIIlII - Easing.BOUNCE_OUT.ease(lllllllllllllllllllllllIllllllll - llllllllllllllllllllllllIIIIIllI, 0.0f, llllllllllllllllllllllllIIIIIlII, lllllllllllllllllllllllIllllllll) + llllllllllllllllllllllllIIIIIIIl;
    public static final /* synthetic */ Easing BOUNCE_OUT = (lllllllllllllllllllllllIllllIllI, lllllllllllllllllllllllIlllllIIl, lllllllllllllllllllllllIllllIlII, lllllllllllllllllllllllIllllIlll) -> {
        lllllllllllllllllllllllIllllIllI /= lllllllllllllllllllllllIllllIlll;
        if (o27 < 0.36363637f) {
            return lllllllllllllllllllllllIllllIlII * (7.5625f * lllllllllllllllllllllllIllllIllI * lllllllllllllllllllllllIllllIllI) + lllllllllllllllllllllllIlllllIIl;
        }
        else if (lllllllllllllllllllllllIllllIllI < 0.72727275f) {
            lllllllllllllllllllllllIllllIllI -= 0.54545456f;
            return lllllllllllllllllllllllIllllIlII * (o28 * o29 * lllllllllllllllllllllllIllllIllI + 0.75f) + lllllllllllllllllllllllIlllllIIl;
        }
        else if (lllllllllllllllllllllllIllllIllI < 0.90909094f) {
            lllllllllllllllllllllllIllllIllI -= 0.8181818f;
            return lllllllllllllllllllllllIllllIlII * (o30 * o31 * lllllllllllllllllllllllIllllIllI + 0.9375f) + lllllllllllllllllllllllIlllllIIl;
        }
        else {
            lllllllllllllllllllllllIllllIllI -= 0.95454544f;
            return lllllllllllllllllllllllIllllIlII * (o32 * o33 * lllllllllllllllllllllllIllllIllI + 0.984375f) + lllllllllllllllllllllllIlllllIIl;
        }
    };
    
    float ease(final float p0, final float p1, final float p2, final float p3);
    
    default static {
        final float n;
        final Object o;
        final Object o2;
        final Object o3;
        final float n2;
        final float n3;
        final float n4;
        final Object o4;
        final Object o5;
        final Object o6;
        final Object o7;
        final float n5;
        final Object o8;
        final Object o9;
        final Object o10;
        final Object o11;
        final Object o12;
        final float n6;
        final Object o13;
        final Object o14;
        final Object o15;
        final Object o16;
        final Object o17;
        final float n7;
        final double n8;
        final Object o18;
        final Object o19;
        final float n9;
        final Object o20;
        final Object o21;
        final Object o22;
        final Object o23;
        final Object o24;
        final float n10;
        final Object o25;
        final Object o26;
        final Object o27;
        final Object o28;
        final Object o29;
        final Object o30;
        final Object o31;
        final Object o32;
        final Object o33;
    }
    
    public static class BackInOut extends Back
    {
        public BackInOut(final float lIlIlIlIlIIlI) {
            super(lIlIlIlIlIIlI);
        }
        
        @Override
        public float ease(float lIlIlIlIIIlII, final float lIlIlIlIIIIll, final float lIlIlIlIIlIII, final float lIlIlIlIIIIIl) {
            float lIlIlIlIIIllI = this.getOvershoot();
            if ((lIlIlIlIIIlII /= (Exception)(lIlIlIlIIIIIl / 2.0f)) < 1.0f) {
                return (float)(lIlIlIlIIlIII / 2.0f * (lIlIlIlIIIlII * lIlIlIlIIIlII * (((lIlIlIlIIIllI *= (float)1.525) + 1.0f) * lIlIlIlIIIlII - lIlIlIlIIIllI)) + lIlIlIlIIIIll);
            }
            return (float)(lIlIlIlIIlIII / 2.0f * ((lIlIlIlIIIlII -= (Exception)2.0f) * lIlIlIlIIIlII * (((lIlIlIlIIIllI *= (float)1.525) + 1.0f) * lIlIlIlIIIlII + lIlIlIlIIIllI) + 2.0f) + lIlIlIlIIIIll);
        }
        
        public BackInOut() {
        }
    }
    
    public abstract static class Back implements Easing
    {
        private /* synthetic */ float overshoot;
        
        public float getOvershoot() {
            return this.overshoot;
        }
        
        public void setOvershoot(final float lIIIIIIIllllIl) {
            this.overshoot = lIIIIIIIllllIl;
        }
        
        public Back() {
            this(1.70158f);
        }
        
        public Back(final float lIIIIIIlIIIllI) {
            this.overshoot = lIIIIIIlIIIllI;
        }
    }
    
    public static class BackOut extends Back
    {
        @Override
        public float ease(float lllllllllllllllllllllIIllIIlIllI, final float lllllllllllllllllllllIIllIIllIll, final float lllllllllllllllllllllIIllIIllIlI, final float lllllllllllllllllllllIIllIIlIIll) {
            final float lllllllllllllllllllllIIllIIllIII = this.getOvershoot();
            return lllllllllllllllllllllIIllIIllIlI * ((((lllllllllllllllllllllIIllIIlIllI = ((lllllllllllllllllllllIIllIIlIllI ? 1 : 0) / lllllllllllllllllllllIIllIIlIIll - 1.0f != 0.0f)) * lllllllllllllllllllllIIllIIlIllI) ? 1 : 0) * ((lllllllllllllllllllllIIllIIllIII + 1.0f) * (lllllllllllllllllllllIIllIIlIllI ? 1 : 0) + lllllllllllllllllllllIIllIIllIII) + 1.0f) + lllllllllllllllllllllIIllIIllIll;
        }
        
        public BackOut(final float lllllllllllllllllllllIIllIlIIlII) {
            super(lllllllllllllllllllllIIllIlIIlII);
        }
        
        public BackOut() {
        }
    }
    
    public static class ElasticInOut extends Elastic
    {
        @Override
        public float ease(float lIIllIlIIlllll, final float lIIllIlIlIIllI, final float lIIllIlIIlllIl, final float lIIllIlIIlllII) {
            float lIIllIlIlIIIll = this.getAmplitude();
            float lIIllIlIlIIIlI = this.getPeriod();
            if (lIIllIlIIlllll == 0.0f) {
                return lIIllIlIlIIllI;
            }
            if ((lIIllIlIIlllll /= lIIllIlIIlllII / 2.0f) == 2.0f) {
                return lIIllIlIlIIllI + lIIllIlIIlllIl;
            }
            if (lIIllIlIlIIIlI == 0.0f) {
                lIIllIlIlIIIlI = lIIllIlIIlllII * 0.45000002f;
            }
            float lIIllIlIlIIIIl = 0.0f;
            if (lIIllIlIlIIIll < Math.abs(lIIllIlIIlllIl)) {
                lIIllIlIlIIIll = lIIllIlIIlllIl;
                lIIllIlIlIIIIl = lIIllIlIlIIIlI / 4.0f;
            }
            else {
                lIIllIlIlIIIIl = lIIllIlIlIIIlI / 6.2831855f * (float)Math.asin(lIIllIlIIlllIl / lIIllIlIlIIIll);
            }
            if (lIIllIlIIlllll < 1.0f) {
                return -0.5f * (lIIllIlIlIIIll * (float)Math.pow(2.0, 10.0f * --lIIllIlIIlllll) * (float)Math.sin((lIIllIlIIlllll * lIIllIlIIlllII - lIIllIlIlIIIIl) * 6.283185307179586 / lIIllIlIlIIIlI)) + lIIllIlIlIIllI;
            }
            return lIIllIlIlIIIll * (float)Math.pow(2.0, -10.0f * --lIIllIlIIlllll) * (float)Math.sin((lIIllIlIIlllll * lIIllIlIIlllII - lIIllIlIlIIIIl) * 6.283185307179586 / lIIllIlIlIIIlI) * 0.5f + lIIllIlIIlllIl + lIIllIlIlIIllI;
        }
        
        public ElasticInOut() {
        }
        
        public ElasticInOut(final float lIIllIlIllIlIl, final float lIIllIlIllIlII) {
            super(lIIllIlIllIlIl, lIIllIlIllIlII);
        }
    }
    
    public abstract static class Elastic implements Easing
    {
        private /* synthetic */ float amplitude;
        private /* synthetic */ float period;
        
        public void setAmplitude(final float llllllllllllllllllllIlllIIllIlII) {
            this.amplitude = llllllllllllllllllllIlllIIllIlII;
        }
        
        public float getAmplitude() {
            return this.amplitude;
        }
        
        public void setPeriod(final float llllllllllllllllllllIlllIIlllIll) {
            this.period = llllllllllllllllllllIlllIIlllIll;
        }
        
        public float getPeriod() {
            return this.period;
        }
        
        public Elastic() {
            this(-1.0f, 0.0f);
        }
        
        public Elastic(final float llllllllllllllllllllIlllIlIIlIII, final float llllllllllllllllllllIlllIlIIlIlI) {
            this.amplitude = llllllllllllllllllllIlllIlIIlIII;
            this.period = llllllllllllllllllllIlllIlIIlIlI;
        }
    }
    
    public static class ElasticOut extends Elastic
    {
        @Override
        public float ease(float lIllIlIlIllIlI, final float lIllIlIlIlIIIl, final float lIllIlIlIlIIII, final float lIllIlIlIlIlll) {
            float lIllIlIlIlIllI = this.getAmplitude();
            float lIllIlIlIlIlIl = this.getPeriod();
            if (lIllIlIlIllIlI == 0.0f) {
                return lIllIlIlIlIIIl;
            }
            if ((lIllIlIlIllIlI /= lIllIlIlIlIlll) == 1.0f) {
                return lIllIlIlIlIIIl + lIllIlIlIlIIII;
            }
            if (lIllIlIlIlIlIl == 0.0f) {
                lIllIlIlIlIlIl = lIllIlIlIlIlll * 0.3f;
            }
            float lIllIlIlIlIlII = 0.0f;
            if (lIllIlIlIlIllI < Math.abs(lIllIlIlIlIIII)) {
                lIllIlIlIlIllI = lIllIlIlIlIIII;
                lIllIlIlIlIlII = lIllIlIlIlIlIl / 4.0f;
            }
            else {
                lIllIlIlIlIlII = lIllIlIlIlIlIl / 6.2831855f * (float)Math.asin(lIllIlIlIlIIII / lIllIlIlIlIllI);
            }
            return lIllIlIlIlIllI * (float)Math.pow(2.0, -10.0f * lIllIlIlIllIlI) * (float)Math.sin((lIllIlIlIllIlI * lIllIlIlIlIlll - lIllIlIlIlIlII) * 6.283185307179586 / lIllIlIlIlIlIl) + lIllIlIlIlIIII + lIllIlIlIlIIIl;
        }
        
        public ElasticOut() {
        }
        
        public ElasticOut(final float lIllIlIllIlIII, final float lIllIlIllIIlll) {
            super(lIllIlIllIlIII, lIllIlIllIIlll);
        }
    }
    
    public static class BackIn extends Back
    {
        @Override
        public float ease(float llIIIllIIllII, final float llIIIllIlIIIl, final float llIIIllIlIIII, final float llIIIllIIllll) {
            final float llIIIllIIlllI = this.getOvershoot();
            return llIIIllIlIIII * (llIIIllIIllII /= (int)llIIIllIIllll) * llIIIllIIllII * ((llIIIllIIlllI + 1.0f) * llIIIllIIllII - llIIIllIIlllI) + llIIIllIlIIIl;
        }
        
        public BackIn(final float llIIIllIllIlI) {
            super(llIIIllIllIlI);
        }
        
        public BackIn() {
        }
    }
    
    public static class ElasticIn extends Elastic
    {
        public ElasticIn(final float llIIlllllI, final float llIIlllIlI) {
            super(llIIlllllI, llIIlllIlI);
        }
        
        public ElasticIn() {
        }
        
        @Override
        public float ease(float llIIlIllIl, final float llIIlIllII, final float llIIlIIIll, final float llIIlIIIlI) {
            float llIIlIlIIl = this.getAmplitude();
            float llIIlIlIII = this.getPeriod();
            if (llIIlIllIl == 0.0f) {
                return llIIlIllII;
            }
            if ((llIIlIllIl /= llIIlIIIlI) == 1.0f) {
                return llIIlIllII + llIIlIIIll;
            }
            if (llIIlIlIII == 0.0f) {
                llIIlIlIII = llIIlIIIlI * 0.3f;
            }
            float llIIlIIlll = 0.0f;
            if (llIIlIlIIl < Math.abs(llIIlIIIll)) {
                llIIlIlIIl = llIIlIIIll;
                llIIlIIlll = llIIlIlIII / 4.0f;
            }
            else {
                llIIlIIlll = llIIlIlIII / 6.2831855f * (float)Math.asin(llIIlIIIll / llIIlIlIIl);
            }
            return -(llIIlIlIIl * (float)Math.pow(2.0, 10.0f * --llIIlIllIl) * (float)Math.sin((llIIlIllIl * llIIlIIIlI - llIIlIIlll) * 6.283185307179586 / llIIlIlIII)) + llIIlIllII;
        }
    }
}
