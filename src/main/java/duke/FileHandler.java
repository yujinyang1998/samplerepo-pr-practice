package duke;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class FileHandler {

    private static File file = null;
    private static Scanner scanner;

    public static int openFile(Vector<Task> dataStorage, int counter){
        try{
            file = new File("Saves/Duke.txt");
            file.createNewFile();
            counter = readFile(dataStorage,counter);
        } catch (IOException e) {
            System.out.println("Error, Directory not found.");
        }
        return counter;
    }


    public static void updateFile(Vector<Task> dataStorage, int counter) throws IOException {
        FileWriter fw = new FileWriter(file.getPath());
        for (int i = 0; i < counter - 1; i++) {
            fw.write(dataStorage.get(i).toString());
            if(i != counter-1) {
                fw.write("\n");
            }
        }
        fw.close();
    }

    public static int readFile(Vector<Task> dataStorage,int counter) throws FileNotFoundException {
        Scanner s = new Scanner(file); // create a Scanner using the File as the source
        dataStorage.clear();
        while (s.hasNextLine()) {
            String line  = s.nextLine();
            Boolean isDone = (line.charAt(4) == '\u2713');
            String formattedLine = line.substring(6).trim();
            String description = formattedLine.split("\\(",2)[0].trim();

            switch (line.toUpperCase().charAt(1)){
                case 'T':
                    ToDo toDo = new ToDo(description);
                    toDo.isDone = isDone;
                    dataStorage.add(toDo);
                    counter++;
                    break;
                case 'E':
                    String at = formattedLine.split("at:",2)[1].substring(0,(formattedLine.split("at:",2)[1].length()-1));
                    Event event = new Event(description, at.trim());
                    event.isDone = isDone;
                    dataStorage.add(event);
                    counter++;
                    break;
                case 'D':
                    String by = formattedLine.split("by:",2)[1].substring(0,(formattedLine.split("by:",2)[1].length()-1));
                    Deadline deadline = new Deadline(description, by.trim());
                    deadline.isDone = isDone;
                    dataStorage.add(deadline);
                    counter++;
                    break;
                default:

            }
        }
        return counter;
    }

}
