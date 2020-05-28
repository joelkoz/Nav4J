package org.nav4j.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.nav4j.Application;


/**
 * A JPanel that is drawn with a shaded background according to the Theme system.
 * The panel has an optional drop shadow to give it some dimension.
 * @see Theme
 * 
 * @author Joel Kozikowski
 */
public class ShadedPanel extends JPanel {

    /**
     * @param layout The layout manager to use in this panel
     * @param shadowSize The number of pixels to use to draw the drop shadow. If 
     *   this value is zero or less, no drop shadow will be used.
     */
    public ShadedPanel(LayoutManager layout, int shadowSize) {
        super(layout);
        
        this.shadowSize = shadowSize;
        this.drawShadow = (shadowSize > 0);
        
        if (drawShadow) {
           this.setBorder(new EmptyBorder(5, 5, 5+shadowSize, 5+shadowSize));
        }
        else {
            this.setBorder(new EmptyBorder(5, 5, 5, 5));
        }
        
        Theme.setup(this);
        
        // Force this window to have its background painted
        // (required even if the background paint is transparent).
        this.setOpaque(true);
    }


    protected boolean drawShadow;
    protected int shadowSize = 10;
    
    
    
    /**
     * Paint a themed background for this panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        g2d.setPaint(Theme.getBackgroundPaint(h));
        
        if (drawShadow) {
            g2d.fillRect(0, 0, w-shadowSize, h - shadowSize);
            g2d.setPaint(Theme.getShadowPaint());
            g2d.fillRect(shadowSize, h - shadowSize, w-shadowSize, shadowSize);
            g2d.fillRect(w - shadowSize, shadowSize, shadowSize, h - shadowSize*2);
        }
        else {
            g2d.fillRect(0, 0, w, h);
        }
    }



    @Override
    public void repaint() {
        if (Application.displayWindow != null) {
            // Since we are using transparencies, make sure the
            // main display window repaints itself also
            Application.displayWindow.repaint();
        }
        super.repaint();
    }

    
}
