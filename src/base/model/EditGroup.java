package base.model;

import com.sun.jdi.ArrayReference;

import java.util.ArrayList;

public class EditGroup {
	private ArrayList<EditView> editsInGroup;
	private String name;
	
	public EditGroup(String name) {
		//initializing constructor
		this.editsInGroup = new ArrayList<>();
		this.name = name;
	}

	/**
	 * Set the groupName attribute
	 * @param groupName
	 */
	public void setGroupName(String groupName) { //set name for the group of edits
		this.name = groupName;
	}

	/**
	 * Get the groupName
	 * @return String
	 */
	public String getGroupName() { //get the name of the edit group
		return name;
	}

	/**
	 * Determine how many edits are in a group
	 * @return int
	 */
	public int getGroupSize() {
		return this.editsInGroup.size();
	}

	/**
	 * Add EditViews to this group
	 * @param newEdits list of the EditViews to add
	 */
	public void add(ArrayList<EditView> newEdits) { //adding a list of edits to the group
		this.editsInGroup.addAll(newEdits);
	}

	/**
	 * Remove edits from this group based on IDs
	 * @param editIds list of the edit IDs to remove
	 */
	public void removeById(ArrayList<Integer> editIds) { //remove list of edits out of the group
		ArrayList<Integer> editIdxs = new ArrayList<>();
		int curSize = 0;
		for (int i = 0; i < this.editsInGroup.size(); i++) {
			EditView editView = this.editsInGroup.get(i);
			if (editIds.contains(editView.getId())) {
				editIdxs.add(i - curSize);
				curSize++;
			}
		}
		for (int i: editIdxs) {
			this.editsInGroup.remove(i);
		}
	}

	/**
	 * See the Edits that are in this group
	 * @return list of the EditViews in the group
	 */
	public ArrayList<EditView> getEdits() {
		return this.editsInGroup;
	}

	/**
	 * array representation of the Group, used by interactiveModelTestDriver
	 * @param maxLen
	 * @return
	 */
	public ArrayList<String> arrayRepr(int maxLen) {
		ArrayList<String> asIndividualArrayList = new ArrayList<>();
		this.editsInGroup.forEach(edit -> asIndividualArrayList.add(edit.forInteractiveModelTest(maxLen)));
		StringBuilder theName = new StringBuilder((this.name.length() > maxLen - 4) ? this.name.substring(0, maxLen - 4) + "...." : this.name);
		ArrayList<String> asArrayList = new ArrayList<>();
		StringBuilder separator = new StringBuilder(" ");
		int totalLength = maxLen + 12;
		for (int i = 0; i < totalLength - 2; i++) {
			separator.append("-");
		}
		separator.append(" ");
		int nameLength = theName.length();
		asArrayList.add(separator.toString());
		for (int i = 0; i < (totalLength - 3) - nameLength; i++) {
			theName.append(" ");
		}
		asArrayList.add("| " + theName.toString() + "|");
		asArrayList.add(separator.toString());
		for (String s: asIndividualArrayList) {
			int lineLength = s.length();
			StringBuilder sb = new StringBuilder(s);
			for (int i = 0; i < (totalLength - 3) - lineLength; i++) {
				sb.append(" ");
			}
			asArrayList.add("| " + sb.toString() + "|");
		}
		asArrayList.add(separator.toString());
		return asArrayList;
	}

}
