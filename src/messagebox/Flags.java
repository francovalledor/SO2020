package messagebox;

/**
 * embeddable flags 
 */
public class Flags {
    private final int ACK = 0b1;
    int value;

    public Flags() {
        this.value = 0b0;
    }

    private void setBit(int bit){
        value |= bit;
    }

    private void clearBit(int bit) {
        value &= ~bit;
    }

    private void toggleBit(int bit) {
        value ^= bit;
    }

    private boolean getBit(int bit) {
        return ( (bit & value) == 1 );
    }


    public boolean getACK() {
        return getBit(ACK);
    }

    public void setACK() {
        setBit(ACK);
    }
    
    public void clearACK() {
        clearBit(ACK);
    }

    public void toggleACK() {
        value ^= ACK;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public static Flags fromString(String value){
        Flags flags = new Flags();
        flags.value = Integer.parseInt(value);      
        return flags;
    }   
}
