package duke;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Duke {
    public static void main(String[] args) throws IOException {
        SystemMessages.welcomeMessage();

        boolean breakCheck = false;
        String input,firstWord;
        Vector<Task> dataStorage = new Vector<>();
        int counter = 1;
        Scanner in = new Scanner(System.in);

        FileHandler file = new FileHandler();
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
                    SystemMessages.userPrompt();
                    break;

                case "list":
                    printList(dataStorage, counter);
                    break;

                case "done":
                    markAsDone(input, dataStorage, counter);
                    break;

                case "delete":
                    counter = deleteTask(input, dataStorage, counter);
                    break;

                default:
                    counter = addTask(input, dataStorage, counter);
                    break;
                }
            } catch (DukeException e){
                System.out.println(e.description);
            }
            file.updateFile(dataStorage,counter);
        }
        SystemMessages.goodbyeMessage();
    }

    private static int deleteTask(String input, Vector<Task> dataStorage, int counter) throws DukeException{
        if(input.trim().toLowerCase().equals("delete")) {
            throw new DukeException("No number entered");
        }
        if(counter <= 1){
            throw new DukeException("Please add something to the list first.");
        }

        int deleteIndex = Integer.parseInt(input.split(" ")[1].trim());

        //checking for invalid input
        if(input.split(" ").length>2 || !input.split(" ")[1].matches("^-?\\d+$") || deleteIndex<=0){
            throw new DukeException("Invalid input.");
        }
        
        if(deleteIndex <= counter) {
            SystemMessages.removedTask();
            System.out.println(dataStorage.get(deleteIndex-1).toString());
            dataStorage.remove(deleteIndex - 1);
            counter--;
            SystemMessages.tasksCounter(counter - 1);
            return counter;
        }else{
            throw new DukeException("This task does not exist.");
        }
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
            SystemMessages.markedAsDone();
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

    private static int addTask(String input, Vector<Task> dataStorage, int counter) throws DukeException{
        switch (input.toLowerCase().split(" ")[0]){
        case "todo":
            ToDo newToDo = new ToDo(input.split("todo",2)[1].trim());
            dataStorage.add(newToDo);
            SystemMessages.addedTask();
            System.out.println(dataStorage.get(counter - 1).toString());
            SystemMessages.tasksCounter(counter);
            counter++;
            break;
        case "deadline":
            Deadline newDeadline = new Deadline(input.split(" ",2)[1].split("/by",2)[0].trim(),input.split(" ",2)[1].split("/by",2)[1].trim());
            dataStorage.add(newDeadline);
            SystemMessages.addedTask();
            System.out.println(dataStorage.get(counter - 1).toString());
            SystemMessages.tasksCounter(counter);
            counter++;
            break;
        case "event":
            Event newEvent = new Event(input.split(" ",2)[1].split("/at",2)[0].trim(),input.split(" ",2)[1].split("/at",2)[1].trim());
            dataStorage.add(newEvent);
            SystemMessages.addedTask();
            System.out.println(dataStorage.get(counter - 1).toString());
            SystemMessages.tasksCounter(counter);
            counter++;
            break;
        default:
            throw new DukeException("Invalid Command");
        }
        return counter;
    }
}
