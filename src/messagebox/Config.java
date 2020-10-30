package messagebox;

import java.nio.charset.StandardCharsets;

/**
 * messagebox config
 */
public class Config {
    
    
    /**
     * Timeout for wait ACK
     */
    public static int timeOut = 1000;

    /**
     * re-send message max attempts
     */
    public static int maxAttempts = 20;

    /**
     * message max length and datagram size
     */
    public static int maxLength = 1500;
}