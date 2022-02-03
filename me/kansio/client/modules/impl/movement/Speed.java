package me.kansio.client.modules.impl.movement;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.modules.impl.movement.speed.*;
import com.google.common.util.concurrent.*;
import me.kansio.client.value.value.*;
import me.kansio.client.utils.java.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import me.kansio.client.gui.notification.*;
import com.google.common.eventbus.*;
import me.kansio.client.utils.player.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.value.*;

@ModuleData(name = "Speed", category = ModuleCategory.MOVEMENT, description = "Move faster than normal.")
public class Speed extends Module
{
    private /* synthetic */ SpeedMode currentMode;
    private final /* synthetic */ AtomicDouble hDist;
    private final /* synthetic */ List<? extends SpeedMode> modes;
    private final /* synthetic */ NumberValue<Double> speed;
    private final /* synthetic */ BooleanValue forceFriction;
    private final /* synthetic */ NumberValue<Float> timer;
    private final /* synthetic */ ModeValue frictionMode;
    private final /* synthetic */ ModeValue mode;
    
    public AtomicDouble getHDist() {
        return this.hDist;
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.mode.getValueAsString()));
    }
    
    public NumberValue<Double> getSpeed() {
        return this.speed;
    }
    
    public BooleanValue getForceFriction() {
        return this.forceFriction;
    }
    
    public ModeValue getFrictionMode() {
        return this.frictionMode;
    }
    
    public Speed() {
        this.modes = ReflectUtils.getReflects(String.valueOf(new StringBuilder().append(this.getClass().getPackage().getName()).append(".speed")), SpeedMode.class).stream().map(lIIlllIIIIlIl -> {
            try {
                return (SpeedMode)lIIlllIIIIlIl.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception lIIlllIIIIllI) {
                lIIlllIIIIllI.printStackTrace();
                return null;
            }
        }).sorted(Comparator.comparing(lIIlllIIIlIlI -> (lIIlllIIIlIlI != null) ? lIIlllIIIlIlI.getName() : null)).collect((Collector<? super Object, ?, List<? extends SpeedMode>>)Collectors.toList());
        this.mode = new ModeValue("Mode", this, (String[])this.modes.stream().map((Function<? super Object, ?>)SpeedMode::getName).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).toArray(new String[0]));
        this.currentMode = (this.modes.stream().anyMatch(lIIlllIIIlllI -> lIIlllIIIlllI.getName().equalsIgnoreCase(this.mode.getValue())) ? this.modes.stream().filter(lIIlllIIlIlII -> lIIlllIIlIlII.getName().equalsIgnoreCase(this.mode.getValue())).findAny().get() : null);
        this.speed = new NumberValue<Double>("Speed", this, 0.5, 0.0, 8.0, 0.1);
        this.timer = new NumberValue<Float>("Timer Speed", this, 1.0f, 1.0f, 2.5f, 0.1f, this.mode, new String[] { "Watchdog" });
        this.forceFriction = new BooleanValue("Force Friction", this, true);
        this.frictionMode = new ModeValue("Friction", this, this.forceFriction, new String[] { "NCP", "NEW", "LEGIT", "SILENT" });
        this.hDist = new AtomicDouble();
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lIIllllIIllll) {
        this.currentMode.onUpdate(lIIllllIIllll);
        if (Speed.mc.thePlayer.ticksExisted < 5 && this.isToggled()) {
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "World Change!", "Speed disabled", 1));
            this.toggle();
        }
    }
    
    public NumberValue<Float> getTimer() {
        return this.timer;
    }
    
    @Override
    public void onDisable() {
        TimerUtil.Reset();
        PlayerUtil.setMotion(0.0);
        this.hDist.set(0.0);
        this.currentMode.onDisable();
    }
    
    @Override
    public void onEnable() {
        this.currentMode = (this.modes.stream().anyMatch(lIIlllIIllIII -> lIIlllIIllIII.getName().equalsIgnoreCase(this.mode.getValue())) ? this.modes.stream().filter(lIIlllIlIIIII -> lIIlllIlIIIII.getName().equalsIgnoreCase(this.mode.getValue())).findAny().get() : null);
        if (this.currentMode == null) {
            ChatUtil.log("§c§lError! §fIt looks like this mode doesn't exist anymore, setting it to a mode that exists.");
            this.currentMode = (SpeedMode)this.modes.get(0);
            this.toggle();
            return;
        }
        this.currentMode.onEnable();
    }
    
    @Subscribe
    public void onMove(final MoveEvent lIIllllIIlIIl) {
        this.currentMode.onMove(lIIllllIIlIIl);
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lIIllllIIIlIl) {
        this.currentMode.onPacket(lIIllllIIIlIl);
    }
    
    public double handleFriction(final AtomicDouble lIIlllIlllIIl) {
        if (this.forceFriction.getValue()) {
            final double lIIlllIllllIl = lIIlllIlllIIl.get();
            final float lIIlllIllIlll = ((Value<Float>)this.frictionMode).getValue();
            int lIIlllIllIllI = -1;
            switch (((String)lIIlllIllIlll).hashCode()) {
                case 77115: {
                    if (((String)lIIlllIllIlll).equals("NCP")) {
                        lIIlllIllIllI = 0;
                        break;
                    }
                    break;
                }
                case 77184: {
                    if (((String)lIIlllIllIlll).equals("NEW")) {
                        lIIlllIllIllI = 1;
                        break;
                    }
                    break;
                }
                case 72313753: {
                    if (((String)lIIlllIllIlll).equals("LEGIT")) {
                        lIIlllIllIllI = 2;
                        break;
                    }
                    break;
                }
                case -1848997803: {
                    if (((String)lIIlllIllIlll).equals("SILENT")) {
                        lIIlllIllIllI = 3;
                        break;
                    }
                    break;
                }
            }
            switch (lIIlllIllIllI) {
                case 0: {
                    lIIlllIlllIIl.set(lIIlllIllllIl - lIIlllIllllIl / 159.0);
                    break;
                }
                case 1: {
                    lIIlllIlllIIl.set(lIIlllIllllIl * 0.98);
                    break;
                }
                case 2: {
                    lIIlllIlllIIl.set(lIIlllIllllIl * 0.91);
                    break;
                }
                case 3: {
                    lIIlllIlllIIl.set(lIIlllIllllIl - 1.0E-9);
                    break;
                }
            }
            return Math.max(lIIlllIlllIIl.get(), PlayerUtil.getVerusBaseSpeed());
        }
        return lIIlllIlllIIl.get();
    }
}
