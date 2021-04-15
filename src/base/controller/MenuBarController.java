package base.controller;

import base.model.*;

public class MenuBarController implements ControllerInterface {
	
    private UndoPanelController undoPanel;
    public MenuBarController(View undoPanelView, GroupContainer  groupContainer, EditContainer editC)
    {
    	undoPanel.setUndoPanelView(undoPanelView);
    	undoPanel.setGroupContainer(groupContainer);
    	undoPanel.setEditContainer(editC);
    }
	
	@Override
	public void addNewEdit(int groupIndex, String text, int editIndex, boolean isAddition) {
		// TODO Auto-generated method stub
		undoPanel.addNewEdit(groupIndex,text,editIndex,isAddition);
	}

	@Override
	public void addNewGroup(int groupIndex) {
		// TODO Auto-generated method stub
		undoPanel.addNewGroup(groupIndex);
	}

	@Override
	public void undoEdit(int groupIndex, int editIndex) {
		// TODO Auto-generated method stub
		undoPanel.undoEdit(groupIndex, editIndex);
	}

	@Override
	public void undoGroupEdits(int groupIndex) {
		// TODO Auto-generated method stub
		undoPanel.undoGroupEdits(groupIndex);
	}

	@Override
	public void deleteRandomEdits(int groupIndex) {
		// TODO Auto-generated method stub
		undoPanel.deleteRandomEdits(groupIndex);
	}

	@Override
	public void deleteGroup(int groupIndex) {
		// TODO Auto-generated method stub
		undoPanel.deleteGroup(groupIndex);
	}

	@Override
	public void deleteAllGroups() {
		// TODO Auto-generated method stub
		undoPanel.deleteAllGroups();
	}

	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		undoPanel.updateView();
	}

}
