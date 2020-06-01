package org.nav4j.menu;

/**
 * An abstract base class for most executable menu commands.
 * 
 * @author Joel Kozikowski
 */
public abstract class AbstractMenuOption implements MenuCommand {

    private String optionName;
    
    public AbstractMenuOption(String optionName) {
        this.optionName = optionName;
    }


    @Override
    public String getOptionName() {
        return this.optionName;
    }


}
