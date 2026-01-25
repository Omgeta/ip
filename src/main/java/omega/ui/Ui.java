package omega.ui;

import java.util.Scanner;

import omega.task.Task;
import omega.task.TaskList;

/**
 * Handles user interactions, including displaying messages and reading input.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String LOGO = """
          ___  _ __ ___   ___  __ _  __ _
         / _ \\| '_ ` _ \\ / _ \\/ _` |/ _` |
        | (_) | | | | | |  __/ (_| | (_| |
         \\___/|_| |_| |_|\\___|\\__, |\\__,_|
                               __/ |
                              |___/
        """;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Greetings, my name is\n" + LOGO + "\nHow may I be of assistance?");
    }

    /**
     * Displays the goodbye message to the user.
     */
    public void showBye() {
        System.out.println("Au revoir.");
    }

    /**
     * Reads a command from the user input.
     * 
     * @return The command entered by the user.
     */
    public String readCommand() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays a horizontal line for formatting.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays an error message to the user.
     * 
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println("Unfortunately, I ran into an error:\n" + message);
    }

    /**
     * Displays a message indicating that a task has been added.
     * 
     * @param t    The task that was added.
     * @param size The current size of the task list.
     */
    public void showAdded(Task t, int size) {
        System.out.println("I've added the task:\n " + t + "\nNow you have " + size + " tasks in the list");
    }

    /**
     * Displays a message indicating that a task has been deleted.
     * 
     * @param t    The task that was deleted.
     * @param size The current size of the task list.
     */
    public void showDeleted(Task t, int size) {
        System.out.println("I've removed the task:\n " + t + "\nNow you have " + size + " tasks in the list");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     * 
     * @param t The task that was marked.
     */
    public void showMarked(Task t) {
        System.out.println("I've marked the task as done: " + t);
    }

    /**
     * Displays a message indicating that a task has been unmarked as not done.
     * 
     * @param t The task that was unmarked.
     */
    public void showUnmarked(Task t) {
        System.out.println("I've unmarked the task as not done: " + t);
    }

    /**
     * Displays the list of tasks to the user.
     * 
     * @param tasks The task list to be displayed.
     */
    public void showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty");
        } else {
            System.out.println("Task list:\n" + tasks);
        }
    }

    public void showFindList(String keyword, TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Failed to find any matches for " + keyword);
        } else {
            System.out.println("Here are the matches for " + keyword + ":\n" + tasks);
        }
    }
}
