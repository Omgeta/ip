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
     *
     * @return Welcome message as String
     */
    public String showWelcome() {
        String output = "Greetings, my name is\n" + LOGO + "\nHow may I be of assistance?";
        System.out.println(output);
        return output;
    }

    /**
     * Displays the goodbye message to the user.
     *
     * @return Bye message as String
     */
    public String showBye() {
        String output = "Au revoir.";
        System.out.println(output);
        return output;
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
     * @return Error message as String
     */
    public String showError(String message) {
        String output = "Unfortunately, I ran into an error:\n" + message;
        System.out.println(output);
        return output;
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param t    The task that was added.
     * @param size The current size of the task list.
     * @return Added message as String
     */
    public String showAdded(Task t, int size) {
        String output = "I've added the task:\n " + t + "\nNow you have " + size + " tasks in the list";
        System.out.println(output);
        return output;
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param t    The task that was deleted.
     * @param size The current size of the task list.
     * @return Deleted message as String
     */
    public String showDeleted(Task t, int size) {
        String output = "I've removed the task:\n " + t + "\nNow you have " + size + " tasks in the list";
        System.out.println(output);
        return output;
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param t The task that was marked.
     * @return Marked message as String
     */
    public String showMarked(Task t) {
        String output = "I've marked the task as done: " + t;
        System.out.println(output);
        return output;
    }

    /**
     * Displays a message indicating that a task has been unmarked as not done.
     *
     * @param t The task that was unmarked.
     * @return Unmarked message as String
     */
    public String showUnmarked(Task t) {
        String output = "I've unmarked the task as not done: " + t;
        System.out.println(output);
        return output;
    }

    /**
     * Displays the list of tasks to the user.
     *
     * @param tasks The task list to be displayed
     * @return List message as String
     */
    public String showList(TaskList tasks) {
        String output;
        if (tasks.isEmpty()) {
            output = "Task list is empty";
        } else {
            output = "Task list:\n" + tasks;
        }
        System.out.println(output);
        return output;
    }

    /**
     * Displays filtered list of tasks to the user.
     *
     * @param keyword Keyword filtered by
     * @param tasks   The task list to be displayed.
     * @return Find message as String
     */
    public String showFindList(String keyword, TaskList tasks) {
        String output;
        if (tasks.isEmpty()) {
            output = "Failed to find any matches for " + keyword;
        } else {
            output = "Here are the matches for " + keyword + ":\n" + tasks;
        }
        System.out.println(output);
        return output;
    }
}
