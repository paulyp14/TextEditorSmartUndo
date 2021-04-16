package tests.component.controller;

import base.model.EditContainer;
import base.model.GroupContainer;
import base.view.UndoPanelView;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.TestCase;
import java.util.*;

/**
 * Tests various functions for the UndoPanelController
 * Functions tested are: addNewEdit(), addNewGroup(), and undoEdit()
 * @author Ian Ngaruiya Njoroge
 */
public class UndoPanelControllerTest extends TestCase
{
    
    public UndoPanelControllerTest()
    {
    }
    
    @BeforeClass
    public void setUp() throws Exception
    {
        super.setUp();
        System.out.println("UndoPanelControllerTest setUp() method");
    }
    
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        System.out.println("UndoPanelControllerTest tearDown() method");
        System.out.println();
    }

    /**
     * Test of addNewEdit method, of class UndoPanelController.
     * Makes sure that each new edit is inserted in the appropriate group according to where it was added from
     */
    @Test
    public void testAddNewEdit()
    {
        System.out.println();
        System.out.println("Test addNewEdit method");
        Random rand = new Random();
        String text = "";
        
        int numOfButtons = 5;
        int numOfClicks[] = new int[5];
                
        UndoPanelView upv = new UndoPanelView(0);
        GroupContainer gcp = GroupContainer.getContainer();
        EditContainer edc = EditContainer.getContainer();
        UndoPanelController upc = new UndoPanelController(upv, gcp, edc);
        
        //Fills the numOfClicks array with random numbers
        for(int i = 0; i < numOfClicks.length; i++)
        {
            numOfClicks[i] = rand.nextInt(8);
        }
        
        //Simulates adding a number of edits for each button
        for(int i = 0; i < numOfButtons; i++)
        {
            int clicks = numOfClicks[i];
            System.out.println("EditGroup{" + i +"}");
            upc.addNewGroup(i + 1);
            for(int j = 0; j < clicks; j++)
            {
                System.out.println("Adding edit to EditGroup{" + i +"}");
                upc.addNewEdit(i + 1, text, j, true);
            }                
        }
    }

    /**
     * Test of addNewGroup method, of class UndoPanelController.
     * Makes sure that the added group has the its index set as its name once created
     */
    @Test
    public void testAddNewGroup()
    {
        System.out.println("Test addNewGroup method");
        String failedString = "EditGroup not added";
        int groupIndex = 1;
        int expectedName;
        
        UndoPanelView upv = new UndoPanelView(0);
        GroupContainer gcp = GroupContainer.getContainer();
        UndoPanelController upc = new UndoPanelController(upv, gcp, null);
        
        upc.addNewGroup(groupIndex);
        expectedName = Integer.parseInt(upc.getGroupContainer().stringRepr().substring(234, 236).trim());
        
        assertEquals(failedString, groupIndex, expectedName);
    }

    /**
     * Test of undoEdit method, of class UndoPanelController.
     * Simulates undoing a number of Edit items for a each button (Given a certain number of buttons)
     */
    @Test
    public void testUndoEdit()
    {
        System.out.println("Test undoEdit method");
        Random rand = new Random();
        String text = "";
        
        int numOfButtons = 5;
        int numOfClicks[] = new int[5];
        int clicks;
                
        UndoPanelView upv = new UndoPanelView(0);
        GroupContainer gcp = GroupContainer.getContainer();
        EditContainer edc = EditContainer.getContainer();
        UndoPanelController upc = new UndoPanelController(upv, gcp, edc);
        
        //Fills the numOfClicks array with random numbers
        for(int i = 0; i < numOfClicks.length; i++)
        {
            numOfClicks[i] = rand.nextInt(8);
        }
        
        //Adds edits that will be undone
        for(int i = 0; i < numOfButtons; i++)
        {
            clicks = numOfClicks[i];
            upc.addNewGroup(i + 1);
            for(int j = 0; j < clicks; j++)
            {
                upc.addNewEdit(i, text, j, true);
            }                
        }
        
        //Simulates undoing the Edits for each button
        for(int k = 0; k < numOfButtons; k++)
        {
            clicks = numOfClicks[k];
            System.out.println("Edit{" + k +"}");
            for(int l = 0; l < clicks; l++)
            {
                System.out.println("Undoing Edit{" + l + "}");
                upc.undoEdit(k + 1, l);
            }
        }
    }
}
