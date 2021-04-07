package tests.component.model;

import base.model.Edit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


public class ModelEditTesterWithArray {

    private Edit[] edits;

    @BeforeEach
    public void setUp() {
        this.edits = new Edit[10];
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


        for (int i = 0; i < arr.length; i++) {
            Edit edit = new Edit(arr[i], true);
            this.edits[i] = edit;
        }

        StringBuilder sb = new StringBuilder();
        for (Edit edit: this.edits) {
            sb.append(edit.asText());
        }

        assertEquals(fullText, sb.toString(), "Edits should accurately depict the text entered by the user");
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

        int runningTotal = 0;
        int positionTotal = 0;
        for (int i = 0; i < arr.length; i++) {
            Edit edit = new Edit(arr[i], true);
            this.edits[i] = edit;
            positionTotal += runningTotal;
            runningTotal += arr[i].length();
            if (i > 0) {
                edit.insertBehind(this.edits[i -1]);
            }
        }

        this.edits[0].updatePosition();

        Edit edit = this.edits[0];
        StringBuilder sb = new StringBuilder();
        int linkedListTotal = 0;
        while (edit != null) {
            sb.append(edit.asText());
            linkedListTotal += edit.getPosition();
            edit = edit.getNext();
        }

        assertEquals(fullText, sb.toString(), "Edits should accurately depict the text entered by the user in linked list form");
        assertEquals(positionTotal, linkedListTotal, "Edits should have an accurate position in the list");
    }

    @Test
    @DisplayName("Third test")
    public void testThree() {
        // TEST THREE AS DESCRIBED IN A3
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";


        for (int i = 0; i < arr.length; i++) {
            Edit edit = new Edit(arr[i], true);
            this.edits[i] = edit;
            if (i > 0) {
                edit.insertBehind(this.edits[i -1]);
                this.edits[i - 1].insertInFront(edit);
            }
        }

        // select five for deletion;
        this.edits[0].delete();
        this.edits[3].delete();
        this.edits[4].delete();
        this.edits[5].delete();
        this.edits[8].delete();

        Edit edit = this.edits[0];
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        while (edit != null) {
            sb.append(edit.asText());
            edit = edit.getNext();
            counter++;
        }

        assertEquals(fullText, sb.toString(), "Edits should accurately depict the text entered by the user in linked list form");
        assertEquals(arr.length, counter, "The delete method should only mark edits as deleted");
    }

    @Test
    @DisplayName("Fourth test")
    public void testFour() {
        // TEST THREE AS DESCRIBED IN A3
        String[] arr = {
                "The oceans are ", "going to be devoid of fish ", "by 2050", " and the earth is already 1.2C ",
                "warmer than preindustrial times", " while wealth inequality is increasing ", "and the housing",
                " crisis spirals out of control", " but I'm writing unit tests like everything is normal", " even though we're living in the twilight of human civilization"
        };

        String fullText = "The oceans are going to be devoid of fish by 2050 and the earth is already 1.2C warmer than preindustrial times while wealth inequality is increasing and the housing crisis spirals out of control but I'm writing unit tests like everything is normal even though we're living in the twilight of human civilization";

        for (int i = 0; i < arr.length; i++) {
            Edit edit = new Edit(arr[i], true);
            this.edits[i] = edit;
            if (i > 0) {
                edit.insertBehind(this.edits[i - 1]);
                this.edits[i - 1].insertInFront(edit);
            }
            if (i > 0 && (i % 2 == 0)) {
                edit = new Edit(arr[i], false);
                edit.insertBehind(this.edits[i]);
                // add a new duplicated item that is a delete of text
                this.edits[i].insertInFront(edit);
                this.edits[i] = edit;
            }
        }

        Edit edit = this.edits[0];
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        while (edit != null) {
            sb.append(edit.asText());
            edit = edit.getNext();
            counter++;
        }

        assertEquals(fullText, sb.toString(), "Edits should accurately depict the text entered by the user in linked list form, duplicated removals of text should not affect");
    }
}
