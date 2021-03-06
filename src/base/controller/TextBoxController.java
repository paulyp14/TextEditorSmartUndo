package base.controller;

import base.model.*;
import base.view.*;

/**
 * 
 * @author andy nguyen
 */
public class TextBoxController implements ControllerInterface {
	
	// attributes, the TextBoxController is connected to the GroupContainer and the TextBoxView, 
	// also need to be connected to the undopanelcontroller, to get the currently focused group.
	private TextBoxView textBoxView;
    private GroupContainer groupContainer;
    private UndoPanelController undoPanelController;

    /**
	 * contructor
	 * @param TextBoxView our textboxview view
	 * @param UndoPanelComtroler our undopanelcontroller
	 */
    public TextBoxController(TextBoxView tbv, UndoPanelController upc) {
    	textBoxView = tbv;
    	groupContainer = GroupContainer.getContainer();
    	undoPanelController = upc;
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
		// String groupName; // to store the currently focused group name as string
		
		// //create the edit in the editcontainer.
		// groupContainer.getEditContainer().create(text, position, isAddition);
		// int newItemId = groupContainer.getEditContainer().mostRecentEdit().getId(); // store the current id of the newly created edit.
		
		// if the undopanel view is focused on a group, create an edit in that group
		int focusedGroupIndex = undoPanelController.getUndoPanelView().getCurrentlyFocusedGroup();
		if (focusedGroupIndex != -1)
		{
			// groupName = String.valueOf(focusedGroupIndex);
			// groupContainer.add(groupName, newItemId);
			undoPanelController.addNewEdit(focusedGroupIndex, text, position, isAddition);
		}
		
		groupContainer.update();
	}
	/**
	 * reading from the textboxview a ctrl+z and undoing.
	 */
	public void undo() {
		if (groupContainer.getEditContainer().mostRecentEdit() == null) // if there are no edits, do nothing.
			return;

		groupContainer.getEditContainer().undo(); // call the undo() in the editcontainer to undo the last operation
		groupContainer.update(); // update the groupcontainer
		updateView(); // update the textboxview

		int focusedGroupIndex = undoPanelController.getUndoPanelView().getCurrentlyFocusedGroup();
		if (focusedGroupIndex != -1)
		{
			int lastItem = undoPanelController.getUndoPanelView().getEditGroupViews().get(focusedGroupIndex).getListModel().getSize() - 1;
			undoPanelController.undoEdit(focusedGroupIndex, lastItem);
		}
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
	 * method that needs to be overriden from the interface but the textboxcontroller does not need to implement this method.
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
		textBoxView.updateStr(); // update the str attribute to the text in the textarea
	}
	
	/**
     * Displays the content of both the editcontainer and groupcontainer on the console. (for testing)
     */
	public void StringRepr() {
		System.out.println(groupContainer.stringRepr());
		System.out.println(groupContainer.getEditContainer().stringRepr());
	}

}
