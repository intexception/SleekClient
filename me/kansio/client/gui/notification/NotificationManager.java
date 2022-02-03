package me.kansio.client.gui.notification;

import java.util.concurrent.*;
import me.kansio.client.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.modules.impl.visuals.*;
import com.google.common.eventbus.*;

public class NotificationManager
{
    private /* synthetic */ LinkedBlockingQueue<Notification> pendingNotifications;
    private static /* synthetic */ NotificationManager notificationManager;
    private /* synthetic */ Notification currentNotification;
    
    public Notification getCurrentNotification() {
        return this.currentNotification;
    }
    
    public static NotificationManager getNotificationManager() {
        return NotificationManager.notificationManager;
    }
    
    public NotificationManager() {
        this.pendingNotifications = new LinkedBlockingQueue<Notification>();
        this.currentNotification = null;
        Client.getInstance().getEventBus().register((Object)this);
    }
    
    @Subscribe
    public void render(final RenderOverlayEvent llIIIIIlIlllll) {
        if (!HUD.notifications && Client.getInstance().getModuleManager().getModuleByName("Hud").isToggled()) {
            return;
        }
        this.update();
        if (this.currentNotification != null) {
            this.currentNotification.render();
        }
    }
    
    public void show(final Notification llIIIIIllIIlll) {
        this.pendingNotifications.add(llIIIIIllIIlll);
    }
    
    public void update() {
        if (this.currentNotification != null && !this.currentNotification.isShown()) {
            this.currentNotification = null;
        }
        if (this.currentNotification == null && !this.pendingNotifications.isEmpty()) {
            this.currentNotification = this.pendingNotifications.poll();
            this.currentNotification.show();
        }
    }
    
    public LinkedBlockingQueue<Notification> getPendingNotifications() {
        return this.pendingNotifications;
    }
    
    static {
        NotificationManager.notificationManager = new NotificationManager();
    }
}
