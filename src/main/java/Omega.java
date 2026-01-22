import java.util.Scanner;

public class Omega {
    private static final String LINE = "____________________________________________________________";
    private static final String LOGO = """
                  ___  _ __ ___   ___  __ _  __ _ 
                 / _ \\| '_ ` _ \\ / _ \\/ _` |/ _` |
                | (_) | | | | | |  __/ (_| | (_| |
                 \\___/|_| |_| |_|\\___|\\__, |\\__,_|
                                       __/ |      
                                      |___/       
                """;

    public static void main(String[] args) {
        printWrapped(
                "Hello! I'm",
                LOGO,
                "How may I be of assistance?"
        );

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("bye")) {
                break;
            }

            printWrapped(command);
        }

        printWrapped("Au revoir!");
    }

    public static void printWrapped(String... messages) {
        System.out.println(LINE);
        for (String msg: messages) {
            System.out.println(msg);
        }
        System.out.println(LINE);
    }
}
