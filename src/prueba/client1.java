package prueba;
import messenger.Messenger;

public class client1 {

    public static void main(String [] args) {
        Messenger msgr = new Messenger(28000);
        msgr.setRecipient("localhost", 28001);
    }
}
