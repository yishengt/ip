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
        return arr.get(index);

    }

    public void add(Task task){
        arr.add(task);
    }


    @Override
    public Iterator<Task> iterator() {
        return arr.iterator();
    }

    public void remove(int index){
        arr.remove(index);
    }

}