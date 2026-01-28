
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.nio.file.*;


public class Tyrone {
    public static void main(String[] args) throws Exception {

        System.out.println(AsciiArt.getArt());
        String banner =
                " Hello! I'm Tyrone\n" +
                        " What can I do for you?\n";

        System.out.println(banner);
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        String input = "";

        while (!input.equalsIgnoreCase("bye")) {

            input = sc.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            try {

                if (input.equalsIgnoreCase("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                Integer idx = extractIndex(input.toLowerCase());

                if (idx != -1) {

                    if (idx < 1 || idx > tasks.size()) {
                        throw new TyroneException("Invalid task number.");
                    }

                    Task t = tasks.get(idx - 1);

                    if (input.startsWith("mark")) {
                        t.mark();
                        System.out.println("Nice! I've marked this task as done:");
                    } else {
                        t.unmark();
                        System.out.println("OK, I've marked this task as not done yet:");
                    }

                    System.out.println(t);
                    continue;
                }

                String[] words = input.split(" ", 2);

                if (words[0].equalsIgnoreCase("delete")) {

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
                    System.out.println("Deleted task " + deleteIndex);
                    save(tasks);
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

                    System.out.println("Got it. I've added this task:");
                    System.out.println(item);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                    save(tasks);
                    continue;
                }

                // ---------- DEADLINE ----------
                if (parts[0].equalsIgnoreCase("deadline")) {

                    if (parts.length == 1) {
                        throw new TyroneException("Bruh deadline what??");
                    }

                    String[] d = parts[1].split("/by", 2);

                    if (d.length != 2 || !isValidDate(d[1].trim())) {
                        throw new TyroneException(AsciiArt.getQuestionMark());
                    }

                    Deadline itemDeadLine =
                            new Deadline(d[0].trim(), d[1].trim());

                    tasks.add(itemDeadLine);

                    System.out.println("Got it. I've added this task:");
                    System.out.println(itemDeadLine);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                    save(tasks);
                    continue;
                }

                if (parts[0].equalsIgnoreCase("event")) {

                    if (parts.length == 1) {
                        throw new TyroneException("Bruh event what??");
                    }

                    String[] e = parts[1].split("/at", 2);

                    if (e.length != 2) {
                        throw new TyroneException(AsciiArt.getQuestionMark());
                    }

                    Event itemEvent =
                            new Event(e[0].trim(), e[1].trim());

                    tasks.add(itemEvent);

                    System.out.println("Got it. I've added this task:");
                    System.out.println(itemEvent);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list");
                    save(tasks);
                    continue;
                }

                throw new TyroneException(AsciiArt.getDefeatedFace());

            } catch (TyroneException e) {
                System.out.println(e.getMessage());
            }
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

    public static void save(ArrayList<Task> arr) throws IOException {
        try {
            FileWriter writer = new FileWriter("data/tyrone.txt");

            for(Task a : arr) {
                writer.write(a.toString());
                writer.write("\n");
            }

            writer.close();

        } catch (Exception e){
            System.out.print(e);
        }
    }

}
