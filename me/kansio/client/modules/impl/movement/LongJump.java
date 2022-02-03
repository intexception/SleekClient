package me.kansio.client.modules.impl.movement;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.value.value.*;
import me.kansio.client.utils.player.*;
import java.text.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.kansio.client.value.*;

@ModuleData(name = "Long Jump", category = ModuleCategory.MOVEMENT, description = "Jump further than normal")
public class LongJump extends Module
{
    private /* synthetic */ NumberValue<Double> vertical;
    private /* synthetic */ Stopwatch damageWaiterThing;
    private /* synthetic */ NumberValue<Double> boost;
    private /* synthetic */ ModeValue mode;
    /* synthetic */ boolean launched;
    /* synthetic */ boolean wasLaunched;
    /* synthetic */ boolean jumped;
    
    @Override
    public void onEnable() {
        this.launched = false;
        this.wasLaunched = false;
        this.jumped = false;
        this.damageWaiterThing.resetTime();
        final int lIlIIIlIllll = ((Value<Integer>)this.mode).getValue();
        byte lIlIIIlIlllI = -1;
        switch (((String)lIlIIIlIllll).hashCode()) {
            case 82544993: {
                if (((String)lIlIIIlIllll).equals("Verus")) {
                    lIlIIIlIlllI = 0;
                    break;
                }
                break;
            }
        }
        switch (lIlIIIlIlllI) {
            case 0: {
                if (!LongJump.mc.thePlayer.onGround) {
                    this.toggle();
                    return;
                }
                TimerUtil.setTimer(0.3f);
                PlayerUtil.damageVerus();
                break;
            }
        }
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.mode.getValueAsString()));
    }
    
    @Subscribe
    public void render(final RenderOverlayEvent lIlIIIIlIIIl) {
        if (this.mode.getValue().equals("Test")) {
            LongJump.mc.fontRendererObj.drawStringWithShadow(new DecimalFormat("0.#").format(this.damageWaiterThing.getTimeRemaining(1000L) / 1000.0), (float)lIlIIIIlIIIl.getSr().getScaledWidth_double() / 2.0f, (float)lIlIIIIlIIIl.getSr().getScaledHeight_double() / 2.0f, -1);
        }
    }
    
    @Override
    public void onDisable() {
        TimerUtil.Reset();
        this.jumped = false;
    }
    
    @Subscribe
    public void onMove(final MoveEvent lIlIIIIIlIIl) {
        final byte lIlIIIIIIllI = ((Value<Byte>)this.mode).getValue();
        int lIlIIIIIIlIl = -1;
        switch (((String)lIlIIIIIIllI).hashCode()) {
            case 82661738: {
                if (((String)lIlIIIIIIllI).equals("Viper")) {
                    lIlIIIIIIlIl = 0;
                    break;
                }
                break;
            }
        }
        switch (lIlIIIIIIlIl) {
            case 0: {
                if (!LongJump.mc.thePlayer.onGround) {
                    return;
                }
                TimerUtil.setTimer(0.3f);
                if (LongJump.mc.thePlayer.isMoving()) {
                    for (int lIlIIIIIlIll = 0; lIlIIIIIlIll < 17; ++lIlIIIIIlIll) {
                        PlayerUtil.TPGROUND(lIlIIIIIlIIl, 0.32, 0.0);
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public LongJump() {
        this.launched = false;
        this.wasLaunched = false;
        this.jumped = false;
        this.mode = new ModeValue("Mode", this, new String[] { "Verus", "Viper", "Vanilla", "Test" });
        this.vertical = new NumberValue<Double>("Vertical Boost", this, 0.8, 0.05, 6.0, 0.1);
        this.boost = new NumberValue<Double>("Speed", this, 1.45, 0.05, 10.0, 0.1);
        this.damageWaiterThing = new Stopwatch();
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lIlIIIIllllI) {
        final short lIlIIIIlllII = ((Value<Short>)this.mode).getValue();
        char lIlIIIIllIll = (char)(-1);
        switch (((String)lIlIIIIlllII).hashCode()) {
            case 82544993: {
                if (((String)lIlIIIIlllII).equals("Verus")) {
                    lIlIIIIllIll = '\0';
                    break;
                }
                break;
            }
            case 1897755483: {
                if (((String)lIlIIIIlllII).equals("Vanilla")) {
                    lIlIIIIllIll = '\u0001';
                    break;
                }
                break;
            }
            case 2603186: {
                if (((String)lIlIIIIlllII).equals("Test")) {
                    lIlIIIIllIll = '\u0002';
                    break;
                }
                break;
            }
        }
        switch (lIlIIIIllIll) {
            case '\0': {
                if (LongJump.mc.thePlayer.hurtTime > 1 && !this.launched) {
                    this.launched = true;
                }
                if (this.launched) {
                    if (!this.jumped) {
                        LongJump.mc.thePlayer.motionY = this.vertical.getValue();
                        this.jumped = true;
                    }
                    PlayerUtil.setMotion(this.boost.getValue().floatValue());
                    this.launched = false;
                    this.wasLaunched = true;
                    this.toggle();
                    break;
                }
                break;
            }
            case '\u0001': {
                if (LongJump.mc.thePlayer.isMoving()) {
                    if (LongJump.mc.thePlayer.onGround) {
                        LongJump.mc.thePlayer.motionY = this.vertical.getValue();
                    }
                    PlayerUtil.setMotion(this.boost.getValue().floatValue());
                    break;
                }
                break;
            }
            case '\u0002': {
                if (this.damageWaiterThing.timeElapsed(1000L)) {
                    final double lIlIIIlIIIlI = LongJump.mc.thePlayer.posX;
                    final double lIlIIIlIIIIl = LongJump.mc.thePlayer.posY;
                    final double lIlIIIlIIIII = LongJump.mc.thePlayer.posZ;
                    for (short lIlIIIlIIIll = 0; lIlIIIlIIIll <= 64.0; ++lIlIIIlIIIll) {
                        LongJump.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(LongJump.mc.thePlayer.posX, LongJump.mc.thePlayer.posY + 0.03125, LongJump.mc.thePlayer.posZ, false));
                        LongJump.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(LongJump.mc.thePlayer.posX, LongJump.mc.thePlayer.posY + 0.0625, LongJump.mc.thePlayer.posZ, false));
                        LongJump.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(LongJump.mc.thePlayer.posX, LongJump.mc.thePlayer.posY, LongJump.mc.thePlayer.posZ, lIlIIIlIIIll == 64.0));
                    }
                    this.damageWaiterThing.resetTime();
                    break;
                }
                break;
            }
        }
    }
}
