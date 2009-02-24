
//****************************************************
//  Default constructor
//****************************************************
public class MemoryManager {

    public DiskMemory disk;
    private RamMemory ram;


    //****************************************************
    //  Default constructor
    //****************************************************
    public MemoryManager() {

        disk = new DiskMemory(2048);
        ram = new RamMemory(1024);

    }

    //****************************************************
    //  This writes the given data to the disk starting at
    //  the location provided.
    //****************************************************
    public synchronized void writeDiskData(int loc, String data) {

        disk.writeData(loc, data);

    }

    //****************************************************
    //  Returns a string representation of the hex code
    //****************************************************
    public synchronized String readDiskData(int r) {

        return disk.readData(r);

    }
    
    //****************************************************
    //  Default constructor
    //****************************************************
    public synchronized void writeRamData(int loc, short data) {

        ram.writeData(loc, data);
        
    }


    //****************************************************
    //  Default constructor
    //****************************************************
    public synchronized short readRamData(int r) {

        return ram.readData(r);

    }

    public String printDisk() {
        return disk.toString();
    }

    public String printRam() {
        return ram.toString();
    }
}
