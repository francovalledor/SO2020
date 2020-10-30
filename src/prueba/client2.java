package prueba;
import messenger.Messenger;

public class client2 {

    public static void main(String [] args) {
        Messenger msgr = new Messenger(28001);
        msgr.setRecipient("localhost", 28000);
    }
}
