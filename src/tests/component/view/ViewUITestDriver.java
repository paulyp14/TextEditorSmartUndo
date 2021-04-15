package tests.component.view;

import javax.swing.DefaultListModel;

import base.view.*;
import base.*;

/**
 * Runs the driver that will test the UI interactivities.
 * 
 * @author Thanh Tung Nguyen
 */
public class ViewUITestDriver {

    private TextEditor editor;

    public static void main(String[] args) {
        ViewUITestDriver tester = new ViewUITestDriver();

        System.out.println("\n\nTesting on ViewUITestDriver \n");
        tester.startTest();
        System.out.println("\n\nEnd of Tests on ViewUITestDriver \n");
    }

    public ViewUITestDriver() {
        editor = new TextEditor();
    }

    private void startTest() {

        boolean result;

        System.out.println("Test creating new groups");
        System.out.println("---------------------");
        result = TestCreateNewGroup();
        System.out.println("---------------------");
        System.out.println("Test Passed: " + result + "\n");

        System.out.println("Test deleting edit groups");
        System.out.println("---------------------");
        result = TestDeleteNewGroup();
        System.out.println("---------------------");
        System.out.println("Test Passed: " + result + "\n");

        System.out.println("Test focused/expanded edit group");
        System.out.println("---------------------");
        result = TestOnExpandGroup();
        System.out.println("---------------------");
        System.out.println("Test Passed: " + result + "\n");

        System.out.println("Test collapse edit group");
        System.out.println("---------------------");
        result = TestOnCollapseGroup();
        System.out.println("---------------------");
        System.out.println("Test Passed: " + result + "\n");

        System.out.println("Test add new edit");
        System.out.println("---------------------");
        result = TestAddNewEdit();
        System.out.println("---------------------");
        System.out.println("Test Passed: " + result + "\n");

        System.out.println("Test undo edit");
        System.out.println("---------------------");
        result = TestUndoEdit();
        System.out.println("---------------------");
        System.out.println("Test Passed: " + result + "\n");

        System.out.println("Test undo edit");
        System.out.println("---------------------");
        result = TestUndoAllEdits();
        System.out.println("---------------------");
        System.out.println("Test Passed: " + result + "\n");
    }

    /**
     * To test:
     *  1. cannot be more than 7 edit groups
     */
    private boolean TestCreateNewGroup() {

        UndoPanelView undoPanelRef = editor.getUndoPanelView();

        System.out.println("Adding 7 new edit groups..");
        for (int i = 0; i < 7; i++) {
            undoPanelRef.createNewGroup();
        }
        System.out.println("Number of edit groups: " + undoPanelRef.getNumOfEditGroups());
        if (undoPanelRef.getNumOfEditGroups() != 7)
            return false;
        
        System.out.println("Attempt creating a new edit group above 7 (max)..");
        System.out.println("Number of edit groups: " + undoPanelRef.getNumOfEditGroups());
        boolean success = undoPanelRef.createNewGroup();
        if (success || undoPanelRef.getNumOfEditGroups() > 7)
            return false;

        return true;
    }

    /**
     * To test:
     *  1. can't delete with index out of range
     *  2. deleting a group will move index of groups below it to decrement
     */
    private boolean TestDeleteNewGroup() {

        UndoPanelView undoPanelRef = editor.getUndoPanelView();

        // repopulate if size is not 7
        while (undoPanelRef.getNumOfEditGroups() < 7)
            undoPanelRef.createNewGroup();

        System.out.println("Deleting 5th group out of the 7, expecting index of group 6 and 7 to re-adjust..");
        undoPanelRef.deleteEditGroup(4);
        
        System.out.println("Number of edit groups: " + undoPanelRef.getNumOfEditGroups());
        if (undoPanelRef.getNumOfEditGroups() != 6)
            return false;
        
        int group6Index = undoPanelRef.getEditGroupViews().get(4).getGroupIndex();
        int group7Index = undoPanelRef.getEditGroupViews().get(5).getGroupIndex();
        System.out.println("Group 6 index (expect 4): " + group6Index);
        System.out.println("Group 7 index (expect 5): " + group7Index);
        if (group6Index != 4 || group7Index != 5)
            return false;

        System.out.println("Attempt deleting at index -1..");
        if (undoPanelRef.deleteEditGroup(-1))   // returns true if success
            return false;

        System.out.println("Attempt deleting at index over group size..");
        if (undoPanelRef.deleteEditGroup(undoPanelRef.getNumOfEditGroups())) // returns true if success
            return false;

        return true;
    }

    /**
     * To test:
     *  1. expanding an edit group should change current focused group
     *  2. expanding with index outside of range should fail
     */
    private boolean TestOnExpandGroup() {

        UndoPanelView undoPanelRef = editor.getUndoPanelView();

        // make sure size is at least 1
        if (undoPanelRef.getNumOfEditGroups() < 1)
            undoPanelRef.createNewGroup();

        System.out.println("Changing edit group 1 as focus..");
        undoPanelRef.onExpandEditGroup(0); // focus group 1
        System.out.println("Focused index after expanding: " + undoPanelRef.getCurrentlyFocusedGroup());
        if (undoPanelRef.getCurrentlyFocusedGroup() != 0)
            return false;

        System.out.println("Attempt expand at index -1..");
        undoPanelRef.onExpandEditGroup(-1);
        if (undoPanelRef.getCurrentlyFocusedGroup() == -1)
            return false;

        System.out.println("Attempt expand at index above size..");
        undoPanelRef.onExpandEditGroup(undoPanelRef.getNumOfEditGroups());
        if (undoPanelRef.getCurrentlyFocusedGroup() == undoPanelRef.getNumOfEditGroups())
            return false;

        return true;
    }

