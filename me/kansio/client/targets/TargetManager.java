package me.kansio.client.targets;

import java.util.*;
import net.minecraft.entity.player.*;

public class TargetManager
{
    private final /* synthetic */ ArrayList<String> target;
    
    public boolean isTarget(final String lIllIlIIlIIIII) {
        return this.target.contains(lIllIlIIlIIIII);
    }
    
    public TargetManager() {
        this.target = new ArrayList<String>();
    }
    
    public ArrayList<String> getTarget() {
        return this.target;
    }
    
    public boolean isTarget(final EntityPlayer lIllIlIIlIlIII) {
        return this.target.contains(lIllIlIIlIlIII.getName());
    }
}
