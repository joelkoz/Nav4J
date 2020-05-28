package org.nav4j.display;

import java.awt.BorderLayout;
import javax.swing.JViewport;

import org.nav4j.Application;
import org.nav4j.control.ButtonCommandResponse;

/**
 * A class that represents a window that pops up in the middle of the display
 * with an optional local menu displayed at the bottom.
 * To activate the window, simply call open().
 * The window will open itself up and start responding to button pushes directly.
 * 
 * @author Joel Kozikowski
 */
public class PopupDialog extends PopupPanel implements ButtonCommandResponse {

    protected ShadedJList<String> localMenu;
    
    public PopupDialog(int width, int height) {
        super(width, height);
        localMenu = new ShadedJList<String>(true, Theme.MENU_FONT_SIZE);
    }

    
    public void open() {
        Application.uiController.pushResponse(this);
    }
    
    
    /**
     * Closes this window, removing it from the display, and popping it off of
     * the control stack.
     */
    public void close() {
        setVisible(false);
        Application.displayWindow.setPopupWindow(null);
        Application.uiController.popResponse();
    }
    
    
    /**
     * Adds a menu option to the local menu.  Should be called
     * prior to calling open().
     * @param optionName
     */
    public void addMenuOption(String optionName) {
        localMenu.addElement(optionName);
    }
    
    
    @Override
    public void initResponse() {
        if (localMenu.getListSize() > 0) {
            // Local menu options have been specified. Activate
            // the local menu...
            JViewport viewport = new JViewport();
            viewport.setOpaque(false);
            viewport.add(localMenu);
            this.add(viewport, BorderLayout.PAGE_END);
        }
        Application.displayWindow.setPopupWindow(this);
    }


    @Override
    public void buttonLeftShort() {
        if (localMenu.getListSize() > 0) {
            int ndx = localMenu.getSelectedIndex();
            if (ndx > 0) {
                ndx--;
                setSelection(ndx);
            }
        }
    }

    
    private void setSelection(int ndx) {
        localMenu.setSelectedIndex(ndx);
        localMenu.ensureIndexIsVisible(ndx);
        repaint();
    }
    
    
    @Override
    public void buttonLeftLong() {
        this.buttonLeftShort();
    }

    
    @Override
    public void buttonCenterShort() {
        if (localMenu.getListSize() > 0) {
            String selection = localMenu.getSelectedValue();
            this.onMenuSelection(selection);
        }
    }

    
    /**
     * Responds to a local menu selection.
     * @param selection The item that was selected when
     *   the center button was pushed.
     */
    protected void onMenuSelection(String selection) {
        System.out.println("Selected " + selection);
        this.close();
    }


    @Override
    public void buttonCenterLong() {
        this.close();
    }

    
    @Override
    public void buttonRightShort() {
        if (localMenu.getListSize() > 0) {
            int ndx = localMenu.getSelectedIndex();
            if (ndx >= 0) {
                ndx++;
                if (ndx >= localMenu.getListSize()) {
                    ndx = 0;
                }
                setSelection(ndx);
            }
        }
    }

    
    @Override
    public void buttonRightLong() {
        this.buttonRightShort();
    }

}
