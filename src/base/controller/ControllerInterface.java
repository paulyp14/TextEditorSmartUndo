package base.controller;

public interface ControllerInterface
{
	public void addNewEdit(String text,int editIndex, boolean isAddition );
	public void addNewGroup(int groupIndex, int editIndex);
	
	public void undoEdit(int groupIndex, int editIndex);
	public void undoGroupEdits(int groupIndex);
	
	public void deleteRandomEdits(int groupIndex);
	public void deleteGroup(int groupIndex);
	public void deleteAllGroups();
	
	public void updateView();
}
