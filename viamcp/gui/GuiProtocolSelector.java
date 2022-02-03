package viamcp.gui;

import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import viamcp.protocols.*;
import viamcp.*;

public class GuiProtocolSelector extends GuiScreen
{
    public static float sliderDragValue;
    private GuiScreen parent;
    public SlotList list;
    
    public GuiProtocolSelector(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, GuiProtocolSelector.width / 2 - 100, GuiProtocolSelector.height - 27, 200, 20, "Back"));
        this.list = new SlotList(this.mc, GuiProtocolSelector.width, GuiProtocolSelector.height, 32, GuiProtocolSelector.height - 32, 10);
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_actionPerformed_1_) throws IOException {
        this.list.actionPerformed(p_actionPerformed_1_);
        if (p_actionPerformed_1_.id == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        this.list.handleMouseInput();
        super.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int p_drawScreen_1_, final int p_drawScreen_2_, final float p_drawScreen_3_) {
        this.list.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0, 2.0, 2.0);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD + "ViaMCP Reborn", GuiProtocolSelector.width / 4, 5, 16777215);
        GlStateManager.scale(0.25, 0.25, 0.25);
        this.drawString(this.fontRendererObj, "Maintained by Hideri (1.8.x Version)", 3, 3, -1);
        this.drawString(this.fontRendererObj, "Discord: Hideri#9003", 3, 13, -1);
        this.drawString(this.fontRendererObj, "Credits", 3, (GuiProtocolSelector.height - 15) * 2, -1);
        this.drawString(this.fontRendererObj, "ViaForge: https://github.com/FlorianMichael/ViaForge", 3, (GuiProtocolSelector.height - 10) * 2, -1);
        this.drawString(this.fontRendererObj, "Original ViaMCP: https://github.com/LaVache-FR/ViaMCP", 3, (GuiProtocolSelector.height - 5) * 2, -1);
        GlStateManager.popMatrix();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }
    
    static {
        GuiProtocolSelector.sliderDragValue = -1.0f;
    }
    
    class SlotList extends GuiSlot
    {
        public SlotList(final Minecraft p_i1052_1_, final int p_i1052_2_, final int p_i1052_3_, final int p_i1052_4_, final int p_i1052_5_, final int p_i1052_6_) {
            super(p_i1052_1_, p_i1052_2_, p_i1052_3_, p_i1052_4_, p_i1052_5_, 18);
        }
        
        @Override
        protected int getSize() {
            return ProtocolCollection.values().length;
        }
        
        @Override
        protected void elementClicked(final int i, final boolean b, final int i1, final int i2) {
            ViaMCP.getInstance().setVersion(ProtocolCollection.values()[i].getVersion().getVersion());
        }
        
        @Override
        protected boolean isSelected(final int i) {
            return false;
        }
        
        @Override
        protected void drawBackground() {
            GuiProtocolSelector.this.drawDefaultBackground();
        }
        
        @Override
        protected void drawSlot(final int i, final int i1, final int i2, final int i3, final int i4, final int i5) {
            GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, ((ViaMCP.getInstance().getVersion() == ProtocolCollection.values()[i].getVersion().getVersion()) ? (EnumChatFormatting.GREEN.toString() + EnumChatFormatting.BOLD) : EnumChatFormatting.GRAY.toString()) + ProtocolCollection.getProtocolById(ProtocolCollection.values()[i].getVersion().getVersion()).getName(), this.width / 2, i2 + 2, -1);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GuiProtocolSelector.this.drawCenteredString(this.mc.fontRendererObj, "Ver: " + ProtocolCollection.getProtocolById(ProtocolCollection.values()[i].getVersion().getVersion()).getVersion(), this.width, (i2 + 2) * 2 + 20, -1);
            GlStateManager.popMatrix();
        }
    }
}
