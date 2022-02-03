package me.kansio.client.friend;

import java.util.concurrent.*;

public class FriendManager
{
    private final /* synthetic */ CopyOnWriteArrayList<Friend> friends;
    
    public boolean isFriend(final String lIIlIlllIllIlI) {
        for (final Friend lIIlIlllIllllI : this.friends) {
            if (lIIlIlllIllllI.getName().equals(lIIlIlllIllIlI)) {
                return true;
            }
        }
        return false;
    }
    
    public CopyOnWriteArrayList<Friend> getFriends() {
        return this.friends;
    }
    
    public Friend getFriend(final String lIIlIlllIlIIIl) {
        for (final Friend lIIlIlllIlIIll : this.friends) {
            if (lIIlIlllIlIIll.getName().equals(lIIlIlllIlIIIl)) {
                return lIIlIlllIlIIll;
            }
        }
        return null;
    }
    
    public FriendManager() {
        this.friends = new CopyOnWriteArrayList<Friend>();
    }
    
    public void removeFriend(final String lIIlIllllIIIll) {
        this.friends.removeIf(lIIlIlllIIIllI -> lIIlIlllIIIllI.getName().equalsIgnoreCase(lIIlIllllIIIll));
    }
    
    public void clearFriends() {
        this.friends.clear();
    }
    
    public void addFriend(final Friend lIIlIllllIlIIl) {
        this.friends.add(lIIlIllllIlIIl);
    }
}
