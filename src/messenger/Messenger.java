package messenger;

import messagebox.Machine;
import messagebox.Message;
import messagebox.MessageBox;
import messagebox.MessageInBox;
import messagebox.MessageOutBox;

/**
 * app for send and receive msgs.
 * usage:
 *      Messenger msgr = new Messenger(port);
 *      msgr.setRecipient(destinationAddress, destinationPort);
 * 
 */
public class Messenger {
    int port;
    MessageBox messageBox;
    Machine recipient;
    Receiver receiver;
    Sender sender;

    public Messenger(int port) {
        this.port = port;
        messageBox = new MessageBox(port);

        //Threads
        receiver = new Receiver(this);
        receiver.start();

        sender = new Sender(this);
        sender.start();
    }

    public Messenger() {
        this(Config.defaultPort);
    }


    public void setRecipient(String address, int port){
        try {
            recipient = new Machine(address, port);
        } catch (Exception e) {
            System.err.println("Wrong address");
        }
    }


    public void sendMessage(String message){
        messageBox.send(recipient.getAddress(), recipient.getPort(), message);
        printMessage("Me", message);
    }

    protected void printMessage(String sender, String message) {
        String text = "";
        text += sender;
        text += "\n----------\n";
        text += message;
        text += "\n_______________________________\n";
        text += '\n';
        System.out.println(text);
    }
}
