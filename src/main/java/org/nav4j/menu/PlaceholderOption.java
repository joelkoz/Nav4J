package org.nav4j.menu;

/**
 * A placeholder option is a menu command that is used during development. It
 * simply outputs the option name when selected.
 * 
 * @author Joel Kozikowski
 */
public class PlaceholderOption extends AbstractMenuOption {

    
    public PlaceholderOption(String optionName) {
        super(optionName);
    }
    
    
    @Override
    public void execute() {
        System.out.println("Placeholder option not yet implemented: " + this.getOptionName());
    }

}
