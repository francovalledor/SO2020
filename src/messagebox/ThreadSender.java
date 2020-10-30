package messagebox;

public class ThreadSender extends Thread {
    private MessageOutBox msgbox;
    
    /**
     * Checks for outgoing messages and sends them
     */
    protected ThreadSender(MessageOutBox msgbox) {
        this.msgbox = msgbox;
    }
    @Override
    public void run() {
        while (true) {
            if (msgbox.hasMessage()) {
                 msgbox.sendNext();
            } else {
                msgbox.reEnqueueExpired();
            }
        }
    }
}
