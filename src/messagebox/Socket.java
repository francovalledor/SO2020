package messagebox;

import java.net.DatagramSocket;

/**
 * Singleton.
 * return the same DatagramSocket ever
 * usage: DatagramSocket ds = Socket.getInstance(desired_port).getDatagramSocket()
 * "desired_port" is ignored if an instance of Socket already exists
 */
public class Socket {
    DatagramSocket socket;
    private static Socket instance = null;

    private Socket(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static Socket getInstance(int port){
        if (instance == null) {
            instance = new Socket(port);
        } 
        return instance;
    }

    public DatagramSocket getDatagramSocket() {
        return socket;
    }
}
