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
        MenuBarView menu = new MenuBarView();
        TextBoxView tbox = new TextBoxView();
        UndoPanelView undoPanel = new UndoPanelView(SIZE_HEIGHT);

        // Wrap with textarea scroll pane
        JScrollPane tbox_scroll = new JScrollPane (
            tbox, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        frame.getContentPane().add(BorderLayout.NORTH, menu);
        frame.getContentPane().add(BorderLayout.CENTER, tbox_scroll);
        frame.getContentPane().add(BorderLayout.LINE_START, undoPanel);


        // Set up Controllers
    }

    /**
     * Configurates the settings for the JFrame.
     */
    private void configSettings() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SIZE_WIDTH, SIZE_HEIGHT);
        frame.setResizable(false);
    }
}