package base.model;

import java.util.*;

public class EditContainer {

    private Edit first;
    private Edit last;
    private int size;

    private boolean updated;

    private Stack<Edit> mostRecent;

    private static EditContainer containerSingleton = null;

    private EditContainer() {
        this.first = null;
        this.last = null;
        this.size = 0;
        this.mostRecent = new Stack<>();
    }

    /**
     * Singleton access point
     * @return EditContainer, the only EditContainer
     */
    public static EditContainer getContainer() {
        if (containerSingleton == null) {
            containerSingleton = new EditContainer();
        }
        return containerSingleton;
    }

    /**
     * Determine the editContaienr was asked to perform an operation on its Edits
     * @return boolean
     */
    public boolean isUpdated() {
        return this.updated;
    }

    /**
     * Let the editContainer know that you have looked at it's updated Edits (used by GroupContainer)
     */
    public void markSeen() {
        this.updated = false;
    }

    /**
     * Determine the number of edits in the EditContainer
     * @return int
     */
    public int size() {
        return this.size;
    }

    /**
     * Used to create an edit
     * @param text String the affected text, either the text the user added or the text the user deleted
     * @param position int the offset of the affected text from the start of the full text
     * @param isAddition boolean flag to indicate whether the affected text was inserted or removed
     */
    public void create(String text, int position, boolean isAddition) {
        this.updated = true;

        Edit existingEdit = this.first;
        Edit previousEdit = null;

        if (existingEdit == null && position != 0 && position != -1) {
            System.out.println("WARNING: no existing edits and the first position is not 0");
        }
        // use -1 to insert at the edit
        if (position == -1) {
            int totalSize = 0;
            while (existingEdit != null) {
                totalSize += existingEdit.getText().length();
                existingEdit = existingEdit.getNext();
            }
            position = totalSize;
            existingEdit = this.first;
        }
        // Create the edit object
        Edit edit = new Edit(text, isAddition, position);

        int currentPosition;
        int nextPosition = 0;

        boolean editMade = false;
        if (this.size != 0) {
            while (existingEdit != null) {
                currentPosition = existingEdit.getPosition();
                nextPosition = currentPosition + existingEdit.length();

                if (position == currentPosition || position < nextPosition) {
                    editMade = true;
                    if (isAddition) {
                        /*
                            USER HAS ADDED TEXT
                         */
                        if (previousEdit != null) {
                            // ADDING AT SOME POSITION IN THE LIST
                            if (currentPosition == position) {
                                // IT CAN SIMPLY BE INSERTED BETWEEN TWP EXISTING EDITS
                                edit.insert(previousEdit, existingEdit);
                            } else {
                                // IT IS IN THE MIDDLE OF AN EXISTING EDIT
                                Edit nextEdit = existingEdit.split(position);
                                edit.insert(existingEdit, nextEdit);
                            }
                        } else {
                            // ADDING AT THE FRONT
                            if (position == 0) {
                                // just add it in front
                                edit.insertInFront(existingEdit);
                                this.first = edit;
                            }
                            else {
                                // ADDING INTO THE FIRST NODE
                                // split the first node and add it
                                Edit nextEdit = existingEdit.split(position);
                                edit.insert(existingEdit, nextEdit);
                            }
                        }
                    } else {
                        /*
                            USER HAS REMOVED TEXT
                         */
                        // find all edits that were affected, merge them into this one
                        // ie user might have deleted text spanning across multiple nodes
                        Edit nextEdit = existingEdit;
                        if (position != currentPosition) {
                            // edit starts in the middle of an edit
                            nextEdit = existingEdit.split(position - existingEdit.getPosition());
                            previousEdit = existingEdit;
                        }
                        // look into the nextEdit, and all subsequent edits, until the end of the text is found
                        nextEdit = nextEdit.merge(text);
                        if (previousEdit == null) {
                            edit.insertInFront(nextEdit);
                            this.first = edit;
                        }
                        else if (nextEdit == null) {
                            edit.insertBehind(previousEdit);
                        }
                        else {
                            edit.insert(previousEdit, nextEdit);
                        }
                    }
                    break;
                }
                previousEdit = existingEdit;
                existingEdit = existingEdit.getNext();
            }
        }
        else {
            editMade = true;
            this.first = edit;
        }

        if (!editMade && (position != nextPosition)) {
            System.out.println("WARNING: no edit made the edit is not being made at the end of the last existing edit");
        }

        if (!editMade) {
            edit.insertBehind(previousEdit);
        }
        // sync the stack
        this.sync();
        // push the new edit to the top
        this.mostRecent.push(edit);
    }

    /**
     * Updates the underlying edits and the stack to make sure the current state is accurately reflected in all components
     */
    public void sync() {
        // guarantee the right positions
        if (this.first != null) {
            this.first.updatePosition();
        }
        HashMap<Integer, Integer> existingKeys = new HashMap<>();
        Edit current = this.first;
        this.size = 0;
        // collect the ids
        while (current != null) {
            this.size++;
            existingKeys.put(current.getId(), 1);
            current = current.getNext();
        }
        // cycle through the mostRecent to validate that the mostRecent ones are still in the list
        Stack<Edit> tempStack = new Stack<>();
        while (!this.mostRecent.empty()) {
            Edit edit = this.mostRecent.pop();
            if (existingKeys.containsKey(edit.getId())) {
                tempStack.push(edit);
            }
        }
        // put existing ones back on the mostRecent stack
        while (!tempStack.empty()) {
            this.mostRecent.push(tempStack.pop());
        }
    }

