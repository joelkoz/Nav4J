package org.nav4j;

import org.map4j.swing.Map4JPanel;
import org.nav4j.control.MenuNavigationResponse;
import org.nav4j.control.UserInterfaceController;
import org.nav4j.display.DisplayWindow;

/**
 * Application is an application context object that holds static references to the application's high
 * level singletons, reducing the amount of parameter passing required. These values are supplied
 * by DisplayWindow, and are available as soon as a DisplayWindow instance is finished constructing.
 * @see DisplayWindow
 * 
 * @author Joel Kozikowski
 */
public class Application {

    public static DisplayWindow displayWindow;
    
    public static MenuNavigationResponse mainMenu;
    
    public static UserInterfaceController uiController;
    
    public static Map4JPanel mapPanel;
    
}

