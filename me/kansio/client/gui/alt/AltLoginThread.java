package me.kansio.client.gui.alt;

import net.minecraft.client.*;
import net.minecraft.util.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.*;

public class AltLoginThread extends Thread
{
    private final /* synthetic */ Minecraft mc;
    private final /* synthetic */ String username;
    private final /* synthetic */ String password;
    private /* synthetic */ String status;
    
    @Override
    public void run() {
        if (this.password.equals("")) {
            this.mc.session = new Session(this.username.replace("&", "§"), "", "", "mojang");
            this.status = String.valueOf(new StringBuilder().append(EnumChatFormatting.GREEN).append("Set username to ").append(this.username));
            return;
        }
        this.status = String.valueOf(new StringBuilder().append(EnumChatFormatting.AQUA).append("Authenticating..."));
        final Session lllIllllIIlI = this.createSession(this.username, this.password);
        if (lllIllllIIlI == null) {
            this.status = String.valueOf(new StringBuilder().append(EnumChatFormatting.RED).append("Failed"));
        }
        else {
            this.status = String.valueOf(new StringBuilder().append(EnumChatFormatting.GREEN).append("Logged into ").append(lllIllllIIlI.getUsername()));
            this.mc.session = lllIllllIIlI;
        }
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public AltLoginThread(final String llllIlIIIlIl, final String llllIIllllIl) {
        super("Alt Login Thread");
        this.mc = Minecraft.getMinecraft();
        this.username = llllIlIIIlIl;
        this.password = llllIIllllIl;
        this.status = String.valueOf(new StringBuilder().append(EnumChatFormatting.GRAY).append("Waiting"));
    }
    
    public void setStatus(final String lllIlllIllII) {
        this.status = lllIlllIllII;
    }
    
    private Session createSession(final String llllIIlIIIlI, final String llllIIIllIIl) {
        final YggdrasilAuthenticationService llllIIIllllI = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication llllIIIlllII = (YggdrasilUserAuthentication)llllIIIllllI.createUserAuthentication(Agent.MINECRAFT);
        llllIIIlllII.setUsername(llllIIlIIIlI);
        llllIIIlllII.setPassword(llllIIIllIIl);
        try {
            llllIIIlllII.logIn();
            return new Session(llllIIIlllII.getSelectedProfile().getName(), llllIIIlllII.getSelectedProfile().getId().toString(), llllIIIlllII.getAuthenticatedToken(), "mojang");
        }
        catch (Exception llllIIlIIlll) {
            llllIIlIIlll.printStackTrace();
            return null;
        }
    }
}
