package tests.component.model;

import java.util.ArrayList;

import base.model.Edit;
import base.model.EditGroup;
import base.model.EditView;

public class ModelTestEditGroup {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ModelTestEditGroup test = new ModelTestEditGroup();
		test.testOne();
		System.out.println();
		test.testTwo();
		System.out.println();
		test.testThree();
		System.out.println();
		test.testFour();
	}
	
	 public void testOne() {// test adding new edit in edit group,
		Edit[] edits = new Edit[6];
		ArrayList<EditView> newEdits = new ArrayList<EditView>();
		EditGroup groupOne = new EditGroup("GroupOne");
        String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};

        for (int i = 0; i < asArr.length; i++) {
            Edit edit = new Edit(asArr[i], true);
            edits[i] = edit;
            EditView newEdit = new EditView(edit.getId(), edit.asText(), true);
            newEdits.add(newEdit);
        }
        
        groupOne.add(newEdits);
	   
	    System.out.println(groupOne.getGroupSize());
	 }
	 
	 public void testTwo() { // test removing edits from a group based on arraylist integer of ids
		 Edit[] edits = new Edit[6];
			ArrayList<EditView> newEdits = new ArrayList<EditView>();
			ArrayList<Integer> groupIds = new ArrayList<Integer>();
			EditGroup groupOne = new EditGroup("GroupOne");
	        String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};

	        for (int i = 0; i < asArr.length; i++) {
	            Edit edit = new Edit(asArr[i], true);
	            edits[i] = edit;
	            EditView newEdit = new EditView(edit.getId(), edit.asText(), true);
	            newEdits.add(newEdit);
	            groupIds.add(edit.getId());
	        }
	        
	        groupOne.add(newEdits);
	        groupOne.removeById(groupIds);
	        System.out.println(groupOne.getGroupSize());
	 }
	 
	 public void testThree() { // test removing edit based on integer id
		 Edit[] edits = new Edit[6];
			ArrayList<EditView> newEdits = new ArrayList<EditView>();
			ArrayList<Integer> groupIds = new ArrayList<Integer>();
			EditGroup groupOne = new EditGroup("GroupOne");
	        String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};

	        for (int i = 0; i < asArr.length; i++) {
	            Edit edit = new Edit(asArr[i], true);
	            edits[i] = edit;
	            EditView newEdit = new EditView(edit.getId(), edit.asText(), true);
	            newEdits.add(newEdit);
	            groupIds.add(edit.getId());
	        }
	        
	        groupOne.add(newEdits);
	        groupIds.remove(0);
	        groupIds.remove(1);
	        groupOne.removeById(groupIds);
	        System.out.println(groupOne.getGroupSize());
	 }
	 
	 public void testFour() // test get set group name
	 {
		 EditGroup groupFour = new EditGroup("");
		 groupFour.setGroupName("groupFour");
		 System.out.println(groupFour.getGroupName());
	 }
}
