package me.kansio.client.modules.impl.movement.speed.misc;

import me.kansio.client.modules.impl.movement.speed.*;
import viamcp.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import me.kansio.client.utils.network.*;
import net.minecraft.network.*;

public class Matrix extends SpeedMode
{
    @Override
    public void onEnable() {
        if (ViaMCP.getInstance().getVersion() != 755) {
            ChatUtil.log("§cYou must use 1.17 with viaversion to use this mode.");
            this.getSpeed().toggle();
        }
        Matrix.mc.gameSettings.keyBindJump.pressed = true;
    }
    
    public Matrix() {
        super("Matrix Hop");
    }
    
    @Override
    public void onDisable() {
        Matrix.mc.gameSettings.keyBindJump.pressed = false;
    }
    
    @Override
    public void onUpdate(final UpdateEvent lIlIIIIllIIIll) {
        if (Matrix.mc.thePlayer.onGround) {
            TimerUtil.setTimer(0.5f);
        }
        else {
            TimerUtil.setTimer(1.5f);
        }
        PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(Matrix.mc.thePlayer.getPosition().down(255), 256, null, 0.0f, 0.0f, 0.0f));
    }
}
