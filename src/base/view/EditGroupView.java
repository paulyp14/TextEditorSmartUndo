import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.*;


public class EditGroupView extends JPanel implements ActionListener {
    
    private int groupIndex;
    UndoPanelView parentView;
    private JList<String> editList;
    private DefaultListModel<String> listModel;

    private int SIZE_WIDTH = 500;

    public EditGroupView() {}

    public EditGroupView(UndoPanelView parentView, int groupIndex, int width) {

        // Set index and parent panel ref
        this.groupIndex = groupIndex;
        this.parentView = parentView;
        this.SIZE_WIDTH = width;

        // Set panel settings
        //Dimension d = new Dimension(width, 200);
        //this.setPreferredSize(new Dimension(width, SIZE_HEIGHT));
        //this.setSize(d);
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        setTopPanel();
        setEditList();
        setBottomPanel();

        listModel.addElement("1");
        listModel.addElement("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        listModel.addElement("KEKW");
        editList.ensureIndexIsVisible(listModel.getSize() - 1);
    }


    //**********    PUBLIC  METHODS   **********//

    public void actionPerformed(ActionEvent e) {

    }


    //**********    PRIVATE METHODS   **********//

    private void setTopPanel() {

        JPanel topPanel = new JPanel();
        //topPanel.setPreferredSize(new Dimension(9999, 50));
        
        JLabel groupTitle = new JLabel("1");
        //groupTitle.setHorizontalAlignment(JLabel.LEFT);
        //groupTitle.setHorizontalTextPosition(SwingConstants.LEFT);

        // ImageIcon deleteIcon = new ImageIcon("garbage.png");
        // ImageIcon minimizeIcon = new ImageIcon("minus.png");
        // ImageIcon maximizeIcon = new ImageIcon("addition.png");
        topPanel.add(groupTitle);

        JButton x = new JButton("X");
        JButton m = new JButton("+");
        m.setAlignmentX(JButton.RIGHT_ALIGNMENT);
        topPanel.add(x);
        topPanel.add(m);

        add(topPanel, BorderLayout.NORTH);
    }

    private void setEditList() {

        listModel = new DefaultListModel<>(); 
        editList = new JList<>(listModel);
        //editList.setVisibleRowCount(2);
        editList.setPreferredSize(new Dimension(SIZE_WIDTH - 30, 200));
        editList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one item can be selected at a time
        JScrollPane scrollPane = new JScrollPane(editList);
        //scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setBottomPanel() {

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JButton("Undo"));
        bottomPanel.add(new JButton("Undo All"));

        // for later when we need padding
        //button2.setBorder(new EmptyBorder(0, 20, 0, 0));

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Show a dialog box to confirm if user wants to 
     * proceed with this action.
     * 
     * @param str
     * @return 0 if yes, 1 if no
     */
    private int showConfirmDialog(String str) {
        return JOptionPane.NO_OPTION;
    }

    /**
     * Highlight the edit text on the TextArea
     * 
     * @param index
     */
    private void highlightEdit(int index) {

    }

    /**
     * Add a new edit in the edit list of this group and
     * update the Model.
     * 
     * @param str
     * @return true if added new edit successfully
     */
    private boolean addNewEdit(String str) {
        return false;
    }

    /**
     * Undo an edit in the edit list of this group and 
     * update the Model.
     * 
     * @param index of selected undo
     * @return true if undo'd edit successfully
     */
    private boolean undoEdit(int index) {
        return false;
    }

    /**
     * Undo all edits in the edit list for this group and 
     * update the Model.
     * 
     * @return true if undo all edits successfully
     */
    private boolean undoAllEdits() {
        return false;
    }

    /**
     * Minimize this edit group and allow another group 
     * to be maximized if desired by user.
     */
    private void minimizeEditList() {
        
    }

    /**
     * Maximize this edit group and minimize (if any)
     * the currently maximized edit group.
     */
    private void maximizeEditList() {

    }

    /**
     * Call the parent UndoPanelView to remove this 
     * edit group component from panel.
     */
    private void deleteThisGroup() {
        
    }
}
