package base.view;

import javax.swing.JTextArea;

import base.controller.TextBoxController;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * View class that manages the user interface of 
 * the JTextArea.
 * 
 * @author Thanh Tung Nguyen, Andy Nguyen
 */
public class TextBoxView extends JTextArea {

    private TextBoxController controller;
	private String str;

    /**
     * Default construtor
     */
    public TextBoxView() { 

        // Characters beyond JTextArea's width will wrap to new line
        this.setLineWrap(true);

		str = getText(); // initialize the str variable.
    	// Deffered Document Listener to detect 5 sec of inactivity (no typing)
    	DeferredDocumentListener listener = new DeferredDocumentListener(5000, new ActionListener() {
    		/**
    		 * Action listener (no activity for 5 sec)
    		 */
            @Override
            public void actionPerformed(ActionEvent e) {
            	String temp = getText();
            	readEdits(str, temp); // read the edits after 5 secs of inactivity.
                str = getText(); // update previous tbox
            }
        }, true);

		getDocument().addDocumentListener(listener);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	str = getText();
                listener.reset();
            }

            @Override
            public void focusLost(FocusEvent e) {
            	String temp = getText();
            	readEdits(str, temp); // read the edits if user clicks outside the text
                listener.stop();
                str = getText();
            }
        });
        addKeyListener(new KeyListener() {

        	@Override
    		public void keyTyped(KeyEvent e) {
    			// need to override to implement this method but will not be used.
    			
    		}

    		@Override
    		public void keyPressed(KeyEvent e) {
    			// if user clicks ctrl+z
    			if ((e.getKeyCode() == KeyEvent.VK_Z) && e.isControlDown()) {
    				listener.reset();
    				String temp = getText();
                	readEdits(str, temp); // read the edits before undoing
    				undo();
    				str = getText(); // update previous tbox
    			}
    			// when the user clicks up, down, left or right, read the edits
    			if ((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_LEFT)
    					|| (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
    				String temp = getText();
                	readEdits(str, temp); // read the edits
                    str = getText(); // update previous tbox
    			}
    			// when the user clicks backspace or delete, read the edits before removing text.
    			if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) || (e.getKeyCode() == KeyEvent.VK_DELETE)) {
    				String temp = getText();
                	readEdits(str, temp); // read the edits
                    str = getText(); // update previous tbox
    			}
    		

    		}

    		@Override
    		public void keyReleased(KeyEvent e) {
    			// when the user releases enter or tab, read the edits (the string \n or \t)
    			if ((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_TAB)) {
    				String temp = getText();
                	readEdits(str, temp); // read the edits
                    str = getText(); // update previous tbox
    			}
    			// when the user releases backspace or delete, read the edits after removing text.
    			if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) || (e.getKeyCode() == KeyEvent.VK_DELETE)) {
    				String temp = getText();
                	readEdits(str, temp); // read the edits
                    str = getText(); // update previous tbox
    			}
    		}
        	
        });
    	addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent me) {
        		String temp = getText();
            	readEdits(str, temp); // read the edits if user clicks on the text
                str = getText();
        	}
        });
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
    
    /**
     * Call the undo method from the textboxcontroller
     */
    public void undo() {
    	controller.undo();
    }
    
    /**
     * update the str to the text in the textarea
     */
    public void updateStr() {
    	str = getText();
    }
    
    /**
     * Displays the content of both the editcontainer and groupcontainer on the console.
     */
    public void displayOnConsole() {
    	controller.StringRepr();
    }
    
    
    /**
     * DeferredDocumentListener to detect x sec of inactivity. will reset the timer if a document is updated.
     */
	public class DeferredDocumentListener implements DocumentListener {

        private final Timer timer;
        
        /**
         * 
         * @param timeOut how long to create an actionlistener
         * @param listener actionlistener
         * @param repeats true if wants to be repeated.
         */
        public DeferredDocumentListener(int timeOut, ActionListener listener, boolean repeats) {
            timer = new Timer(timeOut, listener);
            timer.setRepeats(repeats);
        }
        
        public void reset() {
            timer.restart();
        }

        public void start() {
            timer.start();
        }

        public void stop() {
            timer.stop();
        }
        
        /**
         * if there is an insert in the document, restart timer
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            timer.restart();
        }

        /**
         * if there is a removal in the document, restart timer
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            timer.restart();
        }
        
        /**
         * if there is an update in the document (moreso in the formatting), restart timer
         * probably will not be used.
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            timer.restart();
        }

    }
    
}
