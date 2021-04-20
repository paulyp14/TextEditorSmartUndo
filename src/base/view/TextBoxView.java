package base.view;

import javax.swing.JTextArea;

import base.controller.TextBoxController;

/**
 * View class that manages the user interface of 
 * the JTextArea.
 * 
 * @author Thanh Tung Nguyen
 */
public class TextBoxView extends JTextArea {

    private TextBoxController controller;

    /**
     * Default construtor
     */
    public TextBoxView() { 

        // Characters beyond JTextArea's width will wrap to new line
        this.setLineWrap(true);
    }


    //**********    PUBLIC  METHODS   **********//

    public void setController(TextBoxController controller) {
         this.controller = controller;
    }
    
    public void addEdit (String str, int position, boolean isAddition) {
    	controller.add(str, position, isAddition);
    }
    
    public void undo() {
    	controller.undo();
    }


    //**********    PRIVATE METHODS   **********//
    
}
