import java.util.ArrayList;

public class EditGroup{
	private ArrayList<Edit> edits;
	private String name;
	private static String getEdits;
	
	public EditGroup() { //initializing constructor
		this.edits = null;
		this.name = "";
	}
	
	public void setGroupName(String groupName) { //set name for the group of edits
		this.name = groupName;
	}
	
	public String getGroupName() { //get the name of the edit group
		return name;
	}
	
	public int getGroupSize() {
		return edits.size();
	}
	
	public void setStringEdits(ArrayList<Edit> edits) { //save all edits of the group in a string called getEdits
	    for(int i = 0; i < edits.size(); i++){
	    	getEdits += edits.get(i).toString() + "\t";
	    }
	}
	
	public String getStringEdits() { //return all the edits of the group in form of string
		return getEdits; 
	}
	
	public void add(ArrayList<Edit> newEdits) { //adding a list of edits to the group
		int count = 0;
		
		if (newEdits == null) {
			System.out.print("List provided is empty, nothing is added.");
		}
		
		else {
			for (int j=0; j < newEdits.size(); j++) {
				for (int i=0; i<edits.size(); i++) {
					if (edits.get(i).getId() == newEdits.get(j).getId()) {
						count++;
					}
				}
				if (count >= 1) {
					count = 0; //reset count
					System.out.print("Edit already exist");
				}
				
				else if (count == 0) {
					edits.add(newEdits.get(j));
				}
			}
		}
	}
	
	public void removeFromId(ArrayList<Edit> removeEdits) { //remove list of edits out of the group
		if (edits.size() == 0) {
			System.out.print("The list is empty, nothing to be removed.");
		}
		
		else if (edits.size() < 0) {
			System.out.print("Illegal list.");
		}
		
		else {
			for(int j = 0; j < edits.size(); j++) {
				for (int i = 0; i < removeEdits.size(); i++) {
					 if (edits.get(j).getId() == removeEdits.get(i).getId()) {
					    System.out.print(edits.get(j).getId() + " has been removed from the group.");
					    edits.remove(edits.get(j)); //remove the element
					 }
				}
			}
		}
	}

	
	
}
