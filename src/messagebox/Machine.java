package messagebox;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * set of InetAddress and port
 */
public class Machine {
    private InetAddress address;
    private int port;

    public Machine(String address, int port) throws UnknownHostException {
        setAddress(address);
        setPort(port);
    }

    public Machine(InetAddress address, int port){
        this.address = address;
        setPort(port);
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(String address) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
