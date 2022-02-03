package me.kansio.client.rank;

import net.minecraft.util.*;

public enum UserRank
{
    DEVELOPER("Developer", EnumChatFormatting.GOLD);
    
    private /* synthetic */ String displayName;
    
    USER("Normal", EnumChatFormatting.GRAY), 
    BETA("Beta", EnumChatFormatting.LIGHT_PURPLE);
    
    private /* synthetic */ EnumChatFormatting color;
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    private UserRank(final String llIIIlllllIlll, final EnumChatFormatting llIIIlllllIIIl) {
        this.displayName = llIIIlllllIlll;
        this.color = llIIIlllllIIIl;
    }
    
    public EnumChatFormatting getColor() {
        return this.color;
    }
}
