package me.kansio.client.modules.impl.visuals;

import me.kansio.client.modules.impl.*;
import me.kansio.client.modules.api.*;
import me.kansio.client.value.value.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.*;
import me.kansio.client.utils.render.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.google.common.eventbus.*;

@ModuleData(name = "Enemy List", category = ModuleCategory.VISUALS, description = "Shows all enemies in ur render distance on ur screen")
public class EnemyList extends Module
{
    private /* synthetic */ NumberValue<Integer> ypos;
    private /* synthetic */ NumberValue<Integer> xpos;
    
    public EnemyList() {
        this.xpos = new NumberValue<Integer>("X-Pos", this, 5, 0, 1000, 1);
        this.ypos = new NumberValue<Integer>("Y-Pos", this, 200, 0, 1000, 1);
    }
    
    @Subscribe
    public void onRender(final RenderOverlayEvent lIlIlIlIlllIlI) {
        final List<String> lIlIlIlIlllIIl = Client.getInstance().getTargetManager().getTarget();
        final HashMap<String, Double> lIlIlIlIlllIII = new HashMap<String, Double>();
        RenderUtils.drawRect(this.xpos.getValue(), this.ypos.getValue(), 130.0, 1.0, ColorUtils.getColorFromHud(1).getRGB());
        RenderUtils.drawRect(this.xpos.getValue(), this.ypos.getValue() + 1, 130.0, 15.0, new Color(0, 0, 0, 100).getRGB());
        int lIlIlIlIllIlll = this.ypos.getValue() + 10;
        for (final Entity lIlIlIlIlllllI : EnemyList.mc.theWorld.loadedEntityList) {
            if (!(lIlIlIlIlllllI instanceof EntityPlayer)) {
                continue;
            }
            final EntityPlayer lIlIlIlIllllll = (EntityPlayer)lIlIlIlIlllllI;
            for (final String lIlIlIllIIIIII : lIlIlIlIlllIIl) {
                if (lIlIlIlIllllll.getName().equalsIgnoreCase(lIlIlIllIIIIII)) {
                    lIlIlIlIlllIII.put(lIlIlIllIIIIII, (double)EnemyList.mc.thePlayer.getDistanceToEntity(lIlIlIlIlllllI));
                }
            }
        }
        EnemyList.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("Enemies | ").append(lIlIlIlIlllIII.keySet().size())), (float)(this.xpos.getValue() + 5), (float)(this.ypos.getValue() + 5), -1);
        for (final String lIlIlIlIllllII : lIlIlIlIlllIII.keySet()) {
            final double lIlIlIlIllllIl = lIlIlIlIlllIII.get(lIlIlIlIllllII);
            RenderUtils.drawRect(this.xpos.getValue(), 6 + lIlIlIlIllIlll, 130.0, 13.0, new Color(0, 0, 0, 100).getRGB());
            EnemyList.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("  §7» §c").append(Math.round(lIlIlIlIllllIl)).append("§7: §f").append(lIlIlIlIllllII)), this.xpos.getValue(), (float)(lIlIlIlIllIlll + 6), -1);
            lIlIlIlIllIlll += 13;
        }
    }
}
