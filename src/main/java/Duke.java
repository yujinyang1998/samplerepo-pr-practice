import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class Duke {
    public static void main(String[] args) {
        SystemMessages.welcomeMessage();

        boolean breakCheck = false;
        String input,firstWord;
        Vector<Task> dataStorage = new Vector<Task>();
        int counter = 1;
        Scanner in = new Scanner(System.in);

        while(breakCheck == false) {
            input = in.nextLine().trim();
            firstWord = input.split(" ",2)[0];

            switch (firstWord.toLowerCase()) {
                case "bye":
                    breakCheck = true;
                    break;

                case "":
                    System.out.println("Please enter something.");
                    break;

                case "list":
                    Iterator<Task> i = dataStorage.iterator();
                    int listCounter = 1;
                    while(i.hasNext()){
                        Task printTask = i.next();
                        System.out.println(listCounter + ".[" + printTask.getStatusIcon() + "] " + printTask.description);
                        listCounter++;
                    }
                    break;

                case "done":
                    if(input.split(" ").length>2 || !input.split(" ")[1].matches("^-?\\d+$")){
                        System.out.println("Invalid input.");
                        break;
                    }
                    int completedIndex = Integer.parseInt(input.split(" ")[1].trim());

                    if(completedIndex <= counter && !dataStorage.get(completedIndex - 1).isDone){
                        Task replacement = new Task(dataStorage.get(completedIndex-1).description);
                        replacement.isDone = true;
                        dataStorage.set(completedIndex-1,replacement);
                        System.out.println("Nice! I've marked this task as done: ");
                        System.out.println("[" + dataStorage.get(completedIndex-1).getStatusIcon() + "]" + dataStorage.get(completedIndex-1).description);
                    }else if(completedIndex <= counter && dataStorage.get(completedIndex - 1).isDone){
                        System.out.println("The task has already been marked as completed.");
                    }else{
                        System.out.println("This task does not exist.");
                    }
                    break;

                default:
                    Task newTask = new Task(input);
                    dataStorage.add(newTask);
                    System.out.println("added: " + input);
                    counter++;
            }
        }

        SystemMessages.goodbyeMessage();
    }
}
