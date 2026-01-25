package omega.ui;

import java.util.Scanner;

import omega.task.Task;
import omega.task.TaskList;

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

    public void showWelcome() {
        System.out.println("Greetings, my name is\n" + LOGO + "\nHow may I be of assistance?");
    }

    public void showBye() {
        System.out.println("Au revoir.");
    }

    public String readCommand() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println("Unfortunately, I ran into an error:\n" + message);
    }

    public void showAdded(Task t, int size) {
        System.out.println("I've added the task:\n " + t + "\nNow you have " + size + " tasks in the list");
    }

    public void showDeleted(Task t, int size) {
        System.out.println("I've removed the task:\n " + t + "\nNow you have " + size + " tasks in the list");
    }

    public void showMarked(Task t) {
        System.out.println("I've marked the task as done: " + t);
    }

    public void showUnmarked(Task t) {
        System.out.println("I've unmarked the task as not done: " + t);
    }

    public void showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("omega.task.Task list is empty");
        } else {
            System.out.println("omega.task.Task list:\n" + tasks);
        }
    }
}
