package org.nav4j.display;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import org.map4j.swing.Map4JPanel;
import org.nav4j.Application;
import org.nav4j.control.DefaultButtonResponse;
import org.nav4j.control.MenuNavigationResponse;
import org.nav4j.control.UserInterfaceController;

/**
 * A frame that represents the main display window for a Nav4J application
 *
 * @author Joel Kozikowski
 *
 */
public class DisplayWindow extends JFrame {

    private UserInterfaceController uiController;
    
    private static final long serialVersionUID = 1L;

    private JLayeredPane displayLayers;
    
    private final SplitDisplayPanel splitPanel;
    
    private PopupPanel popupPanel;
    private boolean showPopup = false;

    private PopupPanel menuPanel;
    private boolean showMenu = false;

    
    public DisplayWindow() {
        super("Main Window");

        Dimension fixedSize = new Dimension(800,600);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);
        setUndecorated(true);
        setLocationRelativeTo(null);
        
        displayLayers = new JLayeredPane();
        
        splitPanel = new SplitDisplayPanel();
        
        menuPanel = new PopupPanel(794, 60, false);
        menuPanel.setLocation(3, 540);
        menuPanel.setVisible(false);
        
        displayLayers.add(splitPanel, JLayeredPane.DEFAULT_LAYER);
        displayLayers.add(menuPanel, JLayeredPane.PALETTE_LAYER);
        
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(displayLayers, BorderLayout.CENTER);
        
        // Initialize the Application context...
        Application.displayWindow = this;
        uiController = new UserInterfaceController();
        uiController.setDefaultResponse(new DefaultButtonResponse());
        Application.uiController = uiController;
        Application.mapPanel = this.getMapPanel();
        Application.mainMenu = new MenuNavigationResponse();
    }


    /**
     * Returns the user interface controller responsible for responding
     * to user button presses.
     */
    public UserInterfaceController getController() {
        return uiController;
    }

    
    /**
     * Sets the displayed popup window to popup, centering it in the
     * display window. If popup is NULL, any current popup panel
     * will be hidden and removed from the display layers.
     */
    public void setPopupWindow(PopupPanel popup) {

        if (popupPanel != null) {
            // Remove any pre-existing popup...
            popupPanel.setVisible(false);
            displayLayers.remove(popupPanel);
        }
        
        popupPanel = popup;
        
        if (popupPanel != null) {
            // Display the new window, centering it in the display...
            displayLayers.add(popupPanel, JLayeredPane.PALETTE_LAYER);
            popupPanel.setVisible(showPopup);
            int x = (this.getWidth() - popupPanel.getWidth()) / 2;
            int y = (this.getHeight() - popupPanel.getHeight()) / 2;
            popupPanel.setLocation(x, y);
            popupPanel.setVisible(true);
        }
        
        displayLayers.revalidate();
        splitPanel.repaint();
        repaint();
    }

    
    /**
     * Returns the popup panel that holds menu options.
     */
    public PopupPanel getMenuPanel() {
        return menuPanel;
    }
    
    
    /**
     * Turns the display of the menu panel on or off
     * @param visible TRUE if the popup menu panel should be displayed
     */
    public void setMenuVisible(boolean visible) {
        if (showMenu != visible) {
            showMenu = visible;
            menuPanel.setVisible(showMenu);
            splitPanel.repaint();
            repaint();
        }
    }
    
    
    /**
     * Returns the Map4JPanel that contains the actual moving map
     */
    public Map4JPanel getMapPanel() {
        return splitPanel.getMapPanel();
    }
    
    
    /**
     * The moving Map4JPanel is contained in a split panel that allows
     * a second window to be opened in a split screen to the right of 
     * the map panel.
     */
    public SplitDisplayPanel getSplitPanel() {
        return splitPanel;
    }

    
    @Override 
    public void setVisible(boolean vis) {
        if (vis) {
          displayLayers.setSize(this.getWidth(), this.getHeight());            
          splitPanel.setSize(this.getWidth(), this.getHeight());
        }
        super.setVisible(vis);
    }
}
