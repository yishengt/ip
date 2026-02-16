package tyrone;

import com.sun.tools.javac.Main;

import javafx.application.Application;
import tyrone.Parser.Parser;
import tyrone.exception.TyroneException;
import tyrone.storage.Storage;
import tyrone.task.Deadline;
import tyrone.task.Event;
import tyrone.task.TaskList;
import tyrone.task.Todo;
import tyrone.ui.Ui;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Tyrone {

    private static final int COMMAND_INDEX = 0;
    private static final int FIRST_ARGUMENT_INDEX = 1;
    private static final int MIN_TASK_INDEX = 1;
    private static final int MIN_COMMAND_PARTS = 2;
    private static final int EXPECTED_SPLIT_PARTS = 2;
    private static final int TASK_LIST_OFFSET = 1;
    private static final int REGEX_NUMBER_GROUP = 2;
    private static final int INVALID_INDEX = -1;
    private static final String MARK_ONLY_REGEX = "^(mark) (\\d+)$";
    private static final String MARK_UNMARK_REGEX = "^(mark|unmark) (\\d+)$";

    private Storage storage;
    private TaskList tasks;
    private Parser parser;
    private Ui ui = new Ui();


    /**
     * Constructs a Tyrone instance with the specified file path for data storage.
     * Initializes the UI, parser, storage, and task list components.
     *
     * @param filePath The file path where task data should be stored.
     */

    public Tyrone(String filePath) {
        assert filePath != null : "File path cannot be null";
        assert !filePath.isEmpty() : "File path cannot be empty";

        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);
        storage.load();
        tasks = new TaskList();
    }

    /**
     * Starts the main application loop.
     *
     * <p>Continuously reads user input and processes commands
     * until the user enters "bye".</p>
     *
     * @throws TyroneException If an invalid command is encountered.
     */
    public void run() throws TyroneException {
        ui.showWelcome();
        String input = "";
        try {
            while (!input.equalsIgnoreCase("bye")) {
                input = ui.readInputs();
                if (input.isEmpty()) {
                    continue;
                }
                processCommand(input);
            }
        } catch (TyroneException e){
            System.out.println(e);
        }
    }


    /**
     * Processes a single user command.
     *
     * @param input The full command string entered by the user.
     * @throws TyroneException If the command is invalid or malformed.
     */
    private void processCommand(String input) throws TyroneException{
        String[] words = Parser.parse(input);
        String command = words[COMMAND_INDEX].toLowerCase();

        assert input != null : "Input cannot be null";
        assert !input.isEmpty() : "Input cannot be empty";

        if (markHandler(input)){
            ui.showTaskList(tasks);
            return;
        }

        switch(command){
        case "find":
            findHandler(words, command);
            break;
        case "delete":
            deleteHandler(words, command);
            break;
        case "todo":
            todoHandler(input);
            break;
        case "event":
            eventHandler(input);
            break;
        case "deadline":
            deadlineHandler(input);
            break;
        case "list":
            ui.showTaskList(tasks);
            break;
        default:
            throw new TyroneException(AsciiArt.getDefeatedFace());
        }
    }

    /**
     * Processes a mark and unmark user commands.
     *
     * @param input The full command string entered by the user.
     */
    private boolean markHandler(String input) {
        if (extractIndex(input) == INVALID_INDEX) {
            return false;
        }

        Pattern markRegex = Pattern.compile(MARK_UNMARK_REGEX);
        Matcher matcherValue = markRegex.matcher(input);

        if (matcherValue.matches()) {
            tasks.get(extractIndex(input) - TASK_LIST_OFFSET).mark();
        } else {
            tasks.get(extractIndex(input) - TASK_LIST_OFFSET).unmark();
        }
        return true;
    }

    private void findHandler(String[] words, String command){
        tasks.search(words[FIRST_ARGUMENT_INDEX]);
    }

    private void todoHandler(String input) throws TyroneException{
        String[] parts = input.split(" ", MIN_COMMAND_PARTS);

        assert parts.length > 0 : "Input is not a complete argument";

        if (parts.length < MIN_COMMAND_PARTS) {
            throw new TyroneException("Bruh todo what??");
        }

        Todo item = new Todo(parts[FIRST_ARGUMENT_INDEX]);
        tasks.add(item);
        ui.showTaskAdded(tasks, item);
        saveTask(tasks);
    }

    private void eventHandler(String input) throws TyroneException{
        String[] parts = input.split(" ", MIN_COMMAND_PARTS);

        if (parts.length < MIN_COMMAND_PARTS) {
            throw new TyroneException("Bruh event what??");
        }

        String[] eventParts = parts[FIRST_ARGUMENT_INDEX].split("/at", MIN_COMMAND_PARTS);

        if (eventParts.length != EXPECTED_SPLIT_PARTS) {
            throw new TyroneException(AsciiArt.getQuestionMark());
        }

        Event itemEvent =
                new Event(eventParts[COMMAND_INDEX].trim(), eventParts[FIRST_ARGUMENT_INDEX].trim());
        tasks.add(itemEvent);
        ui.showTaskAdded(tasks, itemEvent);
        saveTask(tasks);
    }

    private void deadlineHandler(String input) throws TyroneException{
        String[] parts = input.split(" ", MIN_COMMAND_PARTS);

        if (!parts[COMMAND_INDEX].equalsIgnoreCase("deadline")) {
            return;
        }

        if (parts.length < MIN_COMMAND_PARTS) {
            throw new TyroneException("Bruh deadline what??");
        }

        String[] deadlineParts = parts[FIRST_ARGUMENT_INDEX].split("/by", MIN_COMMAND_PARTS);

        if (deadlineParts.length != EXPECTED_SPLIT_PARTS || !isValidDate(deadlineParts[FIRST_ARGUMENT_INDEX].trim())) {
            throw new TyroneException(AsciiArt.getQuestionMark());
        }

        Deadline itemDeadline =
                new Deadline(deadlineParts[COMMAND_INDEX].trim(), deadlineParts[FIRST_ARGUMENT_INDEX].trim());
        tasks.add(itemDeadline);
        ui.showTaskAdded(tasks, itemDeadline);
        saveTask(tasks);
    }

    private void deleteHandler(String[] words) throws TyroneException {
        if (words.length != MIN_COMMAND_PARTS) {
            throw new TyroneException(AsciiArt.getQuestionMark());
        }

        int deleteIndex;

        try {
            deleteIndex = Integer.parseInt(words[FIRST_ARGUMENT_INDEX]);
        } catch (NumberFormatException e) {
            throw new TyroneException("Delete requires a number.");
        }

        if (deleteIndex < MIN_TASK_INDEX || deleteIndex > tasks.size()) {
            throw new TyroneException("Task number out of range.");
        }

        tasks.remove(deleteIndex - TASK_LIST_OFFSET);
        ui.showTaskDeleted(deleteIndex);
        saveTask(tasks);
    }

    private void saveTask(TaskList tasks){
        try{
            Storage.save(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidDate(String input) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static Integer extractIndex(String input) {
        Pattern markPattern = Pattern.compile("^(mark|unmark) (\\d+)$");
        Matcher matcher = markPattern.matcher(input);

        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(REGEX_NUMBER_GROUP));
        }
        return INVALID_INDEX;
    }

    public String getResponse(String input) {
        return "Tyrone heard: " + input;
    }
}
