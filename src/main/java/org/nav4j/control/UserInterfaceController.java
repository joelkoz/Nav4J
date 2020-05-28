package org.nav4j.control;

import java.util.Stack;

/**
 * The UserInterfaceController routes actual button presses from the
 * physical world to button command response objects. Responses are
 * pushed and popped from a response stack. The item on the top of
 * the stack is the "active" response. If there are not responses
 * on the stack, the default response (defined by calling setDefaultResponse())
 * is used. This class exists as a singleton who's instance can be found
 * in <code>Application.uiController</code>
 * 
 * @author Joel Kozikowski
 */
public class UserInterfaceController implements ButtonCommandResponse {

    private Stack<ButtonCommandResponse> stack;
    
    private ButtonCommandResponse defaultResponse;
    
    public UserInterfaceController() {
        stack = new Stack<ButtonCommandResponse>();
    }

    
    public void setDefaultResponse(ButtonCommandResponse defaultResponse) {
        this.defaultResponse = defaultResponse;
    }
    
    
    public void pushResponse(ButtonCommandResponse newResponse) {
        stack.push(newResponse);
        initResponse();
    }

    
    public void popResponse() {
        if (!stack.isEmpty()) {
            stack.pop();
            initResponse();
        }
    }
    

    @Override
    public void initResponse() {
        if (!stack.isEmpty()) {
            stack.peek().initResponse();
        }
        else if (this.defaultResponse != null) {
            this.defaultResponse.initResponse();
        }
    }
    
    
    @Override
    public void buttonLeftShort() {
        if (!stack.isEmpty()) {
            stack.peek().buttonLeftShort();
        }
        else if (defaultResponse != null) {
            defaultResponse.buttonLeftShort();
        }
    }

    
    @Override
    public void buttonCenterShort() {
        if (!stack.isEmpty()) {
            stack.peek().buttonCenterShort();
        }
        else if (defaultResponse != null) {
            defaultResponse.buttonCenterShort();
        }
    }

    
    @Override
    public void buttonRightShort() {
        if (!stack.isEmpty()) {
            stack.peek().buttonRightShort();
        }
        else if (defaultResponse != null) {
            defaultResponse.buttonRightShort();
        }
    }
    
    
    @Override
    public void buttonLeftLong() {
        if (!stack.isEmpty()) {
            stack.peek().buttonLeftLong();
        }
        else if (defaultResponse != null) {
            defaultResponse.buttonLeftLong();
        }
    }

    
    @Override
    public void buttonCenterLong() {
        if (!stack.isEmpty()) {
            stack.peek().buttonCenterLong();
        }
        else if (defaultResponse != null) {
            defaultResponse.buttonCenterLong();
        }
    }

    
    @Override
    public void buttonRightLong() {
        if (!stack.isEmpty()) {
            stack.peek().buttonRightLong();
        }
        else if (defaultResponse != null) {
            defaultResponse.buttonRightLong();
        }
    }


    public void clearResponseStack() {
        stack.clear();
        initResponse();
    } 

}


