package org.nav4j.control;

/**
 * The base class for most button response handlers
 * 
 * @author Joel Kozikowski
 */
public abstract class AbstractButtonResponse implements ButtonCommandResponse {
    
    @Override
    public void buttonLeftLong() {
        this.buttonLeftShort();
    }


    @Override
    public void buttonCenterLong() {
        this.buttonCenterShort();
    }


    @Override
    public void buttonRightLong() {
        this.buttonRightShort();
    }

}
