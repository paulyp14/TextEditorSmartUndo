package base.view;

import base.controller.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;


/**
 * Class that manages the Undo Panel View.
 * 
 * @author Thanh Tung Nguyen
 */
public class UndoPanelView extends JPanel {
    
    private UndoPanelController controller;
    private ArrayList<EditGroupView> editGroupViews;
    private int curFocusedGroup = -1;
    private JButton newGroupBtn;

    private final int SIZE_WIDTH = 200;
    private final int GROUP_SIZE_MAX = 7;


    /**
     * Constructor
     * @param height of the app
     */
    public UndoPanelView(int height) {

        editGroupViews = new ArrayList<EditGroupView>();

        this.setPreferredSize(new Dimension(SIZE_WIDTH, height));
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //((FlowLayout)getLayout()).setVgap(0);

        // Button to creat a new edit group
        newGroupBtn = new JButton("New Group");
        newGroupBtn.setBackground(Color.WHITE);
        newGroupBtn.setFont(new Font("Arial", Font.BOLD, 15));
        add(newGroupBtn);

        newGroupBtn.addActionListener(new ActionListener() {            //Handles NewGroup creation
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int groupIndex = editGroupViews.size();
                controller.addNewGroup(groupIndex);
            }
        });
    }


    //**********    PUBLIC  METHODS   **********//

    
    /**
     * Set undo panel controller reference.
     * @param controller
     */
    public void setController(UndoPanelController controller) {
        this.controller = controller;
    }

    /**
     * Total number of edit groups
     * @return
     */
    public int getNumOfEditGroups() {
        return editGroupViews.size();
    }

    /**
     * Returns boolean if we reached the limit # of edit groups
     * @return
     */
    public boolean hasSpaceForNewGroup() {
        return (editGroupViews.size() < GROUP_SIZE_MAX);
    }

    /**
     * Returns all edit group views
     * @return
     */
    public ArrayList<EditGroupView> getEditGroupViews() {
        return editGroupViews;
    }

    /**
     * Returns the edit group currently focused/expanded. Returns 
     * -1 if there are no currently focused edit group.
     * @return
     */
    public int getCurrentlyFocusedGroup() {
        return curFocusedGroup;
    }

    /**
     * Add a new edit group on UndoPanel and place at bottom.
     * Creates a new container in Model.
     * 
     * @return true if delete was successful
     */
    public boolean createNewGroup() {
        
        if (editGroupViews.size() >= GROUP_SIZE_MAX) {
            System.out.println("UndoPanelView.createNewGroup: Cannot exceed " + GROUP_SIZE_MAX + " edit groups");
            return false;
        }

        // Create a new EditGroupView
        int groupIndex = editGroupViews.size();
        EditGroupView newEditGroup = new EditGroupView(this, controller, groupIndex, SIZE_WIDTH);
        editGroupViews.add(newEditGroup);
        editGroupViews.get(groupIndex).expandEditList();

        // Add edit group ui component
        remove(newGroupBtn);
        add(newEditGroup);
        add(newGroupBtn);

        // remove "New Group" button if reach limit
        if (editGroupViews.size() >= GROUP_SIZE_MAX)
            remove(newGroupBtn);

        updateUI(); //Fixes issue with double clicking NewGroup, this updates the interface everytime NewGroup is clicked
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

        // reset group being focused
        if (index == curFocusedGroup)
            onCollapseEditGroup();
        else // or move current focused group index down if its index is above deleted index
        if (index < curFocusedGroup) 
            curFocusedGroup--;

        this.remove(editGroupViews.get(index)); // remove ui component
        editGroupViews.remove(index);           // remove from list

        // we move all group index down
        for (int i = index; i < editGroupViews.size(); i++) {
            editGroupViews.get(i).moveIndexDown();
        }

        // TODO remove edit from text box

        // re-add "New Group" button if under limit
        if (editGroupViews.size() < 7 && newGroupBtn.getParent() != this)
            add(newGroupBtn);

        updateUI();
        return true;
    }

    /**
     * Called when user expands an edit group, making it the currently 
     * focused edit group and collapses (if any) the other edit group.
     * 
     * @param index
     */
    public void onExpandEditGroup(int index) {

        if (index < 0 || index >= editGroupViews.size()) {
            System.out.println("UndoPanelView.onExpandEditGroup: Index of edit group is out of range.");
            return;
        }

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
