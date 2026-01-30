package tyrone.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * Manages a collection of tasks.
 * Provides methods to add, remove, retrieve, and search tasks.
 * Implements Iterable to allow iteration over the task list.
 */

public class TaskList implements Iterable<Task>{
    private ArrayList<Task> arr;

    /**
     * Constructs an empty TaskList.
     * Initializes the internal ArrayList to store tasks.
     */

    public TaskList(){
        this.arr = new ArrayList<>();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The total number of tasks currently in the list.
     */
    public int size(){
        return this.arr.size();
    }


    /**
     * Retrieves the task at the specified index.
     *
     * @param index The index of the task to retrieve (0-based).
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task get(int index){
        return this.arr.get(index);

    }

    /**
     * Add a task into TaskList.
     *
     * @param task The Task to be added into a ArrayList<Task>
     */

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

    @Override
    public Iterator<Task> iterator() {
        return arr.iterator();
    }


    /**
     * Removes the task at the specified index from the list.
     *
     * @param index The index of the task to remove (0-based).
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public void remove(int index){
        this.arr.remove(index);
    }

}



