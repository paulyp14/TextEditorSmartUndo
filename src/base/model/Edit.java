package base.model;

public class Edit {

    private static int nextId = 0;
    private int id;
    private String text;
    private int position;
    private Edit next;
    private Edit previous;

    private boolean deleted;
    private boolean isAddition;

    /*
        CONSTRUCTORS
     */

    public Edit(String text) {
        this(text, true);
    }

    public Edit(String text, boolean isAddition) {
        this(text, isAddition, 0);
    }

    public Edit(String text, boolean isAddition, int position) {
        this(text, isAddition, position, null, null);
    }

    public Edit(String text, boolean isAddition, int position, Edit previous, Edit next) {
        this.id = Edit.getNextGeneratedId();
        this.text = text;
        this.isAddition = isAddition;
        this.position = position;
        this.deleted = false;

        if (previous != null && next != null) {
            this.insert(previous, next);
        }

        else if (previous == null && next != null) {
            this.insertInFront(next);
        }

        else if (previous != null && next == null) {
            this.insertBehind(previous);
        }

        else {
            // both are null so wtv
            this.previous = previous;
            this.next = next;
        }
    }

    private static int getNextGeneratedId() {
        return Edit.nextId++;
    }

    /*
        ACCESSORS
     */

    public String asText() {
        if (this.isAddition) {
            return this.text;
        } else {
            return "";
        }
    }

    public void delete() {
        this.deleted = true;
        // drop deletes completely
        if (!this.isAddition) {
            if (this.previous != null) {
                this.previous.next = this.next;
            }
            if (this.next != null) {
                this.next.previous = this.previous;
            }
        }
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void insert(Edit previous, Edit next) {
        previous.next = this;
        next.previous = this;
        this.previous = previous;
        this.next = next;
        // TODO determine if it's necessary in these methods to update position....
    }

    public void insertInFront(Edit next) {
        if (next != null) {
            next.previous = this;
        }
        this.next = next;
    }

    public void insertBehind(Edit previous) {
        previous.next = this;
        this.previous = previous;
    }

    public int length() {
        if (this.isAddition) {
            return this.text.length();
        }
        else {
            return 0;
        }
    }

    public void remove() {
        if (this.previous != null) {
            if (this.next != null) {
                this.previous.next = this.next;
            } else {
                this.previous.next = null;
            }
        }

        if (this.next != null) {
            if (this.previous != null) {
                this.next.previous = this.previous;
            } else {
                this.next.previous = null;
            }
        }

        this.previous = null;
        this.next = null;
        // TODO determine if it's necessary in these methods to update position....
    }
    // TODO i'm thinking there should be an update position function

    public boolean hasNext() {
        return this.next != null;
    }

    public boolean hasPrevious() {
        return this.previous != null;
    }

    public Edit getNext() {
        return this.next;
    }

    public Edit split(int position) {
        if (!this.isAddition) {
            System.out.println("WARNING: TRYING TO SPLIT A DELETION");
        }
        /*
            Takes this edit and splits it at the index, returning the next edit
         */
        String endText = this.text.substring(position);
        this.text = this.text.substring(0, position);
        Edit newEdit = new Edit(endText, true);
        newEdit.setDeleted(this.deleted);
        Edit oldNext = this.next;
        this.next = newEdit;
        newEdit.previous = this;
        newEdit.next = oldNext;
        return newEdit;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private void setText(String text) {
        this.text = text;
    }

    public Edit merge(String text) {
        /*
            Gets the next edit that starts at the end of the given text
         */
        String textCopy = text;
        StringBuilder sb = new StringBuilder();
        Edit edit = this;
        while (text.length() > 0) {
            if (!edit.isAddition) {
                edit = edit.getNext();
                continue;
            }
            String curText = edit.asText();
            if (text.length() < curText.length()) {
                String remainingText = curText.substring(text.length());
                edit.setText(curText.substring(0, text.length()));
                Edit newEdit = new Edit(remainingText, true);
                newEdit.insertInFront(edit.next);
                edit.next = newEdit;
                newEdit.previous = edit;
            }
            sb.append(edit.text);
            text = text.substring(edit.text.length());
            edit = edit.getNext();
        }
        if (!sb.toString().equals(textCopy)) {
            System.out.println("ERROR: merged text does not match given text");
        }
        return edit;
    }

    public void updatePosition() {
        if (this.hasPrevious()) {
            this.position = this.previous.position + this.previous.length();
        } else if (this.position != 0) {
            System.out.println("WARNING: node has no previous node but current position is not 0.....");
        }
        if (this.hasNext()) {
            this.next.updatePosition();
        }
    }

    public int getId() {
        return this.id;
    }

    public void mergeDeleted() {
        boolean keepLooking = false;
        Edit first = null;
        Edit edit = this;
        // go through the list
        // if there is a sequence of deleted edits that are inserts, combine them
        while (edit != null) {
            boolean mergeWithFirst = false;
            if (edit.deleted && first == null) {
                first = edit;
            }
            else if (edit.deleted && first != null && !keepLooking) {
                keepLooking = true;
                mergeWithFirst = true;
            }
            else if (edit.deleted) {
                mergeWithFirst = true;
            } else if (keepLooking) {
                break;
            }
            else {
                first = null;
            }
            if (mergeWithFirst) {
                first.text += edit.asText();
                first.next = edit.next;
                if (edit.hasNext()) {
                    edit.next.previous = first;
                }
            }
            edit = edit.getNext();
        }
    }

    public String stringRepr() {
        String[] arr = {
                "    [",
                "       id:       " + this.id,
                "       position: " + this.position,
                "       visible:  " + !this.deleted,
                "       type:     " + ((this.isAddition) ? "ADDITION" : "DELETION"),
                "       text:     " + this.text.replace("\n", "\\n"),
                "    ]"
        };
        return String.join("\n", arr);
    }

    protected String getText() {
        return this.text;
    }

    protected boolean isEditAnAddition() {
        return this.isAddition;
    }
}
