
//****************************************************
//  Default constructor
//****************************************************
public class MemoryManager {

    private DiskMemory disk;
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
    public void writeDiskData(int loc, Byte data) {

        disk.writeData(loc, data);

    }

    //****************************************************
    //  Returns a string representation of the hex code
    //****************************************************
    public String readDiskData(int r) {

        return disk.readData(r);

    }
    
    //****************************************************
    //  Default constructor
    //****************************************************
    public void writeRamData(int loc, Byte data) {

        ram.writeData(loc, data);
        
    }


    //****************************************************
    //  Default constructor
    //****************************************************
    public int readRamData(int r) {

        return ram.readData(r);

    }
}
