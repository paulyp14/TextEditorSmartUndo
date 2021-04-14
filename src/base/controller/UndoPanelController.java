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

    public UndoPanelController(View undoPanelView, GroupContainer  groupContainer)
    {
        this.undoPanelView = undoPanelView;
        this.groupContainer = groupContainer;
    }
	
    public View getUndoPanelView()
    {
    	return this.undoPanelView;
    }
    public GroupContainer getGroupContainer()
    {
    	return this.getGroupContainer;
    }
    public void setUndoPanelView(View undoPan)
    {
    	this.undoPanelView=undoPan;
    }
    public void setGroupContainer(GroupContainer groupC)
    {
    	this.groupContainer = groupC;
    }
    @Override
    public void addNewEdit(int groupIndex)
    {
        //Add buttonlistener?
        String groupName = String.valueOf(groupIndex);
        ArrayList<Integer> addItem = new ArrayList<>();
        Random rand = new Random();
        int randomEditNumber = rand.nextInt(9999999);
        
        addItem.add(randomEditNumber); //maybe something other than random numbers
        
        groupContainer.add(groupName, addItem);
        groupContainer.update();
    }

    @Override
    public void addNewGroup()
    {
        Random rand = new Random();
        int randomGroupNumber = rand.nextInt(9999999);
        //Add buttonlistener?
        String groupName = String.valueOf(randomGroupNumber);
        
        groupContainer.create(groupName);
        groupContainer.update();//Maybe in updateView?
    }

    @Override
    public void undoEdit(int groupIndex, int editIndex)
    {
        //Add buttonlistener?
        
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
