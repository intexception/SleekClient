package me.kansio.client.modules.impl.visuals.hud.info;

import me.kansio.client.modules.impl.visuals.hud.*;
import me.kansio.client.event.impl.*;
import me.kansio.client.utils.math.*;
import me.kansio.client.*;
import me.kansio.client.utils.network.*;
import me.kansio.client.utils.font.*;
import net.minecraft.util.*;
import java.text.*;
import me.kansio.client.utils.render.*;
import me.kansio.client.modules.impl.visuals.*;

public class Sleek extends InfoMode
{
    public Sleek() {
        super("Sleek");
    }
    
    @Override
    public void onRenderOverlay(final RenderOverlayEvent lllIIllIlIlII) {
        final HUD lllIIllIllIII = this.getHud();
        final double lllIIllIlIlll = BPSUtil.getBPS();
        final String lllIIllIlIllI = String.valueOf(new StringBuilder().append("§7").append(UserUtil.getBuildType(Integer.parseInt(Client.getInstance().getUid()))).append(" - §f").append(Client.getInstance().getUid()));
        if (lllIIllIllIII.font.getValue()) {
            Fonts.YantramanavThin.drawStringWithShadow(lllIIllIlIllI, (float)(lllIIllIlIlII.getSr().getScaledWidth() - Fonts.YantramanavThin.getStringWidth(lllIIllIlIllI) - 2), (float)(lllIIllIlIlII.getSr().getScaledHeight() - (Sleek.mc.ingameGUI.getChatGUI().getChatOpen() ? 24 : 10)), -1);
            Fonts.YantramanavThin.drawStringWithShadow(String.valueOf(new StringBuilder().append("BPS: ").append(EnumChatFormatting.GRAY).append(new DecimalFormat("0.##").format(lllIIllIlIlll))), 3.0f, (float)(lllIIllIlIlII.getSr().getScaledHeight() - (Sleek.mc.ingameGUI.getChatGUI().getChatOpen() ? 24 : 10)), ColorUtils.getColorFromHud(5).getRGB());
        }
        else {
            Sleek.mc.fontRendererObj.drawStringWithShadow(lllIIllIlIllI, (float)(lllIIllIlIlII.getSr().getScaledWidth() - Sleek.mc.fontRendererObj.getStringWidth(lllIIllIlIllI) - 2), (float)(lllIIllIlIlII.getSr().getScaledHeight() - (Sleek.mc.ingameGUI.getChatGUI().getChatOpen() ? 24 : 10)), -1);
            Sleek.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append("BPS: ").append(EnumChatFormatting.GRAY).append(new DecimalFormat("0.##").format(lllIIllIlIlll))), 3.0f, (float)(lllIIllIlIlII.getSr().getScaledHeight() - (Sleek.mc.ingameGUI.getChatGUI().getChatOpen() ? 24 : 10)), ColorUtils.getColorFromHud(5).getRGB());
        }
    }
}
