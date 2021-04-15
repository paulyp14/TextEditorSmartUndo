package base.view;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JMenuItem menuItem_newGroup  = new JMenuItem("New Group");
        JMenuItem menuItem_arbitrary = new JMenuItem("Arbitrary Undo Selected Group");
        JMenuItem menuItem_deleteGroup = new JMenuItem("Delete Edit Group");
        JMenuItem menuItem_deleteAllGroup = new JMenuItem("Delete All Edit Group");
        menuEdit.add(menuItem_newGroup);
        menuEdit.add(menuItem_arbitrary);
        menuEdit.add(menuItem_deleteGroup);
        menuEdit.add(menuItem_deleteAllGroup);

        // TODO add actionlistener

        menuItem_quit.addActionListener(new ActionListener() {               //Handles Menu Item "Quit"
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showConfirmDialog("Are you sure you want to Quit?") == 0){
                    System.exit(0);
                }
            }
        });

        menuItem_newGroup.addActionListener(new ActionListener() {           //Handles creation of a New Group for Edits
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call createNewGroup()  use controller here
               /* UndoPanelView upv = new UndoPanelView(){
                    @Override
                    public boolean createNewGroup() {
                        return super.createNewGroup();
                    }
                };*/
            }
        });

        menuItem_arbitrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call controller here to deal with arbitrary undo
            }
        });

        menuItem_deleteGroup.addActionListener(new ActionListener() {           //Handles deletion of an Edit Group
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showConfirmDialog("Are you sure you want to delete this edit group?") == 0){
                    // Use Controller here to deleteEditGroup()
                }
            }
        });

        menuItem_deleteAllGroup.addActionListener(new ActionListener() {        //Handles deletion of ALL Edit Groups
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showConfirmDialog("Are you sure you want to delete all Edit Groups") == 0){
                   // Use Controller here to call deleteAllEditGroup()
                }
            }
        });

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
