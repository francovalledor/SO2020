package messagebox;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.Map;

public class MessageOutBox {
    private Queue<SuperMessage> outgoing;
    private HashMap<UUID, SuperMessage> unconfirmed;
    private int maxLength;
    private DatagramSocket socket;
    
    /**
     * Outgoing message box
     */
    protected MessageOutBox(DatagramSocket socket) {
        this.socket = socket;
        this.maxLength = Config.maxLength;
        this.outgoing = new LinkedList<SuperMessage>();
        this.unconfirmed = new HashMap<UUID, SuperMessage>();

        // daemons
        ThreadSender thSender = new ThreadSender(this);
        thSender.start();
    }

    /**
     * queue new message to send
     */
    protected void enqueue(Machine machine, Message message) {
        SuperMessage smsg = new SuperMessage(machine, message);
        outgoing.add(smsg);
    }

    /**
     * queue new message to send
     */
    protected void enqueue(SuperMessage smsg) {
        outgoing.add(smsg);
    }

    /**
     * confirm that the message was received
     */
    protected void confirm(UUID uuid) {
        unconfirmed.remove(uuid);
    }

    /**
     * are there messages to send?
     */
    protected boolean hasMessage() {
        return !outgoing.isEmpty();
    }

    /**
     * Send next message in queue
     */
    protected void sendNext() {
        SuperMessage smsg = outgoing.remove();
        smsg.setTimestamp();
        send(smsg);
        unconfirmed.put(smsg.message.getId(), smsg);
    }

    /**
     * put in outgoing queue message that have not exceeded maxAttempts
     */
    protected void reEnqueueExpired() {
        for (Map.Entry<UUID, SuperMessage> entry : unconfirmed.entrySet()) {
            SuperMessage smsg = entry.getValue();
            if (smsg.isExpired()) {
                if (smsg.getAttempts() < Config.maxAttempts) {
                    smsg.incAttempts();
                    smsg.setTimestamp();
                    outgoing.add(smsg);
                }
                unconfirmed.remove(entry.getKey());
            }
        }
    }

    /**
     * low level message sender (private)
     */
    private void send(SuperMessage smsg) {
        try {
            DatagramPacket udpPacket;
            InetAddress destinationAddress = smsg.machine.getAddress();
            int destinationPort = smsg.machine.getPort();
            byte[] buffer = smsg.message.toByteArray();

            if (buffer.length > maxLength) {
                throw new Exception("buffer overflow");
            }
            udpPacket = new DatagramPacket(buffer, buffer.length, destinationAddress, destinationPort);
            socket.send(udpPacket);

        } catch (Exception e) {
            System.err.println("Error sending datagram");
        } 
    }

    /**
     * Convert a Message into a ACK and respond it
     */
    protected void sendACK(SuperMessage smsg) {
        SuperMessage newSmsg = new SuperMessage(smsg.machine, smsg.message.makeACK());
        send(newSmsg);
    }
}
