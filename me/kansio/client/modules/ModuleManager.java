package me.kansio.client.modules;

import me.kansio.client.modules.impl.*;
import me.kansio.client.utils.font.*;
import me.kansio.client.utils.java.*;
import net.minecraft.client.gui.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.*;
import me.kansio.client.*;
import java.util.*;
import java.lang.reflect.*;

public class ModuleManager
{
    private final /* synthetic */ ArrayList<Module> modules;
    
    public List<Module> getModulesSorted(final MCFontRenderer llIlIllIIllIIl) {
        final List<Module> llIlIllIIllIII = new ArrayList<Module>(this.modules);
        final String llIlIlIIllIlll;
        final String llIlIlIIllIllI;
        final String llIlIlIIllIlIl;
        final String llIlIlIIllIlII;
        final int llIlIlIIllIIll;
        final int llIlIlIIllIIlI;
        llIlIllIIllIII.sort((llIlIlIIllIIII, llIlIlIIlllIII) -> {
            llIlIlIIllIlll = ((llIlIlIIllIIII.getFormattedSuffix() == null) ? "" : llIlIlIIllIIII.getFormattedSuffix());
            llIlIlIIllIllI = ((llIlIlIIlllIII.getFormattedSuffix() == null) ? "" : llIlIlIIlllIII.getFormattedSuffix());
            llIlIlIIllIlIl = llIlIlIIllIIII.getName();
            llIlIlIIllIlII = llIlIlIIlllIII.getName();
            llIlIlIIllIIll = llIlIllIIllIIl.getStringWidth(String.valueOf(new StringBuilder().append(llIlIlIIllIlIl).append(llIlIlIIllIlll)));
            llIlIlIIllIIlI = llIlIllIIllIIl.getStringWidth(String.valueOf(new StringBuilder().append(llIlIlIIllIlII).append(llIlIlIIllIllI)));
            return llIlIlIIllIIlI - llIlIlIIllIIll;
        });
        return llIlIllIIllIII;
    }
    
    public void reloadModules() {
        for (final Module llIlIllIlIllll : this.modules) {
            if (llIlIllIlIllll.isToggled()) {
                llIlIllIlIllll.toggle();
            }
        }
        this.modules.clear();
        for (final Class<?> llIlIllIlIllII : ReflectUtils.getReflects(String.valueOf(new StringBuilder().append(this.getClass().getPackage().getName()).append(".impl")), Module.class)) {
            try {
                final Module llIlIllIlIlllI = (Module)llIlIllIlIllII.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                this.registerModule(llIlIllIlIlllI);
            }
            catch (Exception llIlIllIlIllIl) {
                llIlIllIlIllIl.printStackTrace();
            }
        }
    }
    
    public Module getModuleByName(final String llIlIllIIIIIII) {
        for (final Module llIlIllIIIIlII : this.modules) {
            if (llIlIllIIIIlII.getName().equalsIgnoreCase(llIlIllIIIIIII)) {
                return llIlIllIIIIlII;
            }
        }
        return null;
    }
    
    public Module getModuleByNameIgnoreSpace(final String llIlIlIlllIlIl) {
        for (final Module llIlIlIllllIIl : this.modules) {
            if (llIlIlIllllIIl.getName().replaceAll(" ", "").equalsIgnoreCase(llIlIlIlllIlIl)) {
                return llIlIlIllllIIl;
            }
        }
        return null;
    }
    
    public List<Module> getModulesFromCategory(final ModuleCategory llIlIlIllIlIll) {
        final ArrayList<Module> llIlIlIllIlIlI = new ArrayList<Module>();
        for (final Module llIlIlIllIllIl : this.modules) {
            if (llIlIlIllIllIl.getCategory() == llIlIlIllIlIll) {
                llIlIlIllIlIlI.add(llIlIlIllIllIl);
            }
        }
        return llIlIlIllIlIlI;
    }
    
    public ArrayList<Module> getModules() {
        return this.modules;
    }
    
    public <T extends Module> T getModuleByClass(final Class<? extends T> llIlIllIIIlIll) {
        final NoSuchElementException ex;
        return (T)this.modules.stream().filter(llIlIlIlIllIIl -> llIlIlIlIllIIl.getClass().equals(llIlIllIIIlIll)).findFirst().orElseThrow(() -> {
            new NoSuchElementException(String.valueOf(new StringBuilder().append("RETARD ALERT: Element belonging to class '").append(llIlIllIIIlIll.getName()).append("' not found")));
            return ex;
        });
    }
    
