

public class RamMemory {

    private final Byte[] ram;

    public RamMemory(int s) {
        
        ram = new Byte[s];

    }

    public void writeData(int loc, Byte data) {
        ram[loc] = data;
    }

    public int readData(int i) {

        return i;

    }
}
