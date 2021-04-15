package tests.component.model;

import base.model.Edit;

import base.model.EditContainer;
import base.model.EditView;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModelEditContainerTest {

    private EditContainer container;

    @BeforeEach
    public void setUp() {
        this.container = EditContainer.getContainer();
    }

    @AfterEach
    public void tearDown() { this.container.empty(); }

    @Test
    @Order(1)
    @DisplayName("Container is representative")
    public void testOne() {
        // TEST ONE AS DESCRIBED IN A3
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s: arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        assertEquals(fullText, this.container.asText(), "Container should faithfully represents what the user has input");
    }

    @Test
    @Order(2)
    @DisplayName("Deleted edits merge")
    public void testTwo() {
        // TEST TWO AS DESCRIBED IN A3
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s: arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        this.container.delete(10);
        this.container.delete(13);
        this.container.delete(14);
        this.container.delete(15);
        this.container.delete(18);

        assertEquals(fullText, this.container.asText(), "Container should faithfully represents what the user has input");
        assertEquals(arr.length - 2, this.container.size(), "Adjacent deleted edits should be merged together");
    }

    @Test
    @Order(3)
    @DisplayName("Deleted elements merged and stack unaffected")
    public void testThree() {
        // TEST TWO AS DESCRIBED IN A3
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s: arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        this.container.delete(22);
        this.container.delete(25);
        this.container.delete(26);
        this.container.delete(27);
        this.container.delete(29);

        assertEquals(fullText, this.container.asText(), "Container should faithfully represents what the user has input");
        assertEquals(arr.length - 2, this.container.size(), "Adjacent deleted edits should be merged together");
        assertEquals(arr[9], this.container.mostRecentEdit().asText(), "Most recent edit should be unaffected");
    }

    @Test
    @Order(4)
    @DisplayName("View is representative")
    public void testFour() {
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s: arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        ArrayList<EditView> asViews = this.container.view();
        boolean equal = true;
        for (int i = 0; i < asViews.size(); i++) {
            if (!arr[i].equals(asViews.get(i).getText())) {
                equal = false;
            }
        }

        assertEquals(true, equal, "View of edits returned by container should represent the text of the array");
    }

    @Test
    @Order(5)
    @DisplayName("Viewing only certain elements")
    public void testFive() {
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s : arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }
        ArrayList<Integer> editIds = new ArrayList<>();
        editIds.add(45);
        editIds.add(49);
        ArrayList<EditView> asViews = this.container.view(editIds);

        assertEquals("going to be devoid of fish ", asViews.get(0).getText());
        assertEquals(" while wealth inequality is increasing ", asViews.get(1).getText());
    }

    @Test
    @Order(6)
    @DisplayName("Can undo all edits")
    public void testSix() {
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s : arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        for (String s: arr) {
            container.undo();
        }

        assertEquals(0, container.size(), "Container should be empty after same number of undos called as edits made");
        assertEquals("", container.asText(), "Container should be empty after same number of undos called as edits made");
    }

    @Test
    @Order(7)
    @DisplayName("Boundary test: add a lot of edits")
    public void testSeven() {
        int total = 1000;
        int counter = 0;
        Random r = new Random();
        String first = null;
        StringBuilder sb = null;
        for (int i = 0; i < total; i++) {
            int length = r.nextInt(200);
            if (length == 0) {
                length = 10;
            }
            sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                int nextChar = r.nextInt(90);
                sb.append((char) (nextChar + 32));
            }
            if (first == null) {
                first = sb.toString();
            }
            container.create(sb.toString(), counter, true);
            counter += length;
        }
        ArrayList<EditView> ev = this.container.view();
        assertEquals(first, ev.get(0).getText());
        assertEquals(sb.toString(), ev.get(ev.size() - 1).getText());
        assertEquals(1000, this.container.size());
    }

    @Test
    @Order(8)
    @DisplayName("Boundary / exception test: Removing too many edits")
    public void testEight() {
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s : arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        for (String s: arr) {
            container.undo();
        }
        container.undo();
        container.undo();

        assertEquals(0, container.size(), "Container should be empty after same number of undos called as edits made");
        assertEquals("", container.asText(), "Container should be empty after same number of undos called as edits made");
    }

    @Test
    @Order(9)
    @DisplayName("Boundary / exception test: trying to remove IDs that don't exist")
    public void testNine() {
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s : arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        ArrayList<Integer> invalidIds = new ArrayList<>();
        invalidIds.add(5);
        invalidIds.add(100);

        container.undoByIds(invalidIds);

        for (String s: arr) {
            container.undo();
        }
        container.undo();
        container.undo();

        container.undoByIds(invalidIds);

        assertEquals(0, container.size(), "Container should be empty after same number of undos called as edits made");
        assertEquals("", container.asText(), "Container should be empty after same number of undos called as edits made");
    }

    @Test
    @Order(10)
    @DisplayName("Boundary / exception test: try to add at an invalid position")
    public void testTen() {
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s : arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        Exception exception = assertThrows(RuntimeException.class, () -> {
            container.create(fullText, -100, true);
        });
    }

    @Test
    @Order(11)
    @DisplayName("Boundary / exception test: undo valid and invalid ids at the same time")
    public void testEleven() {
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        int counter = 0;
        for (String s : arr) {
            this.container.create(s, counter, true);
            counter += s.length();
        }

        ArrayList<Integer> editIds = new ArrayList<>();
        editIds.add(1095);
        editIds.add(1098);
        editIds.add(40);
        editIds.add(1099);
        editIds.add(51);
        this.container.undoByIds(editIds);

        assertEquals(7, this.container.size());
    }
}
