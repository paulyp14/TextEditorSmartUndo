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

    /* ======================================================================
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

    /* =============================================================================
        ACCESSORS
     */

    /**
     * Returns the text of the edit
     * @return
     */
    public String asText() {
        if (this.isAddition) {
            return this.text;
        } else {
            return "";
        }
    }

    /**
     * Once the user no longer wants to see the edit, this makes the edit invisible
     * Removals of text are forgotten, because they are useless once invisible to the user
     */
    public void delete() {
        this.deleted = true;
        // drop deletes completely
        // a delete does not need to be remembered once hte user no longer wants to see it
        if (!this.isAddition) {
            if (this.previous != null) {
                this.previous.next = this.next;
            }
            if (this.next != null) {
                this.next.previous = this.previous;
            }
        }
    }

    /**
     * is this invisible to the user?
     * @return boolean
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Get the position from the start of the FULL TEXT that the edit starts at
     * @return int
     */
    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * inserts an edit in between two edits
     * @param previous Edit the edit in front of it
     * @param next Edit the edit after it
     */
    public void insert(Edit previous, Edit next) {
        previous.next = this;
        next.previous = this;
        this.previous = previous;
        this.next = next;
    }

    /**
     * Inserts THIS edit in front of the edit passed to the function
     * @param next Edit -- the edit that will come after the edit this function is called on
     */
    public void insertInFront(Edit next) {
        if (next != null) {
            next.previous = this;
        }
        this.next = next;
    }

    /**
     * Inserts THIS edit behind the edit passed to the function
     * @param previous Edit -- the edit that will come before the edit this function is called on
     */
    public void insertBehind(Edit previous) {
        previous.next = this;
        this.previous = previous;
    }

    /**
     * Get the size of the text this edit represents
     * @return
     */
    public int length() {
        if (this.isAddition) {
            return this.text.length();
        }
        else {
            return 0;
        }
    }

    /**
     * Get rid of this edit
     */
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
    }

    /**
     * Does an edit come after this one?
     * @return boolean
     */
    public boolean hasNext() {
        return this.next != null;
    }

    /**
     * Does an edit come before this one?
     * @return boolean
     */
    public boolean hasPrevious() {
        return this.previous != null;
    }

    /**
     * Get the edit that comes after this one
     * @return Edit
     */
    public Edit getNext() {
        return this.next;
    }

    /**
     * based on the position, split this edit into two edit, and return the edit that was split
     * @param position
     * @return
     */
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
        this.id = Edit.getNextGeneratedId();
        return newEdit;
    }

    /**
     * Sets the value of the deleted attribute...
     * @param deleted boolean
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Set the text attribute...
     * @param text String
     */
    private void setText(String text) {
        this.text = text;
    }

    /**
     * Based on the text, merges subsequent edits into this one until the text is exhausted
     * @param text String the text being merged
     * @return Edit the next edit once the merge is performed
     */
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

    /**
     * call on first node of list to update the position
     */
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

    /**
     * Merges all deleted edits that are sequential into a single edit
     */
    public void mergeDeleted() {
        boolean keepLooking = false;
        boolean mergedWithFirst = false;
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
                if (!mergedWithFirst) {
                    mergedWithFirst = true;
                }
                first.text += edit.asText();
                first.next = edit.next;
                if (edit.hasNext()) {
                    edit.next.previous = first;
                }
            }
            edit = edit.getNext();
        }
        if (mergedWithFirst) {
            first.id = Edit.getNextGeneratedId();
        }
    }

    /**
     * String representation of the Edit, used to display editContainer
     * @return String
     */
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

    /**
     * gets the text affected by the edit
     * @return String
     */
    protected String getText() {
        return this.text;
    }

    /**
     * determine if text was inserted or deleted by this edit
     * @return
     */
    protected boolean isEditAnAddition() {
        return this.isAddition;
    }

    /**
     * undo this edit
     */
    public void undo() {
        if (this.isAddition) {
            // remove itself from the list
            if (this.hasPrevious()) {
                this.previous.next = this.next;
            }
            if (this.hasNext()) {
                this.next.previous = this.previous;
            }

            if (this.hasPrevious()) {
                this.previous = null;
            }

            if (this.hasNext()) {
                this.next = null;
            }

        }
        else {
            // become an addition of text
            this.isAddition = true;
        }
    }
}
