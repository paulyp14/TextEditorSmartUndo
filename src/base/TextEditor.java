package base;

import base.view.*;
import base.model.*;
import base.controller.*;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * Class that contains the entire structure of the application.
 * 
 * @author Thanh Tung Nguyen, Andy Nguyen
 */
public class TextEditor {

    private JFrame frame;
    private final int SIZE_WIDTH = 860;
    private final int SIZE_HEIGHT = 600;

    private MenuBarView menu;
    private TextBoxView tbox;
    private UndoPanelView undoPanel;
    
    private String str;

    /**
     * Default constructor
     */
    public TextEditor() {
        init();
        configSettings();
    }

    /**
     * Run app by setting the JFrame to visible
     */
    public void run() {
    	frame.setVisible(true);
    	
    	str = tbox.getText(); // initialize the str variable.
    	// Deffered Document Listener to detect 5 sec of inactivity (no typing)
    	DeferredDocumentListener listener = new DeferredDocumentListener(5000, new ActionListener() {
    		/**
    		 * Action listener (no activity for 5 sec)
    		 */
            @Override
            public void actionPerformed(ActionEvent e) {
            	String temp = tbox.getText();
            	tbox.readEdits(str, temp); // read the edits after 5 secs of inactivity.
                str = tbox.getText(); // update previous tbox
            }
        }, true);
        tbox.getDocument().addDocumentListener(listener);
        tbox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            	str = tbox.getText();
                listener.reset();
            }

            @Override
            public void focusLost(FocusEvent e) {
            	String temp = tbox.getText();
            	tbox.readEdits(str, temp); // read the edits if user clicks outside the text
                listener.stop();
                str = tbox.getText();
            }
        });
        tbox.addKeyListener(new KeyListener() {

        	@Override
    		public void keyTyped(KeyEvent e) {
    			// need to override to implement this method but will not be used.
    			
    		}

    		@Override
    		public void keyPressed(KeyEvent e) {
    			// if user clicks ctrl+z
    			if ((e.getKeyCode() == KeyEvent.VK_Z) && e.isControlDown()) {
    				listener.reset();
    				String temp = tbox.getText();
                	tbox.readEdits(str, temp); // read the edits before undoing
    				tbox.undo();
    				str = tbox.getText(); // update previous tbox
    			}
    			// when the user clicks enter or tab, read the edits before adding the string \n or \t
    			if ((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_TAB)) {
    				String temp = tbox.getText();
                	tbox.readEdits(str, temp); // read the edits
                    str = tbox.getText(); // update previous tbox
    			}
    			// when the user clicks up, down, left or right, read the edits
    			if ((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_LEFT)
    					|| (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
    				String temp = tbox.getText();
                	tbox.readEdits(str, temp); // read the edits
                    str = tbox.getText(); // update previous tbox
    			}
    			// when the user clicks backspace or delete, read the edits before removing text.
    			if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) || (e.getKeyCode() == KeyEvent.VK_DELETE)) {
    				String temp = tbox.getText();
                	tbox.readEdits(str, temp); // read the edits
                    str = tbox.getText(); // update previous tbox
    			}
    		

    		}

    		@Override
    		public void keyReleased(KeyEvent e) {
    			// when the user releases enter or tab, read the edits (the string \n or \t)
    			if ((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_TAB)) {
    				String temp = tbox.getText();
                	tbox.readEdits(str, temp); // read the edits
                    str = tbox.getText(); // update previous tbox
    			}
    			// when the user releases backspace or delete, read the edits after removing text.
    			if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) || (e.getKeyCode() == KeyEvent.VK_DELETE)) {
    				String temp = tbox.getText();
                	tbox.readEdits(str, temp); // read the edits
                    str = tbox.getText(); // update previous tbox
    			}
    		}
        	
        });
        tbox.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent me) {
        		String temp = tbox.getText();
            	tbox.readEdits(str, temp); // read the edits if user clicks on the text
                str = tbox.getText();
        	}
        });

    }


    /**
     * Sets up the Model, View, and Controller, 
     * and connect them to each other.
     */
    private void init() {

        // Set up Views
        frame = new JFrame("TESU");
        menu = new MenuBarView();
        tbox = new TextBoxView();
        undoPanel = new UndoPanelView(SIZE_HEIGHT);
        setupFrameComponents();

        // Set up Controllers
        UndoPanelController undoPanelController = new UndoPanelController(undoPanel);
        MenuBarController menuBarController = new MenuBarController(menu, undoPanelController);
        TextBoxController textBoxController = new TextBoxController(tbox, undoPanel);

        undoPanel.setController(undoPanelController);
        menu.setController(menuBarController);
        tbox.setController(textBoxController);
    }

    /**
     * Configurates the settings for the JFrame.
     */
    private void configSettings() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE_WIDTH, SIZE_HEIGHT);
        frame.setResizable(false);
    }

    public MenuBarView getMenuBarView() {
        return menu;
    }

    public TextBoxView getTextBoxView() {
        return tbox;
    }

    public UndoPanelView getUndoPanelView() {
        return undoPanel;
    }

    private void setupFrameComponents() {

        // Wrap with textarea scroll pane
        JScrollPane tbox_scroll = new JScrollPane (
            tbox, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        // Wrap with textarea scroll pane
        // JScrollPane undoPanel_scroll = new JScrollPane (
        //     undoPanel, 
        //     JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        //     JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        // );

        frame.getContentPane().add(BorderLayout.NORTH, menu);
        frame.getContentPane().add(BorderLayout.CENTER, tbox_scroll);
        frame.getContentPane().add(BorderLayout.LINE_START, undoPanel);
    }
    
    public class DeferredDocumentListener implements DocumentListener {

        private final Timer timer;

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

        @Override
        public void insertUpdate(DocumentEvent e) {
            timer.restart();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            timer.restart();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            timer.restart();
        }

    }
}