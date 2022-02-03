package me.kansio.client.keybind;

import me.kansio.client.*;
import me.kansio.client.modules.impl.*;
import com.google.gson.*;
import java.io.*;

public class KeybindManager
{
    private /* synthetic */ File keybindFile;
    
    public void load() {
        try {
            if (!this.keybindFile.exists()) {
                this.keybindFile.createNewFile();
                return;
            }
            final Reader llIIllllIlIIll = new FileReader(this.keybindFile);
            final JsonElement llIIllllIlIIlI = new JsonParser().parse(llIIllllIlIIll);
            if (!llIIllllIlIIlI.isJsonArray()) {
                return;
            }
            final JsonArray llIIllllIlIIIl = llIIllllIlIIlI.getAsJsonArray();
            final JsonObject llIIlllIllIIlI;
            final String llIIlllIllIIIl;
            final Module llIIlllIllIIII;
            llIIllllIlIIIl.forEach(llIIlllIlIllll -> {
                llIIlllIllIIlI = llIIlllIlIllll.getAsJsonObject();
                llIIlllIllIIIl = llIIlllIllIIlI.get("name").getAsString();
                llIIlllIllIIII = Client.getInstance().getModuleManager().getModuleByName(llIIlllIllIIIl);
                if (llIIlllIllIIII != null && llIIlllIllIIlI.get("keybind") != null) {
                    llIIlllIllIIII.setKeyBind(llIIlllIllIIlI.get("keybind").getAsInt());
                }
            });
        }
        catch (Exception llIIllllIlIIII) {
            llIIllllIlIIII.printStackTrace();
        }
    }
    
    public KeybindManager(final File llIIllllIllIlI) {
        this.keybindFile = new File(llIIllllIllIlI, "keybinds.json");
        this.load();
    }
    
    public void save() {
        try {
            final JsonArray llIIllllIIIllI = new JsonArray();
            Client.getInstance().getModuleManager().getModules().forEach(llIIlllIlllIII -> llIIllllIIIllI.add((JsonElement)llIIlllIlllIII.saveKeybind()));
            final Writer llIIllllIIIlIl = new FileWriter(this.keybindFile);
            final String llIIllllIIIlII = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)llIIllllIIIllI);
            System.out.println(llIIllllIIIlII);
            llIIllllIIIlIl.write(llIIllllIIIlII);
            llIIllllIIIlIl.close();
        }
        catch (Exception llIIllllIIIIll) {
            llIIllllIIIIll.printStackTrace();
        }
    }
}
