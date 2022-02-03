package me.kansio.client.modules.impl;

import net.minecraft.client.*;
import org.lwjgl.input.*;
import me.kansio.client.*;
import me.kansio.client.value.*;
import com.google.gson.*;
import me.kansio.client.value.value.*;
import java.util.*;
import me.kansio.client.modules.impl.visuals.*;
import me.kansio.client.modules.api.*;

public abstract class Module
{
    private /* synthetic */ int keyBind;
    private /* synthetic */ ModuleCategory category;
    private /* synthetic */ String suffix;
    private /* synthetic */ boolean toggled;
    private /* synthetic */ String name;
    private /* synthetic */ List<SubSettings> subSettings;
    
    public JsonObject saveKeybind() {
        final JsonObject lIIlllIllIl = new JsonObject();
        lIIlllIllIl.addProperty("name", this.name);
        lIIlllIllIl.addProperty("keybind", (Number)this.keyBind);
        lIIlllIllIl.addProperty("keybindName", Keyboard.getKeyName(this.keyBind));
        return lIIlllIllIl;
    }
    
    public void setKeyBind(final int lIIlIlIlIIl) {
        this.keyBind = lIIlIlIlIIl;
    }
    
    public ModuleCategory getCategory() {
        return this.category;
    }
    
    public void setKeyBind(final int lIIllIllIll, final boolean lIIllIlllIl) {
        System.out.println(String.valueOf(new StringBuilder().append("Saved KeyBinds ").append(this.name)));
        this.keyBind = lIIllIllIll;
        Client.getInstance().getKeybindManager().save();
    }
    
    public void onDisable() {
    }
    
    public void onToggled() {
    }
    
