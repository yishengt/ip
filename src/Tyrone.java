
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
        String input = "";
        Scanner sc = new Scanner(System.in);
        ArrayList<Pair<Integer, String>> arr = new ArrayList<>();

        Integer arrIndex = 0;

        while (!input.equalsIgnoreCase("exit")) {
            System.out.print("> ");          // Command prompt
            input = sc.nextLine();     // Read user input

            String markPattern = "^mark (\\d+)$";
            String unmarkPattern = "^unmark (\\d+)$";

            Integer command = processCommand(input);
            if(command < 2){
                char[] charArr = input.toCharArray();
                Character index = charArr[charArr.length - 1];
                arrIndex = Character.getNumericValue(index);
                if(command == 0){
                    arr.get(arrIndex - 1).setFirst(0);
                    input = "mark";
                } else if (command == 1) {
                    arr.get(arrIndex - 1).setFirst(1);
                    input = "unmark";
                }

            }

            switch (input.toLowerCase()) {

                case "mark":
                    System.out.println("Nice! I've marked this task as done");
                    System.out.println("[X] " + arr.get(arrIndex -1).getSecond());
                    break;
                case "unmark":
                    System.out.println("K, I've marked this task as not done yet:\n");
                    System.out.println("[ ] " + arr.get(arrIndex -1).getSecond());
                    break;
                case "list":
                    System.out.println("list");
                    break;
                case "break":
                    System.out.println("break");
                    break;
                case "blah":
                    System.out.println("blah");
                    break;
                case "return list":
                    for(int i = 1; i<arr.size()+1; i++){
                        if(arr.get(i-1).getFirst() == 1){
                            System.out.print(i);
                            System.out.print(". ");
                            System.out.print("[ ] ");
                            System.out.println(arr.get(i-1).getSecond());
                        } else {
                            System.out.print(i);
                            System.out.print(". ");
                            System.out.print("[X] ");
                            System.out.println(arr.get(i-1).getSecond());
                        }

                    }
                    break;
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                default:
                    Pair<Integer, String> temp = new Pair<Integer, String>(1, input);
                    arr.add(temp);
                    System.out.println("added " + input);
            }
        }

        sc.close();
    }

    public static Integer processCommand(String input) {
        Pattern markPattern = Pattern.compile("^mark (\\d+)$");
        Pattern unmarkPattern = Pattern.compile("^unmark (\\d+)$");

        Matcher markMatcher = markPattern.matcher(input);
        Matcher unmarkMatcher = unmarkPattern.matcher(input);

        if (markMatcher.matches()) {
           return 0;
        } else if (unmarkMatcher.matches()) {
            return 1;
        } else {
            return 2;
        }
    }
}
