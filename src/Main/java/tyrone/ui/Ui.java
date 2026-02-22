package tyrone.ui;

import tyrone.AsciiArt;
import tyrone.task.Task;
import tyrone.task.TaskList;

import java.util.*;

public class Ui{
    private Scanner scanner;

    public Ui(){
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome(){
        System.out.println(AsciiArt.getArt());
        System.out.println("Hello! Im Tyronne your chatbot");
        System.out.println("How can I help you :)");

    }

    public void showTaskAdded(TaskList tasks, Task item){
        System.out.println("Got it. I've added this task:");
        System.out.println(item);
        System.out.println("Now you have " + tasks.size() + " tasks in the list");
    }

    public String showTaskDeleted(int deleteIndex){
        return ("Deleted task " + deleteIndex);
    }

    public void showTaskMarked(){

    }

    public void showGoodBye(){
        System.out.println("Bye. Hope to see you again soon!");
    }

    public String readInputs(){
        return scanner.nextLine().trim();
    }

    public String showTaskList(TaskList taskList){
        StringBuilder sb = new StringBuilder();
        int index = 1;

        for (Task task : taskList) {
            sb.append(index).append(". ").append(task.toString()).append("\n");
            index++;
        }

        return sb.toString();
    }
}