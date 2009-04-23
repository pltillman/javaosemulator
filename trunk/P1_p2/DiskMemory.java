
    //****************************************************
    //  Default constructor
    //****************************************************

public class DiskMemory {

    private String[] disk;


    //****************************************************
    //  Default constructor
    //****************************************************
    /**
     * Creates an array of the given size to represent disk
     * 
     * @param s size of disk
     */
    public DiskMemory(int s) {

        disk = new String[s];

    }

    //****************************************************
    //  Default constructor
    //****************************************************
    /**
     * Writes the given data to the given location on the disk
     *
     * @param loc location on disk to write data
     * @param data to write to disk
     */
    public void writeData(int loc, String data) {
        
        disk[loc] = data;
    }


    //****************************************************
    //  Default constructor
    //****************************************************
    /**
     * Read data from disk at a given index
     * @param i index on disk to read data from
     * @return data from disk
     */
    public String readData(int i) {
        
        return disk[i];
        
    }

    public String toString() {

        System.out.println( disk.toString() );
        return disk.toString();
    }
}
