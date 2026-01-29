
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

    public Tyrone(String filePath) {
        ui = new Ui();
        Parser parser = new Parser();
        storage = new Storage("data/tyrone.txt");
        storage.load();

        tasks = new TaskList();
    }

    public void run(){
        ui.showWelcome();
        String input = "";

        while (!input.equalsIgnoreCase("bye")) {

            input = ui.readInputs();
            if (input.isEmpty()) {
                continue;
            }

            try {

                if (input.equalsIgnoreCase("bye")) {
                    ui.showGoodBye();
                    break;
                }

                String[] words = Parser.parse(input);
                String command = words[0].toLowerCase();

                if (command.equalsIgnoreCase("delete")) {

                    if (words.length != 2) {
                        throw new TyroneException(AsciiArt.getQuestionMark());
                    }

                    int deleteIndex;

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

                    try{
                        storage.save(tasks);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                if (input.equalsIgnoreCase("list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    continue;
                }

                String[] parts = input.split(" ", 2);

                if (parts[0].equalsIgnoreCase("todo")) {

                    if (parts.length == 1) {
                        throw new TyroneException("Bruh todo what??");
                    }

                    Todo item = new Todo(parts[1]);
                    tasks.add(item);
                    ui.showTaskAdded(tasks, item);

                    try{
                        Storage.save(tasks);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

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

                    try{
                        Storage.save(tasks);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                if (parts[0].equalsIgnoreCase("event")) {

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

                    try{
                        Storage.save(tasks);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                throw new TyroneException(AsciiArt.getDefeatedFace());

            } catch (TyroneException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public static void main(String[] args){
        new Tyrone("data/tyrone.txt").run();
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
}
