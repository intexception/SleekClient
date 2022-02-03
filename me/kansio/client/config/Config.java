package me.kansio.client.config;

import java.io.*;
import me.kansio.client.gui.notification.*;
import java.nio.file.*;

public class Config
{
    private /* synthetic */ String name;
    private /* synthetic */ File file;
    private /* synthetic */ String author;
    private /* synthetic */ String lastUpdated;
    private /* synthetic */ boolean isOnline;
    
    public String getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void setAuthor(final String llllllllllllllllllllllIllIlIllll) {
        this.author = llllllllllllllllllllllIllIlIllll;
    }
    
    public void rename(final String llllllllllllllllllllllIlllIIIlII) {
        try {
            final Path llllllllllllllllllllllIlllIIlIII = Paths.get(this.file.getCanonicalPath(), new String[0]);
            final Path llllllllllllllllllllllIlllIIIlll = Paths.get(this.file.getPath(), new String[0]);
            Files.move(llllllllllllllllllllllIlllIIlIII, llllllllllllllllllllllIlllIIIlll, new CopyOption[0]);
        }
        catch (Exception llllllllllllllllllllllIlllIIIllI) {
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.ERROR, "Error!", "Couldn't rename config!", 1));
            llllllllllllllllllllllIlllIIIllI.printStackTrace();
        }
    }
    
    public void setFile(final File llllllllllllllllllllllIllIIlllll) {
        this.file = llllllllllllllllllllllIllIIlllll;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public Config(final String llllllllllllllllllllllIlllIlIIII, final String llllllllllllllllllllllIlllIlIlIl, final String llllllllllllllllllllllIlllIIlllI, final boolean llllllllllllllllllllllIlllIlIIll, final File llllllllllllllllllllllIlllIlIIlI) {
        this.author = llllllllllllllllllllllIlllIlIlIl;
        this.lastUpdated = llllllllllllllllllllllIlllIIlllI;
        this.name = llllllllllllllllllllllIlllIlIIII;
        this.file = llllllllllllllllllllllIlllIlIIlI;
        this.isOnline = llllllllllllllllllllllIlllIlIIll;
    }
    
    public void setName(final String llllllllllllllllllllllIllIlllIII) {
        this.name = llllllllllllllllllllllIllIlllIII;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setLastUpdated(final String llllllllllllllllllllllIllIlIIllI) {
        this.lastUpdated = llllllllllllllllllllllIllIlIIllI;
    }
    
    public void setOnline(final boolean llllllllllllllllllllllIllIIlIllI) {
        this.isOnline = llllllllllllllllllllllIllIIlIllI;
    }
    
    public boolean isOnline() {
        return this.isOnline;
    }
    
    public Config(final String llllllllllllllllllllllIllllIIIlI, final File llllllllllllllllllllllIlllIllllI) {
        this.name = llllllllllllllllllllllIllllIIIlI;
        this.file = llllllllllllllllllllllIlllIllllI;
    }
    
    public String getName() {
        return this.name;
    }
}
