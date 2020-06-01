package org.nav4j.display;


/**
 * Classes implementing PopupSelectionResponder can respond
 * to selections made in the PopupSelectionWindow class.
 * 
 * @author Joel Kozikowski
 */
public interface PopupSelectionResponder<OptionType> {
    
    /**
     * Responds to a selection of list item. TRUE
     * should be returned if the selection window
     * should be closed.
     */
    public boolean onListSelection(OptionType selection);
    
}
