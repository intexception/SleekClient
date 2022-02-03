package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.modules.impl.visuals.hud.*;
import me.kansio.client.event.impl.*;
import com.google.common.eventbus.*;
import me.kansio.client.utils.java.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

@ModuleData(name = "HUD", category = ModuleCategory.VISUALS, description = "The HUD... nothing special")
public class HUD extends Module
{
    public /* synthetic */ BooleanValue hideRender;
    private final /* synthetic */ List<? extends WaterMarkMode> watermarkmodes;
    public /* synthetic */ BooleanValue noti;
    private final /* synthetic */ ModeValue scoreboardLocation;
    private /* synthetic */ WaterMarkMode currentwatermarkmode;
    private /* synthetic */ ArrayListMode currentarraylistmode;
    private final /* synthetic */ NumberValue<Double> scoreboardPos;
    private final /* synthetic */ List<? extends ArrayListMode> arraylistmodes;
    public /* synthetic */ ModeValue line;
    private final /* synthetic */ ModeValue infomode;
    public /* synthetic */ NumberValue arrayListY;
    public /* synthetic */ StringValue clientName;
    private final /* synthetic */ ModeValue watermarkmode;
    public final /* synthetic */ NumberValue<Integer> bgalpha;
    public /* synthetic */ BooleanValue font;
    private final /* synthetic */ ModeValue arraylistmode;
    private final /* synthetic */ ModeValue colorMode;
    public /* synthetic */ StringValue listSuffix;
    private final /* synthetic */ List<? extends InfoMode> infomodes;
    private /* synthetic */ InfoMode currentinfomode;
    
    public InfoMode getCurrentinfomode() {
        return this.currentinfomode;
    }
    
    public NumberValue<Double> getScoreboardPos() {
        return this.scoreboardPos;
    }
    
    public ModeValue getLine() {
        return this.line;
    }
    
    public ArrayListMode getCurrentarraylistmode() {
        return this.currentarraylistmode;
    }
    
    @Subscribe
    public void onRenderOverlay(final RenderOverlayEvent lIIIlIIIIIIIl) {
        this.currentwatermarkmode = (this.watermarkmodes.stream().anyMatch(lIIIIlIlIIIIl -> lIIIIlIlIIIIl.getName().equalsIgnoreCase(this.watermarkmode.getValue())) ? this.watermarkmodes.stream().filter(lIIIIlIlIIlll -> lIIIIlIlIIlll.getName().equalsIgnoreCase(this.watermarkmode.getValue())).findAny().get() : null);
        this.currentarraylistmode = (this.arraylistmodes.stream().anyMatch(lIIIIlIlIllll -> lIIIIlIlIllll.getName().equalsIgnoreCase(this.arraylistmode.getValue())) ? this.arraylistmodes.stream().filter(lIIIIlIllIIll -> lIIIIlIllIIll.getName().equalsIgnoreCase(this.arraylistmode.getValue())).findAny().get() : null);
        this.currentinfomode = (this.infomodes.stream().anyMatch(lIIIIlIlllIIl -> lIIIIlIlllIIl.getName().equalsIgnoreCase(this.infomode.getValue())) ? this.infomodes.stream().filter(lIIIIlIllllll -> lIIIIlIllllll.getName().equalsIgnoreCase(this.infomode.getValue())).findAny().get() : null);
        this.currentwatermarkmode.onRenderOverlay(lIIIlIIIIIIIl);
        this.currentarraylistmode.onRenderOverlay(lIIIlIIIIIIIl);
        this.currentinfomode.onRenderOverlay(lIIIlIIIIIIIl);
    }
    
    public WaterMarkMode getCurrentwatermarkmode() {
        return this.currentwatermarkmode;
    }
    
    public BooleanValue getNoti() {
        return this.noti;
    }
    
    public ModeValue getScoreboardLocation() {
        return this.scoreboardLocation;
    }
    
    public ModeValue getColorMode() {
        return this.colorMode;
    }
    
    public ModeValue getWatermarkmode() {
        return this.watermarkmode;
    }
    
    public NumberValue<Integer> getBgalpha() {
        return this.bgalpha;
    }
    
    public BooleanValue getFont() {
        return this.font;
    }
    
    public NumberValue getArrayListY() {
        return this.arrayListY;
    }
    
    public ModeValue getArraylistmode() {
        return this.arraylistmode;
    }
    
    public List<? extends ArrayListMode> getArraylistmodes() {
        return this.arraylistmodes;
    }
    
