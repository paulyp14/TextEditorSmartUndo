package base.controller;

import base.model.*;
import base.view.*;
import java.util.*;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;
/**  
 * @author Ian Ngaruiya Njoroge
 */
public class UndoPanelController implements ControllerInterface
{
    private UndoPanelView undoPanelView; 
    private GroupContainer groupContainer;
    private EditContainer editContainer;

    public UndoPanelController(UndoPanelView undoPanelView)
    {
        this.undoPanelView = undoPanelView;
        this.groupContainer = GroupContainer.getContainer();
        this.editContainer = EditContainer.getContainer();
    }

    @Override
    public void addNewEdit(int groupIndex, String text, int editIndex, boolean isAddition)
    {
        String groupName = String.valueOf(groupIndex);
        
        editContainer.create(text,editIndex, isAddition);
        int newItemId = editContainer.mostRecentEdit().getId();
        
        groupContainer.add(groupName, newItemId);
        groupContainer.update();
    }

    @Override
    public void addNewGroup(int groupIndex)
    {        
        String groupName = String.valueOf(groupIndex);
        
        groupContainer.create(groupName);//groupName
        groupContainer.update();
        
        undoPanelView.createNewGroup();
    }

    @Override
    public void undoEdit(int groupIndex, int editIndex)
    {
        ArrayList<Integer> undoItem = new ArrayList<>();
        undoItem.add(editIndex);
        
        String groupName = String.valueOf(groupIndex);
        
        groupContainer.undoEditsInGroup(groupName, undoItem);
        groupContainer.update();

        undoPanelView.getEditGroupViews().get(groupIndex).undoEdit(editIndex);
    }

    @Override
    public void undoGroupEdits(int groupIndex)
    {
        String groupName = String.valueOf(groupIndex);
        groupContainer.undoGroup(groupName);
        groupContainer.update();

        undoPanelView.getEditGroupViews().get(groupIndex).undoAllEdits();
    }

    @Override
    public void deleteGroup(int groupIndex)
    {        
        String groupName = String.valueOf(groupIndex);
        groupContainer.removeAndDeleteGroup(groupName);
        groupContainer.update();
        
        undoPanelView.deleteEditGroup(groupIndex);
    }

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
    
    /*
        ACCESSORS AND MUTATORS
    */
    public UndoPanelView getUndoPanelView()
    {
        return undoPanelView;
    }

    public void setUndoPanelView(UndoPanelView undoPanelView)
    {
        this.undoPanelView = undoPanelView;
    }

    public GroupContainer getGroupContainer()
    {
        return groupContainer;
    }

    public void setGroupContainer(GroupContainer groupContainer)
    {
        this.groupContainer = groupContainer;
    }
    
    public EditContainer getEditContainer()
    {
    	return this.editContainer;
    }
    public void setEditContainer(EditContainer editC)
    {
    	this.editContainer=editC;
    }
}
