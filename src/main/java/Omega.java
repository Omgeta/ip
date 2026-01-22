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
        if (input.isEmpty()) return true;

        String[] parts = input.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String args = (parts.length == 2) ? parts[1].trim() : "";

        switch (command) {
            case "bye":
                return false;
            case "list":
                printTaskList();
                return true;
            case "mark":
                handleMarking(args, true);
                return true;
            case "unmark":
                handleMarking(args, false);
                return true;
            default:
                tasks.add(new Task(input));
                printWrapped("added: " + input);
                return true;
        }
    }

    public static void printTaskList() {
        if (tasks.isEmpty()) {
            printWrapped("Task list empty.");
            return;
        }

        String[] lines = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            lines[i] = (i+1) + "." + tasks.get(i);
        }
        printWrapped(lines);
    }

    private static void handleMarking(String args, boolean isDone) {
        if (args.isEmpty()) {
            printWrapped("Usage: mark N | unmark N");
            return;
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(args) - 1;
            if (taskNumber < 0 || taskNumber >= tasks.size())
                throw new NumberFormatException("Out of bounds");
        } catch (NumberFormatException e) {
            printWrapped("Provide valid task list number");
            return;
        }

        Task t = tasks.get(taskNumber);
        if (isDone) {
            t.markDone();
            printWrapped("Nice! I've marked this task as done:", "  " + t);
        } else {
            t.unmarkDone();
            printWrapped("OK, I've marked this task as not done yet:", " " + t);
        }
    }
}
