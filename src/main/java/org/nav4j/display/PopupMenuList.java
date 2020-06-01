package org.nav4j.display;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.nav4j.menu.MenuCommand;

/**
 * A specialized JList component for displaying menu options on the popup window designated
 * as the menu window
 * @see org.nav4j.control.MenuNavigationResponse
 * 
 * @author Joel Kozikowski
 */
public class PopupMenuList extends ShadedJList<MenuCommand> {

    public PopupMenuList() {
        super(true, Theme.MENU_FONT_SIZE);
    }


    @Override
    protected void setRenderValues(JLabel renderCell, MenuCommand value) {
        renderCell.setText(value.getOptionName());
        
        Icon icon = value.getOptionIcon();
        if (icon != null) {
            renderCell.setIcon(icon);
        }
    }
}
