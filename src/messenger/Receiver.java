package messenger;

import messagebox.MessageBox;
import messagebox.SuperMessage;

/**
 * check for incoming messages and show them
 */
public class Receiver extends Thread {

    private MessageBox messageBox;
    private Messenger msgr;


    protected Receiver(Messenger msgr) {
        this.msgr = msgr;
        this.messageBox = msgr.messageBox;
    }

    @Override
    public void run() {

        while (true) {
            if (messageBox.hasMessages()){
                SuperMessage smsg = messageBox.getNextMessage();
                String sender = smsg.getMachine().getAddress().getHostAddress() + ":" + smsg.getMachine().getPort();
                msgr.printMessage( sender, smsg.getMessage().getMsg());
            }
        }
    }
}

