package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Chams", category = ModuleCategory.VISUALS, description = "Shows players behind walls")
public class Chams extends Module
{
    public /* synthetic */ NumberValue<Integer> alpha;
    public /* synthetic */ NumberValue<Integer> r;
    public /* synthetic */ NumberValue<Integer> b;
    public /* synthetic */ BooleanValue fill;
    public /* synthetic */ NumberValue<Integer> g;
    
    public BooleanValue getFill() {
        return this.fill;
    }
    
    public NumberValue<Integer> getAlpha() {
        return this.alpha;
    }
    
    public NumberValue<Integer> getR() {
        return this.r;
    }
    
    @Subscribe
    public void onRender(final EntityLivingRenderEvent llllllllllllllllllllIIIIlllIIIII) {
        if (llllllllllllllllllllIIIIlllIIIII.isPre() && llllllllllllllllllllIIIIlllIIIII.getEntity() instanceof EntityPlayer) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
        else if (llllllllllllllllllllIIIIlllIIIII.isPost() && llllllllllllllllllllIIIIlllIIIII.getEntity() instanceof EntityPlayer) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 1100000.0f);
        }
    }
    
    public Chams() {
        this.fill = new BooleanValue("Fill", this, true);
        this.alpha = new NumberValue<Integer>("Alpha", this, 255, 0, 255, 1);
        this.r = new NumberValue<Integer>("Red", this, 255, 0, 255, 1);
        this.g = new NumberValue<Integer>("Green", this, 255, 0, 255, 1);
        this.b = new NumberValue<Integer>("Blue", this, 255, 0, 255, 1);
    }
    
    public NumberValue<Integer> getG() {
        return this.g;
    }
    
    public NumberValue<Integer> getB() {
        return this.b;
    }
}
