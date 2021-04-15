package base.controller;

import base.view.MenuBarView;

/**
 * @author Duc Minh Bui, Thanh Tung Nguyen
 */
public class MenuBarController implements ControllerInterface {
	
	private MenuBarView menuBarView;
    private UndoPanelController undoPanelController;

    public MenuBarController(MenuBarView menuBarView, UndoPanelController undoPanel)
    {
		this.menuBarView = menuBarView;	
		this.undoPanelController = undoPanel;
    }

	@Override
	public void addNewGroup(int groupIndex) {
		undoPanelController.addNewGroup(groupIndex);
	}

	@Override
	public void deleteRandomEdits(int groupIndex) {
		undoPanelController.deleteRandomEdits(groupIndex);
	}

	@Override
	public void deleteGroup(int groupIndex) {
		undoPanelController.deleteGroup(groupIndex);
	}

	@Override
	public void deleteAllGroups() {
		undoPanelController.deleteAllGroups();
	}

	@Override
	public void addNewEdit(int groupIndex, String text, int editIndex, boolean isAddition) { }
	
	@Override
	public void undoEdit(int groupIndex, int editIndex) { }

	@Override
	public void undoGroupEdits(int groupIndex) { }

	@Override
	public void updateView() { }

	public UndoPanelController getUndoPanelController() {
		return undoPanelController;
	}
}
