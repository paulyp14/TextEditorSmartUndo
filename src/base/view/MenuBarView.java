import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;

/**
 * View class that manages the user interface of 
 * the JMenu.
 * 
 * @author Thanh Tung Nguyen
 */
public class MenuBarView extends JMenuBar {
    
    //private MenuBarController controller

    /**
     * Default constructor.
     */
    MenuBarView() {

        JMenu menuFile = new JMenu("File");
        JMenuItem menuItem_new  = new JMenuItem("New");
        JMenuItem menuItem_quit = new JMenuItem("Quit");
        menuFile.add(menuItem_new);
        menuFile.add(menuItem_quit);

        JMenu menuEdit = new JMenu("Edit");
        JMenuItem menuItem_undo  = new JMenuItem("Undo");
        JMenuItem menuItem_arbitrary = new JMenuItem("Arbitrary Undo Selected Group");
        JMenuItem menuItem_deleteGroup = new JMenuItem("Delete Edit Group");
        JMenuItem menuItem_deleteAllGroup = new JMenuItem("Delete All Edit Group");
        menuEdit.add(menuItem_undo);
        menuEdit.add(menuItem_arbitrary);
        menuEdit.add(menuItem_deleteGroup);
        menuEdit.add(menuItem_deleteAllGroup);

        // TODO add actionlistener

        this.add(menuFile);
        this.add(menuEdit);
    }
    
    
    //**********    PUBLIC  METHODS   **********//

    // public void setController(MenuBarController controller) {
    //     this.controller = controller;
    // }


    //**********    PRIVATE METHODS   **********//

    /**
     * Show a dialog box to confirm if user wants to 
     * proceed with this action.
     * 
     * @param str
     * @return 0 if yes, 1 if no
     */
    private int showConfirmDialog(String message) { 

        int dialogResult = JOptionPane.showConfirmDialog (
                null, 
                message,
                "TESU",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return dialogResult;
    }
}
