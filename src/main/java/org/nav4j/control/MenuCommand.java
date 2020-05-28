package org.nav4j.control;

import javax.swing.Icon;

/**
 * Individual menu options available to the user implement this
 * MenuCommand interface. It allows the menu command to specify
 * how it is presented on the menu, as well as defines the
 * behavior when the option is selected (via the <code>execute()</code>
 * method).
 * 
 * @author Joel Kozikowski
 */
public interface MenuCommand {

    /**
     * Returns the name of this option. This will be displayed as the
     * choice on the menu.
     */
    public String getOptionName();
    
    
    /**
     * Returns an icon that represents this menu option. If the menu option does not
     * have an icon, NULL should be returned.
     */
    default public Icon getOptionIcon() {
        return null;
    }

    
    /**
     * The code to execute when this choice has been selected.
     * The Application object can be used to get references to
     * necessary top level items like displayWindow and uiController
     * @see org.nav4j.Application
     */
    public void execute();
    
    
    /**
     * Returns TRUE if the menu popup should be closed and the tree reset prior to calling execute().
     */
    default public boolean closeMenu() {
        return true;
    }
    
}
