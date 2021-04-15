package base.view;

import base.controller.*;
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;


/**
 * Class that manages the UI component of a edit group.
 * 
 * @author Thanh Tung Nguyen
 */
public class EditGroupView extends JPanel {
    
    private UndoPanelController controller;
    private int groupIndex;
    private UndoPanelView parentView;
    private int SIZE_WIDTH;

    private DefaultListModel<String> listModel;
    private JList<String> editList;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JButton expandCollapseBtn;

    private String deleteImgPath = "resources/delete.png";
    private String expandImgPath = "resources/plus.png";
    private String collapseImgPath = "resources/minus.png";


    /**
     * Constructor
     * @param parentView
     * @param groupIndex
     * @param width of ui component
     */
    public EditGroupView(UndoPanelView parentView, UndoPanelController controller, int groupIndex, int width) {

        // Set index and parent panel ref
        this.groupIndex = groupIndex;
        this.controller = controller;
        this.parentView = parentView;
        this.SIZE_WIDTH = width;

        // Set panel settings
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setTopPanel();
        setEditList();
        setBottomPanel();
        add(new JSeparator());

        // listModel.addElement("1");
        // listModel.addElement("2");
        // listModel.addElement("3");
        // listModel.addElement("4");
        // listModel.addElement("5");
        // listModel.addElement("6");
        // listModel.addElement("61");
    }


    //**********    PUBLIC  METHODS   **********//


    /**
     * Returns group index of this group edit.
     */
    public int getGroupIndex() {
        return groupIndex;
    }

    /**
     * Returns the list of edits.
     * @return
     */
    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    /**
     * When deleting an edit group in a list, we decrement group  
     * index to fill in the holes of our arraylist.
     */
    public void moveIndexDown() {
        groupIndex -= 1;
    }

    /**
     * Add a new edit in the edit list of this group and
     * update the Model.
     * 
     * @param str
     * @return true if added new edit successfully
     */
    public boolean addNewEdit(String str) {

        if (str == null || str == "") {
            System.out.println("EditGroupView.addNewEdit: String is empty or null.");
            return false;
        }

        listModel.addElement(str);
        editList.ensureIndexIsVisible(listModel.getSize() - 1); // make sure it's scrolled down when adding new items

        return true;
    }

    /**
     * Undo an edit in the edit list of this group and 
     * update the Model.
     * 
     * @param index of selected undo
     * @return true if undo'd edit successfully
     */
    public boolean undoEdit(int index) {

        if (index < 0 || index >= listModel.size()) {
            
            return false;
        }
        
        if (listModel.isEmpty()) {
            System.out.println("EditGroupView.undoEdit: no edits to undo.");
            return false;
        }
            
        listModel.remove(index);
        // TODO use controller
        return true;
    }

    /**
     * Undo all edits in the edit list for this group and 
     * update the Model.
     */
    public boolean undoAllEdits() {

        if (listModel.isEmpty()) {
            System.out.println("EditGroupView.undoEdit: no edits to undo.");
            return false;
        }
            
        listModel.clear();
        // TODO use controller
        return true;
    }

    /**
     * Call the parent UndoPanelView to remove this 
     * edit group component from panel.
     */
    public void deleteThisGroup() {
        parentView.deleteEditGroup(groupIndex);
    }

    /**
     * Collapse this edit group and allow another group 
     * to be maximized if desired by user.
     */
    public void collapseEditList() {
        middlePanel.setVisible(false);
        bottomPanel.setVisible(false);

        expandCollapseBtn.setIcon(new ImageIcon(expandImgPath));
        parentView.onCollapseEditGroup();
    }

    /**
     * Expand this edit group and minimize (if any)
     * the currently maximized edit group.
     */
    public void expandEditList() {
        middlePanel.setVisible(true);
        bottomPanel.setVisible(true);

        expandCollapseBtn.setIcon(new ImageIcon(collapseImgPath));
        parentView.onExpandEditGroup(groupIndex);
    }


    //**********    PRIVATE METHODS   **********//


