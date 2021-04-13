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

        // TODO add actionlistener

        menuFile.add(menuItem_new);
        menuFile.add(menuItem_quit);

        this.add(menuFile);
    }

    // Overloaded constructor
    //MenuBarView(MenuBarController controller) {
    //    this.controller = controller
    //}
    
    //**********    PUBLIC  METHODS   **********//


    //**********    PRIVATE METHODS   **********//

    /**
     * Show a dialog box to confirm if user wants to 
     * proceed with this action.
     * 
     * @param str
     * @return 0 if yes, 1 if no
     */
    private int showConfirmDialog(String str) { 
        return JOptionPane.NO_OPTION;
    }
}
