package org.nav4j.display;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * An adapter class that makes it easy to register component event
 * listeners that response to a Swing component being resized.
 * 
 * @author Joel Kozikowski
 */
public abstract class ResizeListener implements ComponentListener {

    @Override
    abstract public void componentResized(ComponentEvent e);


    @Override
    public void componentMoved(ComponentEvent e) {
    }


    @Override
    public void componentShown(ComponentEvent e) {
    }


    @Override
    public void componentHidden(ComponentEvent e) {
    }

}
