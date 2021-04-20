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
    private UndoPanelView undoPanelView;

    /**
	 * contructor
	 * @param TextBoxView our textboxview view
	 * @param GroupContainer our groupcontainer model
	 */
    public TextBoxController(TextBoxView tbv, UndoPanelView upv) {
    	textBoxView = tbv;
    	groupContainer = GroupContainer.getContainer();
    	undoPanelView = upv;
    }
    
    /**
	 * method that needs to be overriden from the interface but the textboxcontroller does not need to implement this method.
	 * @param int groupIndex
	 * @param String text
	 * @param int editIndex
	 * @param booolean isAddition
	 */
	@Override
	public void addNewEdit(int groupIndex, String text,int editIndex, boolean isAddition) {
		throw new UnsupportedOperationException("Not supported yet."); // the textboxcontroller does not need to implement this method so we throw an exception (it shouldn't be called)
	}
	
	
	/**
	 * reading from the textboxview and creating newedits.
	 * @param String text
	 * @param int position
	 * @param booolean isAddition
	 */
	public void add(String text, int position, boolean isAddition) {
		String groupName;
		groupContainer.getEditContainer().create(text, position, isAddition);
		int newItemId = groupContainer.getEditContainer().mostRecentEdit().getId();
		
		if (undoPanelView.getCurrentlyFocusedGroup() != -1)
		{
			groupName = String.valueOf(undoPanelView.getCurrentlyFocusedGroup());
			groupContainer.add(groupName, newItemId);
		}
		
		groupContainer.update();
	}
	/**
	 * reading from the textboxview and creating newedits.
	 * @param String text
	 * @param int position
	 * @param booolean isAddition
	 */
	public void undo() {
		if (groupContainer.getEditContainer().mostRecentEdit() == null)
			return;

		groupContainer.getEditContainer().undo();
		groupContainer.update();
		updateView();
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
		throw new UnsupportedOperationException("Not supported yet."); // the textboxcontroller does not need to implement this method so we throw an exception (it shouldn't be called)
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
