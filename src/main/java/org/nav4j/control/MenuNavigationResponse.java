package org.nav4j.control;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JViewport;

import org.nav4j.Application;
import org.nav4j.display.PopupMenuList;
import org.nav4j.display.PopupPanel;
import org.nav4j.menu.MenuCommand;

/**
 * A MenuNavigationResponse holds one or more menu options, presents
 * them to the user, and allows the buttons to be used to navigate
 * between the menu options.  The short center press selects and
 * executes the menu option. A long center press "goes backward", 
 * popping this response off the response stack.
 * <p>Individual menu options are any class that implements the
 * <code>MenuCommand</code> interface. Add those options to this
 * menu by calling <code>addOption</code>
 * @see MenuCommand
 * @see MenuNavigationResponse#addOption(MenuCommand)
 * 
 * @author Joel Kozikowski
 */
public class MenuNavigationResponse extends AbstractButtonResponse {

    private ArrayList<MenuCommand> options;
    
    private PopupMenuList menu;
    
    public MenuNavigationResponse() {
        super();
        options = new ArrayList<MenuCommand>();
    }

    
    @Override
    public void initResponse() {
        menu = new PopupMenuList();
        menu.clear();
        menu.addAll(options);
        PopupPanel menuPanel = Application.displayWindow.getMenuPanel();
        menuPanel.removeAll();
        JViewport viewport = new JViewport();
        viewport.setOpaque(false);
        viewport.add(menu);
        menuPanel.add(viewport, BorderLayout.CENTER);
        menuPanel.revalidate();
        Application.displayWindow.setMenuVisible(true);
    }


    @Override
    public void buttonLeftShort() {
        int ndx = menu.getSelectedIndex();
        if (ndx > 0) {
            ndx--;
            setMenuIndex(ndx);
        }
    }

    
    private void setMenuIndex(int ndx) {
        menu.setSelectedIndex(ndx);
        menu.ensureIndexIsVisible(ndx);
        Application.displayWindow.repaint();
    }

    @Override
    public void buttonCenterShort() {
        MenuCommand selection = menu.getSelectedValue();
        if (selection.closeMenu()) {
            Application.displayWindow.setMenuVisible(false);
            Application.uiController.clearResponseStack();
        }
        selection.execute();
    }


    @Override
    public void buttonCenterLong() {
        Application.uiController.popResponse();
    }
    
    
    @Override
    public void buttonRightShort() {
        int ndx = menu.getSelectedIndex();
        if (ndx >= 0) {
            ndx++;
            if (ndx >= menu.getListSize()) {
                ndx = 0;
            }
            setMenuIndex(ndx);
        }
    }

    
    /**
     * Adds the specified menuCommand to the list of options supported by this
     * menu response. this is returned, allowing for the multiple calls to addOption()
     * to be chained together.
     */
    public MenuNavigationResponse addOption(MenuCommand menuCommand) {
        options.add(menuCommand);
        return this;
    }
    
}
