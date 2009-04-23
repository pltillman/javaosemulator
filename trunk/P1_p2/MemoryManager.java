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
     *
     * @param u
     */
    public void initFrameTable(int u) {
        frameTable[u] = new FrameTableEntry();
        framePool.add(u);
    }

    /**
     * 
     * @param f
     */
    public void reclaimFrame(int page) {
        framePool.add(OSDriver.PCB.searchForPage(page));
    }
    
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

    /**
     *
     * @param x
     */
    public int getPage(int x) {
        System.out.println("\n\tRETRIEVING PAGE: " + x);
        int beginFrame = getNextFrame();
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
     *
     * @param frameNum
     * @param pageNum
     * @param alloc
     * @param jID
     */
    public void updateFrameTable(int frameNum, int pageNum, Boolean alloc, int jID) {
        frameTable[frameNum].updateFrameEntry(pageNum, jID, alloc);
    }

    /**
     *
     */
    public void printFrameTable() {
        System.out.println("\n\tFRAME TABLE CONTENTS");
        for (FrameTableEntry fte : frameTable) {
            System.out.println(fte.toString());
        }
        System.out.println("");
    }

    /**
     *
     * @param p
     * @return
     */
    public int getPhysicalAddress(int p, int ptbr) {
        System.out.println("\n\tGetting physical address for: " + p + " \tPTBR: " + ptbr);
        
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
        page += ptbr;
        
        // get the last 2 bits for the offset
        offset = logAddress.substring(6, 10);

        frameNumber = OSDriver.PCB.getFrame(page);
        System.out.println("\tLogical address: " + logAddress);
        System.out.println("\tPage: " + page + "\tOffset: " + offset);
        System.out.println("\tFrame #: " + frameNumber);
        
        newPC = (frameNumber * 16) + Integer.valueOf(offset,2);
        //System.out.println("Logical address: " + logAddress + "\tOffset: " + Integer.valueOf(offset,2));
        System.out.println("\tCalculated Physical index value: " + newPC);
        return newPC;
    }


    /**
     *  This writes the given data to the disk starting at
     *  the location provided.
     *
     * @param loc
     * @param data
     */
    public synchronized void writeDiskData(int loc, String data) {

        disk.writeData(loc, data);

    }


    /**
     * Returns a string representation of the hex code
     *
     * @param r
     * @return
     */
    public synchronized String readDiskData(int r) {
        return disk.readData(r);
    }


    /**
     *
     * @param loc
     * @param data
     */
    public synchronized void writeRamData(int loc, short data) {
        ram.writeData(loc, data);
    }


    /**
     *
     * @param r
     * @return
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

    /**
     *
     * @return
     */
    public String printDisk() {
        return disk.toString();
    }

    /**
     * 
     * @return
     */
    public String printRam() {
        return ram.toString();
    }
}
