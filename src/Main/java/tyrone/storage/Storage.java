package tyrone.storage;

import tyrone.task.Task;
import tyrone.task.TaskList;

import java.io.*;

/**
 * Handles the loading and saving of task data to and from a file.
 * Provides file-based persistence for the task list, allowing tasks
 * to be stored and retrieved between application sessions.
 */

public class Storage{
    private final String path;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param path The file path where task data will be stored and loaded from.
     */

    public Storage(String path){
        this.path = path;
    }

    /**
     * Saves the task list to a file.
     * Writes each task in the list to the file "data/tyrone.txt", with each task
     * on a separate line. The task is saved using its string representation.
     *
     * @param arr The TaskList containing all tasks to be saved.
     * @throws IOException If an I/O error occurs during file writing.
     */

    public static void save(TaskList arr) throws IOException {
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

    /**
     * Loads and displays task data from the file.
     * Reads each line from the file specified by the path and prints it to
     * the console. This method is typically called during application startup
     * to restore previously saved tasks.
     * If the file does not exist or an I/O error occurs, the error is printed
     * to the console.
     */

    public void load(){
        try {
            FileReader fileReader = new FileReader(this.path);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
