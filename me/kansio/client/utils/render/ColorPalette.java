package me.kansio.client.utils.render;

import java.awt.*;

public enum ColorPalette
{
    GREY(new Color(158, 158, 158)), 
    BROWN(new Color(121, 85, 72)), 
    DEEP_ORANGE(new Color(255, 87, 34)), 
    LIGHT_GREEN(new Color(139, 195, 74)), 
    INDIGO(new Color(63, 81, 181)), 
    TEAL(new Color(0, 150, 136)), 
    PINK(new Color(233, 30, 99));
    
    private final /* synthetic */ Color color;
    
    GREEN(new Color(76, 175, 80)), 
    CYAN(new Color(0, 188, 212)), 
    BLUE(new Color(33, 150, 243)), 
    LIME(new Color(205, 220, 57)), 
    YELLOW(new Color(255, 235, 59)), 
    LIGHT_BLUE(new Color(3, 169, 244)), 
    ORANGE(new Color(255, 152, 0)), 
    BLUE_GREY(new Color(96, 125, 139)), 
    PURPLE(new Color(156, 39, 176)), 
    RED(new Color(244, 67, 54)), 
    DEEP_PURPLE(new Color(103, 58, 183)), 
    AMBER(new Color(255, 193, 7)), 
    DARK_GREY(new Color(38, 38, 38));
    
    private ColorPalette(final Color lIlIIlllIIIIlI) {
        this.color = lIlIIlllIIIIlI;
    }
    
    public final Color getColor() {
        return this.color;
    }
}
