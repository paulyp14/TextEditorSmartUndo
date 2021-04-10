package base.model;

import java.util.*;

public class EditContainer {

    private Edit first;
    private Edit last;
    private int size;

    private boolean updated;

    private Stack<Edit> mostRecent;

    public EditContainer() {
        this.first = null;
        this.last = null;
        this.size = 0;
        this.mostRecent = new Stack<>();
    }

    public boolean isUpdated() {
        return this.updated;
    }

    public void markSeen() {
        this.updated = false;
    }

    public int size() {
        return this.size;
    }

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

    public String asText() {
        Edit edit = this.first;
        StringBuilder sb = new StringBuilder();
        while (edit != null) {
            sb.append(edit.asText());
            edit = edit.getNext();
        }
        return sb.toString();
    }

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

    public String stringRepr() {
        String separator = "============================";
        ArrayList<String> editStringReprs = new ArrayList<>();
        Edit edit = this.first;
        while (edit != null) {
            editStringReprs.add(edit.stringRepr());
            edit = edit.getNext();
        }
        String editString = String.join(",\n", editStringReprs);
        String[] components = {
                separator,
                "EditContainer",
                "  Size: " + this.size,
                "  Represents text: \n",
                this.asText(),
                "\n",
                "  Contained edits:",
                editString,
                separator
        };
        // TODO SOME FORMATTING
        return String.join("\n", components);
    }

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

    public Edit mostRecentEdit() {
        return this.mostRecent.peek();
    }

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

    public void undoById(int editId) {
        this.undoByIdWithoutSync(editId);
        this.sync();
    }

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

    public void undoByIds(List<Integer> editIds) {
        for (int editId: editIds) {
            this.undoByIdWithoutSync(editId);
        }
        this.sync();
    }
}
