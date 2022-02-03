package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.utils.network.*;
import me.kansio.client.*;
import me.kansio.client.utils.chat.*;
import java.text.*;
import java.io.*;
import com.google.gson.*;

@CommandData(name = "users", description = "Lists the current online users")
public class UsersCommand extends Command
{
    @Override
    public void run(final String[] lllllllllllllllllllllIIIlllIlIlI) {
        try {
            final JsonElement lllllllllllllllllllllIIIlllIllIl = new JsonParser().parse(HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/getclientplayers"));
            if (lllllllllllllllllllllIIIlllIllIl.isJsonArray()) {
                Client.getInstance().getUsers().clear();
                for (final JsonElement lllllllllllllllllllllIIIlllIlllI : lllllllllllllllllllllIIIlllIllIl.getAsJsonArray()) {
                    final JsonObject lllllllllllllllllllllIIIlllIllll = lllllllllllllllllllllIIIlllIlllI.getAsJsonObject();
                    Client.getInstance().getUsers().put(lllllllllllllllllllllIIIlllIllll.get("ign").getAsString(), lllllllllllllllllllllIIIlllIllll.get("name").getAsString());
                    ChatUtil.log(MessageFormat.format(ChatUtil.translateColorCodes("&b{0} [{1}] &7({2})&b - {3}"), lllllllllllllllllllllIIIlllIllll.get("name").getAsString(), lllllllllllllllllllllIIIlllIllll.get("uid").getAsString(), lllllllllllllllllllllIIIlllIllll.get("ign").getAsString(), lllllllllllllllllllllIIIlllIllll.get("serverIP").getAsString()));
                }
            }
        }
        catch (IOException lllllllllllllllllllllIIIlllIllII) {
            lllllllllllllllllllllIIIlllIllII.printStackTrace();
        }
    }
}
