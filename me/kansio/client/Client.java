package me.kansio.client;

import me.kansio.client.manager.*;
import me.kansio.client.friend.*;
import me.kansio.client.keybind.*;
import me.kansio.client.commands.*;
import me.kansio.client.modules.*;
import me.kansio.client.modules.impl.player.hackerdetect.*;
import me.kansio.client.targets.*;
import me.kansio.client.config.*;
import me.kansio.client.rank.*;
import java.util.*;
import java.text.*;
import me.kansio.client.utils.network.*;
import java.io.*;
import com.google.gson.*;
import com.google.common.eventbus.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.modules.impl.visuals.*;
import net.minecraft.client.*;
import me.kansio.client.gui.config.*;
import net.minecraft.client.gui.*;
import me.kansio.client.modules.impl.*;
import org.apache.logging.log4j.*;
import viamcp.utils.*;
import me.kansio.client.protection.*;
import viamcp.*;
import com.jagrosh.discordipc.*;
import java.time.*;
import com.jagrosh.discordipc.entities.*;
import org.lwjgl.opengl.*;
import java.util.logging.*;

public class Client
{
    private /* synthetic */ ValueManager valueManager;
    private /* synthetic */ FriendManager friendManager;
    private /* synthetic */ KeybindManager keybindManager;
    private /* synthetic */ CommandManager commandManager;
    private /* synthetic */ ModuleManager moduleManager;
    private /* synthetic */ CheckManager checkManager;
    private /* synthetic */ TargetManager targetManager;
    private /* synthetic */ File dir;
    private /* synthetic */ EventBus eventBus;
    private /* synthetic */ ConfigManager configManager;
    private /* synthetic */ String username;
    private /* synthetic */ Map<String, String> users;
    private /* synthetic */ UserRank rank;
    private /* synthetic */ String uid;
    private static /* synthetic */ Client instance;
    private /* synthetic */ String discordTag;
    
    public void setUsername(final String llIIIIlIlIlIl) {
        this.username = llIIIIlIlIlIl;
    }
    
    public void setRank(final String llIIIIllIllll) {
        final long llIIIIllIllII = (long)llIIIIllIllll;
        double llIIIIllIlIll = -1;
        switch (((String)llIIIIllIllII).hashCode()) {
            case 1923286954: {
                if (((String)llIIIIllIllII).equals("Developer")) {
                    llIIIIllIlIll = 0;
                    break;
                }
                break;
            }
            case 2066960: {
                if (((String)llIIIIllIllII).equals("Beta")) {
                    llIIIIllIlIll = 1;
                    break;
                }
                break;
            }
        }
        switch (llIIIIllIlIll) {
            case 0.0: {
                this.rank = UserRank.DEVELOPER;
                break;
            }
            case 1.0: {
                this.rank = UserRank.BETA;
                break;
            }
            default: {
                this.rank = UserRank.USER;
                break;
            }
        }
    }
    
    public Client() {
        this.users = new HashMap<String, String>();
        this.eventBus = new EventBus("Sleek");
    }
    
    public EventBus getEventBus() {
        return this.eventBus;
    }
    
    public String getUid() {
        return this.uid;
    }
    
    public static Client getInstance() {
        return Client.instance;
    }
    
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    public void setDiscordTag(final String llIIIIlIIIIll) {
        this.discordTag = llIIIIlIIIIll;
    }
    
    public Map<String, String> getUsers() {
        return this.users;
    }
    
    public FriendManager getFriendManager() {
        return this.friendManager;
    }
    
    public KeybindManager getKeybindManager() {
        return this.keybindManager;
    }
    
