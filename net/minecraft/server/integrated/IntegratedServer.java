package net.minecraft.server.integrated;

import net.minecraft.server.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import optifine.*;
import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.crash.*;
import net.minecraft.client.*;
import net.minecraft.profiler.*;
import net.minecraft.util.*;
import java.net.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;

public class IntegratedServer extends MinecraftServer
{
    private static final Logger logger;
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;
    private static final String __OBFID = "CL_00001129";
    
    public IntegratedServer(final Minecraft mcIn) {
        super(mcIn.getProxy(), new File(mcIn.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.mc = mcIn;
        this.theWorldSettings = null;
    }
    
    public IntegratedServer(final Minecraft mcIn, final String folderName, final String worldName, final WorldSettings settings) {
        super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, IntegratedServer.USER_CACHE_FILE.getName()));
        this.setServerOwner(mcIn.getSession().getUsername());
        this.setFolderName(folderName);
        this.setWorldName(worldName);
        this.canCreateBonusChest(settings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigManager(new IntegratedPlayerList(this));
        this.mc = mcIn;
        this.theWorldSettings = settings;
    }
    
    @Override
    protected ServerCommandManager createNewCommandManager() {
        return new IntegratedServerCommandManager();
    }
    
    @Override
    protected void loadAllWorlds(final String p_71247_1_, final String p_71247_2_, final long seed, final WorldType type, final String p_71247_6_) {
        this.convertMapIfNeeded(p_71247_1_);
        final ISaveHandler isavehandler = this.getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
        this.setResourcePackFromWorld(this.getFolderName(), isavehandler);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();
        if (Reflector.DimensionManager.exists()) {
            final WorldServer worldserver = (WorldServer)new WorldServerOF(this, isavehandler, worldinfo, 0, this.theProfiler).init();
            worldserver.initialize(this.theWorldSettings);
            final Integer[] ainteger2;
            final Integer[] ainteger = ainteger2 = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
            for (int i = ainteger.length, j = 0; j < i; ++j) {
                final int k = ainteger2[j];
                final WorldServer worldserver2 = (WorldServer)((k == 0) ? worldserver : new WorldServerMulti(this, isavehandler, k, worldserver, this.theProfiler).init());
                worldserver2.addWorldAccess(new WorldManager(this, worldserver2));
                if (!this.isSinglePlayer()) {
                    worldserver2.getWorldInfo().setGameType(this.getGameType());
                }
                if (Reflector.EventBus.exists()) {
                    Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, worldserver2);
                }
            }
            this.getConfigurationManager().setPlayerManager(new WorldServer[] { worldserver });
            if (worldserver.getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        else {
            this.worldServers = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
            this.setResourcePackFromWorld(this.getFolderName(), isavehandler);
            if (worldinfo == null) {
                worldinfo = new WorldInfo(this.theWorldSettings, p_71247_2_);
            }
            else {
                worldinfo.setWorldName(p_71247_2_);
            }
            for (int l = 0; l < this.worldServers.length; ++l) {
                byte b0 = 0;
                if (l == 1) {
                    b0 = -1;
                }
                if (l == 2) {
                    b0 = 1;
                }
                if (l == 0) {
                    (this.worldServers[l] = (WorldServer)new WorldServerOF(this, isavehandler, worldinfo, b0, this.theProfiler).init()).initialize(this.theWorldSettings);
                }
                else {
                    this.worldServers[l] = (WorldServer)new WorldServerMulti(this, isavehandler, b0, this.worldServers[0], this.theProfiler).init();
                }
                this.worldServers[l].addWorldAccess(new WorldManager(this, this.worldServers[l]));
            }
            this.getConfigurationManager().setPlayerManager(this.worldServers);
            if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
                this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
            }
        }
        this.initialWorldChunkLoad();
    }
    
    @Override
    protected boolean startServer() throws IOException {
        IntegratedServer.logger.info("Starting integrated minecraft server version 1.8.8");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        IntegratedServer.logger.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());
        if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
            final Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (!Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, this)) {
                return false;
            }
        }
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
        this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
            final Object object2 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
            if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
                return Reflector.callBoolean(object2, Reflector.FMLCommonHandler_handleServerStarting, this);
            }
            Reflector.callVoid(object2, Reflector.FMLCommonHandler_handleServerStarting, this);
        }
        return true;
    }
    
    @Override
    public void tick() {
        final boolean flag = this.isGamePaused;
        this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
        if (!flag && this.isGamePaused) {
            IntegratedServer.logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (this.isGamePaused) {
            final Queue var3 = this.futureTaskQueue;
            synchronized (this.futureTaskQueue) {
                while (!this.futureTaskQueue.isEmpty()) {
                    Util.func_181617_a(this.futureTaskQueue.poll(), IntegratedServer.logger);
                }
            }
        }
        else {
            super.tick();
            if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
                IntegratedServer.logger.info("Changing view distance to {}, from {}", new Object[] { this.mc.gameSettings.renderDistanceChunks, this.getConfigurationManager().getViewDistance() });
                this.getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
            }
            if (this.mc.theWorld != null) {
                final WorldInfo worldinfo = this.worldServers[0].getWorldInfo();
                final WorldInfo worldinfo2 = this.mc.theWorld.getWorldInfo();
                if (!worldinfo.isDifficultyLocked() && worldinfo2.getDifficulty() != worldinfo.getDifficulty()) {
                    IntegratedServer.logger.info("Changing difficulty to {}, from {}", new Object[] { worldinfo2.getDifficulty(), worldinfo.getDifficulty() });
                    this.setDifficultyForAllWorlds(worldinfo2.getDifficulty());
                }
                else if (worldinfo2.isDifficultyLocked() && !worldinfo.isDifficultyLocked()) {
                    IntegratedServer.logger.info("Locking difficulty to {}", new Object[] { worldinfo2.getDifficulty() });
                    for (final WorldServer worldserver : this.worldServers) {
                        if (worldserver != null) {
                            worldserver.getWorldInfo().setDifficultyLocked(true);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return false;
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }
    
    @Override
    public EnumDifficulty getDifficulty() {
        return (this.mc.theWorld == null) ? this.mc.gameSettings.difficulty : this.mc.theWorld.getWorldInfo().getDifficulty();
    }
    
    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }
    
    @Override
    public boolean func_181034_q() {
        return true;
    }
    
    @Override
    public boolean func_183002_r() {
        return true;
    }
    
    @Override
    public File getDataDirectory() {
        return this.mc.mcDataDir;
    }
    
    @Override
    public boolean func_181035_ah() {
        return false;
    }
    
    @Override
    public boolean isDedicatedServer() {
        return false;
    }
    
    @Override
    protected void finalTick(final CrashReport report) {
        this.mc.crashed(report);
    }
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport report) {
        report = super.addServerInfoToCrashReport(report);
        report.getCategory().addCrashSectionCallable("Type", new Callable() {
            private static final String __OBFID = "CL_00001130";
            
            @Override
            public String call() throws Exception {
                return "Integrated Server (map_client.txt)";
            }
        });
        report.getCategory().addCrashSectionCallable("Is Modded", new Callable() {
            private static final String __OBFID = "CL_00001131";
            
            @Override
            public String call() throws Exception {
                String s = ClientBrandRetriever.getClientModName();
                if (!s.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + s + "'";
                }
                s = IntegratedServer.this.getServerModName();
                return s.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + s + "'");
            }
        });
        return report;
    }
    
    @Override
    public void setDifficultyForAllWorlds(final EnumDifficulty difficulty) {
        super.setDifficultyForAllWorlds(difficulty);
        if (this.mc.theWorld != null) {
            this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
        }
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper playerSnooper) {
        super.addServerStatsToSnooper(playerSnooper);
        playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }
    
    @Override
    public String shareToLAN(final WorldSettings.GameType type, final boolean allowCheats) {
        try {
            int i = -1;
            try {
                i = HttpUtil.getSuitableLanPort();
            }
            catch (IOException ex) {}
            if (i <= 0) {
                i = 25564;
            }
            this.getNetworkSystem().addLanEndpoint(null, i);
            IntegratedServer.logger.info("Started on " + i);
            this.isPublic = true;
            (this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), i + "")).start();
            this.getConfigurationManager().setGameType(type);
            this.getConfigurationManager().setCommandsAllowedForAll(allowCheats);
            return i + "";
        }
        catch (IOException var6) {
            return null;
        }
    }
    
    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    @Override
    public void initiateShutdown() {
        Futures.getUnchecked((Future)this.addScheduledTask(new Runnable() {
            private static final String __OBFID = "CL_00002380";
            
            @Override
            public void run() {
                for (final EntityPlayerMP entityplayermp : Lists.newArrayList((Iterable)IntegratedServer.this.getConfigurationManager().func_181057_v())) {
                    IntegratedServer.this.getConfigurationManager().playerLoggedOut(entityplayermp);
                }
            }
        }));
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    public void setStaticInstance() {
        this.setInstance();
    }
    
    public boolean getPublic() {
        return this.isPublic;
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameMode) {
        this.getConfigurationManager().setGameType(gameMode);
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }
    
    @Override
    public int getOpPermissionLevel() {
        return 4;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
