package base.controller;

import java.util.ArrayList;

import base.model.*;
import base.view.*;

/**
 * 
 * @author andy nguyen
 */
public class TextBoxController implements ControllerInterface {
	
	// attributes, the TextBoxController is connected to the GroupContainer and the TextBoxView
	private TextBoxView textBoxView;
    private GroupContainer groupContainer;
    private int indexEdit;

    /**
	 * contructor
	 * @param TextBoxView our textboxview view
	 * @param GroupContainer our groupcontainer model
	 */
    public TextBoxController(TextBoxView tbv) {
    	textBoxView = tbv;
    	groupContainer = GroupContainer.getContainer();
    	indexEdit = 1;
    }
    
    /**
	 * 
	 * @param TextBoxView our textboxview view
	 * @param GroupContainer our groupcontainer model
	 */
	@Override
	public void addNewEdit(int groupIndex, String text,int editIndex, boolean isAddition) {
		String groupName = String.valueOf(groupIndex);
		
		if (!groupContainer.getGroups().containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}
		
		groupContainer.getEditContainer().create(text,editIndex, isAddition);
        int newItemId = groupContainer.getEditContainer().mostRecentEdit().getId();
        
        groupContainer.add(groupName, newItemId);
        groupContainer.update();
		
	}
	
	/**
	 * method that needs to be overriden from the interface but the textboxcontroller does not need to implement this method.
	 * @param int groupIndex
	 */
	@Override
	public void addNewGroup(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); // the textboxcontroller does not need to implement this method so we throw an exception (it shouldn't be called)
	}
	
	/**
	 * 
	 * @param int groupIndex
	 * @param int editIndex
	 */
	@Override
	public void undoEdit(int groupIndex, int editIndex) {
		if (!groupContainer.getGroups().containsKey(String.valueOf(groupIndex))) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}
		
		ArrayList<Integer> editsToRemove = new ArrayList<>();
		editsToRemove.add(editIndex);
		groupContainer.getGroups().get(String.valueOf(groupIndex)).removeById(editsToRemove);
		
		groupContainer.getEditContainer().undo();
		updateView();
	}
	
	/**
	 * method that needs to be overriden from the interface but the textboxcontroller does not need to implement this method.
	 * @param int groupIndex
	 */
	@Override
	public void undoGroupEdits(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); // the textboxcontroller does not need to implement this method so we throw an exception (it shouldn't be called)
	}
	
	/**
	 * method that needs to be overriden from the interface but the textboxcontroller does not need to implement this method.
	 * @param int groupIndex
	 */
	@Override
	public void deleteRandomEdits(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); // the textboxcontroller does not need to implement this method so we throw an exception (it shouldn't be called)
	}
	
	/**
	 * method that needs to be overriden from the interface but the textboxcontroller does not need to implement this method.
	 * @param int groupIndex
	 */
	@Override
	public void deleteGroup(int groupIndex) {
		throw new UnsupportedOperationException("Not supported yet."); // the textboxcontroller does not need to implement this method so we throw an exception (it shouldn't be called)
	}
	
	/**
	 * method that needs to be overriden from the interface but the textboxcontroller does not need to implement this method.
	 * @param int groupIndex
	 */
	@Override
	public void deleteAllGroups() {
		throw new UnsupportedOperationException("Not supported yet."); // the textboxcontroller does not need to implement this method so we throw an exception (it shouldn't be called).
	}
	
	@Override
	/**
	 * update the text area, in other words, take the test from the editContainer and set the textboxview with it
	 */
	public void updateView() {
		String text = groupContainer.getEditContainer().asText();
		textBoxView.setText(text);
	}

}
