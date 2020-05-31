// License: GPL. For details, see Readme.txt file.
package org.nav4j.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import org.map4j.swing.Map4JPanel;


/**
 * The main mapping panel of the Nav4J UI. It contains the moving
 * map, and an optional "otherPanel" that can be displayed to the right
 * of the map in split screen mode.
 */
public class SplitDisplayPanel extends JPanel {
    
    /** Serial Version UID */
    private static final long serialVersionUID = 3050203054402323973L;

    private JLayeredPane mapLayers;
    private JPanel mapOverlay;
    private Map4JPanel map;
    
    private JPanel otherPanel;
    
    private JSplitPane splitPane;
    
    public SplitDisplayPanel() {
        this(false);
    }

    public SplitDisplayPanel(boolean splitVisible) {
        super();
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        otherPanel = new JPanel();

        mapLayers = new JLayeredPane();
        
        mapOverlay = new JPanel(new BorderLayout());
        mapOverlay.setOpaque(false);
        mapOverlay.setBorder(new EmptyBorder(5,5,5,5));
        
        map = new Map4JPanel(20);
        
        mapLayers.add(map, JLayeredPane.DEFAULT_LAYER);
        mapLayers.add(mapOverlay, JLayeredPane.PALETTE_LAYER);

        splitPane.setDividerLocation(800 - 150);

        map.setMinimumSize(new Dimension(400, 600));
        mapLayers.setMinimumSize(new Dimension(400, 600));
        
        setLayout(new BorderLayout());
        
        
        // Auto-resize the overlays when the layered pane resizes... 
        mapLayers.addComponentListener(new ResizeListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = e.getComponent().getWidth();
                int height = e.getComponent().getHeight();
                mapOverlay.setSize(width, height);
                map.setSize(width, height);
            }
        });        

        setSplitVisible(splitVisible);
    }
    
    
    /**
     * Returns the "other" panel of the split panel. This
     * panel is not visible unless setSplitVisible(true)
     * is called.
     */
    public JPanel getOtherPanel() {
        return otherPanel;
    }

    
    /**
     * Returns an "overlay panel" that can be used to draw items on top of the 
     * moving map. The panel is a transparent panel that is drawn on top
     * of the map panel in the split pane, and is sized to always match that
     * of getMapPanel()
     */
    public JPanel getOverlayPanel() {
        return mapOverlay;
    }
    
    /**
     * Returns the Map4JPanel used to render the map display.
     */
    public Map4JPanel getMapPanel() {
        return map;
    }
    
    
    /**
     * Sets the "otherPanel" to be visible by displaying panel
     * in "split panel" mode with the map panel on the left, and
     * "otherPanel" on the right.
     */
    public void setSplitVisible(boolean visible) {
        removeAll();
        if (visible) {
            // Tree is visible: display window as split pane
            splitPane.setLeftComponent(mapLayers);
            splitPane.setRightComponent(otherPanel);            
            add(splitPane, BorderLayout.CENTER);
        } else { 
            // Tree is NOT visible: display "map only"
            add(mapLayers, BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

    
    /**
     * Sets the divider location
     * @param location The number of pixels from the left to set the divider.
     *   Setting to zero causes the divider to be set to the preferred sizes
     *   of the panels on either side of the divider.
     */
    public void setSplitDividerLocation(int location) {
        splitPane.setDividerLocation(location);
    }
    
    
    @Override
    public void setSize(int width, int height) {
        splitPane.setSize(width, height);
        mapLayers.setSize(width, height);
        otherPanel.setSize(150, height);
        super.setSize(width, height);
    }

}
