package tyrone.Parser;

public class Parser {

    public static String[] parse(String input) {
        return input.split(" ", 2);
    }

    public static String getCommand(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0].toLowerCase();
    }

    public static String getArguments(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}