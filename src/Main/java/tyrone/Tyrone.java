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
        String command = words[0].toLowerCase();

        assert input != null : "Input cannot be null";
        assert !input.isEmpty() : "Input cannot be empty";

        if (markHandler(input)){
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
            listHandler(tasks);
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
    private Boolean markHandler(String input) {
        if (extractIndex(input) != -1) {
            Pattern markRegex = Pattern.compile("^(mark) (\\d+)$");
            Matcher matcherValue = markRegex.matcher(input);

            if (matcherValue.matches()) {
                this.tasks.get(extractIndex(input) - 1).mark();
            } else {
                this.tasks.get(extractIndex(input) - 1).unmark();
            }

            return true;
        } else {
            return false;
        }
    }


    private void findHandler(String[] words, String command){
        if (command.equalsIgnoreCase("find")){
            tasks.search(words[1]);
        }
    }

    private void todoHandler(String input) throws TyroneException{
        String[] parts = input.split(" ", 2);

        if (parts.length == 1) {
            throw new TyroneException("Bruh todo what??");
        }

        Todo item = new Todo(parts[1]);
        tasks.add(item);
        ui.showTaskAdded(tasks, item);
        saveTask(tasks);
    }

    private void eventHandler(String input) throws TyroneException{
        String[] parts = input.split(" ", 2);

        if (parts.length == 1) {
            throw new TyroneException("Bruh event what??");
        }

        String[] splits = parts[1].split("/at", 2);

        if (splits.length != 2) {
            throw new TyroneException(AsciiArt.getQuestionMark());
        }

        Event itemEvent =
                new Event(splits[0].trim(), splits[1].trim());

        tasks.add(itemEvent);
        ui.showTaskAdded(tasks, itemEvent);
        saveTask(tasks);
    }

    private void deadlineHandler(String input) throws TyroneException{
        String[] parts = input.split(" ", 2);

        if (parts[0].equalsIgnoreCase("deadline")) {

            if (parts.length == 1) {
                throw new TyroneException("Bruh deadline what??");
            }

            String[] d = parts[1].split("/by", 2);
            LocalDate d1 = LocalDate.parse(d[1]);

            if (d.length != 2 || !isValidDate(d[1].trim())) {
                throw new TyroneException(AsciiArt.getQuestionMark());
            }

            Deadline itemDeadLine =
                    new Deadline(d[0].trim(), d[1].trim());

            tasks.add(itemDeadLine);
            ui.showTaskAdded(tasks, itemDeadLine);
            saveTask(tasks);
        }
    }

    private void listHandler(TaskList tasks){
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private void deleteHandler(String[] words, String command) throws TyroneException{
        int deleteIndex;

        if (words.length != 2) {
                throw new TyroneException(AsciiArt.getQuestionMark());
            }

            try {
                deleteIndex = Integer.parseInt(words[1]);
            } catch (NumberFormatException e) {
                throw new TyroneException("Delete requires a number.");
            }

            if (deleteIndex < 1 || deleteIndex > tasks.size()) {
                throw new TyroneException("Task number out of range.");
            }

            tasks.remove(deleteIndex - 1);
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
        Pattern p = Pattern.compile("^(mark|unmark) (\\d+)$");
        Matcher m = p.matcher(input);

        if (m.matches()) {
            return Integer.parseInt(m.group(2));
        }
        return -1;
    }

    public String getResponse(String input) {
        return "Tyrone heard: " + input;
    }
}
