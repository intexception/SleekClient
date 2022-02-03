package me.kansio.client.modules.impl.movement.flight;

import me.kansio.client.utils.*;
import me.kansio.client.modules.impl.movement.*;
import me.kansio.client.*;
import me.kansio.client.event.impl.*;

public abstract class FlightMode extends Util
{
    private final /* synthetic */ String name;
    
    public void onCollide(final BlockCollisionEvent lllIlllIIIlll) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public Flight getFlight() {
        return (Flight)Client.getInstance().getModuleManager().getModuleByName("Flight");
    }
    
    public void onDisable() {
    }
    
    public void onUpdate(final UpdateEvent lllIlllIIllIl) {
    }
    
    public FlightMode(final String lllIlllIIllll) {
        this.name = lllIlllIIllll;
    }
    
    public void onMove(final MoveEvent lllIlllIIlIll) {
    }
    
    public void onEnable() {
    }
    
    public void onPacket(final PacketEvent lllIlllIIlIIl) {
    }
}
