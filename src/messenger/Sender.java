package messenger;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Thread, wait for user input and send it
 */
public class Sender extends Thread {
    private Messenger msgr;

    protected Sender(Messenger msgr) {
        this.msgr = msgr;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        while (true) {
            String message = scanner.nextLine();
            msgr.sendMessage(message);
        }
    }
}