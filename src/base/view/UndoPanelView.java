import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * 
 */
public class UndoPanelView extends JPanel {
    
    //private UndoPanelController controller
    private ArrayList<EditGroupView> editGroupViews;

    private final int SIZE_WIDTH = 200;

    /**
     * Default constructor
     */
    UndoPanelView() {}

    UndoPanelView(int height) {

        this.setPreferredSize(new Dimension(SIZE_WIDTH, height));

        EditGroupView editGroup1 = new EditGroupView(this, 0, SIZE_WIDTH);
        EditGroupView editGroup2 = new EditGroupView(this, 0, SIZE_WIDTH);
        EditGroupView editGroup3 = new EditGroupView(this, 0, SIZE_WIDTH);
        add(editGroup1);
        add(editGroup2);
        add(editGroup3);
        remove(editGroup2);
    }

    //UndoPanelView(UndoPanelController controller) {
    //    this.controller = controller
    //}


    //**********    PUBLIC  METHODS   **********//

    /**
     * Delete an edit group on UndoPanel given an index. 
     * Will remove UI component and remove edit from text box.
     * 
     * @param index
     * @return true if delete was successful
     */
    public boolean deleteEditGroup(int index) {
        return false;
    }


    //**********    PRIVATE METHODS   **********//

    /**
     * Add a new edit group on UndoPanel and place at bottom.
     * Creates a new container in Model.
     * 
     * @return true if delete was successful
     */
    private boolean createNewGroup() {
        return false;
    }

    /**
     * Minimizes the UndoPanel section
     */
    private void minimizeUndoPanel() {

    }

    /**
     * Re-open/maximize the UndoPanel section
     */
    private void maximizeUndoPanel() {
        
    }

    
}
