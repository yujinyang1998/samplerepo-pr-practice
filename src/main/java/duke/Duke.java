package duke;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Duke {
    public static void main(String[] args) throws IOException {
        Ui.welcomeMessage();

        boolean breakCheck = false;
        String input,firstWord;
        Vector<Task> dataStorage = new Vector<>();
        int counter = 1;
        Scanner in = new Scanner(System.in);

        Storage file = new Storage();
        counter = file.openFile(dataStorage,counter);
        while(!breakCheck) {
            input = in.nextLine().trim();
            firstWord = input.split(" ",2)[0];

            try {
                switch (firstWord.toLowerCase()) {
                case "bye":
                    breakCheck = true;
                    break;

                case "":
                    Ui.userPrompt();
                    break;

                case "list":
                    System.out.println(counter);
                    printList(dataStorage, counter);
                    break;

                case "done":
                    markAsDone(input, dataStorage, counter);
                    break;

                case "delete":
                    counter = TaskList.deleteTask(input, dataStorage, counter);
                    break;

                default:
                    counter = TaskList.addTask(input, dataStorage, counter);
                    break;
                }
            } catch (DukeException e){
                System.out.println(e.description);
            }
            file.updateFile(dataStorage,counter);
        }
        Ui.goodbyeMessage();
    }

    private static void markAsDone(String input, Vector<Task> dataStorage, int counter) throws DukeException{
        if(input.trim().toLowerCase().equals("done")) {
            throw new DukeException("No number entered");
        }
        if(counter <= 1){
            throw new DukeException("Please add something to the list first.");
        }

        int completedIndex = Integer.parseInt(input.split(" ")[1].trim());

        //checking for invalid input
        if(input.split(" ").length>2 || !input.split(" ")[1].matches("^-?\\d+$") || completedIndex<0){
            throw new DukeException("Invalid input.");
        }

        if(completedIndex <= counter && !dataStorage.get(completedIndex - 1).isDone){
            if(dataStorage.get(completedIndex-1) instanceof Event){
                Event replacement = new Event(dataStorage.get(completedIndex-1).description,  ((Event) dataStorage.get(completedIndex-1)).at);
                replacement.isDone = true;
                dataStorage.set(completedIndex-1,replacement);
            }else if(dataStorage.get(completedIndex-1) instanceof ToDo){
                ToDo replacement = new ToDo(dataStorage.get(completedIndex-1).description);
                replacement.isDone = true;
                dataStorage.set(completedIndex-1,replacement);
            }else if(dataStorage.get(completedIndex-1) instanceof Deadline){
                Deadline replacement = new Deadline(dataStorage.get(completedIndex-1).description, ((Deadline) dataStorage.get(completedIndex-1)).by);
                replacement.isDone = true;
                dataStorage.set(completedIndex-1,replacement);
            }else{
                Task replacement = new Task(dataStorage.get(completedIndex-1).description);
                replacement.isDone = true;
                dataStorage.set(completedIndex-1,replacement);
            }
            Ui.markedAsDone();
            System.out.println(dataStorage.get(completedIndex-1).toString());
        }else if(completedIndex <= counter && dataStorage.get(completedIndex - 1).isDone){
            throw new DukeException("The task has already been marked as completed.");
        }else{
            throw new DukeException("This task does not exist.");
        }
    }


    private static void printList(Vector<Task> dataStorage,int counter) throws DukeException{
        if(counter <= 0){
            throw new DukeException("Please add something to the list.");
        }
        for (int i = 0; i<counter-1; i++){
            System.out.println((i+1) + "." + dataStorage.get(i).toString());
        }
    }

}
