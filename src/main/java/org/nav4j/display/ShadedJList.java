package org.nav4j.display;

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;


/**
 * A specialized JList component for displaying list items inside of a ShadedPanel. It
 * can display its items either horizontally on a single row (horizontalList == TRUE), \
 * or vertically in a single column (horizontalList == FALSE). As a template type, the
 * list can contain any data type. The default behavior is to use a menu option's toString()
 * method as the display value.
 * 
 * @author Joel Kozikowski
 */
public class ShadedJList<OptionType> extends JList<OptionType> {

    private int fontSize = Theme.DATA_FONT_SIZE;
    
    public class CellRenderer extends JLabel implements ListCellRenderer<OptionType> {

        @Override
        public Component getListCellRendererComponent(JList<? extends OptionType> list, OptionType value, int index, 
                                                      boolean isSelected, boolean cellHasFocus) {

            setRenderValues(this, value);
            Theme.setup(this, fontSize);
            if (isSelected) {
                this.setOpaque(true);
                setBackground(new Color(0xF4, 0xCC, 0x70, 175));
                setForeground(new Color(0xE9,0xE9,0xE9,0xFF));
            }
            else {
                this.setOpaque(false);
            }
            setEnabled(list.isEnabled());
            this.setBorder(new EmptyBorder(8,15,8,15));            
            
            return this;        
            
        }
        
    };
    
    
    /**
     * Configures renderCell to be drawn in the list based on the 
     * specified list value.
     * @param renderCell A JLabel that will be rendered in the list. setText() and/or
     *   setIcon() should be called
     * @param value The value to use as the source of values for renderCell
     */
    protected void setRenderValues(JLabel renderCell, OptionType value) {
        renderCell.setText(value.toString());
    }
    
    
    /**
     * Sets the font size to use when rendering the list. The
     * default size is 30
     */
    public void setFontSize(int newFontSize) {
        this.fontSize = newFontSize;
    }
    
    
    private DefaultListModel<OptionType> dataModel;
    
    
    public ShadedJList(boolean horizontalList, int fontSize) {
        super();
        dataModel = new DefaultListModel<OptionType>();
        this.setModel(dataModel);
        this.setCellRenderer(new CellRenderer());
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setOpaque(false);
        this.fontSize = fontSize;

        // For a horizontal menu...
        if (horizontalList) {
           this.setLayoutOrientation(JList.HORIZONTAL_WRAP);
           this.setVisibleRowCount(1);
        }
    }
    
    
    public void clear() {
        dataModel.clear();
    }
    
    
    public void addElement(OptionType element) {
        dataModel.addElement(element);
        if (dataModel.size() == 1) {
            this.setSelectedIndex(0);
        }
    }
    
    
    /**
     * Adds all items in the specified collection to this list.
     */
    public void addAll(Collection<? extends OptionType> collection) {
        for (OptionType item : collection) {
            this.addElement(item);
        }
    }
    
    
    /**
     * Returns the number of elements that are in the list model.
     */
    public int getListSize() {
        return this.getModel().getSize();
    }

}
