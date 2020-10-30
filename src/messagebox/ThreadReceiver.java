package messagebox;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ThreadReceiver extends Thread {
    private DatagramSocket socket;
    private int length;
    private MessageInBox inBox;
    
    /**
     * Check for new incoming messages
     */
    protected ThreadReceiver(DatagramSocket socket, MessageInBox inBox) {
        this.socket = socket;
        this.length = Config.maxLength;
        this.inBox = inBox;
    }
    
    @Override
    public void run() {

        while (true) {

            byte buffer[] = new byte[length];            
            try {
                DatagramPacket udpPacket = new DatagramPacket( buffer, length);
                socket.receive(udpPacket);
                inBox.add(udpPacket.getData(), udpPacket.getAddress(), udpPacket.getPort());
            } catch (Exception e) {
                System.err.println("datagram could not be created");
            }
        }
    }
}
