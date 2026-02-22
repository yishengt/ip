package tyrone.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskList implements Iterable<Task>{
    private ArrayList<Task> arr;

    public TaskList(){
        this.arr = new ArrayList<>();
    }

    public int size(){
        return arr.size();
    }

    public Task getTask(int index){
        return this.arr.get(index);
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.arr = tasks;
    }

    public void add(Task task){
        arr.add(task);
    }

    /**
     * Searches for tasks that match the given search term.
     * Uses regex pattern matching to find tasks whose string representation
     * contains the specified object. All matching tasks are printed to the console.
     *
     * @param object The search term or regex pattern to match against task descriptions.
     */

    public String search(String object) {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile(object);

        for (Task task : this.arr) {
            Matcher matcher = pattern.matcher(task.toString());
            if (matcher.find()) {
                sb.append(task.toString()).append("\n");
            }
        }

        if (sb.isEmpty()) {
            return "No matching tasks found.";
        }

        return sb.toString();
    }


    /**
     * Returns an iterator over the tasks in this list.
     * Allows the TaskList to be used in enhanced for loops.
     *
     * @return An Iterator over the tasks.
     */

    @Override
    public Iterator<Task> iterator() {
        return arr.iterator();
    }

    public void remove(int index){
        arr.remove(index);
    }
}