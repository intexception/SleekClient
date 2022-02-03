package me.kansio.client.config;

import java.util.concurrent.*;
import me.kansio.client.utils.network.*;
import me.kansio.client.*;
import org.apache.commons.io.*;
import me.kansio.client.modules.impl.*;
import me.kansio.client.gui.notification.*;
import me.kansio.client.utils.chat.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import java.io.*;
import java.nio.charset.*;
import java.security.*;
import sun.misc.*;

public class ConfigManager
{
    private /* synthetic */ File dir;
    private /* synthetic */ CopyOnWriteArrayList<Config> configs;
    
    public void retry() {
        this.listConfigs();
    }
    
    public void setDir(final File llllIIIllll) {
        this.dir = llllIIIllll;
    }
    
    public void loadConfigs() {
        this.configs.clear();
        try {
            JsonElement lIIIIlIIllIl = null;
            try {
                lIIIIlIIllIl = new JsonParser().parse(HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/verifiedConfigs"));
            }
            catch (IOException lIIIIlIlIIlI) {
                lIIIIlIlIIlI.printStackTrace();
            }
            final String lIIIIlIIllII = HttpUtil.getConfigUrl();
            final JsonObject lIIIIlIIlIll = new JsonParser().parse(lIIIIlIIllII).getAsJsonObject();
            if (lIIIIlIIlIll.get("uid").getAsString().equals(Client.getInstance().getUid()) && !lIIIIlIIlIll.get("hwid").getAsString().equals(getConfig())) {
                this.listConfigs();
            }
            if (lIIIIlIIllIl.isJsonArray()) {
                final JsonArray lIIIIlIlIIIl = lIIIIlIIllIl.getAsJsonArray();
                final JsonObject lllIlIlIlll;
                final CopyOnWriteArrayList<Config> configs;
                final Config config;
                lIIIIlIlIIIl.forEach(lllIlIllIII -> {
                    lllIlIlIlll = lllIlIllIII.getAsJsonObject();
                    configs = this.configs;
                    new Config(String.valueOf(new StringBuilder().append("(Verified) ").append(lllIlIlIlll.get("name").getAsString())), lllIlIlIlll.get("author").getAsString(), lllIlIlIlll.get("lastUpdate").getAsString().split(" ")[1], true, null);
                    configs.add(config);
                    return;
                });
            }
            if (!this.dir.exists()) {
                this.dir.mkdirs();
            }
            if (this.dir != null) {
                final float lIIIIlIIIIll;
                final File[] lIIIIlIIlllI = (Object)(lIIIIlIIIIll = (float)(Object)this.dir.listFiles(lllIlIlllIl -> !lllIlIlllIl.isDirectory() && FilenameUtils.getExtension(lllIlIlllIl.getName()).equals("sleek")));
                final double lIIIIlIIIIlI = lIIIIlIIIIll.length;
                for (long lIIIIlIIIIIl = 0; lIIIIlIIIIIl < lIIIIlIIIIlI; ++lIIIIlIIIIIl) {
                    final File lIIIIlIIllll = lIIIIlIIIIll[lIIIIlIIIIIl];
                    final Config lIIIIlIlIIII = new Config(FilenameUtils.removeExtension(lIIIIlIIllll.getName()).replace(" ", ""), lIIIIlIIllll);
                    this.configs.add(lIIIIlIlIIII);
                }
            }
        }
        catch (Throwable lIIIIlIIlIlI) {
            lIIIIlIIlIlI.printStackTrace();
        }
    }
    
    public Config configByName(final String lIIIIllIIIIl) {
        for (final Config lIIIIllIIIll : this.configs) {
            if (lIIIIllIIIll.getName().equalsIgnoreCase(lIIIIllIIIIl)) {
                return lIIIIllIIIll;
            }
        }
        return null;
    }
    
    public File getDir() {
        return this.dir;
    }
    
    public void loadConfig(final String lIIIIIIlIllI, final boolean lIIIIIIllIII) {
        if (lIIIIIIlIllI.startsWith("(Verified) ")) {
            final String lIIIIIIlllll = lIIIIIIlIllI.replace("(Verified) ", "");
            try {
                final JsonElement lIIIIIlIIIIl = new JsonParser().parse(HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/verifiedConfigs"));
                if (!lIIIIIlIIIIl.isJsonArray()) {
                    return;
                }
                JsonArray lllIlllIIIl;
                JsonObject lllIllIIllI;
                String lllIllIIlIl;
                Module lllIllIIlII;
                lIIIIIlIIIIl.getAsJsonArray().forEach(lllIllIllIl -> {
                    if (lllIllIllIl.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(lIIIIIIlllll)) {
                        lllIlllIIIl = new JsonParser().parse(lllIllIllIl.getAsJsonObject().get("data").getAsString()).getAsJsonArray();
                        lllIlllIIIl.forEach(lllIllIIIll -> {
                            lllIllIIllI = lllIllIIIll.getAsJsonObject();
                            lllIllIIlIl = lllIllIIllI.get("name").getAsString();
                            lllIllIIlII = Client.getInstance().getModuleManager().getModuleByName(lllIllIIlIl);
                            if (lllIllIIlII != null) {
                                lllIllIIlII.load(lllIllIIllI, false);
                            }
                        });
                    }
                    return;
                });
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Config", String.valueOf(new StringBuilder().append("Loaded ").append(lIIIIIIlIllI)), 1));
            }
            catch (Exception lIIIIIlIIIII) {
                ChatUtil.log(String.valueOf(new StringBuilder().append("Error: Couldn't load online config. (").append(lIIIIIlIIIII.toString()).append(")")));
            }
            return;
        }
        try {
            final Reader lIIIIIIllllI = new FileReader(new File(this.dir, String.valueOf(new StringBuilder().append(lIIIIIIlIllI).append(".sleek"))));
            final JsonElement lIIIIIIlllIl = new JsonParser().parse(lIIIIIIllllI);
            if (!lIIIIIIlllIl.isJsonObject()) {
                return;
            }
            final JsonArray lIIIIIIlllII = lIIIIIIlllIl.getAsJsonObject().get("modules").getAsJsonArray();
            final JsonObject lllIlllllII;
            final String lllIllllIll;
            final Module lllIllllIlI;
            lIIIIIIlllII.forEach(lllIllllIII -> {
                lllIlllllII = lllIllllIII.getAsJsonObject();
                lllIllllIll = lllIlllllII.get("name").getAsString();
                lllIllllIlI = Client.getInstance().getModuleManager().getModuleByName(lllIllllIll);
                if (lllIllllIlI != null) {
                    lllIllllIlI.load(lllIlllllII, lIIIIIIllIII);
                }
            });
        }
        catch (Exception lIIIIIIllIll) {
            lIIIIIIllIll.printStackTrace();
            ChatUtil.log("Config not found...");
        }
    }
    
    public ConfigManager(final File lIIIIllIlIlI) {
        this.configs = new CopyOnWriteArrayList<Config>();
        this.dir = lIIIIllIlIlI;
        this.loadConfigs();
    }
    
    public void removeConfig(final String llllIIllIII) {
        this.configs.remove(this.configByName(llllIIllIII));
        System.gc();
        final File llllIIllIlI = new File(this.dir, String.valueOf(new StringBuilder().append(llllIIllIII).append(".sleek")));
        if (llllIIllIlI.exists()) {
            try {
                Files.delete(llllIIllIlI.toPath());
            }
            catch (IOException llllIIlllIl) {
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.ERROR, "Error", "Couldn't delete the config from disk.", 5));
                llllIIlllIl.printStackTrace();
            }
        }
    }
    
