package base.controller;

import base.model.*;
import base.view.*;
import java.util.*;
/**  
 * Controller component for the undoPanel
 * @author Ian Ngaruiya Njoroge
 */
public class UndoPanelController implements ControllerInterface
{
    private UndoPanelView undoPanelView; 
    private GroupContainer  groupContainer;
    private EditContainer editContainer;

    public UndoPanelController(UndoPanelView undoPanelView, GroupContainer  groupContainer, EditContainer editC)
    {
        this.undoPanelView = undoPanelView;
        this.groupContainer = groupContainer;
        this.editContainer = editC;
    }
    
    /**
     * addNewEdit method, adds an Edit item to a specified Edit group as per its index
     * @param groupIndex The index of the group that will receive the new Edit item
     * @param text The string of text to be added
     * @param editIndex The index of the new Edit item
     * @param isAddition Boolean condition that ascertains if this is a new addition or a modification
     */
    @Override
    public void addNewEdit(int groupIndex, String text, int editIndex, boolean isAddition)
    {
        String groupName = String.valueOf(groupIndex);
        
        editContainer.create(text,editIndex, isAddition);
        int newItemId = editContainer.mostRecentEdit().getId();
        
        groupContainer.add(groupName, newItemId);
        groupContainer.update();
    }

    /**
     * addNewGroup method, adds a new Edit group
     * @param groupIndex The index of the new Edit group
     */
    @Override
    public void addNewGroup(int groupIndex)
    {        
        String groupName = String.valueOf(groupIndex);
        
        groupContainer.create(groupName);//groupName
        groupContainer.update();
        
        undoPanelView.createNewGroup();
    }

    /**
     * undoEdit method, this method will undo a specified Edit item from a given group
     * @param groupIndex The index of the Edit group
     * @param editIndex The index of the  Edit item
     */
    @Override
    public void undoEdit(int groupIndex, int editIndex)
    {
        ArrayList<Integer> undoItem = new ArrayList<>();
        undoItem.add(editIndex);
        
        String groupName = String.valueOf(groupIndex);
        
        groupContainer.undoEditsInGroup(groupName, undoItem);
        groupContainer.update();
    }

    /**
     * undoGroupEdits method, this method will undo all the Edit items within a specified Edit group 
     * @param groupIndex The index of the Edit group
     */
    @Override
    public void undoGroupEdits(int groupIndex)
    {
        String groupName = String.valueOf(groupIndex);
        groupContainer.undoGroup(groupName);
        groupContainer.update();
    }

    /**
     * deleteGroup method, deletes an entire Edit group as well as the Edit items within
     * @param groupIndex The index of the Edit group
     */
    @Override
    public void deleteGroup(int groupIndex)
    {        
        String groupName = String.valueOf(groupIndex);
        groupContainer.removeAndDeleteGroup(groupName);
        groupContainer.update();
        
        undoPanelView.deleteEditGroup(groupIndex);
    }

    /**
     * deleteRandomEdits method, deletes arbitrary Edit items in an Edit group
     * @param groupIndex The index of the Edit group
     */
    @Override
    public void deleteRandomEdits(int groupIndex)
    {
        Random rand = new Random();
        
        ArrayList<Integer> editIds = new ArrayList<>();
        String groupName = String.valueOf(groupIndex);
        
        int numOfEdits = groupContainer.viewEditsInGroup(groupName).size();
        int flip;
        
        //Randomly adds edits to be removed based on their index
        for(int i = 0; i < numOfEdits; i++)
        {
            flip =  rand.nextInt(2);
            
            if(flip == 1)
                editIds.add(i);
        }
        
        groupContainer.undoEditsInGroup(groupName, editIds);
        groupContainer.update();
    }

    /**
     * deleteAllGroups method, this method deletes every group in the UndoPanel
     */
    @Override
    public void deleteAllGroups()
    {
        int maxSize = 10;
        String groupName;
        
        for(int i = 1; i <= maxSize; i++)
        {
            groupName = String.valueOf(i);
            //Deletes all groups provided that their index is numbered between 1 and the max amount of groups
            groupContainer.removeAndDeleteGroup(groupName); 
            undoPanelView.deleteEditGroup(i);
        }
        
        groupContainer.update();
    }

    @Override
    public void updateView()
    {
    }
    
    /**
     * getUndoPanelView, getter method for the UndoPanelView property
     * @return An instance of UndoPanelView
     */   
    public UndoPanelView getUndoPanelView()
    {
        return undoPanelView;
    }

    /**
     * setUndoPanelView, setter method for the UndoPanelView property
     * @param undoPanelView The UndoPanelView instance to be set
     */
    public void setUndoPanelView(UndoPanelView undoPanelView)
    {
        this.undoPanelView = undoPanelView;
    }

    /**
     * getGroupContainer, getter method for the GroupContainer property
     * @return An instance of GroupContainer
     */   
    public GroupContainer getGroupContainer()
    {
        return groupContainer;
    }

    /**
     * setGroupContainer, setter method for the GroupContainer property
     * @param groupContainer The GroupContainer instance to be set
     */
    public void setGroupContainer(GroupContainer groupContainer)
    {
        this.groupContainer = groupContainer;
    }
 
     /**
     * getEditContainer, getter method for the EditContainer property
     * @return An instance of EditContainer
     */   
    public EditContainer getEditContainer()
    {
    	return this.editContainer;
    }
    
    /**
     * setEditContainer, setter method for the EditContainer property
     * @param editC The EditContainer instance to be set
     */
    public void setEditContainer(EditContainer editC)
    {
    	this.editContainer=editC;
    }
}
