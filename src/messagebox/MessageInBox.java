package messagebox;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Incoming message box
 */
public class MessageInBox {

    private Queue<SuperMessage> incoming;
    private MessageOutBox outBox;

    protected MessageInBox(DatagramSocket socket, MessageOutBox outBox) {
        this.outBox = outBox;
        this.incoming = new LinkedList<SuperMessage>();

        //daemons
        ThreadReceiver thReceiver = new ThreadReceiver(socket, this);
        thReceiver.start();
    }
    
    /**
     * Add new incoming message
     */
    protected void add(byte[] binaryMsg, InetAddress address, int port) {
        try {
            Message message = Message.fromByteArray(binaryMsg);
            Machine machine = new Machine(address, port);
            SuperMessage smsg = new SuperMessage(machine, message);
            if (message.isACK()){
                outBox.confirm(message.getId());
            } else {
                incoming.add(smsg);
                outBox.sendACK(smsg);
            }

        } catch (Exception e) {
            //Invalid message...discard...
        }
    }

    /**
     * are there messages in the inbox?
     */
    protected boolean hasMessages(){
        return !incoming.isEmpty();
    }

    /**
     * return next message in queue 
     */
    protected SuperMessage getNextMessage(){
        return incoming.remove();
    }
}
