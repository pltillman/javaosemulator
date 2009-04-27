
/**
 * Implementation of the OS's disk.
 * Contains methods to add/modify disk
 * data via the memory manager, this class
 * is not directly accessed. 
 */
public class DiskMemory {

    private String[] disk;


    /**
     * Creates an array of the given size to represent disk
     * 
     * @param s size of disk
     */
    public DiskMemory(int s) {

        disk = new String[s];

    }

    /**
     * Writes the given data to the given location on the disk
     *
     * @param loc location on disk to write data
     * @param data to write to disk
     */
    public void writeData(int loc, String data) {
        
        disk[loc] = data;
    }


    /**
     * Reads data from disk at a given index
     *
     * @param i index on disk to read data from
     * @return data from disk
     */
    public String readData(int i) {
        
        return disk[i];
        
    }
    /**
     * Prints the contents of disk
     *
     * @return String representation on objects on disk
     */
    public String toString() {

        System.out.println( disk.toString() );
        return disk.toString();
    }
}
