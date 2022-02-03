package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.*;
import me.kansio.client.modules.impl.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.utils.network.*;
import java.text.*;
import java.io.*;
import com.google.gson.*;

@CommandData(name = "config", description = "Handles configs")
public class ConfigCommand extends Command
{
    @Override
    public void run(final String[] lIIIlIllIIIllI) {
        try {
            final String lowerCase = lIIIlIllIIIllI[0].toLowerCase();
            String lIIIlIllIIIlII = (String)(-1);
            switch (lowerCase.hashCode()) {
                case 3522941: {
                    if (lowerCase.equals("save")) {
                        lIIIlIllIIIlII = (String)0;
                        break;
                    }
                    break;
                }
                case 3327206: {
                    if (lowerCase.equals("load")) {
                        lIIIlIllIIIlII = (String)1;
                        break;
                    }
                    break;
                }
                case -934641255: {
                    if (lowerCase.equals("reload")) {
                        lIIIlIllIIIlII = (String)2;
                        break;
                    }
                    break;
                }
                case -1335458389: {
                    if (lowerCase.equals("delete")) {
                        lIIIlIllIIIlII = (String)3;
                        break;
                    }
                    break;
                }
                case -934610812: {
                    if (lowerCase.equals("remove")) {
                        lIIIlIllIIIlII = (String)4;
                        break;
                    }
                    break;
                }
                case 3322014: {
                    if (lowerCase.equals("list")) {
                        lIIIlIllIIIlII = (String)5;
                        break;
                    }
                    break;
                }
                case -1994383672: {
                    if (lowerCase.equals("verified")) {
                        lIIIlIllIIIlII = (String)6;
                        break;
                    }
                    break;
                }
            }
            Label_0624: {
                switch (lIIIlIllIIIlII) {
                    case 0L: {
                        Client.getInstance().getConfigManager().saveConfig(lIIIlIllIIIllI[1]);
                        ChatUtil.log(String.valueOf(new StringBuilder().append("Saved Config ").append(lIIIlIllIIIllI[1])));
                        break;
                    }
                    case 1L: {
                        if (lIIIlIllIIIllI.length == 3) {
                            Client.getInstance().getConfigManager().loadConfig(lIIIlIllIIIllI[1], lIIIlIllIIIllI[2].equals("keys"));
                            break;
                        }
                        Client.getInstance().getConfigManager().loadConfig(lIIIlIllIIIllI[1], false);
                        break;
                    }
                    case 2L: {
                        Client.getInstance().getConfigManager().loadConfigs();
                        ChatUtil.log("Reloaded Configs");
                        break;
                    }
                    case 3L:
                    case 4L: {
                        Client.getInstance().getConfigManager().removeConfig(lIIIlIllIIIllI[1]);
                        ChatUtil.log(String.valueOf(new StringBuilder().append("Removed Config ").append(lIIIlIllIIIllI[1])));
                        break;
                    }
                    case 5L: {
                        final double lIIIlIllIIIIll = (Object)Client.getInstance().getConfigManager().getDir().listFiles();
                        final float lIIIlIllIIIIlI = lIIIlIllIIIIll.length;
                        for (final File lIIIlIllIIllIl : lIIIlIllIIIIll) {
                            ChatUtil.log(String.valueOf(new StringBuilder().append("- ").append(lIIIlIllIIllIl.getName().replaceAll(".sleek", ""))));
                        }
                        break;
                    }
                    case 6L: {
                        final double lIIIlIllIIIIll = (double)lIIIlIllIIIllI[1].toLowerCase();
                        float lIIIlIllIIIIlI = -1;
                        switch (((String)lIIIlIllIIIIll).hashCode()) {
                            case 3322014: {
                                if (((String)lIIIlIllIIIIll).equals("list")) {
                                    lIIIlIllIIIIlI = 0;
                                    break;
                                }
                                break;
                            }
                            case 3327206: {
                                if (((String)lIIIlIllIIIIll).equals("load")) {
                                    lIIIlIllIIIIlI = 1;
                                    break;
                                }
                                break;
                            }
                        }
                        switch (lIIIlIllIIIIlI) {
                            case 0.0f: {
                                final JsonElement lIIIlIllIIlIll = new JsonParser().parse(HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/verifiedConfigs"));
                                if (lIIIlIllIIlIll.isJsonArray()) {
                                    final JsonArray lIIIlIllIIllII = lIIIlIllIIlIll.getAsJsonArray();
                                    final JsonObject lIIIlIlIlIIlll;
                                    lIIIlIllIIllII.forEach(lIIIlIlIlIlIII -> {
                                        lIIIlIlIlIIlll = lIIIlIlIlIlIII.getAsJsonObject();
                                        ChatUtil.log(MessageFormat.format("Config \"{0}\" made by {1} was last updated on {2}", lIIIlIlIlIIlll.get("name").getAsString(), lIIIlIlIlIIlll.get("author").getAsString(), lIIIlIlIlIIlll.get("lastUpdate").getAsString().split(" ")[1]));
                                        return;
                                    });
                                    break Label_0624;
                                }
                                break Label_0624;
                            }
                            case 1.0f: {
                                final JsonElement lIIIlIllIIlIlI = new JsonParser().parse(HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/verifiedConfigs"));
                                if (!lIIIlIllIIlIlI.isJsonArray()) {
                                    return;
                                }
                                JsonArray lIIIlIlIllllII;
                                JsonObject lIIIlIlIllIIIl;
                                String lIIIlIlIllIIII;
                                Module lIIIlIlIlIllll;
                                lIIIlIllIIlIlI.getAsJsonArray().forEach(lIIIlIlIlllIII -> {
                                    if (lIIIlIlIlllIII.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(lIIIlIllIIIllI[2])) {
                                        lIIIlIlIllllII = new JsonParser().parse(lIIIlIlIlllIII.getAsJsonObject().get("data").getAsString()).getAsJsonArray();
                                        lIIIlIlIllllII.forEach(lIIIlIlIlIlllI -> {
                                            lIIIlIlIllIIIl = lIIIlIlIlIlllI.getAsJsonObject();
                                            lIIIlIlIllIIII = lIIIlIlIllIIIl.get("name").getAsString();
                                            lIIIlIlIlIllll = Client.getInstance().getModuleManager().getModuleByName(lIIIlIlIllIIII);
                                            if (lIIIlIlIlIllll != null) {
                                                lIIIlIlIlIllll.load(lIIIlIlIllIIIl, false);
                                            }
                                        });
                                    }
                                    return;
                                });
                                break Label_0624;
                            }
                        }
                        break;
                    }
                }
            }
        }
        catch (Throwable lIIIlIllIIlIIl) {
            lIIIlIllIIlIIl.printStackTrace();
            ChatUtil.log(".config save <configName>");
            ChatUtil.log(".config load <configName>");
            ChatUtil.log(".config remove <configName>");
            ChatUtil.log(".config verified <list | load> [name]");
            ChatUtil.log(".config reload");
            ChatUtil.log(".config list");
        }
    }
}
