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

import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * Class that contains the entire structure of the application.
 * 
 * @author Thanh Tung Nguyen
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
    	
    	str = tbox.getText();
    	DeferredDocumentListener listener = new DeferredDocumentListener(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (str.length() < tbox.getText().length())
            		tbox.addEdit(tbox.getText().substring(str.length(), tbox.getText().length()),-1,true);
            	str = tbox.getText();
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
            	if (str.length() < temp.length())
            	{
            		int i = 0;
            		while (i < str.length())
            		{
            			if (str.charAt(i) != (temp.charAt(i)))
            				break;
            			i++;
            		}
            		if (i == str.length())
            			tbox.addEdit(temp.substring(i, temp.length()-1), -1, true);
            		else
            			tbox.addEdit(temp.substring(i, temp.length()-i), i, true);
            	}
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
    			if ((e.getKeyCode() == KeyEvent.VK_Z) && e.isControlDown()) {
    				listener.reset();
    				tbox.undo();
    			}

    		}

    		@Override
    		public void keyReleased(KeyEvent e) {
    			// need to override to implement this method but will not be used.
    			
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