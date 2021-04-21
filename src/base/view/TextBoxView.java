package base.view;

import javax.swing.JTextArea;

import base.controller.TextBoxController;

/**
 * View class that manages the user interface of 
 * the JTextArea.
 * 
 * @author Thanh Tung Nguyen, Andy Nguyen
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
    
    public void readEdits(String prev, String temp) {
    	// case where there was an insert in the document (previous tbox is shorter than current tbox)
    	if (prev.length() < temp.length())
    	{
    		int i = 0; // i is used to detect the position of the insertion
    		while (i < prev.length()) 
    		{
    			if (prev.charAt(i) != (temp.charAt(i)))
    				break;
    			i++;
    		}
    		if (i == prev.length()) // if the insertion at the end of the document
    			controller.add(temp.substring(i, temp.length()), -1, true);
    		else // if the insertion was not at the end
    		{
    			try {
    			controller.add(temp.substring(i, temp.length()-prev.length()+i), i, true);
    			} catch (Exception e) {
    				i--;
    				controller.add(temp.substring(i, temp.length()-prev.length()+i), i, true);
    			}
    		}
    	}
    	// case where there was a removal in the document (previous tbox is longer than current tbox)
    	else if (prev.length() > temp.length())
    	{
    		int i = 0; // i is used to detect the position of the deletion
    		while (i < temp.length()) 
    		{
    			if (prev.charAt(i) != (temp.charAt(i)))
    				break;
    			i++;
    		}
    		
    		try {
    			controller.add(prev.substring(i, prev.length()-temp.length()+i), i, false);
    		} catch (Exception e) {
    			i--;
    			controller.add(prev.substring(i, prev.length()-prev.length()+i), i, false);
    		}
    	}
    }
    
    public void undo() {
    	controller.undo();
    }
    
    /**
     * Displays the content of both the editcontainer and groupcontainer on the console.
     */
    public void displayOnConsole() {
    	controller.StringRepr();
    }


    //**********    PRIVATE METHODS   **********//
    
}
