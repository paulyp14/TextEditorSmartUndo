package base.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

public class EditContainer {

    private Edit first;
    private Edit last;
    private int size;

    private Stack<Edit> mostRecent;

    public EditContainer() {
        this.first = null;
        this.last = null;
        this.size = 0;
        this.mostRecent = new Stack<>();
    }

    public void create(String text, int position, boolean isAddition) {
        Edit edit = new Edit(text, isAddition, position);
        Edit existingEdit = this.first;
        Edit previousEdit = null;

        if (existingEdit == null && position != 0) {
            System.out.println("WARNING: no existing edits and the first position is not 0");
        }

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
                        if (previousEdit != null) {
                            if (currentPosition == position) {
                                // just add in the middle
                                edit.insert(previousEdit, existingEdit);
                            } else {
                                // have to split the existingEdit
                                Edit nextEdit = existingEdit.split(position);
                                edit.insert(existingEdit, nextEdit);
                            }
                        } else {
                            if (position == 0) {
                                // just add it in front
                                edit.insertInFront(existingEdit);
                                this.first = edit;
                            }
                            else {
                                // split the first node and add it
                                Edit nextEdit = existingEdit.split(position);
                                edit.insert(existingEdit, nextEdit);
                            }
                        }
                    } else {
                        // find all edits that were affected, merge them into this one
                        Edit nextEdit = existingEdit;
                        if (position != currentPosition) {
                            nextEdit = existingEdit.split(position - existingEdit.getPosition());
                            previousEdit = existingEdit;
                        }
                        nextEdit = nextEdit.merge(text);
                        if (previousEdit == null)
                            edit.insertInFront(nextEdit);
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
        this.first.updatePosition();
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
        return String.join("\n", components);
    }
}