import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class Duke {
    public static void main(String[] args) {
        SystemMessages.welcomeMessage();

        String line;
        Vector<String> dataStorage = new Vector<String>();
        Scanner in = new Scanner(System.in);
        do{
            line = in.nextLine().trim();

            if(line.toLowerCase().equals("list")){
                Iterator<String> i = dataStorage.iterator();
                int counter = 1;
                while(i.hasNext()){
                    System.out.println(counter + ": " + i.next());
                    counter++;
                }
            }else if(line.trim().equals("")){
                System.out.println("Please enter something.");
            }else if(!line.toLowerCase().equals("bye")){
                dataStorage.add(line);
                System.out.println("added: " + line);
            }
        }while(!line.toLowerCase().equals("bye"));

        SystemMessages.goodbyeMessage();
    }
}
