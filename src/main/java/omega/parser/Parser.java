package omega.parser;

import omega.OmegaException;
import omega.command.AddCommand;
import omega.command.Command;
import omega.command.DeleteCommand;
import omega.command.ExitCommand;
import omega.command.FindCommand;
import omega.command.ListCommand;
import omega.command.MarkCommand;
import omega.command.UnmarkCommand;
import omega.task.Deadline;
import omega.task.Event;
import omega.task.Task;
import omega.task.Todo;

/**
 * Parses user input strings into corresponding Command objects.
 */
public class Parser {
    /**
     * Parses the given input string and returns the corresponding Command object.
     *
     * @param input User input string.
     * @return Corresponding Command object.
     * @throws OmegaException If the input is invalid or cannot be parsed.
     */
    public static Command parse(String input) throws OmegaException {
        if (input == null || input.trim().isEmpty()) {
            throw new OmegaException("Please enter a command.");
        }

        String[] parts = input.trim().split("\\s+", 2);
        String cmdCode = parts[0].toLowerCase();
        String args = (parts.length == 2) ? parts[1].trim() : "";

        Command cmd;
        switch (cmdCode) {
        case "bye" -> cmd = new ExitCommand();
        case "list" -> cmd = new ListCommand();

        case "mark" -> cmd = new MarkCommand(parseIndex(args, "mark"));
        case "unmark" -> cmd = new UnmarkCommand(parseIndex(args, "unmark"));
        case "delete" -> cmd = new DeleteCommand(parseIndex(args, "delete"));
        case "find" -> cmd = new FindCommand(parseKeyword(args));

        case "todo" -> cmd = new AddCommand(parseTodo(args));
        case "deadline" -> cmd = new AddCommand(parseDeadline(args));
        case "event" -> cmd = new AddCommand(parseEvent(args));

        default -> throw new OmegaException("I don't understand that command.");
        }

        return cmd;
    }

    private static String parseKeyword(String args) throws OmegaException {
        if (args.isEmpty()) {
            throw new OmegaException("Usage: find <keyword>");
        }
        return args;
    }

    private static int parseIndex(String args, String cmd) throws OmegaException {
        if (args.isEmpty()) {
            throw new OmegaException("Usage: " + cmd + " <taskNumber>");
        }
        try {
            return Integer.parseInt(args.trim()) - 1; // convert to 0-based
        } catch (NumberFormatException e) {
            throw new OmegaException("the task number must be a number.");
        }
    }

    private static Task parseTodo(String desc) throws OmegaException {
        if (desc.isEmpty()) {
            throw new OmegaException("Usage: todo <description>");
        }
        return new Todo(desc);
    }

    private static Task parseDeadline(String args) throws OmegaException {
        String[] parts = args.split("\\s+/by\\s+", 2);
        if (parts.length < 2) {
            throw new OmegaException("Usage: deadline <description> /by <by>");
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();

        if (desc.isEmpty()) {
            throw new OmegaException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new OmegaException("The /by part of a deadline cannot be empty.");
        }

        return new Deadline(desc, by); // if you did Level 8, call omega.task.Deadline.fromUserInput(desc, by)
    }

    private static Task parseEvent(String args) throws OmegaException {
        String[] first = args.split("\\s+/from\\s+", 2);
        if (first.length < 2) {
            throw new OmegaException("Usage: event <description> /from <from> /to <to>");
        }

        String desc = first[0].trim();
        String rest = first[1].trim();

        String[] second = rest.split("\\s+/to\\s+", 2);
        if (second.length < 2) {
            throw new OmegaException("Usage: event <description> /from <from> /to <to>");
        }

        String from = second[0].trim();
        String to = second[1].trim();

        if (desc.isEmpty()) {
            throw new OmegaException("The description of an event cannot be empty.");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new OmegaException("event must have both /from and /to.");
        }

        return new Event(desc, from, to);
    }
}
