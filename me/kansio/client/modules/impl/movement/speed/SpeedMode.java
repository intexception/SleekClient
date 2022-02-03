package me.kansio.client.modules.impl.movement.speed;

import me.kansio.client.utils.*;
import me.kansio.client.modules.impl.movement.*;
import me.kansio.client.*;
import me.kansio.client.event.impl.*;

public abstract class SpeedMode extends Util
{
    private final /* synthetic */ String name;
    
    public String getName() {
        return this.name;
    }
    
    public void onEnable() {
    }
    
    public Speed getSpeed() {
        return (Speed)Client.getInstance().getModuleManager().getModuleByName("Speed");
    }
    
    public void onPacket(final PacketEvent lIlIllIlIIIlIl) {
    }
    
    public void onMove(final MoveEvent lIlIllIlIIIlll) {
    }
    
    public SpeedMode(final String lIlIllIlIIlIll) {
        this.name = lIlIllIlIIlIll;
    }
    
    public void onUpdate(final UpdateEvent lIlIllIlIIlIIl) {
    }
    
    public void onDisable() {
    }
}
