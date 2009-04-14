import java.util.Stack;

//****************************************************
//  File                 ProcessControlBlock.java
//  Purpose:             Contains all information related
//                       to each process.
//****************************************************

public class ProcessControlBlock {

    private static Stack<PCB_block> jobQueue;
    private static pageTableEntry[] pageTable;
    private PCB_block pcb_e; 
    private static int count;

    /**
     * Default constructor
     */
    public ProcessControlBlock() {
        
        count = 0;
        jobQueue = new Stack<PCB_block>();
        pageTable = new pageTableEntry[128];

        /**
         * Limit page array to 128 elements
         */
        for (int j=0; j<128; j++) {
            createPage(j);
        }

    }

    /**
     * Creates a new PCB_block object and it will be
     * added to the queue once the data related data
     * to the object.
     * @param i - jobID
     * @param s - jobSize
     * @param p - jobPriority
     * @param a - address
     */
    public void createJob(int i, int s, int p, int a) {

        pcb_e = new PCB_block(i, s, p, a);

    }

    /**
     * Creates a new Page array.
     * @param pageIndex - page index
     */
     public void createPage(int pageIndex) {

        pageTable[pageIndex] = new pageTableEntry();
    }

    /**
     * Adds the data metadata to the object and then adds
     * the object to the queue.
     * @param i - input buffer size
     * @param o - output bufer size
     * @param t - temp buffer size
     */
    public void addMeta(int i, int o, int t) {

        pcb_e.addMetadata(i, o, t);
        jobQueue.add(pcb_e);
        count++;

    }

    /**
     * Updated the page table with frame number and valid flag.
     * @param pIndex - page index
     * @param frameNumber - frame number
     * @param validFlag - valid flag
     */
    public void updateTableEntry(int pIndex, int frameNumber, Boolean validFlag) {
        pageTable[pIndex].updatePageEntry(frameNumber, validFlag);
    }

    /**
     * Get frame number from free frame list.
     * @param a - page number
     * @return - frame number
     */
    public int getFrame(int a) {

        try {
            pageTable[a].getValid();
            return pageTable[a].getFrameNumber();
        } catch (PageFault p) {
            OSDriver.MemManager.getPage(a);
            return pageTable[a].getFrameNumber();
        }
    }

    /**
     * Set data size.
     * @param s - index
     */
    public void setDataSize(int s) {
        pcb_e.setDataSize(s);
    }

    /**
     * Get job count.
     * @return - count number
     */
    public synchronized int getJobCount() {
        return count;
    }

    /**
     * Get PCB_block job.
     * @param i - index
     * @return
     */
    public synchronized PCB_block getJob(int i) {
        return jobQueue.get(i);
    }

    /**
     * Removes a job from the queue.
     * @param j - index
     */
    public synchronized void removeJob(int j) {
        jobQueue.remove(j);
    }

    /**
     * Print PCB.
     */
    public void printPCB() {

        for (PCB_block v : jobQueue) {
            System.out.println("JobID: " + v.getJobID() + "\tJobPriority: " +
                    v.getJobPriority() + "\tJobSize: " + v.getJobSize());

        }
    }
}