    public void registerModule(final Module llIlIllIlllIll) {
        this.modules.add(llIlIllIlllIll);
        final Exception llIlIllIlllIII = (Object)llIlIllIlllIll.getClass().getDeclaredFields();
        final short llIlIllIllIlll = (short)llIlIllIlllIII.length;
        for (String llIlIllIllIllI = (String)0; llIlIllIllIllI < llIlIllIllIlll; ++llIlIllIllIllI) {
            final Field llIlIllIllllIl = llIlIllIlllIII[llIlIllIllIllI];
            try {
                llIlIllIllllIl.setAccessible(true);
                final Object llIlIllIllllll = llIlIllIllllIl.get(llIlIllIlllIll);
                if (llIlIllIllllll instanceof Value) {
                    Collections.addAll(Client.getInstance().getValueManager().getObjects(), (Value[])new Value[] { (Value)llIlIllIllllll });
                }
            }
            catch (IllegalAccessException llIlIllIlllllI) {
                llIlIllIlllllI.printStackTrace();
            }
        }
    }
    
    public void sort(final FontRenderer llIlIllIIIllll) {
        final String llIlIlIlIIlllI;
        final String llIlIlIlIIllIl;
        final int llIlIlIlIIllII;
        final int llIlIlIlIIlIll;
        this.modules.sort((llIlIlIlIIlIIl, llIlIlIlIIllll) -> {
            llIlIlIlIIlllI = ((llIlIlIlIIlIIl.getFormattedSuffix() == null) ? "" : llIlIlIlIIlIIl.getFormattedSuffix());
            llIlIlIlIIllIl = ((llIlIlIlIIllll.getFormattedSuffix() == null) ? "" : llIlIlIlIIllll.getFormattedSuffix());
            llIlIlIlIIllII = llIlIllIIIllll.getStringWidth(String.valueOf(new StringBuilder().append(llIlIlIlIIlIIl.getName()).append(llIlIlIlIIlllI)));
            llIlIlIlIIlIll = llIlIllIIIllll.getStringWidth(String.valueOf(new StringBuilder().append(llIlIlIlIIllll.getName()).append(llIlIlIlIIllIl)));
            return llIlIlIlIIlIll - llIlIlIlIIllII;
        });
    }
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
        for (final Class<?> llIlIlllIIllII : ReflectUtils.getReflects(String.valueOf(new StringBuilder().append(this.getClass().getPackage().getName()).append(".impl")), Module.class)) {
            try {
                final Module llIlIlllIIlllI = (Module)llIlIlllIIllII.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                this.registerModule(llIlIlllIIlllI);
            }
            catch (Exception llIlIlllIIllIl) {
                llIlIlllIIllIl.printStackTrace();
            }
        }
    }
    
    public List<Module> getModulesSorted(final FontRenderer llIlIllIIlllll) {
        final List<Module> llIlIllIlIIIIl = new ArrayList<Module>(this.modules);
        final String llIlIlIIIlIllI;
        final String llIlIlIIIlIlIl;
        final String llIlIlIIIlIlII;
        final String llIlIlIIIlIIll;
        final int llIlIlIIIlIIlI;
        final int llIlIlIIIlIIIl;
        llIlIllIlIIIIl.sort((llIlIlIIIllIII, llIlIlIIIIlllI) -> {
            llIlIlIIIlIllI = ((llIlIlIIIllIII.getFormattedSuffix() == null) ? "" : llIlIlIIIllIII.getFormattedSuffix());
            llIlIlIIIlIlIl = ((llIlIlIIIIlllI.getFormattedSuffix() == null) ? "" : llIlIlIIIIlllI.getFormattedSuffix());
            llIlIlIIIlIlII = llIlIlIIIllIII.getName();
            llIlIlIIIlIIll = llIlIlIIIIlllI.getName();
            llIlIlIIIlIIlI = llIlIllIIlllll.getStringWidth(String.valueOf(new StringBuilder().append(llIlIlIIIlIlII).append(llIlIlIIIlIllI)));
            llIlIlIIIlIIIl = llIlIllIIlllll.getStringWidth(String.valueOf(new StringBuilder().append(llIlIlIIIlIIll).append(llIlIlIIIlIlIl)));
            return llIlIlIIIlIIIl - llIlIlIIIlIIlI;
        });
        return llIlIllIlIIIIl;
    }
}
