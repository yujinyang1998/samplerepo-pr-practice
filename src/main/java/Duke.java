import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        SystemMessages.welcomeMessage();

        String line;
        Scanner in = new Scanner(System.in);
        do{
            line = in.nextLine().trim();
            System.out.println(line);

        }while(!line.toLowerCase().equals("bye"));

        SystemMessages.goodbyeMessage();
    }
}