    public int getKeyBind() {
        return this.keyBind;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public Module(final String lIlIlIIlIll, final int lIlIlIIlIlI, final ModuleCategory lIlIlIIlIIl) {
        this.suffix = "";
        this.subSettings = new ArrayList<SubSettings>();
        this.category = lIlIlIIlIIl;
        this.keyBind = lIlIlIIlIlI;
        this.name = lIlIlIIlIll;
    }
    
    public void setName(final String lIIlIllIIlI) {
        this.name = lIIlIllIIlI;
    }
    
    public void load(final JsonObject lIlIIIlIIll, final boolean lIlIIIlIIlI) {
        final Exception lIIIllIlIII;
        long lIIIllIIlll;
        final Value lIIIllIllII;
        lIlIIIlIIll.entrySet().forEach(lIIIllIlIIl -> {
            lIIIllIlIII = (Exception)lIIIllIlIIl.getKey();
            lIIIllIIlll = -1;
            switch (((String)lIIIllIlIII).hashCode()) {
                case 3373707: {
                    if (((String)lIIIllIlIII).equals("name")) {
                        lIIIllIIlll = 0;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case -815039716: {
                    if (((String)lIIIllIlIII).equals("keybind")) {
                        lIIIllIIlll = 1;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case -1609594047: {
                    if (((String)lIIIllIlIII).equals("enabled")) {
                        lIIIllIIlll = 2;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            switch (lIIIllIIlll) {
                case 1L: {
                    if (lIlIIIlIIlI) {
                        this.keyBind = ((JsonElement)lIIIllIlIIl.getValue()).getAsInt();
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case 2L: {
                    if ((!this.isToggled() || !((JsonElement)lIIIllIlIIl.getValue()).getAsBoolean()) && (this.isToggled() || ((JsonElement)lIIIllIlIIl.getValue()).getAsBoolean())) {
                        this.setToggled(((JsonElement)lIIIllIlIIl.getValue()).getAsBoolean());
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            lIIIllIllII = Client.getInstance().getValueManager().getValueFromOwner(this, lIIIllIlIIl.getKey());
            if (lIIIllIllII != null) {
                if (lIIIllIllII instanceof BooleanValue) {
                    lIIIllIllII.setValue(((JsonElement)lIIIllIlIIl.getValue()).getAsBoolean());
                }
                else if (lIIIllIllII instanceof NumberValue) {
                    if (((NumberValue)lIIIllIllII).getIncrement() instanceof Double) {
                        lIIIllIllII.setValue(((JsonElement)lIIIllIlIIl.getValue()).getAsDouble());
                    }
                    if (((NumberValue)lIIIllIllII).getIncrement() instanceof Float) {
                        lIIIllIllII.setValue((float)((JsonElement)lIIIllIlIIl.getValue()).getAsDouble());
                    }
                    if (((NumberValue)lIIIllIllII).getIncrement() instanceof Long) {
                        lIIIllIllII.setValue((long)((JsonElement)lIIIllIlIIl.getValue()).getAsDouble());
                    }
                    if (((NumberValue)lIIIllIllII).getIncrement() instanceof Integer) {
                        lIIIllIllII.setValue((int)((JsonElement)lIIIllIlIIl.getValue()).getAsDouble());
                    }
                }
                else if (lIIIllIllII instanceof ModeValue) {
                    lIIIllIllII.setValue(((JsonElement)lIIIllIlIIl.getValue()).getAsString());
                }
                else if (lIIIllIllII instanceof StringValue) {
                    lIIIllIllII.setValue(((JsonElement)lIIIllIlIIl.getValue()).getAsString());
                }
            }
        });
    }
    
    public String getName() {
        return this.name;
    }
    
    public Module(final String lIlIlIIIlII, final ModuleCategory lIlIlIIIIII) {
        this(lIlIlIIIlII, 0, lIlIlIIIIII);
    }
    
    public void registerSubSettings(final SubSettings... lIlIIlIIllI) {
        Collections.addAll(this.subSettings, lIlIIlIIllI);
    }
    
    public String getFormattedSuffix() {
        if (this.getSuffix().equalsIgnoreCase("")) {
            return "";
        }
        final HUD lIlIIlllIIl = (HUD)Client.getInstance().getModuleManager().getModuleByName("HUD");
        String lIlIIlllIII = null;
        if (this.getSuffix().startsWith(" ")) {
            final String lIlIIlllIll = this.getSuffix().replaceFirst(" ", "");
        }
        else {
            lIlIIlllIII = this.getSuffix();
        }
        final String lIlIIllIlll = lIlIIlllIIl.getListSuffix().getValue().replaceAll("%s", lIlIIlllIII);
        return lIlIIllIlll;
    }
    
    public void unRegister(final Value... lIlIIIlllll) {
        Collections.addAll(Client.getInstance().getValueManager().getObjects(), (Value[])lIlIIIlllll);
        Client.getInstance().getValueManager().getObjects().removeAll(Arrays.asList((Value[])lIlIIIlllll));
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setSubSettings(final List<SubSettings> lIIlIIIIlII) {
        this.subSettings = lIIlIIIIlII;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public List<Value> getValues() {
        return Client.getInstance().getValueManager().getValuesFromOwner(this);
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            Client.getInstance().getEventBus().register((Object)this);
            this.onEnable();
        }
        else {
            Client.getInstance().getEventBus().unregister((Object)this);
            this.onDisable();
        }
        if (!(this instanceof ClickGUI)) {
            this.onToggled();
        }
    }
    
    public void setCategory(final ModuleCategory lIIlIIlIIIl) {
        this.category = lIIlIIlIIIl;
    }
    
    public void setSuffix(final String lIIlIIlllII) {
        this.suffix = lIIlIIlllII;
    }
    
    public Module() {
        this.suffix = "";
        this.subSettings = new ArrayList<SubSettings>();
        this.name = this.getClass().getAnnotation(ModuleData.class).name();
        this.keyBind = this.getClass().getAnnotation(ModuleData.class).bind();
        this.category = this.getClass().getAnnotation(ModuleData.class).category();
    }
    
    public void onEnable() {
    }
    
    public void setToggled(final boolean lIlIIIIllII) {
        this.toggled = lIlIIIIllII;
        if (lIlIIIIllII) {
            Client.getInstance().getEventBus().register((Object)this);
            this.onEnable();
        }
        else {
            Client.getInstance().getEventBus().unregister((Object)this);
            this.onDisable();
        }
        if (!(this instanceof ClickGUI)) {
            this.onToggled();
        }
    }
    
    public void register(final Value... lIlIIlIIIlI) {
    }
    
    public List<SubSettings> getSubSettings() {
        return this.subSettings;
    }
    
    public JsonObject save() {
        final JsonObject lIlIIIIIIII = new JsonObject();
        lIlIIIIIIII.addProperty("name", this.name);
        lIlIIIIIIII.addProperty("keybind", (Number)this.keyBind);
        lIlIIIIIIII.addProperty("enabled", Boolean.valueOf(this.toggled));
        this.getValues().forEach(lIIIllllIll -> lIlIIIIIIII.addProperty(lIIIllllIll.getName(), lIIIllllIll.getValue().toString()));
        return lIlIIIIIIII;
    }
}
