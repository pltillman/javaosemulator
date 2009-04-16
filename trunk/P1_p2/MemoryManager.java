
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
/**
 * Initialize frame table
 *
 * @param u frame table value to initialize
 */
    public void initFrameTable(int u) {
        frameTable[u] = new FrameTableEntry();
    }
 /**
  * Finds the first unallocated frame
  *
  * @return value of next available frame
  */
    public int getNextFrame() {
        int a = 0;
        while (frameTable[a].isAllocated()) {
            a++;
        }
        return a;
    }
  /**
   * Retreives the page data by calculating where
   *   to read data from disk
   *
   * @param x
   */
    public void getPage(int x) {
        int index = x*4;
        for (int i=index; i<index+4; i++) {
            this.readDiskData(x);
        }
    }
/**
 * Adds or modifies an entry of the frame table at the
 *   given frame number
 *
 * @param frameNum frame number associated with job
 * @param pageNum  page number associated with job
 * @param alloc    set to true if allocated, false otherwise
 * @param jID      Job ID
 */
    public void updateFrameTable(int frameNum, int pageNum, Boolean alloc, int jID) {
        frameTable[frameNum].updateFrameEntry(pageNum, jID, alloc);
    }

    /**
     *
     * @param p
     * @return
     */
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

    /**
     * This writes the given data to the disk starting at
     *  the location provided.
     *
     * @param loc  location on disk to start writing data
     * @param data data to write to disk
     */
    public synchronized void writeDiskData(int loc, String data) {

        disk.writeData(loc, data);

    }

    //****************************************************
    //  Returns a string representation of the hex code
    //****************************************************

    /**
     * This reads the data from the disk starting at the
     *  location provided
     *
     * @param r location on disk to start reading
     * @return data from RAM
     */
    public synchronized String readDiskData(int r) {

        return disk.readData(r);

    }


    //****************************************************
    //  Default constructor
    //****************************************************
    /**
     * This writes the given data to RAM starting at the
     *  location provided
     * @param loc location in RAM to start writing data
     * @param data data to write to RAM
     */
    public synchronized void writeRamData(int loc, short data) {

        ram.writeData(loc, data);

    }


    //****************************************************
    //  Default constructor
    //****************************************************
   /**
    * This reads data from RAM starting at the location
    *  provided 
    * 
    * @param r location in RAM to start reading data
    * @return data from RAM
    */
    public synchronized short readRamData(int r) {

        return ram.readData(r);

    }

    public int getNextFrameNum() {
        return frameNum;
    }

    public int getNextPageNum() {
        return pageNum;
    }

    /**
     * Prints the contents of the disk
     *
     * @return contents of the disk
     */
    public String printDisk() {
        return disk.toString();
    }
  /**
   * Prints the contents of RAM
   *
   * @return contents of RAM
   */
    public String printRam() {
        return ram.toString();
    }
}
