package me.kansio.client.gui.clickgui.utils.render.animation.easings;

public class Animate
{
    private /* synthetic */ float max;
    private /* synthetic */ float value;
    private /* synthetic */ float min;
    private /* synthetic */ boolean reversed;
    private /* synthetic */ float speed;
    private /* synthetic */ float time;
    private /* synthetic */ Easing ease;
    
    public Animate setMax(final float lIIllIllII) {
        this.max = lIIllIllII;
        return this;
    }
    
    public Animate setMin(final float lIIlllIIlI) {
        this.min = lIIlllIIlI;
        return this;
    }
    
    public Animate() {
        this.ease = Easing.LINEAR;
        this.value = 0.0f;
        this.min = 0.0f;
        this.max = 1.0f;
        this.speed = 50.0f;
        this.reversed = false;
    }
    
    public Animate setValue(final float lIIlllIllI) {
        this.value = lIIlllIllI;
        return this;
    }
    
    public Easing getEase() {
        return this.ease;
    }
    
    private float clamp(final float lIIlIIIIIl, final float lIIlIIIIII, final float lIIIllllll) {
        return (lIIlIIIIIl < lIIlIIIIII) ? lIIlIIIIII : ((lIIlIIIIIl > lIIIllllll) ? lIIIllllll : lIIlIIIIIl);
    }
    
    public float getValue() {
        return this.value;
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public Animate update() {
        if (this.reversed) {
            if (this.time > this.min) {
                this.time -= Delta.DELTATIME * 0.001f * this.speed;
            }
        }
        else if (this.time < this.max) {
            this.time += Delta.DELTATIME * 0.001f * this.speed;
        }
        this.time = this.clamp(this.time, this.min, this.max);
        this.value = this.getEase().ease(this.time, this.min, this.max, this.max);
        return this;
    }
    
    public Animate setSpeed(final float lIIllIIlII) {
        this.speed = lIIllIIlII;
        return this;
    }
    
    public Animate setEase(final Easing lIIlIllIlI) {
        this.ease = lIIlIllIlI;
        return this;
    }
    
    public Animate setReversed(final boolean lIIlIllllI) {
        this.reversed = lIIlIllllI;
        return this;
    }
    
    public float getMax() {
        return this.max;
    }
    
    public float getMin() {
        return this.min;
    }
    
    public void reset() {
        this.time = this.min;
    }
    
    public boolean isReversed() {
        return this.reversed;
    }
}
