package org.nav4j.display;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A shaded panel that represents a pop up window on the display. It
 * is used for things like the menu and dialog system. The popup panel
 * can have an optional title that will appear centered at the top of
 * the window
 * @see PopupPanel#setTitle(String)
 * 
 * @author Joel Kozikowski
 */
public class PopupPanel extends ShadedPanel {

    private JLabel titleLabel = null;
    
    public PopupPanel(int width, int height) {
        this(width, height, true);
    }
    
    public PopupPanel(int width, int height, boolean drawShadow) {
        super(new BorderLayout(), drawShadow ? SHADOW_SIZE : 0);
        
        Dimension fixedSize = new Dimension(width, height);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);
        setPreferredSize(fixedSize);
        this.setOpaque(true);
        this.setSize(width, height);
    }

    
    /**
     * Sets a title to be displayed on this window
     */
    public void setTitle(String title) {
        if (titleLabel == null) {
            titleLabel = new JLabel(title, SwingConstants.CENTER);
            Theme.setup(titleLabel, Theme.POPUP_TITLE_SIZE);
            add(titleLabel, BorderLayout.PAGE_START);
        }
        else {
            titleLabel.setText(title);
        }
    }
    
    private final static int SHADOW_SIZE = 10;
}
