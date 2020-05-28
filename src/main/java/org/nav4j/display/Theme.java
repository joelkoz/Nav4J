package org.nav4j.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;

import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;

/**
 * Sets colors and fonts according to the current display theme.
 * 
 * @author Joel Kozikowski
 */
public class Theme {

    public static final int MENU_FONT_SIZE = 30;

    public static final int POPUP_FONT_SIZE = 27;
    public static final int POPUP_TITLE_SIZE = 30;
    
    public static final int DATA_TITLE_FONT_SIZE = 13;
    public static final int DATA_FONT_SIZE = 20;
    
    /**
     * Configures the component according to this theme with a 30 point font and
     * a 3 pixel empty border for spacing.
     */
    public static void setup(JComponent comp) {
        Theme.setup(comp, 30);
    }
    
    
    
    /**
     * Configures the component according to the theme with the specified font and
     * a 3 pixel empty border for spacing.
     * @param ptFontSize The font size, in points.
     */
    public static void setup(JComponent comp, int ptFontSize) {
        Theme.setFont(comp, ptFontSize);
        Theme.setColor(comp);
        comp.setBorder(new EmptyBorder(3,3,3,3));
    }
    
    
    public static void setColor(JComponent comp) {
        comp.setOpaque(false);
        comp.setForeground(new Color(0xE9,0xE9,0xE9,0xFF));
        
        // In the event that setOpaque() is turned back
        // on, specify a color that is somewhat close to
        // what we want...
        comp.setBackground(new Color(13, 54, 132, 225));
    }

    
    public static void setFont(JComponent comp, int ptFontSize) {
        comp.setFont(new Font("San Serif", Font.BOLD, ptFontSize));
    }
    
    
    private final static int ALPHA_TRANSPARENCY = 225;


    /**
     * Returns a Paint object that can be used to paint the background
     * of windows according to our theme.
     * @param gradientHeight The height of the gradient before it repeats.
     */
    public static Paint getBackgroundPaint(int gradientHeight) {
        
// Colors we have tried...
//      Color color1 = new Color(0x00, 0x3B, 0x46, 175);
//      Color color2 = new Color(0xC4, 0xDF, 0xE6, 175);
//      Color color1 = new Color(0x4F, 0xAC, 0xFE, 200);
//      Color color2 = new Color(0x00, 0xF2, 0xFE, 200);
//      Color color1 = new Color(0x33, 0x08, 0x67, ALPHA_TRANSPARENCY);
//      Color color2 = new Color(0x30, 0xCF, 0xD0, ALPHA_TRANSPARENCY);
        
        Color color1 = new Color(13, 54, 132, ALPHA_TRANSPARENCY);
        Color color2 = new Color(80, 110, 172, ALPHA_TRANSPARENCY);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, gradientHeight, color2);
        return gp;
    }

    
    /**
     * Returns a Paint object that can be used to paint a drop shadow
     * on application windows
     * @return
     */
    public static Paint getShadowPaint() {
        return new Color(0x69, 0x69, 0x69, ALPHA_TRANSPARENCY);
    }
    
}
