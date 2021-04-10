package base.model;


public class GroupView {
	String groupName;
	int groupSize;
	String edits;
	
	public GroupView(String groupName, String edits, int groupSize) { //initializing the constructor
		this.groupName = groupName; 
		this.edits = edits;
		this.groupSize = groupSize;
	}
	
	public String getGroupName() {
		return this.groupName;
	}
	
	public String getEdits() {
		return this.edits;
	}
	
	public int getGroupSize() {
		return this.groupSize;
	}
	
}
