package org.nav4j.control;

import org.nav4j.display.PopupSelectionWindow;

public class SamplePopupCommand extends AbstractMenuOption {

    public SamplePopupCommand(String optionName) {
        super(optionName);
    }


    @Override
    public void execute() {
        PopupSelectionWindow<String> popup = new PopupSelectionWindow<String>(500, 300);
        popup.setTitle("Test Selection Window");
        popup.addMenuOption("Cancel");
        popup.addMenuOption("OK");
        popup.addSelectionOption("Choice #1");
        popup.addSelectionOption("Choice #2");
        popup.addSelectionOption("Choice #3");
        popup.addSelectionOption("Choice #4");
        popup.open();
    }

}