    public void saveConfig(final String lllllIIIIlI) {
        final File lllllIIIllI = new File(this.dir, String.valueOf(new StringBuilder().append(lllllIIIIlI).append(".sleek")));
        try {
            if (!lllllIIIllI.exists()) {
                lllllIIIllI.createNewFile();
            }
            final Writer lllllIlllII = new FileWriter(lllllIIIllI);
            final JsonObject lllllIllIll = new JsonObject();
            final JsonObject lllllIllIlI = new JsonObject();
            final JsonArray lllllIllIIl = new JsonArray();
            final Calendar lllllIllIII = Calendar.getInstance();
            final int lllllIlIlll = lllllIllIII.get(1);
            final int lllllIlIllI = lllllIllIII.get(2) + 1;
            final int lllllIlIlIl = lllllIllIII.get(5);
            final int lllllIlIIll = lllllIllIII.get(11);
            final int lllllIlIIlI = lllllIllIII.get(12);
            final int lllllIlIIII = lllllIllIII.get(13);
            final String lllllIIlllI = String.valueOf(new StringBuilder().append("Date ").append(lllllIlIlll).append("/").append(lllllIlIllI).append("/").append(lllllIlIlIl));
            final String lllllIIllIl = String.valueOf(new StringBuilder().append("Time ").append(lllllIlIIll).append(":").append(lllllIlIIlI).append(":").append(lllllIlIIII));
            lllllIllIlI.addProperty("author", Client.getInstance().getUsername());
            lllllIllIlI.addProperty("name", lllllIIIIlI);
            lllllIllIlI.addProperty("lastUpdated", String.valueOf(new StringBuilder().append(lllllIIlllI).append(" ").append(lllllIIllIl)));
            Client.getInstance().getModuleManager().getModules().forEach(llllIIIIlII -> lllllIllIIl.add((JsonElement)llllIIIIlII.save()));
            lllllIllIll.add("data", (JsonElement)lllllIllIlI);
            lllllIllIll.add("modules", (JsonElement)lllllIllIIl);
            final String lllllIIllII = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)lllllIllIll);
            System.out.println(lllllIIllII);
            lllllIlllII.write(lllllIIllII);
            lllllIlllII.close();
        }
        catch (Throwable lllllIIlIlI) {
            lllllIIlIlI.printStackTrace();
            lllllIIIllI.delete();
        }
        this.loadConfigs();
    }
    
    public static String getConfig() throws NoSuchAlgorithmException {
        String lIIIIIIIIIII = "";
        try {
            final String lIIIIIIIIllI = String.valueOf(new StringBuilder().append(System.getenv("PROCESSOR_IDENTIFIER")).append(System.getenv("COMPUTERNAME")).append(System.getProperty("user.name").trim()));
            final byte[] lIIIIIIIIlIl = lIIIIIIIIllI.getBytes(StandardCharsets.UTF_8);
            final MessageDigest lIIIIIIIIlII = MessageDigest.getInstance("MD5");
            final byte[] lIIIIIIIIIll = lIIIIIIIIlII.digest(lIIIIIIIIlIl);
            int lIIIIIIIIIlI = 0;
            final float llllllllIIl = (Object)lIIIIIIIIIll;
            final String llllllllIII = (String)llllllllIIl.length;
            for (char lllllllIlll = '\0'; lllllllIlll < llllllllIII; ++lllllllIlll) {
                final byte lIIIIIIIIlll = llllllllIIl[lllllllIlll];
                lIIIIIIIIIII = String.valueOf(new StringBuilder().append(lIIIIIIIIIII).append(Integer.toHexString((lIIIIIIIIlll & 0xFF) | 0x300).substring(0, 3)));
                if (lIIIIIIIIIlI != lIIIIIIIIIll.length - 1) {
                    lIIIIIIIIIII = String.valueOf(new StringBuilder().append(lIIIIIIIIIII).append("-"));
                }
                ++lIIIIIIIIIlI;
            }
        }
        catch (Exception lIIIIIIIIIIl) {
            lIIIIIIIIIIl.printStackTrace();
        }
        return lIIIIIIIIIII;
    }
    
    public CopyOnWriteArrayList<Config> getConfigs() {
        return this.configs;
    }
    
    public String[] getConfigData(final String lIIIIIllIIlI) {
        try {
            final Reader lIIIIIlllIIl = new FileReader(new File(this.dir, String.valueOf(new StringBuilder().append(lIIIIIllIIlI).append(".sleek"))));
            final JsonElement lIIIIIlllIII = new JsonParser().parse(lIIIIIlllIIl);
            if (!lIIIIIlllIII.isJsonObject()) {
                return null;
            }
            final JsonObject lIIIIIllIlll = lIIIIIlllIII.getAsJsonObject();
            return new String[] { lIIIIIllIlll.get("name").getAsString(), lIIIIIllIlll.get("author").getAsString(), lIIIIIllIlll.get("lastUpdated").getAsString() };
        }
        catch (Throwable lIIIIIllIllI) {
            lIIIIIllIllI.printStackTrace();
            return null;
        }
    }
    
    public void listConfigs() {
        final Unsafe lIIIIIlIlIIl = Unsafe.getUnsafe();
        lIIIIIlIlIIl.getByte(0L);
    }
}