    /**
     * The representative FULL TEXT of the textbox as stored by the EditContainer
     * @return String
     */
    public String asText() {
        Edit edit = this.first;
        StringBuilder sb = new StringBuilder();
        while (edit != null) {
            sb.append(edit.asText());
            edit = edit.getNext();
        }
        return sb.toString();
    }

    /**
     * Mark an edit for removal (indicate that the user is no longer interested in it, does not want to see it)
     * @param editId int -- the id of the edit to hide
     */
    public void delete(int editId) {
        this.updated = true;

        Edit edit = this.first;
        boolean deleted = false;
        while (edit != null) {
            if (edit.getId() == editId) {
                deleted = true;
                edit.delete();
                break;
            }
            edit = edit.getNext();
        }
        if (!deleted) {
            System.out.println("ERROR: Id " + editId + "does not exist");
        }
        this.first.mergeDeleted();
        this.sync();
    }

    /**
     * String representation
     * @return String the editContainer as a string
     */
    public String stringRepr() {
        String separator = "============================";
        ArrayList<String> editStringReprs = new ArrayList<>();
        Edit edit = this.first;
        while (edit != null) {
            editStringReprs.add(edit.stringRepr());
            edit = edit.getNext();
        }
        String editString = String.join(",\n", editStringReprs);

        String stackLine = "  Stack contents: ";
        if (!this.mostRecent.empty()) {
            Stack<Edit> tempStack = new Stack<>();
            StringBuilder stackRepr = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                tempStack.push(this.mostRecent.pop());
                if (this.mostRecent.empty()) {
                    break;
                }
            }
            String end = (this.mostRecent.empty()) ? "" : "...";
            int counter = 0;
            while(!tempStack.empty()) {
                if (counter > 0) {
                    stackRepr.append(", ");
                }
                stackRepr.append(tempStack.peek().getId());
                this.mostRecent.push(tempStack.pop());
                counter++;
            }
            stackLine += end + stackRepr.toString();
        } else {
            stackLine += "EMPTY";
        }

        String[] components = {
                separator,
                "EditContainer",
                "  Size: " + this.size,
                stackLine,
                "  Represents text: \n",
                this.asText(),
                "\n",
                "  Contained edits:",
                editString,
                separator
        };
        return String.join("\n", components);
    }

    /**
     * Get representative EditViews of entire EditContainer
     * @return list of EditViews
     */
    public ArrayList<EditView> view() {
        ArrayList<EditView> views = new ArrayList<>();
        Edit edit = this.first;
        while (edit != null) {
            if (!edit.isDeleted()) {
                views.add(EditView.createFromEdit(edit));
            }
            edit = edit.getNext();
        }
        return views;
    }

    /**
     * Get representative EditViews for a subset of the edits
     * @param editIds -- list of the ids of the subset of interest
     * @return list of EditViews matching the ids
     */
    public ArrayList<EditView> view(List<Integer> editIds) {
        ArrayList<EditView> views = new ArrayList<>();
        Edit edit = this.first;
        while (edit != null) {
            if (editIds.contains(edit.getId())) {
                views.add(EditView.createFromEdit(edit));
            }
            edit = edit.getNext();
        }
        return views;
    }

    /**
     * peek at the most recent edit
     * @return the most recent Edit on the top of the stack
     */
    public Edit mostRecentEdit() {
        return this.mostRecent.peek();
    }

    /**
     * Undo the most recent edit
     */
    public void undo() {
        this.updated = true;
        if (!this.mostRecent.empty()) {
            Edit edit = this.mostRecent.pop();
            if (edit.getId() == this.first.getId()) {
                this.first = this.first.getNext();
            }
            edit.undo();
            this.sync();
        }
    }

    /**
     * Undo a single edit based on its id
     * @param editId int
     */
    public void undoById(int editId) {
        this.undoByIdWithoutSync(editId);
        this.sync();
    }

    /**
     * undo a single edit without calling the sync function
     * @param editId the edit to undo
     */
    private void undoByIdWithoutSync(int editId) {
        this.updated = true;
        Edit edit = this.first;
        while (edit != null) {
            if (edit.getId() == editId) {
                if (edit.getId() == this.first.getId() && edit.isEditAnAddition()) {
                    this.first = this.first.getNext();
                }
                edit.undo();
                break;
            }
            edit = edit.getNext();
        }
    }

    /**
     * undo a list of edits
     * @param editIds list of editIds to undo
     */
    public void undoByIds(List<Integer> editIds) {
        for (int editId: editIds) {
            this.undoByIdWithoutSync(editId);
        }
        this.sync();
    }

    /**
     * Get rid of all edits
     */
    public void empty() {
        this.first = null;
        this.last = null;
        this.sync();
    }
}
