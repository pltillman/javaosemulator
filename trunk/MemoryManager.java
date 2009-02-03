

public final class MemoryManager {

    DiskMemory disk;
    RamMemory ram;
    
    public MemoryManager() {

        disk = new DiskMemory(2048);
        ram = new RamMemory(1024);

    }

    public void writeDiskData(int d) {

        disk.writeData(d);

    }


    public int readDiskData(int r) {

        return disk.readData(r);

    }
    

    public void writeRamData(int d) {

        ram.writeData(d);
        
    }

    public int readRamData(int r) {

        return ram.readData(r);

    }
}
