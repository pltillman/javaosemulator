import java.util.concurrent.PriorityBlockingQueue;
/**
 *
 * @author Patrick Tillman
 */
public class MemoryManager {

    private DiskMemory disk;
    private RamMemory ram;
    private int totalPageNum;
    private int totalFrameNum;
    private int pageNum;
    private int frameNum;
    private FrameTableEntry[] frameTable;
    private PriorityBlockingQueue<Integer> framePool;

    /**
     * Default constructor
     */
    public MemoryManager() {

        framePool = new PriorityBlockingQueue<Integer>();
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
    /**
     *
     * @param u
     */
    public void initFrameTable(int u) {
        frameTable[u] = new FrameTableEntry();
        framePool.add(u);
    }
<<<<<<< .mine
 /**
  * Finds the first unallocated frame
  *
  * @return value of next available frame
  */
=======

    /**
     * 
     * @param f
     */
    public void reclaimFrame(int f) {
        framePool.add(f);
    }
    
>>>>>>> .r104
    /**
     *
     * @return
     */
    public int getNextFrame() {
        if (framePool.isEmpty()) {
            return framePool.size();
        }
        return framePool.poll();
    }
<<<<<<< .mine
  /**
   * Retreives the page data by calculating where
   *   to read data from disk
   *
   * @param x
   */
=======

    /**
     *
     * @param x
     */
>>>>>>> .r104
    public void getPage(int x) {
        int index = x;
        int frame = this.getNextFrame();
        
        for (int i=index; i<index+4; i++) {
            String bits = OSDriver.tools.getBinaryData(i);

            OSDriver.MemManager.writeRamData(frame++, Short.valueOf(bits.substring(0,8), 2));
            OSDriver.MemManager.writeRamData(frame++, Short.valueOf(bits.substring(8,16), 2));
            OSDriver.MemManager.writeRamData(frame++, Short.valueOf(bits.substring(16,24), 2));
            OSDriver.MemManager.writeRamData(frame++, Short.valueOf(bits.substring(24,32), 2));
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
    /**
     *
     * @param frameNum
     * @param pageNum
     * @param alloc
     * @param jID
     */
    public void updateFrameTable(int frameNum, int pageNum, Boolean alloc, int jID) {
        frameTable[frameNum].updateFrameEntry(pageNum, jID, alloc);
    }
<<<<<<< .mine

    /**
     *
     * @param p
     * @return
     */
    private int getPhysicalAddress(int p) {
        String pageNum;
=======

    /**
     *
     */
    public void printFrameTable() {
        System.out.println("\nFRAME TABLE CONTENTS");
        for (FrameTableEntry fte : frameTable) {
            System.out.println(fte.toString());
        }
    }

    /**
     *
     * @param p
     * @return
     */
    public int getPhysicalAddress(int p, int ptbr) {
        System.out.println("Geting physical address for: " + p + " \tPTBR: " + ptbr);
        
        String pageNumber;
>>>>>>> .r104
        int page;
        String offset;
        int frameNumber;
        int newPC;
        String logAddress = Integer.toBinaryString(p);
        
        // add prefix of 0's cutoff by the toBinaryString method above
        while (logAddress.length() < 10) {
            logAddress = "0" + logAddress;
        }
        // get the first 6 bits for the page number
        pageNumber = logAddress.substring(0, 6);
        page = Integer.valueOf(pageNumber,2);
        page += ptbr;
        
        // get the last 2 bits for the offset
        offset = logAddress.substring(7, 10);

        System.out.println("Logical address: " + logAddress);
        System.out.println("Page: " + page);

        frameNumber = OSDriver.PCB.getFrame(page);
        System.out.println("Frame #: " + frameNumber);
        
        newPC = (frameNumber * 16) + Integer.valueOf(offset,2);
        System.out.println("Logical address: " + logAddress);
        System.out.println("NEW PC Value: " + newPC);
        return newPC;
    }
<<<<<<< .mine

    /**
     * This writes the given data to the disk starting at
     *  the location provided.
     *
     * @param loc  location on disk to start writing data
     * @param data data to write to disk
     */
=======


    /**
     *  This writes the given data to the disk starting at
     *  the location provided.
     *
     * @param loc
     * @param data
     */
>>>>>>> .r104
    public synchronized void writeDiskData(int loc, String data) {

        disk.writeData(loc, data);

    }


    /**
     * Returns a string representation of the hex code
     *
     * @param r
     * @return
     */

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


    /**
     *
     * @param loc
     * @param data
     */
    /**
     * This writes the given data to RAM starting at the
     *  location provided
     * @param loc location in RAM to start writing data
     * @param data data to write to RAM
     */
    public synchronized void writeRamData(int loc, short data) {
        ram.writeData(loc, data);
    }


    /**
     *
     * @param r
     * @return
     */
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

    /**
     *
     * @return
     */
    public int getNextFrameNum() {
        return frameNum;
    }

    /**
     *
     * @return
     */
    public int getNextPageNum() {
        return pageNum;
    }

<<<<<<< .mine
    /**
     * Prints the contents of the disk
     *
     * @return contents of the disk
     */
=======
    /**
     *
     * @return
     */
>>>>>>> .r104
    public String printDisk() {
        return disk.toString();
    }
  /**
   * Prints the contents of RAM
   *
   * @return contents of RAM
   */
    /**
     * 
     * @return
     */
    public String printRam() {
        return ram.toString();
    }
}
