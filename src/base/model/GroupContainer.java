import java.util.ArrayList;

public class GroupContainer {
	private ArrayList<EditGroup> groups;
	private ArrayList<Edit> defaultGroup;
	private EditContainer editContainer;
	
	public GroupContainer() { //initializing the constructor
		this.groups = null;
		this.defaultGroup = null;
		this.editContainer = null;
	}
	
	public void create(String groupName) { //creating a new EditGroup
		int count = 0;
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getGroupName().equals(groupName)) {
				count++;
			}
		}
		if (count > 0) {
			System.out.print("Name already exists, please choose another name.");
		}
		else {
			EditGroup newGroup = new EditGroup();
			newGroup.setGroupName(groupName);
			groups.add(newGroup);
		}
	}
	
	public void delete(String groupName) { //removing an EditGroup (keep the edits)
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getGroupName().equals(groupName)) {
				groups.get(i).setGroupName(null);
			}
		}
	}
	
	public void remove(String groupName, ArrayList<Edit> newList) { // removing a specified list of Edits from a specified EditGroup
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getGroupName().equals(groupName)) {
				groups.get(i).removeFromId(newList);
			}
		}
	}
	
	public void add(String groupName, ArrayList<Edit> newList) { // adding a specified list of Edits to the specified EditGroup
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getGroupName().equals(groupName)) {
				groups.get(i).add(newList);
			}
		}
	}
	
	public String getEdits(String groupName) { //function to get all edits in a group
		String edits = "";

		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).getGroupName().equals(groupName)) {
				edits += groups.get(i).getStringEdits();
			}
		}
		return edits;
	}
	
	
	public ArrayList<GroupView> view(){
		ArrayList<GroupView> groupViews = new ArrayList<GroupView>();
		for (int i = 0; i < groups.size(); i++) {
			groupViews.add(new GroupView(groups.get(i).getGroupName(), getEdits(groups.get(i).getGroupName()), groups.get(i).getGroupSize()));
		}
		return groupViews;
	}


}
