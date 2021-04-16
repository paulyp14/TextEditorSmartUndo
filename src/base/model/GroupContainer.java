package base.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class GroupContainer {

	private HashMap<String, EditGroup> groups;
	private EditGroup defaultGroup;
	private EditContainer editContainer;

	private static GroupContainer containerSingleton = null;
	
	private GroupContainer(EditContainer container) {
		//initializing the constructor
		this.groups = new HashMap<>();
		this.defaultGroup = new EditGroup("DEFAULT");
		this.editContainer = container;
	}

	/**
	 * Singleton access point
	 * @return GroupContainer, the existing (and only) GroupContainer
	 */
	public static GroupContainer getContainer() {
		// singleton access point
		if (null == containerSingleton) {
			containerSingleton = new GroupContainer(EditContainer.getContainer());
		}
		return containerSingleton;
	}

	/**
	 * Create a new user managed group of edits
	 * @param groupName String the name of the group (MUST BE UNIQUE)
	 */
	public void create(String groupName) {
		//creating a new EditGroup
		if (this.groups.containsKey(groupName)) {
			System.out.println("ERROR: group name already exists");
			// TODO this should be an exception
			return;
		}
		this.groups.put(groupName, new EditGroup(groupName));
	}

	/**
	 * Remove a user managed group of edits, without deleting the edits
	 * (put any edits in the group back in the defaultGroup)
	 * @param groupName String
	 */
	public void removeGroup(String groupName) {
		/*
			USER WANTS TO REMOVE THE GROUP, BUT NOT THE EDITS IN THE GROUP
		 */
		//removing an EditGroup (keep the edits)
		if (!this.groups.containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}
		// remove the group
		EditGroup group = this.groups.remove(groupName);
		// transfer the edits back to the default group
		this.defaultGroup.add(group.getEdits());
	}

	/**
	 * Remove a user managed group of edits, and delete any edits that exist in the group
	 * @param groupName String
	 */
	public void removeAndDeleteGroup(String groupName) {
		/*
			USER WANTS TO REMOVE THE GROUP, AND DELETE THE EDITS IN THE GROUP
		 */
		if (!this.groups.containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}
		// remove the group
		EditGroup group = this.groups.remove(groupName);
		// set the edit to DELETED so that it no longer can appear in the history
		for (EditView e: group.getEdits()) {
			this.editContainer.delete(e.getId());
		}
	}

	/**
	 * Undo all edits in a group
	 * @param groupName String
	 */
	public void undoGroup(String groupName) {
		/*
			USER WANTS TO UNDO ALL EDITS IN A SPECIFIC GROUP
		 */
		if (!this.groups.containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}
		// remove the group
		EditGroup group = this.groups.remove(groupName);
		ArrayList<Integer> ids = new ArrayList<>();
		for (EditView e: group.getEdits()) {
			ids.add(e.getId());
		}
		this.editContainer.undoByIds(ids);
	}

	/**
	 * Add edits to a group
	 * @param groupName String the group to add edits to
	 * @param editIds list of EditIds to add to the group
	 */
	public void add(String groupName, ArrayList<Integer> editIds) {
		// adding a specified list of Edits to the specified EditGroup
		if (!this.groups.containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}

		ArrayList<EditView> editsToAdd = new ArrayList<>();
		for (EditView view: this.editContainer.view()) {
			if (editIds.contains(view.getId())) {
				editsToAdd.add(view);
			}
		}

		this.defaultGroup.removeById(editIds);

		this.groups.get(groupName).add(editsToAdd);
	}

	/**
	 * Add a single edit to a group
	 * @param groupName String
	 * @param editId int
	 */
	public void add(String groupName, int editId) {
		this.add(groupName, new ArrayList<>(Collections.singletonList(editId)));
	}

	/**
	 * Undo specific edits in a specific group
	 * @param groupName String
	 * @param editIds the list of edits to undo
	 */
	public void undoEditsInGroup(String groupName, ArrayList<Integer> editIds) {
		if (!this.groups.containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return;
		}

		this.editContainer.undoByIds(editIds);
		this.update();
	}

	/**
	 * Undo the edits that are in the defualt group
	 * @param editIds
	 */
	public void undoEditsInDefaultGroup(ArrayList<Integer> editIds) {
		this.editContainer.undoByIds(editIds);
		this.update();
	}

	/**
	 * Makes sure specific group is up to date
	 * @param groupName
	 * @param editGroup
	 * @param existingIds
	 */
	private void syncEditGroup(String groupName, EditGroup editGroup, ArrayList<Integer> existingIds) {
		// remove any edits that are no longer valid
		ArrayList<Integer> editsToRemove = new ArrayList<>();
		for (EditView editInGroup: editGroup.getEdits()) {
			if (!existingIds.contains(editInGroup.getId())) {
				editsToRemove.add(editInGroup.getId());
			}
		}
		editGroup.removeById(editsToRemove);
	}

	/**
	 * makes sure the default group is up to date
	 * @param edits
	 */
	private void updateDefaultGroup(ArrayList<EditView> edits) {
		ArrayList<Integer> idsInDefault = new ArrayList<>();
		ArrayList<EditView> missing = new ArrayList<>();
		this.defaultGroup.getEdits().forEach((editView) -> idsInDefault.add(editView.getId()));
		for (EditView edit: edits) {
			if (!idsInDefault.contains(edit.getId())) {
				missing.add(edit);
			}
		}
		this.defaultGroup.add(missing);
	}

	/**
	 * Updates all groups if the editContainer was updated so that they accurately reflect the state
	 */
	public void update() {
		if (this.editContainer.isUpdated()) {
			// if an edit was deleted/undone/removed
			// need to update all existing groups to make sure they aren't out of date
			ArrayList<Integer> existingIds = new ArrayList<>();

			ArrayList<EditView> availableEdits = this.editContainer.view();
			for (EditView view: availableEdits) {
				existingIds.add(view.getId());
			}
			// sync all user managed edit groups
			this.groups.forEach((groupName, editGroup) -> this.syncEditGroup(groupName, editGroup, existingIds));
			// make sure the default group is also up to date
			this.syncEditGroup(this.defaultGroup.getGroupName(), this.defaultGroup, existingIds);

			// also add any new edits to the defaultGroup
			this.updateDefaultGroup(availableEdits);
			this.editContainer.markSeen();
		}
	}

	/**
	 *
	 * @return string representation of the GroupContainer
	 */
	public String stringRepr() {
		int maxLen = 60;
		ArrayList<String> components = new ArrayList<>();
		ArrayList<String> finalRepr = new ArrayList<>();
		this.groups.forEach((groupName, group) -> components.addAll(group.arrayRepr(maxLen)));
		components.addAll(this.defaultGroup.arrayRepr(maxLen));
		int finalSize = components.get(0).length() + 4;
		StringBuilder separator = new StringBuilder();
		for (int i = 0; i < finalSize; i++) {
			separator.append("=");
		}
		finalRepr.add(separator.toString());
		StringBuilder nameBuilder = new StringBuilder("| GROUP CONTAINER");
		int curSize = nameBuilder.length();
		for (int i = 0; i < finalSize - curSize - 1; i++) {
			nameBuilder.append(" ");
		}
		nameBuilder.append("|");
		finalRepr.add(nameBuilder.toString());
		for (String s: components) {
			finalRepr.add("| " + s + " |");
		}
		finalRepr.add(separator.toString());
		return String.join("\n", finalRepr);
	}

	/**
	 * Get the representative EditViews for a specific group
	 * @param groupName
	 * @return
	 */
	public ArrayList<EditView> viewEditsInGroup(String groupName) {
		if (!this.groups.containsKey(groupName)) {
			System.out.println("ERROR: this group name does not exist");
			// TODO this should be an exception
			return null;
		}
		return this.groups.get(groupName).getEdits();
	}

	/**
	 * Get the representative EditViews for the default group
	 * @return
	 */
	public ArrayList<EditView> viewEditsInDefaultGroup() {
		return this.defaultGroup.getEdits();
	}
	
	/**
	 * Getter for the groups attribute
	 * @return groups attribute
	 */
	public HashMap<String, EditGroup> getGroups() {
		return this.groups;
	}
	
	/**
	 * Getter for the editContainer attribute
	 * @return editContainer attribute
	 */
	public EditContainer getEditContainer() {
		return this.editContainer;
	}
}
