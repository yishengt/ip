
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tyrone {
    public static void main(String[] args) throws Exception {
        String art =
                "      _____                _____                    _____                   _______                   _____                    _____          \n" +
                        "     /\\\\    \\\\              |\\\\    \\\\                  /\\\\    \\\\                 /::\\\\    \\\\                 /\\\\    \\\\                  /\\\\    \\\\         \n" +
                        "    /::\\\\    \\\\             |:\\\\____\\\\                /::\\\\    \\\\               /::::\\\\    \\\\               /::\\\\____\\\\                /::\\\\    \\\\        \n" +
                        "    \\\\:::\\\\    \\\\            |::|   |               /::::\\\\    \\\\             /::::::\\\\    \\\\             /::::|   |               /::::\\\\    \\\\       \n" +
                        "     \\\\:::\\\\    \\\\           |::|   |              /::::::\\\\    \\\\           /::::::::\\\\    \\\\           /:::::|   |              /::::::\\\\    \\\\      \n" +
                        "      \\\\:::\\\\    \\\\          |::|   |             /:::/\\\\:::\\\\    \\\\         /:::/~~\\\\:::\\\\    \\\\         /::::::|   |             /:::/\\\\:::\\\\    \\\\     \n" +
                        "       \\\\:::\\\\    \\\\         |::|   |            /:::/__\\\\:::\\\\    \\\\       /:::/    \\\\:::\\\\    \\\\       /:::/|::|   |            /:::/__\\\\:::\\\\    \\\\    \n" +
                        "       /::::\\\\    \\\\        |::|   |           /::::\\\\   \\\\:::\\\\    \\\\     /:::/    / \\\\:::\\\\    \\\\     /:::/ |::|   |           /::::\\\\   \\\\:::\\\\    \\\\   \n" +
                        "      /::::::\\\\    \\\\       |::|___|______    /::::::\\\\   \\\\:::\\\\    \\\\   /:::/____/   \\\\:::\\\\____\\\\   /:::/  |::|   | _____    /::::::\\\\   \\\\:::\\\\    \\\\  \n" +
                        "     /:::/\\\\:::\\\\    \\\\      /::::::::\\\\    \\\\  /:::/\\\\:::\\\\   \\\\:::\\\\____\\\\ |:::|    |     |:::|    | /:::/   |::|   |/\\\\    \\\\  /:::/\\\\:::\\\\   \\\\:::\\\\    \\\\ \n" +
                        "    /:::/  \\\\:::\\\\____\\\\    /::::::::::\\\\____\\\\/:::/  \\\\:::\\\\   \\\\:::|    ||:::|____|     |:::|    |/:: /    |::|   /::\\\\____\\\\/:::/__\\\\:::\\\\   \\\\:::\\\\____\\\\\n" +
                        "   /:::/    \\\\::/    /   /:::/~~~~/~~      \\\\::/   |::::\\\\  /:::|____| \\\\:::\\\\    \\\\   /:::/    / \\\\::/    /|::|  /:::/    /\\\\:::\\\\   \\\\:::\\\\   \\\\::/    /\n" +
                        "  /:::/    / \\\\/____/   /:::/    /          \\\\/____|:::::\\\\/:::/    /   \\\\:::\\\\    \\\\ /:::/    /   \\\\/____/ |::| /:::/    /  \\\\:::\\\\   \\\\:::\\\\   \\\\/____/ \n" +
                        " /:::/    /           /:::/    /                 |:::::::::/    /     \\\\:::\\\\    /:::/    /            |::|/:::/    /    \\\\:::\\\\   \\\\:::\\\\    \\\\     \n" +
                        "/:::/    /           /:::/    /                  |::|\\\\::::/    /       \\\\:::\\\\__/:::/    /             |::::::/    /      \\\\:::\\\\   \\\\:::\\\\____\\\\    \n" +
                        "\\\\::/    /            \\\\::/    /                   |::| \\\\::/____/         \\\\::::::::/    /              |:::::/    /        \\\\:::\\\\   \\\\::/    /    \n" +
                        " \\\\/____/              \\\\/____/                    |::|  ~|                \\\\::::::/    /               |::::/    /          \\\\:::\\\\   \\\\/____/     \n" +
                        "                                                 |::|   |                 \\\\::::/    /                /:::/    /            \\\\:::\\\\    \\\\         \n" +
                        "                                                 \\\\::|   |                  \\\\::/____/                /:::/    /              \\\\:::\\\\____\\\\        \n" +
                        "                                                  \\\\:|   |                   ~~                      \\\\::/    /                \\\\::/    /        \n" +
                        "                                                   \\\\|___|                                            \\\\/____/                  \\\\/____/         \n";

        System.out.println(art);


        String banner =
                " Hello! I'm Tyrone\n" +
                        " What can I do for you?\n";

        System.out.println(banner);
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        String input = "";

        while (!input.equalsIgnoreCase("bye")) {
            input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            Integer idx = extractIndex(input.toLowerCase());

            if (idx != -1) {

                if (idx < 1 || idx > tasks.size()) {
                    System.out.println("Invalid task number.");
                    continue;
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


            if (input.equalsIgnoreCase("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                continue;
            }

            String[] parts = input.split(" ");
            if(parts.length == 1){
                throw new TyroneException("Bruh " + parts[0] + " what??");
            }
            if (parts[0].equalsIgnoreCase("todo")) {
                Todo item = new Todo(parts[1]);
                tasks.add(item);
                System.out.println("Got it. I've added this task:");
                System.out.println(item.toString());
                System.out.println("Now you have " + tasks.size() + " tasks in the list");

                continue;
            }

            if (parts[0].equalsIgnoreCase("deadline")) {
                String[] d = parts[1].split("/by", 2);
                Deadline itemDeadLine = new Deadline(d[0].trim(), d[1].trim());
                System.out.println("Got it. I've added this task:");
                tasks.add(itemDeadLine);
                System.out.println(itemDeadLine.toString());
                System.out.println("Now you have " + tasks.size() + " tasks in the list");

                continue;
            }

            if (parts[0].equalsIgnoreCase("event")) {
                String[] e = parts[1].split("/at", 2);
                Event itemEvent = new Event(e[0].trim(), e[1].trim());
                System.out.println("Got it. I've added this task:");
                tasks.add(itemEvent);
                System.out.println(itemEvent.toString());
                System.out.println("Now you have " + tasks.size() + " tasks in the list");

                continue;
            } else {
                throw new TyroneException("I dunno this command bro\n");
            }

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
