package messagebox;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Set of InetAddress, port, Message
 */
public class SuperMessage {
    Machine machine;
    Message message;
    private Instant timestamp;
    private Instant expiresAt;
    private int attempts;

    public SuperMessage(Machine machine, Message message) {
        setMachine(machine);
        setMessage(message);
        setTimestamp();
        this.attempts = 1;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * set timestamp to control timeout
     */
    public void setTimestamp() {
        this.timestamp = Instant.now();
        this.expiresAt  = this.timestamp.plus(Config.timeOut, ChronoUnit.MILLIS);
    }
    
    public Boolean isExpired(){
        return Instant.now().compareTo(expiresAt) >= 1;
    }

    /**
     * how many times was the message sent?
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * attempts++
     */
    public void incAttempts() {
        this.attempts += 1;
    }
}
