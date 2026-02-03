package tyrone.task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList implements Iterable<Task>{
    private ArrayList<Task> arr;

    public TaskList(){
        this.arr = new ArrayList<>();
    }

    public int size(){
        return arr.size();
    }

    public Task get(int index){
<<<<<<< Updated upstream
        return arr.get(index);

=======
        return this.arr.get(index);
>>>>>>> Stashed changes
    }

    public void add(Task task){
        arr.add(task);
    }


<<<<<<< Updated upstream
=======
    /**
     * Searches for tasks that match the given search term.
     * Uses regex pattern matching to find tasks whose string representation
     * contains the specified object. All matching tasks are printed to the console.
     *
     * @param object The search term or regex pattern to match against task descriptions.
     */

    public void search(String object){
        Pattern pattern = Pattern.compile(object);
        for(Task tasks : this.arr){
            Matcher matcher = pattern.matcher(tasks.toString());
            if (matcher.find()) {
                System.out.println(tasks.toString());
            }
        }
    }

    /**
     * Returns an iterator over the tasks in this list.
     * Allows the TaskList to be used in enhanced for loops.
     *
     * @return An Iterator over the tasks.
     */

>>>>>>> Stashed changes
    @Override
    public Iterator<Task> iterator() {
        return arr.iterator();
    }

    public void remove(int index){
        arr.remove(index);
    }

}