    @Subscribe
    public void onJoin(final ServerJoinEvent llIIIIllllIIl) {
        try {
            System.out.println(HttpUtil.delete(MessageFormat.format("http://zerotwoclient.xyz:13337/api/v1/leaveserver?clientname={0}", this.username)));
            System.out.println(HttpUtil.post(String.valueOf(new StringBuilder().append("http://zerotwoclient.xyz:13337/api/v1/joinserver?name=").append(this.username).append("&uid=1&ign=").append(llIIIIllllIIl.getIgn()).append("&serverIP=").append(llIIIIllllIIl.getServerIP())), ""));
            final JsonElement llIIIIllllllI = new JsonParser().parse(HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/getclientplayers"));
            if (llIIIIllllllI.isJsonArray()) {
                this.users.clear();
                for (final JsonElement llIIIIlllllll : llIIIIllllllI.getAsJsonArray()) {
                    final JsonObject llIIIlIIIIIII = llIIIIlllllll.getAsJsonObject();
                    this.users.put(llIIIlIIIIIII.get("ign").getAsString(), llIIIlIIIIIII.get("name").getAsString());
                }
            }
        }
        catch (IOException llIIIIlllllIl) {
            llIIIIlllllIl.printStackTrace();
        }
    }
    
    public void onShutdown() {
        try {
            System.out.println(HttpUtil.delete(MessageFormat.format("http://zerotwoclient.xyz:13337/api/v1/leaveserver?clientname={0}", this.username)));
        }
        catch (IOException llIIIlIIllIII) {
            llIIIlIIllIII.printStackTrace();
        }
        if (this.keybindManager != null) {
            this.keybindManager.save();
        }
    }
    
    @Subscribe
    public void onChat(final PacketEvent llIIIlIIIlIlI) {
        if (llIIIlIIIlIlI.getPacket() instanceof S02PacketChat) {
            final S02PacketChat llIIIlIIIlllI = llIIIlIIIlIlI.getPacket();
            for (final Map.Entry<String, String> llIIIlIIIllll : this.users.entrySet()) {
                if (llIIIlIIIlllI.getChatComponent().getUnformattedText().contains(llIIIlIIIllll.getKey())) {
                    llIIIlIIIlllI.chatComponent = new ChatComponentText(llIIIlIIIlllI.getChatComponent().getFormattedText().replaceAll(llIIIlIIIllll.getKey(), MessageFormat.format("§b{0} §7({1})", llIIIlIIIllll.getValue(), llIIIlIIIllll.getKey())));
                }
            }
        }
    }
    
    public ValueManager getValueManager() {
        return this.valueManager;
    }
    
    public CheckManager getCheckManager() {
        return this.checkManager;
    }
    
    public String getDiscordTag() {
        return this.discordTag;
    }
    
    public void setUid(final String llIIIIlIIllII) {
        this.uid = llIIIIlIIllII;
    }
    
    public UserRank getRank() {
        return this.rank;
    }
    
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
    
    static {
        Client.instance = new Client();
    }
    
    public TargetManager getTargetManager() {
        return this.targetManager;
    }
    
    public File getDir() {
        return this.dir;
    }
    
    @Subscribe
    public void onKeyboard(final KeyboardEvent llIIIIllIIIlI) {
        final int llIIIIllIIIIl = llIIIIllIIIlI.getKeyCode();
        if (llIIIIllIIIIl == 54) {
            final ClickGUI llIIIIllIIlIl = (ClickGUI)getInstance().getModuleManager().getModuleByName("Click GUI");
            llIIIIllIIlIl.toggle();
        }
        if (llIIIIllIIIIl == 210) {
            Minecraft.getMinecraft().displayGuiScreen(new ConfigurationGUI());
        }
        for (final Module llIIIIllIIlII : this.moduleManager.getModules()) {
            if (llIIIIllIIlII.getKeyBind() == -1) {
                continue;
            }
            if (llIIIIllIIlII.getKeyBind() != llIIIIllIIIIl) {
                continue;
            }
            llIIIIllIIlII.toggle();
        }
    }
    
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    
    public void onStart() {
        final Logger llIIIlIIllllI = new JLoggerToLog4j(LogManager.getLogger("checksum"));
        llIIIlIIllllI.log(Level.INFO, String.valueOf(new StringBuilder().append("current checksum: ").append(ProtectionUtil.huisdfhufisdhfiusdhifsudfsihdusdiuhsfdiusfdhuisdfiuhsdfhisfdhiufsdhui())));
        this.dir = new File(Minecraft.getMinecraft().mcDataDir, "Sleek");
        this.eventBus.register((Object)this);
        this.valueManager = new ValueManager();
        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager(new File(this.dir, "configs"));
        this.keybindManager = new KeybindManager(this.dir);
        this.keybindManager.load();
        this.friendManager = new FriendManager();
        this.targetManager = new TargetManager();
        this.checkManager = new CheckManager();
        try {
            ViaMCP.getInstance().start();
        }
        catch (Exception llIIIlIlIIIlI) {
            llIIIlIlIIIlI.printStackTrace();
        }
        try {
            final IPCClient llIIIlIlIIIIl = new IPCClient(937350566886137886L);
            llIIIlIlIIIIl.setListener(new IPCListener() {
                @Override
                public void onReady(final IPCClient llIllllIIlIl) {
                    final RichPresence.Builder llIllllIIlll = new RichPresence.Builder();
                    llIllllIIlll.setState(String.valueOf(new StringBuilder().append("UID: ").append(Client.this.uid))).setDetails("Destroying servers").setStartTimestamp(OffsetDateTime.now()).setLargeImage("canary-large", "Discord Canary").setSmallImage("ptb-small", "Discord PTB");
                    llIllllIIlIl.sendRichPresence(llIllllIIlll.build());
                }
            });
            llIIIlIlIIIIl.connect(new DiscordBuild[0]);
        }
        catch (Exception llIIIlIlIIIII) {
            System.out.println("Discord not found, not setting rpc.");
        }
        System.out.println("Client has been started.");
        Display.setTitle("Sleek v0.1");
    }
    
    public String getUsername() {
        return this.username;
    }
}
