package me.kansio.client.modules.impl.movement.speed.misc;

import me.kansio.client.modules.impl.movement.speed.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.player.*;

public class Vanilla extends SpeedMode
{
    public Vanilla() {
        super("Vanilla");
    }
    
    @Override
    public void onUpdate(final UpdateEvent llllIlIllIIll) {
        PlayerUtil.setMotion(this.getSpeed().getSpeed().getValue());
    }
}
