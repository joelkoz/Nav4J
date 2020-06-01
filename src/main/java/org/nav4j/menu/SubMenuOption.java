package org.nav4j.menu;

import org.nav4j.Application;
import org.nav4j.control.MenuNavigationResponse;


/**
 * A menu option that when selected displays yet another
 * menu of options (loaded into this option by calling
 * <code>addOption()</code>
 * @see SubMenuOption#addOption(MenuCommand)
 * @author Joel Kozikowski
 */
public class SubMenuOption extends AbstractMenuOption {

    private MenuNavigationResponse menu;
    
    public SubMenuOption(String optionName) {
        super(optionName);
        this.menu = new MenuNavigationResponse();
    }


    @Override
    public void execute() {
        Application.uiController.pushResponse(menu);
    }


    /**
     * Adds a new menu command to this sub-menu option.
     */
    public MenuNavigationResponse addOption(MenuCommand menuCommand) {
        return this.menu.addOption(menuCommand);
    }
    
    
    @Override
    public boolean closeMenu() {
        return false;
    }

    
}
