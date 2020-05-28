package org.nav4j.display;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JViewport;

import org.nav4j.Application;
import org.nav4j.control.ButtonCommandResponse;

/**
 * A class that represents a window that pops up in the middle of the display,
 * has a list of options to select from in the middle, and has an
 * an optional local menu displayed at the bottom.
 * To activate the window, simply call open().
 * The window will open itself up and start responding to button pushes.
 * 
 * @author Joel Kozikowski
 */
public class PopupSelectionWindow<OptionType> extends PopupPanel implements ButtonCommandResponse {

    protected ShadedJList<String> localMenu;
    protected ShadedJList<OptionType> selectionList;
    protected ShadedJList<?> focusList; 
    
    public PopupSelectionWindow(int width, int height) {
        super(width, height);
        localMenu = new ShadedJList<String>(true, Theme.MENU_FONT_SIZE);
        selectionList = new ShadedJList<OptionType>(false, Theme.POPUP_FONT_SIZE);
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
    

    /**
     * Adds a an option to the selection list.  Should be called
     * prior to calling open().
     * @param optionName
     */
    public void addSelectionOption(OptionType optionName) {
        selectionList.addElement(optionName);
    }
    
    
    
    @Override
    public void initResponse() {
        if (localMenu.getListSize() > 0) {
            // Local menu options have been specified. Activate
            // the local menu...
            this.add(makeScrollable(localMenu), BorderLayout.PAGE_END);
            localMenu.clearSelection();
        }
        
        this.add(makeScrollable(selectionList), BorderLayout.CENTER);
        
        focusList = selectionList;
        Application.displayWindow.setPopupWindow(this);
    }

    
    private JViewport makeScrollable(JList<?> list) {
        JViewport viewport = new JViewport();
        viewport.setOpaque(false);
        viewport.add(list);
        return viewport;
    }

    
    @Override
    public void buttonLeftShort() {
        if (focusList.getListSize() > 0) {
            int ndx = focusList.getSelectedIndex();
            if (ndx > 0) {
                ndx--;
                setSelection(ndx);
            }
            else if (focusList.equals(localMenu)) {
                // We've scrolled off the local menu - go back
                // to the selection list...
                focusList = selectionList;
                setSelection(selectionList.getListSize()-1);
                localMenu.clearSelection();
            }
        }
    }

    
    private void setSelection(int ndx) {
        focusList.setSelectedIndex(ndx);
        focusList.ensureIndexIsVisible(ndx);
        repaint();
    }
    
    
    @Override
    public void buttonLeftLong() {
        this.buttonLeftShort();
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public void buttonCenterShort() {
        if (focusList.getListSize() > 0) {
            Object selection = focusList.getSelectedValue();
            if (focusList.equals(localMenu)) {
                this.onMenuSelection((String)selection);
            }
            else {
                this.onListSelection((OptionType)selection);
            }
        }
    }

    
    protected void onListSelection(OptionType selection) {
        System.out.println("List selected " + selection.toString());
    }


    /**
     * Responds to a local menu selection.
     * @param selection The item that was selected when
     *   the center button was pushed.
     */
    protected void onMenuSelection(String selection) {
        System.out.println("Menu selected " + selection);
        this.close();
    }


    @Override
    public void buttonCenterLong() {
        this.close();
    }

    
    @Override
    public void buttonRightShort() {
        if (focusList.getListSize() > 0) {
            int ndx = focusList.getSelectedIndex();
            if (ndx >= 0) {
                ndx++;
                if (ndx >= focusList.getListSize()) {
                    ndx = 0;
                    // We've reached the end of the list.
                    // Possibly change focus...
                    if (focusList.equals(selectionList)) {
                        selectionList.clearSelection();
                        focusList = localMenu;
                    }
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
