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

    public String getText() {
        return this.text;
    }

    public int getId() {
        return this.editId;
    }

    public boolean isEditAnAddition() {
        return this.isAddition;
    }

    public static EditView createFromEdit(Edit edit) {
        return new EditView(edit.getId(), edit.getText(), edit.isEditAnAddition());
    }
}
