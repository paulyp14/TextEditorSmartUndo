package base.controller;

/**
 * ControllerInterface, abstracts the Controller component
 * @author Ian Ngaruiya Njoroge
 */
public interface ControllerInterface
{
	public void addNewEdit(int groupIndex, String text,int editIndex, boolean isAddition);
	public void addNewGroup(int groupIndex);
	
	public void undoEdit(int groupIndex, int editIndex);
	public void undoGroupEdits(int groupIndex);
	
	public void deleteRandomEdits(int groupIndex);
	public void deleteGroup(int groupIndex);
	public void deleteAllGroups();
	
	public void updateView();
}
