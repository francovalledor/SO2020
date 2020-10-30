package messagebox;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Class for send a receive messages
 */
public class MessageBox {
    private DatagramSocket socket;
    private MessageInBox inBox;
    private MessageOutBox outBox;

    public MessageBox(int port) {
        socket = Socket.getInstance(port).getDatagramSocket();
        outBox = new MessageOutBox(socket);
        inBox = new MessageInBox(socket, outBox);
    }

    /**
     * Send a message
     * @param address
     * @param port
     * @param message
     */
    public void send(InetAddress address, int port, String message){
        Message msg = new Message(message);
        Machine machine = new Machine(address, port);
        SuperMessage smsg = new SuperMessage(machine, msg);
        outBox.enqueue(smsg);
    }

    /**
     * Send a message
     * @param machine
     * @param message
     */
    public void send(Machine machine, String message){
        send(machine.getAddress(), machine.getPort(), message);
    }

    /**
     * are there new messages?
     */
    public boolean hasMessages(){
        return inBox.hasMessages();
    }

    /**
     * return next incoming message
     */
    public SuperMessage getNextMessage(){
        return inBox.getNextMessage();
    }   
}
