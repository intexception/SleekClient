package net.minecraft.client.gui;

import com.google.common.base.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import java.awt.*;
import org.apache.commons.lang3.*;
import java.awt.datatransfer.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.stats.*;
import net.minecraft.event.*;
import java.net.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback
{
    private static final Logger LOGGER;
    private static final Set<String> PROTOCOLS;
    private static final Splitter NEWLINE_SPLITTER;
    protected Minecraft mc;
    protected RenderItem itemRender;
    public static int width;
    public static int height;
    protected List<GuiButton> buttonList;
    protected List<GuiLabel> labelList;
    public boolean allowUserInput;
    protected FontRenderer fontRendererObj;
    private GuiButton selectedButton;
    private int eventButton;
    private long lastMouseEvent;
    private int touchValue;
    private URI clickedLinkURI;
    
    public GuiScreen() {
        this.buttonList = (List<GuiButton>)Lists.newArrayList();
        this.labelList = (List<GuiLabel>)Lists.newArrayList();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (int i = 0; i < this.buttonList.size(); ++i) {
            this.buttonList.get(i).drawButton(this.mc, mouseX, mouseY);
        }
        for (int j = 0; j < this.labelList.size(); ++j) {
            this.labelList.get(j).drawLabel(this.mc, mouseX, mouseY);
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public static String getClipboardString() {
        try {
            final Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception ex) {}
        return "";
    }
    
    public static void setClipboardString(final String copyText) {
        if (!StringUtils.isEmpty((CharSequence)copyText)) {
            try {
                final StringSelection stringselection = new StringSelection(copyText);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
            }
            catch (Exception ex) {}
        }
    }
    
    protected void renderToolTip(final ItemStack stack, final int x, final int y) {
        final List<String> list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list.set(i, stack.getRarity().rarityColor + list.get(i));
            }
            else {
                list.set(i, EnumChatFormatting.GRAY + list.get(i));
            }
        }
        this.drawHoveringText(list, x, y);
    }
    
    protected void drawCreativeTabHoveringText(final String tabName, final int mouseX, final int mouseY) {
        this.drawHoveringText(Arrays.asList(tabName), mouseX, mouseY);
    }
    
    protected void drawHoveringText(final List<String> textLines, final int x, final int y) {
        if (!textLines.isEmpty()) {
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int i = 0;
            for (final String s : textLines) {
                final int j = this.fontRendererObj.getStringWidth(s);
                if (j > i) {
                    i = j;
                }
            }
            int l1 = x + 12;
            int i2 = y - 12;
            int k = 8;
            if (textLines.size() > 1) {
                k += 2 + (textLines.size() - 1) * 10;
            }
            if (l1 + i > GuiScreen.width) {
                l1 -= 28 + i;
            }
            if (i2 + k + 6 > GuiScreen.height) {
                i2 = GuiScreen.height - k - 6;
            }
            this.zLevel = 300.0f;
            this.itemRender.zLevel = 300.0f;
            final int m = -267386864;
            this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, m, m);
            this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, m, m);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, m, m);
            this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, m, m);
            this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, m, m);
            final int i3 = 1347420415;
            final int j2 = (i3 & 0xFEFEFE) >> 1 | (i3 & 0xFF000000);
            this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i3, j2);
            this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i3, j2);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i3, i3);
            this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j2, j2);
            for (int k2 = 0; k2 < textLines.size(); ++k2) {
                final String s2 = textLines.get(k2);
                this.fontRendererObj.drawStringWithShadow(s2, (float)l1, (float)i2, -1);
                if (k2 == 0) {
                    i2 += 2;
                }
                i2 += 10;
            }
            this.zLevel = 0.0f;
            this.itemRender.zLevel = 0.0f;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }
    
    protected void handleComponentHover(final IChatComponent p_175272_1_, final int p_175272_2_, final int p_175272_3_) {
        if (p_175272_1_ != null && p_175272_1_.getChatStyle().getChatHoverEvent() != null) {
            final HoverEvent hoverevent = p_175272_1_.getChatStyle().getChatHoverEvent();
            if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
                ItemStack itemstack = null;
                try {
                    final NBTBase nbtbase = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
                    if (nbtbase instanceof NBTTagCompound) {
                        itemstack = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbtbase);
                    }
                }
                catch (NBTException ex) {}
                if (itemstack != null) {
                    this.renderToolTip(itemstack, p_175272_2_, p_175272_3_);
                }
                else {
                    this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", p_175272_2_, p_175272_3_);
                }
            }
            else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
                if (this.mc.gameSettings.advancedItemTooltips) {
                    try {
                        final NBTBase nbtbase2 = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
                        if (nbtbase2 instanceof NBTTagCompound) {
                            final List<String> list1 = (List<String>)Lists.newArrayList();
                            final NBTTagCompound nbttagcompound = (NBTTagCompound)nbtbase2;
                            list1.add(nbttagcompound.getString("name"));
                            if (nbttagcompound.hasKey("type", 8)) {
                                final String s = nbttagcompound.getString("type");
                                list1.add("Type: " + s + " (" + EntityList.getIDFromString(s) + ")");
                            }
                            list1.add(nbttagcompound.getString("id"));
                            this.drawHoveringText(list1, p_175272_2_, p_175272_3_);
                        }
                        else {
                            this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
                        }
                    }
                    catch (NBTException var10) {
                        this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
                    }
                }
            }
            else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
                this.drawHoveringText(GuiScreen.NEWLINE_SPLITTER.splitToList((CharSequence)hoverevent.getValue().getFormattedText()), p_175272_2_, p_175272_3_);
            }
            else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
                final StatBase statbase = StatList.getOneShotStat(hoverevent.getValue().getUnformattedText());
                if (statbase != null) {
                    final IChatComponent ichatcomponent = statbase.getStatName();
                    final IChatComponent ichatcomponent2 = new ChatComponentTranslation("stats.tooltip.type." + (statbase.isAchievement() ? "achievement" : "statistic"), new Object[0]);
                    ichatcomponent2.getChatStyle().setItalic(true);
                    final String s2 = (statbase instanceof Achievement) ? ((Achievement)statbase).getDescription() : null;
                    final List<String> list2 = (List<String>)Lists.newArrayList((Object[])new String[] { ichatcomponent.getFormattedText(), ichatcomponent2.getFormattedText() });
                    if (s2 != null) {
                        list2.addAll(this.fontRendererObj.listFormattedStringToWidth(s2, 150));
                    }
                    this.drawHoveringText(list2, p_175272_2_, p_175272_3_);
                }
                else {
                    this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", p_175272_2_, p_175272_3_);
                }
            }
            GlStateManager.disableLighting();
        }
    }
    
    protected void setText(final String newChatText, final boolean shouldOverwrite) {
    }
    
    protected boolean handleComponentClick(final IChatComponent p_175276_1_) {
        if (p_175276_1_ == null) {
            return false;
        }
        final ClickEvent clickevent = p_175276_1_.getChatStyle().getChatClickEvent();
        if (isShiftKeyDown()) {
            if (p_175276_1_.getChatStyle().getInsertion() != null) {
                this.setText(p_175276_1_.getChatStyle().getInsertion(), false);
            }
        }
        else if (clickevent != null) {
            if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
                if (!this.mc.gameSettings.chatLinks) {
                    return false;
                }
                try {
                    final URI uri = new URI(clickevent.getValue());
                    final String s = uri.getScheme();
                    if (s == null) {
                        throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
                    }
                    if (!GuiScreen.PROTOCOLS.contains(s.toLowerCase())) {
                        throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase());
                    }
                    if (this.mc.gameSettings.chatLinksPrompt) {
                        this.clickedLinkURI = uri;
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
                    }
                    else {
                        this.openWebLink(uri);
                    }
                }
                catch (URISyntaxException urisyntaxexception) {
                    GuiScreen.LOGGER.error("Can't open url for " + clickevent, (Throwable)urisyntaxexception);
                }
            }
            else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
                final URI uri2 = new File(clickevent.getValue()).toURI();
                this.openWebLink(uri2);
            }
            else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                this.setText(clickevent.getValue(), true);
            }
            else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.sendChatMessage(clickevent.getValue(), false);
            }
            else {
                GuiScreen.LOGGER.error("Don't know how to handle " + clickevent);
            }
            return true;
        }
        return false;
    }
    
    public void sendChatMessage(final String msg) {
        this.sendChatMessage(msg, true);
    }
    
    public void sendChatMessage(final String msg, final boolean addToChat) {
        if (addToChat) {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
        }
        this.mc.thePlayer.sendChatMessage(msg);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                final GuiButton guibutton = this.buttonList.get(i);
                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    (this.selectedButton = guibutton).playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                }
            }
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }
    
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = mc.fontRendererObj;
        GuiScreen.width = width;
        GuiScreen.height = height;
        this.buttonList.clear();
        this.initGui();
    }
    
    public void initGui() {
    }
    
    public void handleInput() throws IOException {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }
    
    public void handleMouseInput() throws IOException {
        final int i = Mouse.getEventX() * GuiScreen.width / this.mc.displayWidth;
        final int j = GuiScreen.height - Mouse.getEventY() * GuiScreen.height / this.mc.displayHeight - 1;
        final int k = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
                return;
            }
            this.eventButton = k;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(i, j, this.eventButton);
        }
        else if (k != -1) {
            if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseReleased(i, j, k);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            final long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(i, j, this.eventButton, l);
        }
    }
    
    public void handleKeyboardInput() throws IOException {
        if (Keyboard.getEventKeyState()) {
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        this.mc.dispatchKeypresses();
    }
    
    public void updateScreen() {
    }
    
    public void onGuiClosed() {
    }
    
    public void drawDefaultBackground() {
        this.drawWorldBackground(0);
    }
    
    public void drawWorldBackground(final int tint) {
        if (this.mc.theWorld != null) {
            this.drawGradientRect(0, 0, GuiScreen.width, GuiScreen.height, -1072689136, -804253680);
        }
        else {
            this.drawBackground(tint);
        }
    }
    
    public void drawBackground(final int tint) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(GuiScreen.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final float f = 32.0f;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0, GuiScreen.height, 0.0).tex(0.0, GuiScreen.height / 32.0f + tint).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos(GuiScreen.width, GuiScreen.height, 0.0).tex(GuiScreen.width / 32.0f, GuiScreen.height / 32.0f + tint).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos(GuiScreen.width, 0.0, 0.0).tex(GuiScreen.width / 32.0f, tint).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos(0.0, 0.0, 0.0).tex(0.0, tint).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }
    
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    public void confirmClicked(final boolean result, final int id) {
        if (id == 31102009) {
            if (result) {
                this.openWebLink(this.clickedLinkURI);
            }
            this.clickedLinkURI = null;
            this.mc.displayGuiScreen(this);
        }
    }
    
    private void openWebLink(final URI p_175282_1_) {
        try {
            final Class<?> oclass = Class.forName("java.awt.Desktop");
            final Object object = oclass.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
            oclass.getMethod("browse", URI.class).invoke(object, p_175282_1_);
        }
        catch (Throwable throwable) {
            GuiScreen.LOGGER.error("Couldn't open link", throwable);
        }
    }
    
    public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? (Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)) : (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
    }
    
    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
    
    public static boolean isAltKeyDown() {
        return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
    }
    
    public static boolean isKeyComboCtrlX(final int p_175277_0_) {
        return p_175277_0_ == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }
    
    public static boolean isKeyComboCtrlV(final int p_175279_0_) {
        return p_175279_0_ == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }
    
    public static boolean isKeyComboCtrlC(final int p_175280_0_) {
        return p_175280_0_ == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }
    
    public static boolean isKeyComboCtrlA(final int p_175278_0_) {
        return p_175278_0_ == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
    }
    
    public void onResize(final Minecraft mcIn, final int p_175273_2_, final int p_175273_3_) {
        this.setWorldAndResolution(mcIn, p_175273_2_, p_175273_3_);
    }
    
    public static int getWidth() {
        return GuiScreen.width;
    }
    
    public static void setWidth(final int width) {
        GuiScreen.width = width;
    }
    
    public static int getHeight() {
        return GuiScreen.height;
    }
    
    public static void setHeight(final int height) {
        GuiScreen.height = height;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
        NEWLINE_SPLITTER = Splitter.on('\n');
    }
}
