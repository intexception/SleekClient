package me.kansio.client.commands.impl;

import me.kansio.client.commands.*;
import me.kansio.client.friend.*;
import java.text.*;
import me.kansio.client.utils.chat.*;
import me.kansio.client.*;

@CommandData(name = "friend", description = "Handles friends")
public class FriendCommand extends Command
{
    @Override
    public void run(final String[] lIlllIllllIIII) {
        if (lIlllIllllIIII.length > 1) {
            final String lIlllIlllIllll = lIlllIllllIIII[0].toLowerCase();
            float lIlllIlllIlllI = -1;
            switch (lIlllIlllIllll.hashCode()) {
                case 96417: {
                    if (lIlllIlllIllll.equals("add")) {
                        lIlllIlllIlllI = 0;
                        break;
                    }
                    break;
                }
                case 99339: {
                    if (lIlllIlllIllll.equals("del")) {
                        lIlllIlllIlllI = 1;
                        break;
                    }
                    break;
                }
                case -1335458389: {
                    if (lIlllIlllIllll.equals("delete")) {
                        lIlllIlllIlllI = 2;
                        break;
                    }
                    break;
                }
                case -934610812: {
                    if (lIlllIlllIllll.equals("remove")) {
                        lIlllIlllIlllI = 3;
                        break;
                    }
                    break;
                }
            }
            switch (lIlllIlllIlllI) {
                case 0.0f: {
                    if (lIlllIllllIIII.length == 3) {
                        Client.getInstance().getFriendManager().addFriend(new Friend(lIlllIllllIIII[1], lIlllIllllIIII[2]));
                        ChatUtil.log(MessageFormat.format("Added friend {0} as {1}", lIlllIllllIIII[1], lIlllIllllIIII[2]));
                        break;
                    }
                    Client.getInstance().getFriendManager().addFriend(new Friend(lIlllIllllIIII[1], lIlllIllllIIII[1]));
                    ChatUtil.log(MessageFormat.format("Added friend {0}", lIlllIllllIIII[1]));
                    break;
                }
                case 1.0f:
                case 2.0f:
                case 3.0f: {
                    Client.getInstance().getFriendManager().removeFriend(lIlllIllllIIII[1]);
                    ChatUtil.log(MessageFormat.format("Removed friend {0}", lIlllIllllIIII[1]));
                    break;
                }
            }
        }
        else if (lIlllIllllIIII.length == 1) {
            final String lIlllIlllIllll = lIlllIllllIIII[0].toLowerCase();
            float lIlllIlllIlllI = -1;
            switch (lIlllIlllIllll.hashCode()) {
                case 94746189: {
                    if (lIlllIlllIllll.equals("clear")) {
                        lIlllIlllIlllI = 0;
                        break;
                    }
                    break;
                }
                case 3322014: {
                    if (lIlllIlllIllll.equals("list")) {
                        lIlllIlllIlllI = 1;
                        break;
                    }
                    break;
                }
            }
            switch (lIlllIlllIlllI) {
                case 0.0f: {
                    Client.getInstance().getFriendManager().clearFriends();
                    ChatUtil.log(MessageFormat.format("You now have {0} friends", Client.getInstance().getFriendManager().getFriends().size()));
                    break;
                }
                case 1.0f: {
                    Client.getInstance().getFriendManager().getFriends().forEach(lIlllIlllIlIll -> ChatUtil.log(MessageFormat.format(" - {0} §7({1})", lIlllIlllIlIll.getName(), lIlllIlllIlIll.getDisplayName())));
                    break;
                }
            }
        }
    }
}
