
import java.util.concurrent.PriorityBlockingQueue;
/**
 * All reading and writing to and from disk and ram
 * is done so via the memory manager
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
    * Default Constructor
    *  Create an instance of DiskMemory with a 2048 capacity
    *  Create an instance of RamMemory with a 1024 capacity
    *  The page and frame numbers are calculated by dividing the
    *   capacities of RAM and disk respectively, by 16.
    *  Initialize the frame table space
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
    * @param u frame table value (frame number) to initialize
    */
    public void initFrameTable(int u) {
        frameTable[u] = new FrameTableEntry();
        framePool.add(u);
    }

    /**
     *
     * @param page
     */
    public void reclaimFrame(int page) {
        framePool.add(OSDriver.PCB.searchForPage(page));
    }

   /**
    * Finds the first unallocated frame
    *
    * @return value of next available frame
    */
    public int getNextFrame() {
        System.out.println("FRAME POOL: " + framePool.toString());
        if (framePool.isEmpty()) {
            return framePool.size();
        }
        return framePool.poll();
    }

  /**
   * Retreives the page data by calculating where
   *   to read data from disk
   *
   * @param x Initial index to calculate page value
   *          to read data from
   */
    public int getPage(int x) {
        System.out.println("\tRETRIEVING PAGE: " + x);
        int beginFrame = getNextFrame();
        System.out.println("\tPutting data in frame " + beginFrame);
        int index = beginFrame;
        index *= 16;
        //System.out.println("\n\tAdding data starting at index: " + index);

        for (int i=x*4; i<(x*4)+4; i++) {
            String bits = OSDriver.tools.getBinaryData(i);

            OSDriver.MemManager.writeRamData(index++, Short.valueOf(bits.substring(0,8), 2));
            OSDriver.MemManager.writeRamData(index++, Short.valueOf(bits.substring(8,16), 2));
            OSDriver.MemManager.writeRamData(index++, Short.valueOf(bits.substring(16,24), 2));
            OSDriver.MemManager.writeRamData(index++, Short.valueOf(bits.substring(24,32), 2));
//            System.out.println("\t" + Short.valueOf(bits.substring(0,8),2) + "\t" + Short.valueOf(bits.substring(8,16),2) + "\t" +
//                    Short.valueOf(bits.substring(16,24),2) + "\t" + Short.valueOf(bits.substring(24,32),2));
        }
        return beginFrame;
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
    * Prints the contents of the frame table
    */
    public void printFrameTable() {
      System.out.println("\n\tFRAME TABLE CONTENTS");
      for (FrameTableEntry fte : frameTable) {
        System.out.println(fte.toString());
      }
      System.out.println("");
    }
    
   /**
    * Calculates the physical address 
    *
    * @param p PC value
    * @param ptbr page table base register
    * @return physical address
    */
    public int getPhysicalAddress(int p, PCB_block j) {
        System.out.println("\n\tGetting physical address for: " + p + " \tPTBR: " + j.getPTBR());

        String pageNumber;
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

        // get the last 2 bits for the offset
        offset = logAddress.substring(6, 10);

        frameNumber = OSDriver.PCB.getFrame(page, j.getPTBR());
        System.out.println("\tLogical address: " + logAddress);
        System.out.println("\tPage: " + page + "\tOffset: " + offset);
        System.out.println("\tFound Frame #: " + frameNumber);

        newPC = (frameNumber * 16) + Integer.valueOf(offset,2);
        //System.out.println("Logical address: " + logAddress + "\tOffset: " + Integer.valueOf(offset,2));
        System.out.println("\tCalculated Physical index value: " + newPC);
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


    /**
    * This returns a string representation of the
    * hex code on disk
    *
    * @param r location on disk to start reading
    * @return data from RAM
    */
    public synchronized String readDiskData(int r) {

        return disk.readData(r);

    }


   /**
    * This writes the given data to RAM starting at the
    *  location provided
    *
    * @param loc location in RAM to start writing data
    * @param data data to write to RAM
    */
    public synchronized void writeRamData(int loc, short data) {

        ram.writeData(loc, data);

    }


   
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
    * Accessor method for next frame
    *
    * @return The next available frame number
    */
    public int getNextFrameNum() {
        return frameNum;
    }

   /**
    * Accessor method for next page
    *
    * @return The next available page number
    */
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
