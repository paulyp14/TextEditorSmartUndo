package base.view;

import base.controller.*;

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
    
    private MenuBarController controller;

    /**
     * Default constructor.
     */
    public MenuBarView() {

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


        menuItem_quit.addActionListener(new ActionListener() {               //Handles Menu Item "Quit"
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showConfirmDialog("Are you sure you want to Quit?") == 0){
                    System.exit(0);
                }
            }
        });

        menuItem_new.addActionListener(new ActionListener() {               //Handles Menu Item "Quit"
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showConfirmDialog("Are you sure you want to create new?") == 0){
                    controller.deleteAllGroups();
                    // TODO clear textbox too
                }
            }
        });

        menuItem_newGroup.addActionListener(new ActionListener() {           //Handles creation of a New Group for Edits
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                UndoPanelView undoPanelViewRef = controller.getUndoPanelController().getUndoPanelView();

                // we make a new group if we didn't reach maximum # of groups
                if (undoPanelViewRef.hasSpaceForNewGroup()) {
                    controller.addNewGroup(undoPanelViewRef.getEditGroupViews().size());
                }
            }
        });

        menuItem_arbitrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UndoPanelView undoPanelViewRef = controller.getUndoPanelController().getUndoPanelView();
                int focusedGroupIndex = undoPanelViewRef.getCurrentlyFocusedGroup();
                int numEditsInGroup = undoPanelViewRef.getEditGroupViews().get(focusedGroupIndex).getListModel().size();

                if (focusedGroupIndex != -1 && numEditsInGroup > 0)
                     controller.deleteRandomEdits(focusedGroupIndex);
                else
                    showMessageDialog("Please select a group edit first.");
            }
        });

        menuItem_deleteGroup.addActionListener(new ActionListener() {           //Handles deletion of an Edit Group
            @Override
            public void actionPerformed(ActionEvent e) {
                UndoPanelView undoPanelViewRef = controller.getUndoPanelController().getUndoPanelView();
                int focusedGroupIndex = undoPanelViewRef.getCurrentlyFocusedGroup();

                if (focusedGroupIndex != -1) {
                    if(showConfirmDialog("Are you sure you want to delete this edit group?") == 0){
                        controller.deleteGroup(focusedGroupIndex);
                    }
                }
                else
                    showMessageDialog("Please select a group edit first.");
            }
        });

        menuItem_deleteAllGroup.addActionListener(new ActionListener() {        //Handles deletion of ALL Edit Groups
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showConfirmDialog("Are you sure you want to delete all Edit Groups?") == 0){
                    controller.deleteAllGroups();
                }
            }
        });

        this.add(menuFile);
        this.add(menuEdit);
    }
    
    
    //**********    PUBLIC  METHODS   **********//

    /**
     * Set MenuBarController.
     * @param controller
     */
    public void setController(MenuBarController controller) {
        this.controller = controller;
    }


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
                this, 
                message,
                "TESU",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return dialogResult;
    }

    /**
     * Show a simple message dialog box
     * 
     * @param str
     */
    private void showMessageDialog(String message) { 

        JOptionPane.showMessageDialog (
                this, 
                message,
                "Alert",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
