package tests.component.model;

import base.model.Edit;
import base.model.EditContainer;
import base.model.GroupContainer;

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
    private static GroupContainer groupContainer = new GroupContainer(editContainer);


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
                if (choice == 1 || choice == 2) {
                    getInputPosition();
                }
            }
            switch(choice) {
                case 1:
                case 2:
                    readInputText();
                    break;
                case 3:
                    getIdToDelete();
                    break;
                case 4:
                    editContainer.undo();
                    break;
                case 5:
                    getIdToUndo();
                    break;
                case 6:
                    getGroupNameToCreate();
                    break;
                case 7:
                    getGroupNameToDelete();
                    break;
                case 8:
                    addSingleEditToGroup();
                default:
                    break;
            }
            groupContainer.update();
            System.out.println(editContainer.stringRepr());
            System.out.println(groupContainer.stringRepr());
            System.out.println("\n");
        }
    }

    public static void getInputPosition() {
        while (true) {
            System.out.println("Enter the position of the edit (use -1 to insert at the end):");
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

    public static void getGroupNameToCreate() {
        System.out.println("Enter the name of the group to create:");
        String name = scanner.nextLine();
        if (name.endsWith("##")) {
            name = name.substring(0, name.length() - 2);
        }
        groupContainer.create(name);
    }

    public static void addSingleEditToGroup() {
        System.out.println("Enter the name of the group:");
        String name = scanner.nextLine();
        if (name.endsWith("##")) {
            name = name.substring(0, name.length() - 2);
        }
        int choiceNum = 0;
        while (true) {
            System.out.println("Enter the ID of the edit to add to group: " + name);
            String choice = scanner.nextLine();
            try {
                choiceNum = Integer.parseInt(choice);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid choice....");
            }
        }
        groupContainer.add(name, choiceNum);
    }

    public static void getGroupNameToDelete() {
        System.out.println("Enter the name of the group to create:");
        String name = scanner.nextLine();
        if (name.endsWith("##")) {
            name = name.substring(0, name.length() - 2);
        }
        groupContainer.removeGroup(name);
    }

    public static void makeChoice() {
        while (true) {
            System.out.println("Enter one of the following numbers:");
            System.out.println("  1. Add text");
            System.out.println("  2. Remove text");
            System.out.println("  3. Make an edit no longer visible");
            System.out.println("  4. Undo the most recent edit");
            System.out.println("  5. Undo an edit by ID");
            System.out.println("  6. Create a new EditGroup");
            System.out.println("  7. Delete an EditGroup");
            System.out.println("  8. Add an edit to a group (look in Container representation for ID)");
            String choice = scanner.nextLine();
            try {
                int chosen = Integer.parseInt(choice);
                if (chosen > 0 && chosen < 9) {
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

    public static void getIdToUndo() {
        while (true) {
            System.out.println("Enter the ID of the edit to undo it");
            String choice = scanner.nextLine();
            try {
                int chosen = Integer.parseInt(choice);
                editContainer.undoById(chosen);
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
