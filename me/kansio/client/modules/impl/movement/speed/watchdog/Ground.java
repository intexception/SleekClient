package me.kansio.client.modules.impl.movement.speed.watchdog;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.event.impl.*;

public class Ground extends SpeedMode
{
    private /* synthetic */ double lastDist;
    private /* synthetic */ int stage;
    private /* synthetic */ double moveSpeed;
    
    @Override
    public void onUpdate(final UpdateEvent lllllllllllllllllllllIIIlIIlIIll) {
        if (!Ground.mc.thePlayer.onGround) {
            return;
        }
        switch (this.stage) {
            case 1: {
                ++this.stage;
                break;
            }
            case 2: {
                ++this.stage;
                break;
            }
            default: {
                this.stage = 1;
                if (Ground.mc.thePlayer.isMoving() && !Ground.mc.gameSettings.keyBindJump.isPressed()) {
                    this.stage = 1;
                    break;
                }
                this.moveSpeed = PlayerUtil.getSpeed() / 1.54;
                break;
            }
        }
    }
    
    public Ground() {
        super("Watchdog (Ground)");
    }
    
    @Override
    public void onMove(final MoveEvent lllllllllllllllllllllIIIlIIlIlll) {
        if (!Ground.mc.thePlayer.onGround) {
            return;
        }
        switch (this.stage) {
            case 1: {
                this.moveSpeed = 0.459;
                break;
            }
            case 2: {
                this.moveSpeed = 0.46581;
                break;
            }
            default: {
                this.moveSpeed = PlayerUtil.getSpeed() / 1.59;
                break;
            }
        }
        PlayerUtil.setMotion(this.moveSpeed);
    }
    
    @Override
    public void onEnable() {
        this.moveSpeed = PlayerUtil.getSpeed() / 1.54;
        this.lastDist = 0.0;
        this.stage = 4;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
}
