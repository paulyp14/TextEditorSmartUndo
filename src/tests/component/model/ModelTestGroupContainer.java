package tests.component.model;

import java.util.ArrayList;

import base.model.Edit;
import base.model.EditContainer;
import base.model.EditGroup;
import base.model.EditView;
import base.model.GroupContainer;

public class ModelTestGroupContainer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ModelTestGroupContainer test = new ModelTestGroupContainer();
		test.testSeven();
		
	}
	
    public void testOne() { //Test add method that takes in string for name and arraylist int for ids of the edits. 
        EditContainer container = EditContainer.getContainer();
        GroupContainer groupContainer = GroupContainer.getContainer();
     
		ArrayList<Integer> editIds = new ArrayList<Integer>();
		groupContainer.create("GroupOne");
        String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};
        
        int size = 0;
        String s = "";
        for (int i = 0; i < asArr.length; i++) {
            s = asArr[i];
            container.create(s, size, true);
            size += s.length(); 
        }
        
        for (int i = 0; i < container.size(); i++) {
        	editIds.add(i);
        }
        
        groupContainer.add("GroupOne", editIds);

	    System.out.println(groupContainer.stringRepr());
	    
    }
    
    public void testTwo() { //Test add that takes in string as the name of the group and an int as the id of the edit.
    	EditContainer container = EditContainer.getContainer();
        GroupContainer groupContainer = GroupContainer.getContainer();
		groupContainer.create("GroupTwo");
		String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};
	    	
	        int size = 0;
	        String s = "";
	        for (int i = 0; i < asArr.length; i++) {
	            s = asArr[i];
	            container.create(s, size, true);
	            size += s.length(); 
	        }		

		groupContainer.add("GroupTwo", 0);
        groupContainer.add("GroupTwo", 1);
        groupContainer.add("GroupTwo", 2);
        groupContainer.add("GroupTwo", 3);
        groupContainer.add("GroupTwo", 4);
        groupContainer.add("GroupTwo", 5);
        
	    System.out.println(groupContainer.stringRepr());
    }
    
    public void testThree() { // test removing edits from a group with a string as group's name, the edits are move to default.
    	EditContainer container = EditContainer.getContainer();
        GroupContainer groupContainer = GroupContainer.getContainer();
		groupContainer.create("GroupThree");
		String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};
	    	
	        int size = 0;
	        String s = "";
	        for (int i = 0; i < asArr.length; i++) {
	            s = asArr[i];
	            container.create(s, size, true);
	            size += s.length(); 
	        }		

		groupContainer.add("GroupThee", 0);
        groupContainer.add("GroupThree", 1);
        groupContainer.add("GroupThree", 2);
        groupContainer.add("GroupThree", 3);
        groupContainer.add("GroupThree", 4);
        groupContainer.add("GroupThree", 5);
        
	    System.out.println(groupContainer.stringRepr());
        groupContainer.removeGroup("GroupThree");
        
        System.out.println(groupContainer.stringRepr());
    }
    
    public void testFour() { //test remove and delete group, all the edits will be also deleted along with the group.
    	EditContainer container = EditContainer.getContainer();
        GroupContainer groupContainer = GroupContainer.getContainer();
		groupContainer.create("GroupFour");
		String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};
	    	
	        int size = 0;
	        String s = "";
	        for (int i = 0; i < asArr.length; i++) {
	            s = asArr[i];
	            container.create(s, size, true);
	            size += s.length(); 
	        }		

		groupContainer.add("GroupFour", 0);
        groupContainer.add("GroupFour", 1);
        groupContainer.add("GroupFour", 2);
        groupContainer.add("GroupFour", 3);
        groupContainer.add("GroupFour", 4);
        groupContainer.add("GroupFour", 5);
        
	    System.out.println(groupContainer.stringRepr());
        groupContainer.removeAndDeleteGroup("GroupFour");
        
        System.out.println(groupContainer.stringRepr());
    }
    
    public void testFive() { //UNDO ALL EDITS IN A SPECIFIC GROUP
    	EditContainer container = EditContainer.getContainer();
        GroupContainer groupContainer = GroupContainer.getContainer();
		groupContainer.create("GroupFive");
		String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};
	    	
	        int size = 0;
	        String s = "";
	        for (int i = 0; i < asArr.length; i++) {
	            s = asArr[i];
	            container.create(s, size, true);
	            size += s.length(); 
	        }		

		groupContainer.add("GroupFive", 0);
        groupContainer.add("GroupFive", 1);
        groupContainer.add("GroupFive", 2);
        groupContainer.add("GroupFive", 3);
        groupContainer.add("GroupFive", 4);
        groupContainer.add("GroupFive", 5);
        
	    System.out.println(groupContainer.stringRepr());
        groupContainer.undoGroup("GroupFive");
        
        System.out.println(groupContainer.stringRepr());
        
    }
    
    public void testSix() { //undo specific edits based on id in a specific group (usoing string group name)
    	EditContainer container = EditContainer.getContainer();
        GroupContainer groupContainer = GroupContainer.getContainer();
		groupContainer.create("GroupSix");
		ArrayList<Integer> editIds = new ArrayList<Integer>();
		String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};
	    	
	        int size = 0;
	        String s = "";
	        for (int i = 0; i < asArr.length; i++) {
	            s = asArr[i];
	            container.create(s, size, true);
	            size += s.length(); 
	        }		
	        
		groupContainer.add("GroupSix", 0);
        groupContainer.add("GroupSix", 1);
        groupContainer.add("GroupSix", 2);
        groupContainer.add("GroupSix", 3);
        groupContainer.add("GroupSix", 4);
        groupContainer.add("GroupSix", 5);
        editIds.add(1);
        editIds.add(2);
	    System.out.println(groupContainer.stringRepr());
        groupContainer.undoEditsInGroup("GroupSix", editIds);
        
        System.out.println(groupContainer.stringRepr());
        
    }
    
    public void testSeven() { // test removing edits from default group based on specific ids.
    	EditContainer container = EditContainer.getContainer();
        GroupContainer groupContainer = GroupContainer.getContainer();
		groupContainer.create("GroupSeven");
		ArrayList<Integer> editIds = new ArrayList<Integer>();
		String[] asArr = {"Today is Sunday"," and I am going for a small walk"," to the mall"," It is a beautiful mall",", with beautiful people",", I feel good to be shopping."};
	    	
	        int size = 0;
	        String s = "";
	        for (int i = 0; i < asArr.length; i++) {
	            s = asArr[i];
	            container.create(s, size, true);
	            size += s.length(); 
	        }		

		groupContainer.add("GroupSeven", 0);
        groupContainer.add("GroupSeven", 1);
        groupContainer.add("GroupSeven", 2);
        groupContainer.add("GroupSeven", 3);
        groupContainer.add("GroupSeven", 4);
        groupContainer.add("GroupSeven", 5);
        
        editIds.add(0);
        editIds.add(1);
        
	    System.out.println(groupContainer.stringRepr());
        groupContainer.removeGroup("GroupSeven");
        System.out.println(groupContainer.stringRepr());
        groupContainer.undoEditsInDefaultGroup(editIds);
        System.out.println(groupContainer.stringRepr());
    }
    
    
    
    

}
