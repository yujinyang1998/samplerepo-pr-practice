package duke;

import duke.exception.DukeException;
import duke.task.Task;

import java.util.Vector;

public class Parser {
    public static int checkInput(String firstWord, String input, Vector<Task> dataStorage, int counter) throws DukeException {
        switch (firstWord.toLowerCase()) {
            case "bye":
                Duke.breakCheck = true;
                break;

            case "":
                Ui.userPrompt();
                break;

            case "list":
                TaskList.printList(dataStorage, counter);
                break;

            case "done":
                TaskList.markAsDone(input, dataStorage, counter);
                break;

            case "delete":
                counter = TaskList.deleteTask(input, dataStorage, counter);
                break;

            default:
                counter = TaskList.addTask(input, dataStorage, counter);
                break;
        }
        return counter;
    }
}
