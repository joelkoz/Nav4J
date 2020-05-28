package org.nav4j.control;


/**
 * Classes that implement ButtonCommandResponse are used to
 * respond to user button presses, translating them into
 * actions.
 * 
 * @author Joel Kozikowski
 */
public interface ButtonCommandResponse {

    
    /**
     * Initializes this response whenever it becomes the active response.
     * This is called just before button presses are redirected to 
     * the implementing class.
     */
    public void initResponse();
    
    
    /**
     * Responds to a "short press" of the left button
     */
    public void buttonLeftShort();
    
    
    /**
     * Responds to a "long press" of the left button
     */
    public void buttonLeftLong();
    

    /**
     * Responds to a "short press" of the center button
     */
    public void buttonCenterShort();
    
    
    /**
     * Responds to a "long press" of the center button
     */
    public void buttonCenterLong();
    
    
    /**
     * Responds to a "short press" of the right button
     */
    public void buttonRightShort();
    
    
    /**
     * Responds to a "long press" of the right button
     */
    public void buttonRightLong();
    
}
