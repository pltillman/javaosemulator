
    //****************************************************
    //  Default constructor
    //****************************************************
public class RamMemory {

    private short[] ram;


    //****************************************************
    //  Default constructor
    //****************************************************
    public RamMemory(int s) {
        
        ram = new short[s];

    }

    //****************************************************
    //  Default constructor
    //****************************************************
    public void writeData(int loc, short data) {
        
        ram[loc] = data;
    }


    //****************************************************
    //  Default constructor
    //****************************************************
    public short readData(int i) {

        return ram[i];

    }


    public String toString() {

        return ram.toString();
    }
}
