package tyrone;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Comparator;
import javafx.application.Application;
import tyrone.Parser.Parser;
import tyrone.exception.TyroneException;
import tyrone.storage.Storage;
import tyrone.task.Deadline;
import tyrone.task.Event;
import tyrone.task.Task;
import tyrone.task.TaskList;
import tyrone.task.Todo;
import tyrone.ui.Ui;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Main class for the Tyrone task management application.
 *
 * <p>Tyrone is a chatbot-based task manager that allows users to create, manage,
 * and organize tasks including todos, deadlines, and events. It provides features
 * for adding, deleting, marking, searching, and sorting tasks through a command-line
 * or GUI interface.</p>
 *
 * <p>The application supports persistent storage, allowing tasks to be saved and
 * loaded across sessions. It uses a simple text-based command system for user
 * interaction.</p>
 *
 * <p>Supported commands:
 * <ul>
 *     <li>todo - Add a simple task</li>
 *     <li>deadline - Add a task with a deadline</li>
 *     <li>event - Add an event task</li>
 *     <li>list - Display all tasks</li>
 *     <li>mark/unmark - Toggle task completion status</li>
 *     <li>delete - Remove a task</li>
 *     <li>find - Search for tasks</li>
 *     <li>sort - Sort deadlines by date</li>
 * </ul>
 * </p>
 *
 * @author Toh Yi Sheng
 * @version 1.0
 * @since 1.0

 */
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
    private static final String MARK_UNMARK_REGEX = "^(mark) (\\d+)$";

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
    public String processCommand(String input) throws TyroneException{
        String[] words = Parser.parse(input);
        String command = words[COMMAND_INDEX].toLowerCase();

        assert input != null : "Input cannot be null";
        assert !input.isEmpty() : "Input cannot be empty";

        if (markHandler(input)){
            return ui.showTaskList(tasks);
        }

        try {
            switch (command) {
            case "find":
                return findHandler(words, command);
            case "delete":
                return deleteHandler(words);
            case "todo":
                todoHandler(input);
                return ui.showTaskList(tasks);
            case "event":
                eventHandler(input);
                return ui.showTaskList(tasks);
            case "deadline":
                deadlineHandler(input);
                return ui.showTaskList(tasks);
            case "list":
                return ui.showTaskList(tasks);
            case "sort":
                sortHandler(tasks);
                return ui.showTaskList(tasks);
            default:
                throw new TyroneException(AsciiArt.getDefeatedFace());
            }
        } catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * Handles mark and unmark commands for toggling task completion status.
     *
     * <p>Uses regex pattern matching to identify mark/unmark commands and extract
     * the task index. Marks tasks as done if the command is "mark", otherwise
     * unmarks them.</p>
     *
     * @param input The full command string (e.g., "mark 1" or "unmark 2")
     * @return {@code true} if the input was a valid mark/unmark command, {@code false} otherwise
     */
    private boolean markHandler(String input) {
        if (extractIndex(input) == INVALID_INDEX) {
            return false;
        }

        Pattern markRegex = Pattern.compile(MARK_UNMARK_REGEX);
        Matcher matcherValue = markRegex.matcher(input);

        if (matcherValue.matches()) {
            tasks.getTask(extractIndex(input) - TASK_LIST_OFFSET).mark();
        } else {
            tasks.getTask(extractIndex(input) - TASK_LIST_OFFSET).unmark();
        }
        return true;
    }

    /**
     * Handles the find command to search for tasks containing a keyword.
     *
     * @param words The parsed command words array
     * @param command The command name (expected to be "find")
     * @return A string containing all matching tasks
     */
    private String findHandler(String[] words, String command){
        return tasks.search(words[FIRST_ARGUMENT_INDEX]);
    }

    /**
     * Handles the todo command to add a simple task.
     *
     * <p>Creates a new {@link Todo} task with the provided description,
     * adds it to the task list, saves the updated list, and displays
     * a confirmation message.</p>
     *
     * @param input The full command string (e.g., "todo read book")
     * @throws TyroneException If the description is missing
     * @throws AssertionError if the input parts array is empty (when assertions are enabled)
     */
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

    /**
     * Handles the event command to add an event task.
     *
     * <p>Creates a new {@link Event} task with the provided description and time,
     * using the "/at" delimiter to separate them. Saves and displays the result.</p>
     *
     * @param input The full command string (e.g., "event meeting /at 2pm")
     * @throws TyroneException If the description, time, or delimiter is missing
     */
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

    /**
     * Handles the deadline command to add a deadline task.
     *
     * <p>Creates a new {@link Deadline} task with the provided description and date,
     * using the "/by" delimiter. Validates the date format before creating the task.</p>
     *
     * @param input The full command string (e.g., "deadline homework /by 15-03-2024")
     * @throws TyroneException If the description, date, delimiter is missing, or date format is invalid
     */
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

    /**
     * Handles the delete command to remove a task from the list.
     *
     * @param words The parsed command words array
     * @return A confirmation message indicating which task was deleted
     * @throws TyroneException If the command format is invalid, the index is not a number,
     *                         or the index is out of range
     */
    private String deleteHandler(String[] words) throws TyroneException {
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
        saveTask(tasks);
        return ui.showTaskDeleted(deleteIndex);
    }

    /**
     * Saves the current task list to persistent storage.
     *
     * @param tasks The task list to be saved
     * @throws RuntimeException If an I/O error occurs during saving
     */
    private void saveTask(TaskList tasks){
        try{
            Storage.save(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sorts the tasks in the given TaskList by deadline date in chronological order.
     * <p>
     * This method separates Deadline tasks from other task types,
     * sorts the deadlines based on their {@code by} date (earliest first),
     * and then combines the sorted deadlines with the remaining tasks.
     * Tasks without deadlines retain their original relative order
     * and are placed after the sorted deadlines.
     *
     * @param tasks The TaskList containing tasks to be sorted.
     */
    private void sortHandler(TaskList tasks){
        ArrayList<Deadline> deadlineArrayList = new ArrayList<>();
        ArrayList<Task> taskArrayList = new ArrayList<>();
        ArrayList<Task> finalArrayList = new ArrayList<>();

        for(int i = 0; i < tasks.size(); i++){
            if (tasks.getTask(i) instanceof Deadline){
                deadlineArrayList.add((Deadline) tasks.getTask(i));
            } else {
                taskArrayList.add(tasks.getTask(i));
            }
        }

        deadlineArrayList.sort(Comparator.comparing(Deadline::getBy));
        finalArrayList.addAll(deadlineArrayList);
        finalArrayList.addAll(taskArrayList);
        tasks.setTasks(finalArrayList);
    }

    /**
     * Validates whether a date string matches the expected format.
     *
     * <p>The expected format is "dd-MM-yyyy" (e.g., "15-03-2024" for March 15, 2024).</p>
     *
     * @param input The date string to validate
     * @return {@code true} if the date is valid and matches the format, {@code false} otherwise
     */
    public static boolean isValidDate(String input) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Extracts the task index from mark or unmark commands.
     *
     * <p>Uses regex pattern matching to identify valid mark/unmark commands
     * and extract the numeric task index.</p>
     *
     * @param input The command string (e.g., "mark 1" or "unmark 3")
     * @return The task index as an integer, or {@code INVALID_INDEX} (-1) if not a valid command
     */
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
