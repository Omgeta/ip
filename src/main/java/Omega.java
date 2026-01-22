import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Omega {
    private static final String LINE = "____________________________________________________________";
    private static final String LOGO = """
                  ___  _ __ ___   ___  __ _  __ _ 
                 / _ \\| '_ ` _ \\ / _ \\/ _` |/ _` |
                | (_) | | | | | |  __/ (_| | (_| |
                 \\___/|_| |_| |_|\\___|\\__, |\\__,_|
                                       __/ |      
                                      |___/       
                """;
    private static final List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        printWrapped(
                "Hello! I'm",
                LOGO,
                "How may I be of assistance?"
        );

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while(isRunning) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            isRunning = handleCommand(input);
        }

        printWrapped("Au revoir!");
    }

    public static void printWrapped(String... messages) {
        System.out.println(LINE);
        for (String msg: messages) {
            System.out.println(msg);
        }
        System.out.println(LINE);
    }

    public static boolean handleCommand(String input) {
        input = input.trim();
        if (input.equalsIgnoreCase("bye")) {
            return false;
        }

        if (input.equalsIgnoreCase("list")) {
            printTaskList();
            return true;
        }

        if (!input.isEmpty()) {
            tasks.add(new Task(input));
            printWrapped("added: " + input);
            return true;
        }

        return true;
    }

    public static void printTaskList() {
        if (tasks.isEmpty()) {
            printWrapped("Task list empty.");
            return;
        }

        String[] lines = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            lines[i] = (i+1) + ". " + tasks.get(i);
        }
        printWrapped(lines);
    }
}
