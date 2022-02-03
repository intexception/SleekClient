package me.kansio.client.modules.impl.movement.flight.misc;

import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.utils.math.*;
import net.minecraft.potion.*;
import me.kansio.client.utils.player.*;
import net.minecraft.entity.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.value.*;

public class Funcraft extends FlightMode
{
    private /* synthetic */ Stopwatch timer;
    private /* synthetic */ int xd;
    private /* synthetic */ int level;
    private /* synthetic */ double moveSpeed;
    private /* synthetic */ double lastDist;
    
    @Override
    public void onUpdate(final UpdateEvent llllllllllllllllllllIlIIIllIlIII) {
        if (!llllllllllllllllllllIlIIIllIlIII.isPre()) {
            final double llllllllllllllllllllIlIIIllIlllI = Funcraft.mc.thePlayer.posX - Funcraft.mc.thePlayer.prevPosX;
            final double llllllllllllllllllllIlIIIllIllIl = Funcraft.mc.thePlayer.posZ - Funcraft.mc.thePlayer.prevPosZ;
            this.lastDist = Math.sqrt(llllllllllllllllllllIlIIIllIlllI * llllllllllllllllllllIlIIIllIlllI + llllllllllllllllllllIlIIIllIllIl * llllllllllllllllllllIlIIIllIllIl);
        }
        if (llllllllllllllllllllIlIIIllIlIII.isPre()) {
            if (Funcraft.mc.gameSettings.keyBindJump.isKeyDown()) {
                Funcraft.mc.thePlayer.setPosition(Funcraft.mc.thePlayer.posX, Funcraft.mc.thePlayer.posY + 0.15, Funcraft.mc.thePlayer.posZ);
                Funcraft.mc.thePlayer.motionY = 0.15;
            }
            else if (Funcraft.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Funcraft.mc.thePlayer.setPosition(Funcraft.mc.thePlayer.posX, Funcraft.mc.thePlayer.posY - 0.15, Funcraft.mc.thePlayer.posZ);
                Funcraft.mc.thePlayer.motionY = -0.15;
            }
            else {
                Funcraft.mc.thePlayer.motionY = 0.0;
            }
            if (Funcraft.mc.getCurrentServerData() != null && Funcraft.mc.getCurrentServerData().serverIP != null && Funcraft.mc.getCurrentServerData().serverIP.toLowerCase().contains("funcraft")) {
                llllllllllllllllllllIlIIIllIlIII.setOnGround(true);
            }
            final double llllllllllllllllllllIlIIIllIllII = 3.34947E-9 + MathUtil.getRandomInRange(1.4947E-10, 6.4947E-10);
            if (Funcraft.mc.thePlayer.ticksExisted % 3 == 0) {
                llllllllllllllllllllIlIIIllIlIII.setPosY(Funcraft.mc.thePlayer.posY + llllllllllllllllllllIlIIIllIllII);
                llllllllllllllllllllIlIIIllIlIII.setOnGround(false);
            }
            if ((Funcraft.mc.thePlayer.moveForward != 0.0f || Funcraft.mc.thePlayer.moveStrafing != 0.0f) && Funcraft.mc.thePlayer.onGround) {
                if (!this.getFlight().getBoost().getValue()) {
                    final float n = 0.42f + (Funcraft.mc.thePlayer.isPotionActive(Potion.jump) ? ((Funcraft.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f) : 0.0f);
                }
                if ((this.getFlight().getBoostMode().getValue().equals("Damage") || this.getFlight().getBoostMode().getValue().equals("WOWOMG")) && this.getFlight().getBoost().getValue() && this.xd == 0) {
                    PlayerUtil.damagePlayer();
                    this.xd = 1;
                }
            }
        }
    }
    
    @Override
    public void onCollide(final BlockCollisionEvent llllllllllllllllllllIlIIIlIIIIII) {
        super.onCollide(llllllllllllllllllllIlIIIlIIIIII);
    }
    
    @Override
    public void onMove(final MoveEvent llllllllllllllllllllIlIIIlIlIllI) {
        if (this.getFlight().getExtraBoost().getValue() && this.getFlight().getBoost().getValue()) {
            if (!this.timer.timeElapsed(135L) && this.timer.timeElapsed(20L)) {
                TimerUtil.setTimer(3.5f);
            }
            else {
                TimerUtil.Reset();
            }
            if (this.level < 20) {
                this.timer.resetTime();
            }
        }
        if (this.getFlight().getBoost().getValue()) {
            final float llllllllllllllllllllIlIIIlIllIII = 0.42f + (Funcraft.mc.thePlayer.isPotionActive(Potion.jump) ? ((Funcraft.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f) : 0.0f);
            final int llllllllllllllllllllIlIIIlIlIIlI = ((Value<Integer>)this.getFlight().getBoostMode()).getValue();
            char llllllllllllllllllllIlIIIlIlIIIl = (char)(-1);
            switch (((String)llllllllllllllllllllIlIIIlIlIIlI).hashCode()) {
                case -1955878649: {
                    if (((String)llllllllllllllllllllIlIIIlIlIIlI).equals("Normal")) {
                        llllllllllllllllllllIlIIIlIlIIIl = '\0';
                        break;
                    }
                    break;
                }
                case 2039707535: {
                    if (((String)llllllllllllllllllllIlIIIlIlIIlI).equals("Damage")) {
                        llllllllllllllllllllIlIIIlIlIIIl = '\u0001';
                        break;
                    }
                    break;
                }
                case -1728602806: {
                    if (((String)llllllllllllllllllllIlIIIlIlIIlI).equals("WOWOMG")) {
                        llllllllllllllllllllIlIIIlIlIIIl = '\u0002';
                        break;
                    }
                    break;
                }
            }
            switch (llllllllllllllllllllIlIIIlIlIIIl) {
                case '\0':
                case '\u0001': {
                    if (Funcraft.mc.thePlayer.isMoving()) {
                        if (this.level != 1) {
                            if (this.level == 2) {
                                ++this.level;
                                this.moveSpeed *= (Funcraft.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (this.getFlight().getSpeed().getValue() - 0.3) : this.getFlight().getSpeed().getValue());
                            }
                            else if (this.level == 3) {
                                ++this.level;
                                final double llllllllllllllllllllIlIIIlIlllII = 0.1 * (this.lastDist - this.getBaseMoveSpeed());
                                this.moveSpeed = this.lastDist - llllllllllllllllllllIlIIIlIlllII;
                            }
                            else {
                                ++this.level;
                                if (Funcraft.mc.theWorld.getCollidingBoundingBoxes(Funcraft.mc.thePlayer, Funcraft.mc.thePlayer.getEntityBoundingBox().offset(0.0, Funcraft.mc.thePlayer.motionY, 0.0)).size() > 0 || Funcraft.mc.thePlayer.isCollidedVertically) {
                                    this.level = 1;
                                }
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                            }
                        }
                        else if (Funcraft.mc.thePlayer.hurtResistantTime == 19 || this.getFlight().getBoostMode().getValue().equals("Normal")) {
                            llllllllllllllllllllIlIIIlIlIllI.setMotionY(Funcraft.mc.thePlayer.motionY = llllllllllllllllllllIlIIIlIllIII);
                            this.level = 2;
                            final double llllllllllllllllllllIlIIIlIllIll = Funcraft.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.5 : 1.62;
                            this.moveSpeed = llllllllllllllllllllIlIIIlIllIll * this.getBaseMoveSpeed() - 0.01;
                        }
                    }
                    else {
                        this.moveSpeed = 0.0;
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                    if (this.level == 1 && Funcraft.mc.thePlayer.hurtResistantTime != 19 && !this.getFlight().getBoostMode().getValue().equals("Normal")) {
                        this.moveSpeed = 0.011;
                    }
                    PlayerUtil.setMotion(llllllllllllllllllllIlIIIlIlIllI, this.moveSpeed);
                    break;
                }
                case '\u0002': {
                    if (Funcraft.mc.thePlayer.isMoving()) {
                        if (this.level != 1) {
                            if (this.level == 2) {
                                ++this.level;
                                this.moveSpeed *= (Funcraft.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (this.getFlight().getSpeed().getValue() - 0.3) : this.getFlight().getSpeed().getValue());
                            }
                            else if (this.level == 3) {
                                ++this.level;
                                final double llllllllllllllllllllIlIIIlIllIlI = 0.01 * (this.lastDist - this.getBaseMoveSpeed());
                                this.moveSpeed = this.lastDist - llllllllllllllllllllIlIIIlIllIlI;
                            }
                            else {
                                ++this.level;
                                if (Funcraft.mc.theWorld.getCollidingBoundingBoxes(Funcraft.mc.thePlayer, Funcraft.mc.thePlayer.getEntityBoundingBox().offset(0.0, Funcraft.mc.thePlayer.motionY, 0.0)).size() > 0 || Funcraft.mc.thePlayer.isCollidedVertically) {
                                    this.level = 1;
                                }
                                this.moveSpeed -= this.moveSpeed / 159.9;
                            }
                        }
                        else {
                            llllllllllllllllllllIlIIIlIlIllI.setMotionY(Funcraft.mc.thePlayer.motionY = llllllllllllllllllllIlIIIlIllIII);
                            this.level = 2;
                            final double llllllllllllllllllllIlIIIlIllIIl = Funcraft.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.55 : 1.62;
                            this.moveSpeed = llllllllllllllllllllIlIIIlIllIIl * this.getBaseMoveSpeed() - 0.01;
                        }
                    }
                    else {
                        this.moveSpeed = 0.0;
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                    PlayerUtil.setMotion(llllllllllllllllllllIlIIIlIlIllI, this.moveSpeed);
                    break;
                }
            }
        }
        else {
            PlayerUtil.setMotion(llllllllllllllllllllIlIIIlIlIllI, this.getBaseMoveSpeed());
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    public Funcraft() {
        super("Funcraft");
        this.timer = new Stopwatch();
    }
    
    @Override
    public void onPacket(final PacketEvent llllllllllllllllllllIlIIIlIIIllI) {
        super.onPacket(llllllllllllllllllllIlIIIlIIIllI);
    }
    
    @Override
    public void onDisable() {
        this.lastDist = 0.0;
        this.xd = 0;
    }
    
    private double getBaseMoveSpeed() {
        double llllllllllllllllllllIlIIIlIIllIl = 0.2873;
        if (Funcraft.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            llllllllllllllllllllIlIIIlIIllIl *= 1.0 + 0.2 * (Funcraft.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return llllllllllllllllllllIlIIIlIIllIl;
    }
}
