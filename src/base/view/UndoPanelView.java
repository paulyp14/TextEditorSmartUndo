import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;


/**
 * Class that manages the Undo Panel View.
 * 
 * @author Thanh Tung Nguyen
 */
public class UndoPanelView extends JPanel {
    
    //private UndoPanelController controller
    private ArrayList<EditGroupView> editGroupViews;
    private int curFocusedGroup = -1;
    private JButton newGroupBtn;

    private final int SIZE_WIDTH = 200;

    /**
     * Default constructor
     */
    UndoPanelView() {}

    UndoPanelView(int height) {

        this.setPreferredSize(new Dimension(SIZE_WIDTH, height));
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //((FlowLayout)getLayout()).setVgap(0);

        editGroupViews = new ArrayList<EditGroupView>();

        // EditGroupView editGroup1 = new EditGroupView(this, 0, SIZE_WIDTH);
        // editGroupViews.add(editGroup1);
        // add(editGroup1);

        newGroupBtn = new JButton("New Group");
        newGroupBtn.setBackground(Color.WHITE);
        newGroupBtn.setFont(new Font("Arial", Font.BOLD, 15));
        add(newGroupBtn);

        newGroupBtn.addActionListener(new ActionListener() {            //Handles NewGroup creation
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewGroup();
                updateUI();              //Fixes issue with double clicking NewGroup, this updates the interface everytime NewGroup is clicked
            }
        });
    }




    //**********    PUBLIC  METHODS   **********//


    // public void setController(UndoPanelController controller) {
    //     this.controller = controller;
    // }


    /**
     * Add a new edit group on UndoPanel and place at bottom.
     * Creates a new container in Model.
     * 
     * @return true if delete was successful
     */
    public boolean createNewGroup() {
        
        if (editGroupViews.size()  >= 10) {
            System.out.println("UndoPanelView.createNewGroup: Cannot exceed 10 edit groups");
            return false;
        }

        // Create a new EditGroupView
        int groupIndex = editGroupViews.size();
        EditGroupView newEditGroup = new EditGroupView(this, groupIndex, SIZE_WIDTH);
        editGroupViews.add(newEditGroup);
        editGroupViews.get(groupIndex).expandEditList();

        // Add edit group ui component
        remove(newGroupBtn);
        add(newEditGroup);
        add(newGroupBtn);

        return true;
    }
    
    /**
     * Delete an edit group on UndoPanel given an index. 
     * Will remove UI component and remove edit from text box.
     * 
     * @param index
     * @return true if delete was successful
     */
    public boolean deleteEditGroup(int index) {
        
        if (index < 0 || index >= editGroupViews.size()) {
            System.out.println("UndoPanelView.deleteEditGroup: Index of edit group is out of range.");
            return false;
        }

        onCollapseEditGroup();                  // reset group being focused
        this.remove(editGroupViews.get(index)); // remove ui component
        editGroupViews.remove(index);           // remove from list

        // we move all group index down
        for (int i = index+1; i < editGroupViews.size(); i++) {
            editGroupViews.get(i).moveIndexDown();
        }

        // TODO remove edit from text box

        return true;
    }

    /**
     * Called when user expands an edit group, making it the currently 
     * focused edit group and collapses (if any) the other edit group.
     * 
     * @param index
     */
    public void onExpandEditGroup(int index) {

        // if any other edit group is expanded, collapse it before focusing on another edit group
        if (curFocusedGroup >= 0 && curFocusedGroup < editGroupViews.size()) {
            editGroupViews.get(curFocusedGroup).collapseEditList();
        }

        // mark this the current focused edit group
        curFocusedGroup = index;
    }

    /**
     * Called when a user collapses an edit group, notifying no  
     * edit group is being focused.
     */
    public void onCollapseEditGroup() {
        curFocusedGroup = -1;
    }


    //**********    PRIVATE METHODS   **********//


    /**
     * Collapse the UndoPanel section
     */
    private void collapseUndoPanel() {

    }

    /**
     * Re-open/expand the UndoPanel section
     */
    private void expandUndoPanel() {
        
    }

    
}
