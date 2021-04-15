package base.controller;

import base.model.*;
import base.view.*;
import java.util.*;
/**
 * ADD COMMMENTS
 * @author iann9
 */
public class UndoPanelController implements ControllerInterface
{
    private View undoPanelView; //type:UndoPanelView
    private GroupContainer  groupContainer;
    private EditContainer	editContainer;

    public UndoPanelController(View undoPanelView, GroupContainer  groupContainer, EditContainer editC)
    {
        this.undoPanelView = undoPanelView;
        this.groupContainer = groupContainer;
        this.editContainer = editC;
    }
	
    public View getUndoPanelView()
    {
    	return this.undoPanelView;
    }
    public GroupContainer getGroupContainer()
    {
    	return this.groupContainer;
    }
    public void setUndoPanelView(View undoPan)
    {
    	this.undoPanelView=undoPan;
    }
    public void setGroupContainer(GroupContainer groupC)
    {
    	this.groupContainer = groupC;
    }
    public EditContainer getEditContainer()
    {
    	return this.editContainer;
    }
    public void setEditContainer(EditContainer editC)
    {
    	this.editContainer=editC;
    }
    @Override
    public void addNewEdit(String text,int editIndex, boolean isAddition)
    {
       editContainer.create(text,editIndex, isAddition);
    }
    @Override
    public void addNewGroup(int groupIndex, int editIndex)
    {
        String groupName = String.valueOf(groupIndex);
        groupContainer.add(groupName, editIndex);
    }

    @Override
    public void undoEdit(int groupIndex, int editIndex)
    {
        editContainer.undo();
    }

    @Override
    public void undoGroupEdits(int groupIndex)
    {
        //Add buttonlistener?
        
        String groupName = String.valueOf(groupIndex);
        groupContainer.undoGroup(groupName);
        groupContainer.update();//Maybe in updateView?
    }

    @Override
    public void deleteGroup(int groupIndex)
    {
        //Add buttonlistener? 
        String groupName = String.valueOf(groupIndex);
        groupContainer.removeAndDeleteGroup(groupName);
        groupContainer.update();//Maybe in updateView?
    }

    @Override
    public void updateView()
    {
        
    }
	
    @Override
    public void deleteRandomEdits(int groupIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAllGroups()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
