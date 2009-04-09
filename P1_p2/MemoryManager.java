
//****************************************************
//  Default constructor
//****************************************************
public class MemoryManager {

    private DiskMemory disk;
    private RamMemory ram;
    private int totalPageNum;
    private int totalFrameNum;
    private int pageNum;
    private int frameNum;
    private FrameTableEntry[] frameTable;


    //****************************************************
    //  Default constructor
    //****************************************************
    public MemoryManager() {

        disk = new DiskMemory(2048);
        ram = new RamMemory(1024);
        totalPageNum = 2048/16;
        totalFrameNum = 1024/16;
        frameTable = new FrameTableEntry[totalFrameNum];

        for (int u=0; u<totalFrameNum; u++) {
            initFrameTable(u);
        }
    }

    public void initFrameTable(int u) {
        frameTable[u] = new FrameTableEntry();
    }
    
    public int getNextFrame() {
        int a = 0;
        while (frameTable[a].isAllocated()) {
            a++;
        }
        return a;
    }
    public void getPage(int x) {
        int index = x*4;
        for (int i=index; i<index+4; i++) {
            this.readDiskData(x);
        }
    }

    public void updateFrameTable(int frameNum, int pageNum, Boolean alloc, int jID) {
        frameTable[frameNum].updateFrameEntry(pageNum, jID, alloc);
    }
    
    private int getPhysicalAddress(int p) {
        String pageNum;
        int page;
        String offset;
        int frameNum;
        int newPC;
        String logAddress = Integer.toBinaryString(p);
        int offsetLength = logAddress.length();
        while (offsetLength < 4) {
            logAddress = "0" + logAddress;
        }
        while (offsetLength < 10) {
            logAddress = "0" + logAddress;
        }
        pageNum = logAddress.substring(0, 6);
        page = Integer.valueOf(pageNum);
        offset = logAddress.substring(7, 10);
        frameNum = OSDriver.PCB.getFrame(page);
        newPC = (frameNum * 16)+ Integer.getInteger(offset);
        return newPC;
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

    public int getNextFrameNum() {
        return frameNum;
    }

    public int getNextPageNum() {
        return pageNum;
    }

    public String printDisk() {
        return disk.toString();
    }

    public String printRam() {
        return ram.toString();
    }
}
