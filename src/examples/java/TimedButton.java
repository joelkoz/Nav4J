

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;


/**
 * A JButton that will measure the length of a button press
 * and dispatch a call to action listeners who are listening
 * for one of a "Short" and/or "Long" button press.
 * 
 * @author Joel Kozikowski
 */
public class TimedButton extends JButton {

    private static double SECS_THRESHOLD = 1.0;
    private long clickTimerStart;
    private ActionListener shortAction;
    private ActionListener longAction;
    
    public TimedButton(String text) {
        super(text);
        clickTimerStart = -1;
        
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Start timer...
                clickTimerStart = System.currentTimeMillis();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (clickTimerStart > 0) {
                    double elapsedSecs = (System.currentTimeMillis() - clickTimerStart) / 1000.0;
                    if (elapsedSecs <= SECS_THRESHOLD) {
                        if (shortAction != null) {
                            shortAction.actionPerformed(new ActionEvent(this, 0, "short"));
                        }
                    }
                    else {
                        if (longAction != null) {
                            longAction.actionPerformed(new ActionEvent(this, 0, "long"));
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                clickTimerStart = -1;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                clickTimerStart = -1;
            }
            
        });
    }


    public void addShortClickListener(ActionListener shortAction) {
        this.shortAction = shortAction;
    }

    
    public void addLongClickListener(ActionListener longAction) {
        this.longAction = longAction;
    }
    
}
