package duke;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.util.Vector;

public class TaskList {
    static int deleteTask(String input, Vector<Task> dataStorage, int counter) throws DukeException {
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
            Ui.removedTask();
            System.out.println(dataStorage.get(deleteIndex-1).toString());
            dataStorage.remove(deleteIndex - 1);
            counter--;
            Ui.tasksCounter(counter - 1);
            return counter;
        }else{
            throw new DukeException("This task does not exist.");
        }
    }

    static int addTask(String input, Vector<Task> dataStorage, int counter) throws DukeException{
        switch (input.toLowerCase().split(" ")[0]){
        case "todo":
            ToDo newToDo = new ToDo(input.split("todo",2)[1].trim());
            dataStorage.add(newToDo);
            Ui.addedTask();
            System.out.println(dataStorage.get(counter - 1).toString());
            Ui.tasksCounter(counter);
            counter++;
            break;
        case "deadline":
            Deadline newDeadline = new Deadline(input.split(" ",2)[1].split("/by",2)[0].trim(),input.split(" ",2)[1].split("/by",2)[1].trim());
            dataStorage.add(newDeadline);
            Ui.addedTask();
            System.out.println(dataStorage.get(counter - 1).toString());
            Ui.tasksCounter(counter);
            counter++;
            break;
        case "event":
            Event newEvent = new Event(input.split(" ",2)[1].split("/at",2)[0].trim(),input.split(" ",2)[1].split("/at",2)[1].trim());
            dataStorage.add(newEvent);
            Ui.addedTask();
            System.out.println(dataStorage.get(counter - 1).toString());
            Ui.tasksCounter(counter);
            counter++;
            break;
        default:
            throw new DukeException("Invalid Command");
        }
        return counter;
    }

    static void printList(Vector<Task> dataStorage, int counter) throws DukeException{
        if(counter <= 0){
            throw new DukeException("Please add something to the list.");
        }
        for (int i = 0; i<counter-1; i++){
            System.out.println((i+1) + "." + dataStorage.get(i).toString());
        }
    }

    static void markAsDone(String input, Vector<Task> dataStorage, int counter) throws DukeException{
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

    static void findTask(String input, Vector<Task> dataStorage, int counter) throws DukeException {
        if(counter <= 0){
            throw new DukeException("Please add something to the list.");
        }

        System.out.println("Here are the matching tasks in your list:");
        int printCounter = 1;

        for (int i = 0; i<counter-1; i++){
            String toCheck = new String(dataStorage.get(i).toString());
            if(toCheck.contains(input.substring(4).trim())){
                System.out.println((printCounter) + "." + dataStorage.get(i).toString());
                printCounter++;
            }
        }
    }
}
