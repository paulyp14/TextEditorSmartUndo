package tests.component.model;

import base.model.Edit;
import base.model.EditContainer;

import java.util.Scanner;

public class InteractiveModelTestDriver {
    static final String separator = "===================================================================";
    static Scanner scanner = new Scanner(System.in);
    private static final String firstPrompt = "Enter the first edit:\n";
    private static final String regularPrompt = "Enter the text of the edit:";
    private static boolean first = true;
    private static String currentPrompt;
    private static int choice = 1;
    private static int position = 0;
    private static EditContainer editContainer = new EditContainer();


    private static final String breaker = "%%";
    private static final String endOfInput = "##";

    public static void main(String[] args) {
        welcome();
        run();
    }

    public static void welcome() {
        System.out.println(separator);
        System.out.println("\n\n*********  WELCOME TO THE INTERACTIVE MODEL TEST DRIVER  *********\n\n");
        System.out.println("This system will simulate making edits, and will show how\ninteractions are reflected in the model");
        System.out.println("Instructions:");
        System.out.println(" -- To quit at any time, enter the sequence '%%'");
        System.out.println(" -- To finish typing input to see how it is reflected");
        System.out.println("    in the model, enter the sequence '##'");
        System.out.println("\n");
        System.out.println(separator + "\n\n");
    }

    public static void run() {
        while (true) {
            if (first) {
                currentPrompt = firstPrompt;
                first = false;
            } else {
                currentPrompt = regularPrompt;
                makeChoice();
                if (choice != 3) {
                    getInputPosition();
                }
            }
            if (choice != 3) {
                readInputText();
            }
            else {
                getIdToDelete();
            }
            System.out.println(editContainer.stringRepr());
        }
    }

    public static void getInputPosition() {
        while (true) {
            System.out.println("Enter the position of the edit:");
            String choice = scanner.nextLine();
            try {
                position = Integer.parseInt(choice);
                break;
            } catch (NumberFormatException e) {
                breakCheck(choice);
            } catch (Exception e) {
                // pass
            }
            System.out.println("Invalid integer input...");
        }
    }

    public static void makeChoice() {
        while (true) {
            System.out.println("Enter one of the following numbers:");
            System.out.println("  1. Add text");
            System.out.println("  2. Remove text");
            System.out.println("  3. Make an edit no longer visible");
            String choice = scanner.nextLine();
            try {
                int chosen = Integer.parseInt(choice);
                if (chosen > 0 && chosen < 4) {
                    InteractiveModelTestDriver.choice = chosen;
                    break;
                }
            } catch (NumberFormatException e) {
                breakCheck(choice);
            }
            System.out.println("Invalid choice, try again...");
        }
    }

    public static void getIdToDelete() {
        while (true) {
            System.out.println("Enter the ID of the edit to remove its visibility");
            String choice = scanner.nextLine();
            try {
                int chosen = Integer.parseInt(choice);
                editContainer.delete(chosen);
                break;
            } catch (NumberFormatException e) {
                breakCheck(choice);
            } catch (Exception e) {
                // pass
            }
            System.out.println("Invalid choice, try again...");
        }
    }

    public static void readInputText() {
        System.out.println(currentPrompt);
        StringBuilder sb = new StringBuilder();
        boolean accept = true;
        while (accept) {
            String input = scanner.nextLine();
            breakCheck(input);
            if (input.contains(endOfInput)) {
                input = input.substring(0, input.indexOf(endOfInput));
                accept = false;
            }
            sb.append(input);
        }
        boolean isAddition = (choice == 1);
        editContainer.create(sb.toString(), position, isAddition);
    }

    public static void breakCheck(String input) {
        if (input.contains(breaker)) {
            System.out.println("\n\nBreaker sequence entered. Exiting.");
            System.exit(0);
        }
    }
}
