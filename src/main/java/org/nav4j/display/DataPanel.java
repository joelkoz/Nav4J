package org.nav4j.display;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


/**
 * A Data panel is used to display a data item overlayed on
 * the main map display.
 * 
 * @author Joel Kozikowski
 */
public class DataPanel extends ShadedPanel {

    protected JLabel titleLabel;
    protected JLabel data;
    
    public DataPanel(String title) {
        super(new BorderLayout(), 3);

        titleLabel = new JLabel(title, SwingConstants.CENTER);
        Theme.setup(titleLabel, Theme.DATA_TITLE_FONT_SIZE);
        titleLabel.setBorder(new EmptyBorder(3,3,0,3));
        add(titleLabel, BorderLayout.PAGE_START);

        data = new JLabel("---");
        Theme.setup(data, Theme.DATA_FONT_SIZE);
        data.setBorder(new EmptyBorder(1,3,3,3));
        add(data, BorderLayout.CENTER);
    }
    
    
    /**
     * Sets the data in the panel to the 
     * specified value.
     */
    public void setData(String value) {
        data.setText(value);
    }
    
}
