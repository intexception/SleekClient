package de.gerrygames.viarewind.protocol.protocol1_8to1_9;

import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_8.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.protocol.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets.*;
import de.gerrygames.viarewind.utils.*;
import com.viaversion.viaversion.api.connection.*;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import java.util.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class Protocol1_8TO1_9 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8>
{
    public static final Timer TIMER;
    public static final Set<String> VALID_ATTRIBUTES;
    public static final ValueTransformer<Double, Integer> TO_OLD_INT;
    public static final ValueTransformer<Float, Byte> DEGREES_TO_ANGLE;
    
    public Protocol1_8TO1_9() {
        super(ClientboundPackets1_9.class, ClientboundPackets1_8.class, ServerboundPackets1_9.class, ServerboundPackets1_8.class);
    }
    
    @Override
    protected void registerPackets() {
        EntityPackets.register(this);
        InventoryPackets.register(this);
        PlayerPackets.register(this);
        ScoreboardPackets.register(this);
        SpawnPackets.register(this);
        WorldPackets.register(this);
    }
    
    @Override
    public void init(final UserConnection userConnection) {
        Ticker.init();
        userConnection.put(new Windows(userConnection));
        userConnection.put(new EntityTracker(userConnection));
        userConnection.put(new Levitation(userConnection));
        userConnection.put(new PlayerPosition(userConnection));
        userConnection.put(new Cooldown(userConnection));
        userConnection.put(new BlockPlaceDestroyTracker(userConnection));
        userConnection.put(new BossBarStorage(userConnection));
        userConnection.put(new ClientWorld(userConnection));
    }
    
    static {
        TIMER = new Timer("ViaRewind-1_8TO1_9", true);
        VALID_ATTRIBUTES = new HashSet<String>();
        TO_OLD_INT = new ValueTransformer<Double, Integer>() {
            @Override
            public Integer transform(final PacketWrapper wrapper, final Double inputValue) {
                return (int)(inputValue * 32.0);
            }
        };
        DEGREES_TO_ANGLE = new ValueTransformer<Float, Byte>() {
            @Override
            public Byte transform(final PacketWrapper packetWrapper, final Float degrees) throws Exception {
                return (byte)(degrees / 360.0f * 256.0f);
            }
        };
        Protocol1_8TO1_9.VALID_ATTRIBUTES.add("generic.maxHealth");
        Protocol1_8TO1_9.VALID_ATTRIBUTES.add("generic.followRange");
        Protocol1_8TO1_9.VALID_ATTRIBUTES.add("generic.knockbackResistance");
        Protocol1_8TO1_9.VALID_ATTRIBUTES.add("generic.movementSpeed");
        Protocol1_8TO1_9.VALID_ATTRIBUTES.add("generic.attackDamage");
        Protocol1_8TO1_9.VALID_ATTRIBUTES.add("horse.jumpStrength");
        Protocol1_8TO1_9.VALID_ATTRIBUTES.add("zombie.spawnReinforcements");
    }
}
