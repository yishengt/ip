package tyrone.Parser;
/**
 * Handles parsing of user input commands in the Tyrone application.
 *
 * <p>The Parser class provides static utility methods to break down user input
 * strings into their component parts (command and arguments). This enables the
 * application to interpret and execute user commands correctly.</p>
 *
 * <p>Example usage:
 * <pre>
 * String input = "todo read book";
 * String[] words = Parser.parse(input);           // ["todo", "read", "book"]
 * String command = Parser.getCommand(input);      // "todo"
 * String args = Parser.getArguments(input);       // "read book"
 * </pre>
 * </p>
 *
 * @author yishengt
 * @version 1.0
 * @since 1.0
 */
public class Parser {

    /**
     * Parses the input string and splits it into individual words.
     *
     * <p>The input is split by whitespace characters into an array of words.
     * This method is useful for simple tokenization of user input.</p>
     *
     * @param input The user input string to be parsed
     * @return An array of strings containing each word from the input
     * @throws NullPointerException if input is null
     *
     * @see String#split(String)
     */
    public static String[] parse(String input) {
        return input.split(" ");
    }

    /**
     * Extracts the command word from the user input.
     *
     * <p>The command is the first word of the input string, converted to lowercase
     * for case-insensitive command matching. For example, "TODO buy milk" returns "todo".</p>
     *
     * @param input The user input string containing the command
     * @return The command word in lowercase, or the entire input if no spaces exist
     *
     * @see #getArguments(String)
     */
    public static String getCommand(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0].toLowerCase();
    }

    /**
     * Extracts the arguments portion from the user input.
     *
     * <p>Returns everything after the first word (command) in the input string.
     * If the input contains only a command with no arguments, an empty string
     * is returned.</p>
     *
     * <p>Examples:
     * <ul>
     *     <li>"todo read book" → "read book"</li>
     *     <li>"deadline submit report /by 2024-03-15" → "submit report /by 2024-03-15"</li>
     *     <li>"list" → ""</li>
     * </ul>
     * </p>
     *
     * @param input The user input string containing command and arguments
     * @return The arguments portion of the input, or empty string if no arguments exist
     *
     * @see #getCommand(String)
     */
    public static String getArguments(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}