    public HUD() {
        this.watermarkmodes = ReflectUtils.getReflects(String.valueOf(new StringBuilder().append(this.getClass().getPackage().getName()).append(".hud")), WaterMarkMode.class).stream().map(lIIIIIlIIIIII -> {
            try {
                return (WaterMarkMode)lIIIIIlIIIIII.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception lIIIIIlIIIIIl) {
                lIIIIIlIIIIIl.printStackTrace();
                return null;
            }
        }).sorted(Comparator.comparing(lIIIIIlIIIlII -> (lIIIIIlIIIlII != null) ? lIIIIIlIIIlII.getName() : null)).collect((Collector<? super Object, ?, List<? extends WaterMarkMode>>)Collectors.toList());
        this.watermarkmode = new ModeValue("Watermark Mode", this, (String[])this.watermarkmodes.stream().map((Function<? super Object, ?>)WaterMarkMode::getName).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).toArray(new String[0]));
        this.currentwatermarkmode = (this.watermarkmodes.stream().anyMatch(lIIIIIlIIIlll -> lIIIIIlIIIlll.getName().equalsIgnoreCase(this.watermarkmode.getValue())) ? this.watermarkmodes.stream().filter(lIIIIIlIIllIl -> lIIIIIlIIllIl.getName().equalsIgnoreCase(this.watermarkmode.getValue())).findAny().get() : null);
        this.arraylistmodes = ReflectUtils.getReflects(String.valueOf(new StringBuilder().append(this.getClass().getPackage().getName()).append(".hud")), ArrayListMode.class).stream().map(lIIIIIlIlIlII -> {
            try {
                return (ArrayListMode)lIIIIIlIlIlII.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception lIIIIIlIlIllI) {
                lIIIIIlIlIllI.printStackTrace();
                return null;
            }
        }).sorted(Comparator.comparing(lIIIIIlIllIlI -> (lIIIIIlIllIlI != null) ? lIIIIIlIllIlI.getName() : null)).collect((Collector<? super Object, ?, List<? extends ArrayListMode>>)Collectors.toList());
        this.arraylistmode = new ModeValue("Arraylist Mode", this, (String[])this.arraylistmodes.stream().map((Function<? super Object, ?>)ArrayListMode::getName).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).toArray(new String[0]));
        this.currentarraylistmode = (this.arraylistmodes.stream().anyMatch(lIIIIIlIllllI -> lIIIIIlIllllI.getName().equalsIgnoreCase(this.arraylistmode.getValue())) ? this.arraylistmodes.stream().filter(lIIIIIllIIIlI -> lIIIIIllIIIlI.getName().equalsIgnoreCase(this.arraylistmode.getValue())).findAny().get() : null);
        this.infomodes = ReflectUtils.getReflects(String.valueOf(new StringBuilder().append(this.getClass().getPackage().getName()).append(".hud")), InfoMode.class).stream().map(lIIIIIllIlIlI -> {
            try {
                return (InfoMode)lIIIIIllIlIlI.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception lIIIIIllIlIll) {
                lIIIIIllIlIll.printStackTrace();
                return null;
            }
        }).sorted(Comparator.comparing(lIIIIIllIllll -> (lIIIIIllIllll != null) ? lIIIIIllIllll.getName() : null)).collect((Collector<? super Object, ?, List<? extends InfoMode>>)Collectors.toList());
        this.infomode = new ModeValue("Info Mode", this, (String[])this.infomodes.stream().map((Function<? super Object, ?>)InfoMode::getName).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).toArray(new String[0]));
        this.currentinfomode = (this.infomodes.stream().anyMatch(lIIIIIlllIIll -> lIIIIIlllIIll.getName().equalsIgnoreCase(this.infomode.getValue())) ? this.infomodes.stream().filter(lIIIIIlllIlll -> lIIIIIlllIlll.getName().equalsIgnoreCase(this.infomode.getValue())).findAny().get() : null);
        this.colorMode = new ModeValue("Color Mode", this, new String[] { "Sleek", "Rainbow", "Astolfo", "Nitrogen" });
        this.line = new ModeValue("Line", this, new String[] { "None", "Top", "Wrapped" });
        this.bgalpha = new NumberValue<Integer>("Alpha", this, 80, 0, 200, 1);
        this.font = new BooleanValue("Font", this, false);
        this.noti = new BooleanValue("Notifications", this, true);
        this.hideRender = new BooleanValue("Hide Render", this, true);
        this.clientName = new StringValue("Client Name", this, "Sleek");
        this.listSuffix = new StringValue("Module Suffix", this, " [%s]");
        this.arrayListY = new NumberValue("ArrayList Y", this, (T)4, (T)1, (T)20, (T)1);
        this.scoreboardLocation = new ModeValue("Scoreboard", this, new String[] { "Right", "Left" });
        this.scoreboardPos = new NumberValue<Double>("Scoreboard Y", this, 0.0, -500.0, 500.0, 1.0);
    }
    
    public BooleanValue getHideRender() {
        return this.hideRender;
    }
    
    public StringValue getListSuffix() {
        return this.listSuffix;
    }
    
    @Override
    public void onDisable() {
        this.currentwatermarkmode.onDisable();
        this.currentarraylistmode.onDisable();
        this.currentinfomode.onDisable();
    }
    
    public StringValue getClientName() {
        return this.clientName;
    }
    
    @Override
    public void onEnable() {
        this.currentwatermarkmode = (this.watermarkmodes.stream().anyMatch(lIIIIIlllllIl -> lIIIIIlllllIl.getName().equalsIgnoreCase(this.watermarkmode.getValue())) ? this.watermarkmodes.stream().filter(lIIIIlIIIIlIl -> lIIIIlIIIIlIl.getName().equalsIgnoreCase(this.watermarkmode.getValue())).findAny().get() : null);
        this.currentarraylistmode = (this.arraylistmodes.stream().anyMatch(lIIIIlIIIlIll -> lIIIIlIIIlIll.getName().equalsIgnoreCase(this.arraylistmode.getValue())) ? this.arraylistmodes.stream().filter(lIIIIlIIIllll -> lIIIIlIIIllll.getName().equalsIgnoreCase(this.arraylistmode.getValue())).findAny().get() : null);
        this.currentinfomode = (this.infomodes.stream().anyMatch(lIIIIlIIlIlIl -> lIIIIlIIlIlIl.getName().equalsIgnoreCase(this.infomode.getValue())) ? this.infomodes.stream().filter(lIIIIlIIlllIl -> lIIIIlIIlllIl.getName().equalsIgnoreCase(this.infomode.getValue())).findAny().get() : null);
        this.currentwatermarkmode.onEnable();
        this.currentarraylistmode.onEnable();
        this.currentinfomode.onEnable();
    }
    
    public ModeValue getInfomode() {
        return this.infomode;
    }
    
    public List<? extends InfoMode> getInfomodes() {
        return this.infomodes;
    }
    
    public List<? extends WaterMarkMode> getWatermarkmodes() {
        return this.watermarkmodes;
    }
}
