package net.minecraft.client.renderer;

import java.util.concurrent.*;
import net.minecraft.client.*;

class EntityRendererShit2 implements Callable
{
    final EntityRenderer field_90025_c;
    private static final String __OBFID = "CL_00000948";
    
    EntityRendererShit2(final EntityRenderer p_i46419_1_) {
        this.field_90025_c = p_i46419_1_;
    }
    
    @Override
    public String call() throws Exception {
        return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
    }
}
