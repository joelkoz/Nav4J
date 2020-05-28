package org.nav4j.control;

import org.nav4j.Application;

/**
 * DefaultButtonResponse is the default handler if no other
 * handler is active. The default behavior implemented by
 * this class is the left and right buttons zoom in and out,
 * and the center button brings up the main menu. If you would
 * prefer behavior other than this, define a class that implements
 * the ButtonCommandResponse interface and pass it to
 * <code>Application.uiController.setDefaultResponse()</code>
 * @see UserInterfaceController#setDefaultResponse(ButtonCommandResponse)
 * 
 * @author Joel Kozikowski
 */
public class DefaultButtonResponse extends AbstractButtonResponse {

    @Override
    public void buttonLeftShort() {
        Application.mapPanel.adjustZoom(-1);
    }


    @Override
    public void buttonCenterShort() {
        Application.uiController.pushResponse(Application.mainMenu);
    }


    @Override
    public void buttonRightShort() {
        Application.mapPanel.adjustZoom(1);
    }


    @Override
    public void initResponse() {
        Application.displayWindow.setMenuVisible(false);
    }

}
