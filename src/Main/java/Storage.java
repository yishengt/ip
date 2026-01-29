
import java.io.*;

public class Storage{
    private final String path;

    public Storage(String path){
        this.path = path;
    }

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
