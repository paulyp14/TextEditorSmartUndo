package base;

import base.view.*;
import base.model.*;
import base.controller.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

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
    public void run() { frame.setVisible(true); }


    /**
     * Sets up the Model, View, and Controller, 
     * and connect them to each other.
     */
    private void init() {

        // Set up Models

        // Set up Views
        frame = new JFrame("TESU");
        menu = new MenuBarView();
        tbox = new TextBoxView();
        undoPanel = new UndoPanelView(SIZE_HEIGHT);
        setupFrameComponents();

        // Set up Controllers
        // menu
        // textbox
        UndoPanelController undoPanelController = new UndoPanelController(undoPanel);

        undoPanel.setController(undoPanelController);
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
}