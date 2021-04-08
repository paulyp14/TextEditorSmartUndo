package tests.component.model;

import base.model.Edit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import base.model.EditContainer;
import base.model.EditView;
import org.junit.jupiter.api.*;


import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModelEditContainerTest {

    private EditContainer container;

    @BeforeEach
    public void setUp() {
        this.container = new EditContainer();
    }

    @Test
    @Order(1)
    @DisplayName("First test")
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
    @DisplayName("Second test")
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
    @DisplayName("Third test")
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

        this.container.delete(20);
        this.container.delete(23);
        this.container.delete(24);
        this.container.delete(25);
        this.container.delete(27);

        assertEquals(fullText, this.container.asText(), "Container should faithfully represents what the user has input");
        assertEquals(arr.length - 2, this.container.size(), "Adjacent deleted edits should be merged together");
        assertEquals(arr[9], this.container.mostRecentEdit().asText(), "Most recent edit should be unaffected");
    }

    @Test
    @Order(4)
    @DisplayName("Fourth test")
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
    @DisplayName("Fifth test")
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
    @DisplayName("Sixth test")
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
}
