package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.libs.gson.*;

public class ChatRewriter
{
    public static void toClient(final JsonObject obj, final UserConnection user) {
        if (obj.get("translate") != null && obj.get("translate").getAsString().equals("gameMode.changed")) {
            final EntityTracker1_9 tracker = user.getEntityTracker(Protocol1_9To1_8.class);
            final String gameMode = tracker.getGameMode().getText();
            final JsonObject gameModeObject = new JsonObject();
            gameModeObject.addProperty("text", gameMode);
            gameModeObject.addProperty("color", "gray");
            gameModeObject.addProperty("italic", true);
            final JsonArray array = new JsonArray();
            array.add(gameModeObject);
            obj.add("with", array);
        }
    }
}
