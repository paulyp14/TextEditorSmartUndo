package base.model;

public class EditView {

    // container class for the VIEW of an Edit
    // just gives TEXT + ID of an edit
    private String text;
    private int editId;
    private boolean isAddition;

    public EditView(int editId, String text, boolean isAddition) {
        this.text = text;
        this.editId = editId;
        this.isAddition = isAddition;
    }

    /**
     * Get the text of an Edit
     * @return String
     */
    public String getText() {
        return this.text;
    }

    /**
     * Get the id of an edit
     * @return
     */
    public int getId() {
        return this.editId;
    }

    /**
     * Get whether an edit was an addition
     * @return
     */
    public boolean isEditAnAddition() {
        return this.isAddition;
    }

    /**
     * Get an EditView from an Edit
     * @param edit the edit to view
     * @return EditView
     */
    public static EditView createFromEdit(Edit edit) {
        return new EditView(edit.getId(), edit.getText(), edit.isEditAnAddition());
    }

    /**
     * Get string representation for interactive test
     * @param maxLen int maximum size to display
     * @return String
     */
    public String forInteractiveModelTest(int maxLen) {
        String theText = (this.text.length() > (maxLen - 4)) ? this.text.substring(0,maxLen - 4) + "...." : this.text;
        String theAction = (this.isAddition) ? "INSERT: " : "DELETE: ";
        return " - " + theAction + theText;
    }

}
