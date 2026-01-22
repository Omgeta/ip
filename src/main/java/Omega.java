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

        return switch (command) {
            case "bye" -> false;
            case "list" -> {
                printTaskList();
                yield true;
            }
            case "mark" -> {
                handleMarking(args, true);
                yield true;
            }
            case "unmark" -> {
                handleMarking(args, false);
                yield true;
            }
            case "todo", "event", "deadline" -> {
                try {
                    Task t = createTask(command, args);
                    addTask(t);
                } catch (IllegalArgumentException e) {
                    printWrapped(e.getMessage());
                }
                yield true;
            }
            default -> {
                printWrapped("Sorry, your command doesn't seem to make very much sense.");
                yield true;
            }
        };
    }

    private static Task createTask(String command, String args) {
        return switch (command) {
            case "todo" -> parseTodo(args);
            case "deadline" -> parseDeadline(args);
            case "event" -> parseEvent(args);
            default -> null; // won't ever hit this path
        };
    }

    private static Task parseTodo(String description) {
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: todo <description>");
        }
        return new Todo(description);
    }

    private static Task parseDeadline(String args) {
        String[] parts = args.split("\\s+/by\\s+", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Usage: deadline <description> /by <by>");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new IllegalArgumentException("Usage: deadline <description> /by <by>");
        }

        return new Deadline(description, by);
    }

    private static Task parseEvent(String args) {
        String[] first = args.split("\\s+/from\\s+", 2);
        if (first.length < 2) {
            throw new IllegalArgumentException("Usage: event <description> /from <from> /to <to>");
        }

        String description = first[0].trim();
        String rest = first[1].trim();
        String[] second = rest.split("\\s+/to\\s+", 2);
        if (second.length < 2) {
            throw new IllegalArgumentException("Usage: event <description> /from <from> /to <to>");
        }

        String from = second[0].trim();
        String to = second[1].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException("Usage: event <description> /from <from> /to <to>");
        }

        return new Event(description, from, to);
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

    private static void addTask(Task task) {
        tasks.add(task);
        printWrapped(
            "As requested, I've added the task:",
            "  " + task,
            "Now you have " + tasks.size() + " tasks in the list."
        );
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
