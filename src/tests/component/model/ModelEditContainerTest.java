package tests.component.model;

import base.model.Edit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import base.model.EditContainer;
import base.model.EditView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ModelEditContainerTest {

    private EditContainer container;

    @BeforeEach
    public void setUp() {
        this.container = new EditContainer();
    }

    @Test
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
}
