package omega.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import omega.OmegaException;
import omega.command.AddCommand;
import omega.command.Command;
import omega.command.DeleteCommand;
import omega.command.ExitCommand;
import omega.command.ListCommand;
import omega.command.MarkCommand;
import omega.command.UnmarkCommand;

/**
 * Test class for Parser.
 */
public class ParserTest {
    /**
     * Tests that valid command inputs are parsed into the expected Command types.
     * 
     * @throws Exception If parsing fails unexpectedly.
     */
    @Test
    public void parse_validCommandInput_returnsExpectedCommandType() throws Exception {
        Object[][] cases = {
                { "bye", ExitCommand.class },
                { "list", ListCommand.class },

                { "mark 1", MarkCommand.class },
                { "unmark 1", UnmarkCommand.class },
                { "delete 1", DeleteCommand.class },

                { "todo borrow book", AddCommand.class },
                { "deadline return book /by 2019-10-15", AddCommand.class },
                { "event project meeting /from 2019-10-15 /to 2019-10-16", AddCommand.class }
        };

        for (Object[] c : cases) {
            String input = (String) c[0];
            @SuppressWarnings("unchecked")
            Class<? extends Command> expected = (Class<? extends Command>) c[1];

            Command cmd = Parser.parse(input);
            assertTrue(expected.isInstance(cmd));
        }
    }

    /**
     * Tests that the "bye" command is recognized as an exit command.
     * 
     * @throws Exception
     */
    @Test
    public void parse_bye_isExit() throws Exception {
        Command cmd = Parser.parse("bye");
        assertTrue(cmd.isExit());
    }

    /**
     * Tests that invalid command inputs throw OmegaException.
     */
    @Test
    public void parse_emptyInput_throwsException() {
        assertThrows(OmegaException.class, () -> Parser.parse(" "));
    }

    /**
     * Tests that unrecognized commands throw OmegaException.
     */
    @Test
    public void parse_invalidCommand_throwsException() {
        assertThrows(OmegaException.class, () -> Parser.parse("foo"));
    }

    /**
     * Tests that missing index for commands that require an index throw
     * OmegaException.
     */
    @Test
    public void parse_missingIndex_throwsException() {
        assertThrows(OmegaException.class, () -> Parser.parse("mark"));
    }

    /**
     * Tests that non-numeric index for commands that require an index throw
     */
    @Test
    public void parse_nonNumericIndex_throwsException() {
        assertThrows(OmegaException.class, () -> Parser.parse("mark abc"));
    }

    /**
     * Tests that missing description for todo command throws OmegaException.
     */
    @Test
    public void parse_deadlineMissingBy_throwsException() {
        assertThrows(OmegaException.class, () -> Parser.parse("deadline return book"));
    }

    /**
     * Tests that missing description or date for deadline command throws.
     */
    @Test
    public void parse_eventMissingFromTo_throwsException() {
        String[] invalidInputs = {
                "event project meeting",
                "event project meeting /from 2019-10-15",
                "event project meeting /to 2019-10-16",
                "event project meeting /from  /to 2019-10-16",
                "event project meeting /from 2019-10-15 /to ",
                "event /from 2019-10-15 /to 2019-10-16",
                "event project meeting /from2019-10-15 /to 2019-10-16",
                "event project meeting /from 2019-10-15 /to 2019-10-16 /to 2019-10-17"
        };
        for (String input : invalidInputs) {
            assertThrows(OmegaException.class, () -> Parser.parse(input));
        }
    }
}
