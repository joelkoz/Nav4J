package org.nav4j.display;

/**
 * Classes that implement PopupMenuResponder can add
 * custom actions to the menu selection of various popup
 * windows
 * 
 * @author Joel Kozikowski
 *
 */
public interface PopupMenuResponder {
    public void onMenuSelection(String selection);
}
