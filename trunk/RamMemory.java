
    //****************************************************
    //  Default constructor
    //****************************************************
public class RamMemory {

    private String[] ram;


    //****************************************************
    //  Default constructor
    //****************************************************
    public RamMemory(int s) {
        
        ram = new String[s];

    }

    //****************************************************
    //  Default constructor
    //****************************************************
    public void writeData(int loc, String data) {
        
        ram[loc] = data;
    }


    //****************************************************
    //  Default constructor
    //****************************************************
    public String readData(int i) {

        return ram[i];

    }


    public String toString() {

        return ram.toString();
    }
}
