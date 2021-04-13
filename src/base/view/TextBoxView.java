import javax.swing.JTextArea;

/**
 * View class that manages the user interface of 
 * the JTextArea.
 * 
 * @author Thanh Tung Nguyen
 */
public class TextBoxView extends JTextArea {

    //private TextBoxController controller

    /**
     * Default construtor
     */
    TextBoxView() { 

        // Characters beyond JTextArea's width will wrap to new line
        this.setLineWrap(true);
    }

    // Overloaded constructor
    //TextBoxView(TextBoxController controller) {
    //    this.controller = controller
    //}


    //**********    PUBLIC  METHODS   **********//

    //**********    PRIVATE METHODS   **********//
    
}