    /**
     * To test:
     *  1. collapsing an edit group should reset focus to nothing (focused index = -1)
     */
    private boolean TestOnCollapseGroup() {

        UndoPanelView undoPanelRef = editor.getUndoPanelView();

        // make sure size is at least 1
        if (undoPanelRef.getNumOfEditGroups() < 1)
            undoPanelRef.createNewGroup();

        undoPanelRef.onExpandEditGroup(0); // focus group 1
        System.out.println("Current group index being focused: " + undoPanelRef.getCurrentlyFocusedGroup());
        System.out.println("Collapsing this group and should reset to -1..");
        undoPanelRef.onCollapseEditGroup(); // reset to -1
        System.out.println("Current group index being focused: " + undoPanelRef.getCurrentlyFocusedGroup());

        if (undoPanelRef.getCurrentlyFocusedGroup() != -1)
            return false;

        return true;
    }

    /**
     * To test:
     *  1. add new String elements and see content
     *  2. null and empty string checks
     */
    private boolean TestAddNewEdit() {

        UndoPanelView undoPanelRef = editor.getUndoPanelView();

        // make sure size is at least 1
        if (undoPanelRef.getNumOfEditGroups() < 1)
            undoPanelRef.createNewGroup();

        EditGroupView editGroupRef = editor.getUndoPanelView().getEditGroupViews().get(0);
        DefaultListModel<String> editList = editGroupRef.getListModel();
        String content = "";

        System.out.println("Adding these strings in sequence: I, am, an, edit, list..");
        editGroupRef.addNewEdit("I");
        editGroupRef.addNewEdit("am");
        editGroupRef.addNewEdit("an");
        editGroupRef.addNewEdit("edit");
        editGroupRef.addNewEdit("list");
        System.out.println("Edit list content: ");
        for (int i = 0; i < editList.size(); i++) {
            System.out.println(i + ": " + editList.get(i));
            content += (editList.get(i) + " ");
        }

        if (editList.size() != 5 && !content.equalsIgnoreCase("I am an edit list "))
            return false;

        System.out.println("Attempt input empty string..");
        if (editGroupRef.addNewEdit("")) // returns true if added successfully
            return false;
    
        System.out.println("Attempt input null..");
        if (editGroupRef.addNewEdit(null)) // returns true if added successfully
            return false;

        return true;
    }

    /**
     * To test:
     *  1. undoing edits and seeing content
     *  2. undo with index out of bounds should fail
     *  3. check if theres at least one edit to undo
     */
    private boolean TestUndoEdit() {

        UndoPanelView undoPanelRef = editor.getUndoPanelView();

        // make sure size is at least 1
        if (undoPanelRef.getNumOfEditGroups() < 1)
            undoPanelRef.createNewGroup();

        EditGroupView editGroupRef = editor.getUndoPanelView().getEditGroupViews().get(0);
        DefaultListModel<String> editList = editGroupRef.getListModel();
        String content = "";

        editList.clear();
        editGroupRef.addNewEdit("I'm");
        editGroupRef.addNewEdit("an");
        editGroupRef.addNewEdit("edit");
        editGroupRef.addNewEdit("list");
        for (int i = 0; i < editList.size(); i++) {
            content += (editList.get(i) + " ");
        }
        System.out.println("Edit list content: " + content);

        content = "";
        System.out.println("undoing the second and third edit..");
        editGroupRef.undoEdit(1);
        editGroupRef.undoEdit(2);
        for (int i = 0; i < editList.size(); i++) {
            content += (editList.get(i) + " ");
        }
        System.out.println("Edit list content: " + content);
        if (editList.size() != 2 && !content.equalsIgnoreCase("I'm list "))
            return false;

        System.out.println("Attempt undoing with out of bound index -1..");
        if (editGroupRef.undoEdit(-1)) // returns true if undo'd successfully
            return false;

        System.out.println("Attempt undoing with out of bound index above list size..");
        if (editGroupRef.undoEdit(editList.getSize())) // returns true if undo'd successfully
            return false;
    
        System.out.println("Attempt undoing with no edits..");
        editList.clear();
        if (editGroupRef.undoEdit(0)) // returns true if undo'd successfully
            return false;

        return true;
    }

    /**
     * To test:
     *  1. undoing all edits and seeing content
     *  3. check if theres at least one edit to undo
     */
    private boolean TestUndoAllEdits() {

        UndoPanelView undoPanelRef = editor.getUndoPanelView();

        // make sure size is at least 1
        if (undoPanelRef.getNumOfEditGroups() < 1)
            undoPanelRef.createNewGroup();

        EditGroupView editGroupRef = editor.getUndoPanelView().getEditGroupViews().get(0);
        DefaultListModel<String> editList = editGroupRef.getListModel();
        String content = "";

        editList.clear();
        editGroupRef.addNewEdit("I'm");
        editGroupRef.addNewEdit("an");
        editGroupRef.addNewEdit("edit");
        editGroupRef.addNewEdit("list");
        for (int i = 0; i < editList.size(); i++) {
            content += (editList.get(i) + " ");
        }
        System.out.println("Edit list content: " + content);

        content = "";
        System.out.println("undoing all edits..");
        editGroupRef.undoAllEdits();
        for (int i = 0; i < editList.size(); i++) {
            content += (editList.get(i) + " ");
        }
        System.out.println("Edit list content: " + content);
        if (editList.size() != 0 && !content.equalsIgnoreCase(" "))
            return false;

        System.out.println("Attempt undoing with no edits..");
        editList.clear();
        if (editGroupRef.undoAllEdits()) // returns true if undo'd successfully
            return false;

        return true;
    }

}