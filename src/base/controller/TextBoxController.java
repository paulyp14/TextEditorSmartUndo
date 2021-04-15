package base.controller;

import java.util.ArrayList;

import base.model.*;
import base.view.*;

/**
 * 
 * @author andy nguyen
 */

public class TextBoxController implements ControllerInterface {
	
	private TextBoxView textBoxView;
    private GroupContainer groupContainer;

    
    public TextBoxController(TextBoxView tbv, GroupContainer gc) {
    	textBoxView = tbv;
    	groupContainer = gc;
    }
    
	@Override
	public void addNewEdit(int groupIndex, String text,int editIndex, boolean isAddition) {
		String groupName = String.valueOf(groupIndex);
		
		if (!groupContainer.groups.containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}
		
		groupContainer.editContainer.create(text,editIndex, isAddition);
        int newItemId = groupContainer.editContainer.mostRecentEdit().getId();
        
        groupContainer.add(groupName, newItemId);
        groupContainer.update();
		
	}
	@Override
	public void addNewGroup(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
	@Override
	public void undoEdit(int groupIndex, int editIndex) {
		if (!groupContainer.groups.containsKey(String.valueOf(groupIndex))) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}
		
		ArrayList<Integer> editsToRemove = new ArrayList<>();
		editsToRemove.add(editIndex);
		groupContainer.groups.get(String.valueOf(groupIndex)).removeById(editsToRemove);
		
		groupContainer.editContainer.undo();
		updateView();
	}
	@Override
	public void undoGroupEdits(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
	@Override
	public void deleteRandomEdits(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
	@Override
	public void deleteGroup(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
	@Override
	public void deleteAllGroups() {
		throw new UnsupportedOperationException("Not supported yet."); 
	}
	@Override
	public void updateView() {
		String text = groupContainer.editContainer.asText();
		textBoxView.setText(text);
	}

}
