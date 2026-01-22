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
            try {
                isRunning = handleCommand(input);
            } catch(OmegaException e) {
                printWrapped(e.getMessage());
                isRunning = true;
            }
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

    public static boolean handleCommand(String input) throws OmegaException {
        input = input.trim();
        if (input.isEmpty())
            throw new OmegaException("Please give me a specific command.");

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
                Task t = createTask(command, args);
                addTask(t);
                yield true;
            }
            default -> throw new OmegaException("Sorry, your command doesn't seem to make very much sense.");
        };
    }

    private static Task createTask(String command, String args) throws OmegaException {
        return switch (command) {
            case "todo" -> parseTodo(args);
            case "deadline" -> parseDeadline(args);
            case "event" -> parseEvent(args);
            default -> null; // won't ever hit this path
        };
    }

    private static Task parseTodo(String description) throws OmegaException {
        if (description.trim().isEmpty()) {
            throw new OmegaException("Usage: todo <description>");
        }
        return new Todo(description);
    }

    private static Task parseDeadline(String args) throws OmegaException {
        String[] parts = args.split("\\s+/by\\s+", 2);
        if (parts.length < 2) {
            throw new OmegaException("Usage: deadline <description> /by <by>");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new OmegaException("Usage: deadline <description> /by <by>");
        }

        return new Deadline(description, by);
    }

    private static Task parseEvent(String args) throws OmegaException {
        String[] first = args.split("\\s+/from\\s+", 2);
        if (first.length < 2) {
            throw new OmegaException("Usage: event <description> /from <from> /to <to>");
        }

        String description = first[0].trim();
        String rest = first[1].trim();
        String[] second = rest.split("\\s+/to\\s+", 2);
        if (second.length < 2) {
            throw new OmegaException("Usage: event <description> /from <from> /to <to>");
        }

        String from = second[0].trim();
        String to = second[1].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new OmegaException("Usage: event <description> /from <from> /to <to>");
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

    private static void handleMarking(String args, boolean isDone) throws OmegaException {
        if (args.isEmpty()) {
            throw new OmegaException("Usage: mark N | unmark N");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(args) - 1;

        } catch (NumberFormatException e) {
            throw new OmegaException("Provide valid task list number");
        }

        if (taskNumber < 0 || taskNumber >= tasks.size())
            throw new OmegaException("Out of bounds");

        Task t = tasks.get(taskNumber);
        if (isDone) {
            t.markDone();
            printWrapped("I've marked this task as done:", "  " + t);
        } else {
            t.unmarkDone();
            printWrapped("I've marked this task as not done yet:", " " + t);
        }
    }
}
