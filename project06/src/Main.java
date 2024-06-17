import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a simple bot communication manager for testing
        SimpleBotCommunicationManager botCommManager = new SimpleBotCommunicationManager();

        // Create a user interaction manager with the bot communication manager
        UserInteractionManager userInteractionManager = new UserInteractionManager(botCommManager);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("User: ");
            String userMessage = scanner.nextLine();

            if (userMessage.equalsIgnoreCase("exit")) {
                break;
            }

            userInteractionManager.transmitUserMessage(userMessage);
        }

        scanner.close();
    }
}
