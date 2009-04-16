
    //****************************************************
    //  Default constructor
    //****************************************************
public class RamMemory {

    private short[] ram;

    /**
     * Default Constructor
     * @param s  size of RAM 
     */
    public RamMemory(int s) {
        
        ram = new short[s];

    }


    /**
     * Writes data to given location
     *
     * @param loc  location in RAM to write data
     * @param data the data to write to RAM
     */
    public void writeData(int loc, short data) {
        
        ram[loc] = data;
    }


    /**
     * Reads data from RAM
     * 
     * @param i index of where to read in RAM
     * @return  data from RAM at index i
     */
    public short readData(int i) {

        return ram[i];

    }

/**
 * Prints the contenets of RAM
 *
 * @return contents of RAM
 */
    public String toString() {
        for (int i=0; i<ram.length; i++) {
            System.out.println(ram[i]);
        }
        return ram.toString();
    }
}
