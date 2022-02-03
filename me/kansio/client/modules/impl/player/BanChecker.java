package me.kansio.client.modules.impl.player;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.event.impl.*;
import java.util.concurrent.*;
import me.kansio.client.utils.network.*;
import java.util.*;
import me.kansio.client.gui.notification.*;
import com.google.gson.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Ban Checker", description = "Checks how many people got staff banned recently", category = ModuleCategory.PLAYER)
public class BanChecker extends Module
{
    private /* synthetic */ int lastStaffBans;
    private /* synthetic */ Stopwatch watch;
    private /* synthetic */ int currentStaffBans;
    
    @Override
    public void onEnable() {
        this.watch.resetTime();
        this.lastStaffBans = 0;
        this.currentStaffBans = this.lastStaffBans;
    }
    
    @Subscribe
    public void onUpdate(final UpdateEvent lIlIIIlllIIIIl) {
        if (this.lastStaffBans != 0) {
            if (!this.watch.timeElapsed(TimeUnit.SECONDS.toMillis(30L))) {
                return;
            }
        }
        try {
            if (BanChecker.mc.thePlayer.ticksExisted < 5) {
                final HashMap<String, String> lIlIIIlllIIlIl = new HashMap<String, String>();
                lIlIIIlllIIlIl.put("API-Key", "96dd5933-7d2a-422e-855f-ec43b9a23b85");
                final JsonElement lIlIIIlllIIlII = new JsonParser().parse(HttpUtil.get("https://api.hypixel.net/punishmentstats", lIlIIIlllIIlIl));
                this.lastStaffBans = ((this.lastStaffBans == 0) ? lIlIIIlllIIlII.getAsJsonObject().get("staff_total").getAsInt() : this.currentStaffBans);
                this.currentStaffBans = lIlIIIlllIIlII.getAsJsonObject().get("staff_total").getAsInt();
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.WARNING, "Staff bans", String.valueOf(new StringBuilder().append(this.currentStaffBans - this.lastStaffBans).append(" new bans")), 1));
            }
        }
        catch (Exception lIlIIIlllIIIll) {
            lIlIIIlllIIIll.printStackTrace();
        }
        this.watch.resetTime();
    }
    
    public BanChecker() {
        this.lastStaffBans = 0;
        this.currentStaffBans = 0;
        this.watch = new Stopwatch();
    }
}
