package base.controller;

import base.model.*;
import base.view.*;
import java.util.*;

/**  
 * @author Ian Ngaruiya Njoroge, Thanh Tung Nguyen
 */
public class UndoPanelController implements ControllerInterface
{
    private UndoPanelView undoPanelView; 
    private GroupContainer groupContainer;
    private EditContainer editContainer;
    private TextBoxController textBoxController;

    public UndoPanelController(UndoPanelView undoPanelView)
    {
        this.undoPanelView = undoPanelView;
        this.groupContainer = GroupContainer.getContainer();
        this.editContainer = EditContainer.getContainer();
    }

    public void setTextBoxController(TextBoxController textBoxController) {
        this.textBoxController = textBoxController;
    }

    @Override
    public void addNewEdit(int groupIndex, String text, int editIndex, boolean isAddition)
    {
        String groupName = String.valueOf(groupIndex);
        
        editContainer.create(text,editIndex, isAddition);
        int newItemId = editContainer.mostRecentEdit().getId();
        
        groupContainer.add(groupName, newItemId);
        groupContainer.update();

        undoPanelView.getEditGroupViews().get(groupIndex).addNewEdit(text);
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
        textBoxController.updateView();
    }

    @Override
    public void undoGroupEdits(int groupIndex)
    {
        String groupName = String.valueOf(groupIndex);
        groupContainer.undoGroup(groupName);
        groupContainer.update();

        undoPanelView.getEditGroupViews().get(groupIndex).undoAllEdits();
        textBoxController.updateView();
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
        ArrayList<Integer> editIndexes = new ArrayList<>();
        Random rand = new Random();

        String groupName = String.valueOf(groupIndex);
        int minInclude = 0;
        int maxInclude = groupContainer.viewEditsInGroup(groupName).size() - 1;
        // if above is returning bad values, use:
        // int maxInclude = undoPanelView.getEditGroupViews().get(groupIndex).getListModel().getSize() - 1; 
        
        int randIndex = rand.nextInt(maxInclude - minInclude + 1) + minInclude;
        editIndexes.add(randIndex);
        
        groupContainer.undoEditsInGroup(groupName, editIndexes);
        groupContainer.update();

        undoPanelView.getEditGroupViews().get(groupIndex).undoEdit(randIndex);

        textBoxController.updateView();
    }

    @Override
    public void deleteAllGroups()
    {
        int curSize = undoPanelView.getEditGroupViews().size();
        String groupName;
        
        for(int i = 0; i < curSize; i++)
        {
            groupName = String.valueOf(i);
            //Deletes all groups provided that their index is numbered between 1 and the max amount of groups
            groupContainer.removeAndDeleteGroup(groupName); 
            groupContainer.update();

            undoPanelView.deleteEditGroup(0); // our index is 0 because deleting an edit group will push all index down
        }
    }

    // Update undo panel based on current state of model
    @Override
    public void updateView()
    {
        // TODO
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
