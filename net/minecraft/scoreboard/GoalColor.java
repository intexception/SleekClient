package net.minecraft.scoreboard;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class GoalColor implements IScoreObjectiveCriteria
{
    private final String goalName;
    
    public GoalColor(final String p_i45549_1_, final EnumChatFormatting p_i45549_2_) {
        this.goalName = p_i45549_1_ + p_i45549_2_.getFriendlyName();
        IScoreObjectiveCriteria.INSTANCES.put(this.goalName, this);
    }
    
    @Override
    public String getName() {
        return this.goalName;
    }
    
    @Override
    public int func_96635_a(final List<EntityPlayer> p_96635_1_) {
        return 0;
    }
    
    @Override
    public boolean isReadOnly() {
        return false;
    }
    
    @Override
    public EnumRenderType getRenderType() {
        return EnumRenderType.INTEGER;
    }
}
