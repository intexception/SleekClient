package me.kansio.client.modules.impl.movement;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.modules.impl.movement.flight.*;
import me.kansio.client.value.value.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.utils.player.*;
import net.minecraft.potion.*;
import me.kansio.client.utils.java.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import me.kansio.client.gui.notification.*;
import com.google.common.eventbus.*;
import me.kansio.client.event.impl.*;

@ModuleData(name = "Flight", category = ModuleCategory.MOVEMENT, description = "Allows you to fly")
public class Flight extends Module
{
    private /* synthetic */ ModeValue boostMode;
    private /* synthetic */ NumberValue<Double> timer;
    private final /* synthetic */ List<? extends FlightMode> modes;
    private /* synthetic */ BooleanValue blink;
    public /* synthetic */ float prevFOV;
    private /* synthetic */ BooleanValue boost;
    private /* synthetic */ BooleanValue extraBoost;
    private /* synthetic */ BooleanValue glide;
    private /* synthetic */ FlightMode currentMode;
    private final /* synthetic */ ModeValue mode;
    private /* synthetic */ BooleanValue viewbob;
    public /* synthetic */ float ticks;
    private /* synthetic */ Stopwatch stopwatch;
    private /* synthetic */ BooleanValue antikick;
    private /* synthetic */ NumberValue<Double> speed;
    
    @Override
    public void onDisable() {
        Flight.mc.thePlayer.motionX = 0.0;
        Flight.mc.thePlayer.motionY = 0.0;
        Flight.mc.thePlayer.motionZ = 0.0;
        TimerUtil.Reset();
        this.currentMode.onDisable();
    }
    
    public NumberValue<Double> getSpeed() {
        return this.speed;
    }
    
    public ModeValue getMode() {
        return this.mode;
    }
    
    public BooleanValue getAntikick() {
        return this.antikick;
    }
    
    public FlightMode getCurrentMode() {
        return this.currentMode;
    }
    
    public float getPrevFOV() {
        return this.prevFOV;
    }
    
    public Stopwatch getStopwatch() {
        return this.stopwatch;
    }
    
    private double getBaseMoveSpeed() {
        double lllllllllllllllllllllIIIIIlllllI = 0.2873;
        if (Flight.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            lllllllllllllllllllllIIIIIlllllI *= 1.0 + 0.2 * (Flight.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return lllllllllllllllllllllIIIIIlllllI;
    }
    
    public List<? extends FlightMode> getModes() {
        return this.modes;
    }
    
    public Flight() {
        this.modes = ReflectUtils.getReflects(String.valueOf(new StringBuilder().append(this.getClass().getPackage().getName()).append(".flight")), FlightMode.class).stream().map(llllllllllllllllllllIllllllIlllI -> {
            try {
                return (FlightMode)llllllllllllllllllllIllllllIlllI.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception llllllllllllllllllllIllllllIllll) {
                llllllllllllllllllllIllllllIllll.printStackTrace();
                return null;
            }
        }).sorted(Comparator.comparing(llllllllllllllllllllIlllllllIIll -> (llllllllllllllllllllIlllllllIIll != null) ? llllllllllllllllllllIlllllllIIll.getName() : null)).collect((Collector<? super Object, ?, List<? extends FlightMode>>)Collectors.toList());
        this.mode = new ModeValue("Mode", this, (String[])this.modes.stream().map((Function<? super Object, ?>)FlightMode::getName).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).toArray(new String[0]));
        this.currentMode = (this.modes.stream().anyMatch(llllllllllllllllllllIlllllllIlIl -> llllllllllllllllllllIlllllllIlIl.getName().equalsIgnoreCase(this.mode.getValue())) ? this.modes.stream().filter(llllllllllllllllllllIllllllllIll -> llllllllllllllllllllIllllllllIll.getName().equalsIgnoreCase(this.mode.getValue())).findAny().get() : null);
        this.speed = new NumberValue<Double>("Speed", this, 1.0, 0.0, 10.0, 0.1);
        this.antikick = new BooleanValue("AntiKick", this, true, this.mode, new String[] { "BridgerLand (TP)" });
        this.boost = new BooleanValue("Boost", this, true, this.mode, new String[] { "Funcraft" });
        this.extraBoost = new BooleanValue("Extra Boost", this, true, this.mode, new String[] { "Funcraft" });
        this.glide = new BooleanValue("Glide", this, true, this.mode, new String[] { "Funcraft" });
        this.boostMode = new ModeValue("Boost Mode", this, this.mode, new String[] { "Funcraft" }, new String[] { "Normal", "Damage", "WOWOMG" });
        this.timer = new NumberValue<Double>("Timer", this, 1.0, 1.0, 5.0, 0.1, this.mode, new String[] { "Mush" });
        this.blink = new BooleanValue("Blink", this, true, this.mode, new String[] { "Mush" });
        this.viewbob = new BooleanValue("View Bobbing", this, true);
        this.stopwatch = new Stopwatch();
        this.ticks = 0.0f;
    }
    
    @Override
    public String getSuffix() {
        return String.valueOf(new StringBuilder().append(" ").append(this.mode.getValueAsString()));
    }
    
    public BooleanValue getBlink() {
        return this.blink;
    }
    
    @Override
    public void onEnable() {
        this.currentMode = (this.modes.stream().anyMatch(lllllllllllllllllllllIIIIIIIIIIl -> lllllllllllllllllllllIIIIIIIIIIl.getName().equalsIgnoreCase(this.mode.getValue())) ? this.modes.stream().filter(lllllllllllllllllllllIIIIIIIIlll -> lllllllllllllllllllllIIIIIIIIlll.getName().equalsIgnoreCase(this.mode.getValue())).findAny().get() : null);
        this.currentMode.onEnable();
    }
    
    public NumberValue<Double> getTimer() {
        return this.timer;
    }
    
    public BooleanValue getViewbob() {
        return this.viewbob;
    }
    
    public ModeValue getBoostMode() {
        return this.boostMode;
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lllllllllllllllllllllIIIIlIlIIll) {
        if (this.viewbob.getValue() && Flight.mc.thePlayer.isMoving()) {
            Flight.mc.thePlayer.cameraYaw = 0.05f;
        }
        else {
            Flight.mc.thePlayer.cameraYaw = 0.0f;
        }
        this.currentMode.onUpdate(lllllllllllllllllllllIIIIlIlIIll);
        if (Flight.mc.thePlayer.ticksExisted < 5 && this.isToggled()) {
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "World Change!", "Flight disabled", 1));
            this.toggle();
        }
    }
    
    @Subscribe
    public void onMove(final MoveEvent lllllllllllllllllllllIIIIlIIllll) {
        this.currentMode.onMove(lllllllllllllllllllllIIIIlIIllll);
    }
    
    @Subscribe
    public void onCollide(final BlockCollisionEvent lllllllllllllllllllllIIIIlIIIIll) {
        this.currentMode.onCollide(lllllllllllllllllllllIIIIlIIIIll);
    }
    
    @Subscribe
    public void onPacket(final PacketEvent lllllllllllllllllllllIIIIlIIIlll) {
        this.currentMode.onPacket(lllllllllllllllllllllIIIIlIIIlll);
    }
    
    public BooleanValue getBoost() {
        return this.boost;
    }
    
    public BooleanValue getExtraBoost() {
        return this.extraBoost;
    }
    
    public float getTicks() {
        return this.ticks;
    }
    
    public BooleanValue getGlide() {
        return this.glide;
    }
}
