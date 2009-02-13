
    //****************************************************
    //  Default constructor
    //****************************************************
public class RamMemory {

    private byte[] ram;


    //****************************************************
    //  Default constructor
    //****************************************************
    public RamMemory(int s) {
        
        ram = new byte[s];

    }

    //****************************************************
    //  Default constructor
    //****************************************************
    public void writeData(int loc, byte data) {
        
        ram[loc] = data;
    }


    //****************************************************
    //  Default constructor
    //****************************************************
    public byte readData(int i) {

        return ram[i];

    }


    public String toString() {

        return ram.toString();
    }
}
