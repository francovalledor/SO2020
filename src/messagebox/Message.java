package messagebox;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/* Message:
 *
 * byte array structure: 
 *  id\n 
 *  md5\n 
 *  length\n 
 *  flags\n 
 *  \n 
 *  msg
 */
public class Message {
    public static Charset charset = StandardCharsets.UTF_8;

    private String msg;
    private int length;
    private UUID id;
    private String md5;
    private Flags flags;

    public Message(String message) {
        this.msg = message;
        this.length = message.length();
        this.md5 = Utils.getMd5(message);
        this.id = UUID.randomUUID();
        this.flags = new Flags();
    }

    public Message(String msg, UUID id, Flags flags) {
        this(msg);
        this.id = id;
        this.flags = flags;
    }

    public Message(String msg, String id, Flags flags) {
        this(msg);
        this.setId(id);
        this.flags = flags;
    }

    /**
     * transform a Message into byte[]
    */
    public byte[] toByteArray() {
        String datos = "";
        byte[] binaryMsj;

        datos += id.toString() + "\n";
        datos += md5 + "\n";
        datos += Integer.toString(length) + "\n";
        datos += flags + "\n";
        datos += "\n";
        datos += msg;

        binaryMsj = datos.getBytes(charset);
        return binaryMsj;
    }

    /**
     * create a Message from byte[]
     */
    public static Message fromByteArray(byte[] binaryMsg) throws Exception {
        try {
            String bulk = new String(binaryMsg, charset).trim();
            String[] lines = bulk.split("\n");

            String id = lines[0];
            String md5 = lines[1];
            Flags flags = Flags.fromString(lines[3]);
            int msgBegin = bulk.indexOf("\n\n") + 2;
            String body = bulk.substring(msgBegin);
            Message msg = new Message(body, id, flags);

            if (!msg.compare(md5)) {
                throw new Exception("Checksum error");
            }

            return msg;

        } catch (Exception e) {
            throw new Exception("invalid message");
        }
    }

    /**
     * Compare this checksum against given md5
     */
    public Boolean compare(String md5) {
        return this.md5.equals(md5);
    }

    /**
     * return message body
     */
    public String getMsg() {
        return msg;
    }

    /**
     * return message body length
     */
    public int getLength() {
        return length;
    }

    /**
     * return message UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * return message UUID as string
     */
    public String getIdString() {
        return id.toString();
    }

    /**
     * return checksum
     */
    public String getMd5() {
        return md5;
    }

    /**
     * set message id
     */
    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    /**
     * set message id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * there is a ACK flag active?
     */
    public boolean isACK() {
        return this.flags.getACK();
    }

    /**
     * Create a ACK message
     */
    public Message makeACK(){
        Flags flags = new Flags();
        flags.setACK();

        Message message = new Message("ACK", this.getId(), flags);
        return message;
    }

}