    /**
     * Sets up the UI of the top panel of the edit group.
     */
    private void setTopPanel() {

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 4));
        topPanel.setPreferredSize(new Dimension(SIZE_WIDTH, 35));
        //topPanel.setBackground(Color.LIGHT_GRAY);
        
        // Edit group name
        JLabel groupTitle = new JLabel("Edit Group", SwingConstants.LEFT);
        groupTitle.setFont(new Font("Arial", Font.BOLD, 14));

        // Button for delete edit group
        JButton deleteGroupBtn = new JButton();
        deleteGroupBtn.setIcon(new ImageIcon(deleteImgPath));
        deleteGroupBtn.setBorderPainted(false);
        deleteGroupBtn.setContentAreaFilled(false);
        deleteGroupBtn.setMargin(new Insets(0, 0, 0, 0));
        deleteGroupBtn.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int response = showConfirmDialog("Are you sure you wish to delete this edit group?");
                if (response == 0) { // presses yes
                    controller.deleteGroup(groupIndex);
                }
            }
        });
        
        // Button for expanding and collapsing Edit Group
        expandCollapseBtn = new JButton();
        expandCollapseBtn.setIcon(new ImageIcon(collapseImgPath));
        expandCollapseBtn.setBorderPainted(false);
        expandCollapseBtn.setContentAreaFilled(false);
        expandCollapseBtn.setMargin(new Insets(0, 0, 0, 0));
        expandCollapseBtn.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                // if this is expanded, collapse it
                if (parentView.getCurrentlyFocusedGroup() == getGroupIndex())
                    collapseEditList();
                else
                    expandEditList(); 
            }
        });

        // We wrap in another JPanel so we can align to the right
        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT,0, 0));
        btnWrapper.setPreferredSize(new Dimension(SIZE_WIDTH-120, 35));
        btnWrapper.add(deleteGroupBtn);
        btnWrapper.add(expandCollapseBtn);

        topPanel.add(groupTitle);
        topPanel.add(btnWrapper);
        add(topPanel);
    }

    private void setEditList() {

        listModel = new DefaultListModel<>(); 
        editList = new JList<>(listModel);
        editList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only one item can be selected at a time

        JScrollPane scrollPane = new JScrollPane(editList);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER); // removes horizontal scroll bar
        scrollPane.setPreferredSize(new Dimension(SIZE_WIDTH, 200));

        // Style the scroll bar a bit
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbHighlightColor = Color.GRAY;
            }
        });
        
        middlePanel = new JPanel();
        middlePanel.add(scrollPane);
        middlePanel.setBackground(Color.LIGHT_GRAY);

        add(middlePanel);
    }

    private void setBottomPanel() {

        bottomPanel = new JPanel();

        JButton undoBtn = new JButton("Undo");
        undoBtn.setBackground(Color.WHITE);
        undoBtn.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {

                int indexToUndo = editList.getSelectedIndex();
                if (indexToUndo == -1)
                    indexToUndo = listModel.size()-1; // if no selection, default to last item

                controller.undoEdit(groupIndex, indexToUndo);
            }
        });

        JButton undoAllBtn = new JButton("Undo All");
        undoAllBtn.setBackground(Color.WHITE);
        undoAllBtn.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.undoGroupEdits(groupIndex);
            }
        });

        bottomPanel.add(undoBtn);
        bottomPanel.add(undoAllBtn);
        bottomPanel.setBackground(Color.GRAY);

        ((FlowLayout)bottomPanel.getLayout()).setHgap(25);

        add(bottomPanel);
    }

    /**
     * Show a dialog box to confirm if user wants to 
     * proceed with this action.
     * 
     * @param str
     * @return 0 if yes, 1 if no
     */
    private int showConfirmDialog(String message) {

        int dialogResult = JOptionPane.showConfirmDialog (
                parentView, 
                message,
                "TESU",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return dialogResult;
    }

    /**
     * Highlight the edit text on the TextArea
     * 
     * @param index
     */
    private void highlightEdit(int index) {

    }
}
