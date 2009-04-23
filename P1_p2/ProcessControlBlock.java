
//import java.util.Array;
import java.util.Stack;

//****************************************************
//  File                 ProcessControlBlock.java
//  Purpose:             Contains all information related
//                      to each process.
//****************************************************
public class ProcessControlBlock {

    private static Stack<PCB_block> jobQueue;
    private static pageTableEntry[] pageTable;
    
    //private static String [][] jobQ;
    private PCB_block pcb_e;
    private pageTableEntry pageTable_e;
    
    private static int count;
    
    //****************************************************
    //  Default constructor
    //****************************************************
    public ProcessControlBlock() {
        //jobQ = new String[];
        count = 0;
        jobQueue = new Stack<PCB_block>();
        pageTable = new pageTableEntry[512];
        

        for (int j=0; j<512; j++) {
            createPage(j);
        }
    }

    //****************************************************
    //  Creates a new PCB_block object and it will be
    //  added to the queue once the data related data
    //  to the object.
    //****************************************************
    /**
     * Creates a new PCB_block object, it will be
     * added to the queue with it the data related to it
     *
     * @param i  job ID
     * @param s  job size
     * @param p  job priority
     * @param a  job address
     */
    public void createJob(int i, int s, int p, int a) {

        //Integer.parseInt(s,16)
        pcb_e = new PCB_block(i, s, p, a);

    }
     /**
      *  Creates a new page and adds it to the page
      *  table
      * @param pageIndex index of new page
      */
     public void createPage(int pageIndex) {

        pageTable[pageIndex] = new pageTableEntry(pageIndex);
    }

    /**
     *  Adds the metadata to the object and adds the job
     *  to the queue.
     *
     * @param i job's input buffer size
     * @param o job's output buffer size
     * @param t job's temporary buffer size
     */
    public void addMeta(int i, int o, int t) {

        pcb_e.addMetadata(i, o, t);
        jobQueue.add(pcb_e);
        count++;

    }

    /**
     * Updates an entry of the frame table  at the given index
     *
     * @param pIndex   page table index
     * @param frameNumber associated frame number
     * @param validFlag  valid or invalid indicator
     */
    public void updateTableEntry(int pIndex, int frameNumber, Boolean validFlag) {
        pageTable[pIndex].updatePageEntry(frameNumber, validFlag);
    }

//    public void addFrameTableEntry (int f, boolean a){
//
//        frameTable_e.addFrameTable(f, a);
//        frameTable.add(frameTable_e);
//
//    }
    /**
     * Prints the Page table base registers for all the jobs
     */
     public void printPTBR() {
       System.out.println("\n\tPAGE TABLE BASE REGISTER");
       for (PCB_block p : jobQueue) {
         System.out.println("\tJOB: " + p.getJobID() + "\tPTBR: " + p.getPTBR());
       }
        System.out.println("");
    }

    /**
     * Prints the contents of the page table
     */
    public void printPageTable() {
        System.out.println("\n\tPAGE TABLE CONTENTS");
        for (pageTableEntry pte : pageTable) {
            System.out.println(pte.toString());
        }
        System.out.println("");
    }

    /**
     * Returns the associated frame via the given page table
     * index.
     *
     * @param a index of page table to get associated frame
     * @return frame number
     */
    public int getFrame(int p, int off) {
       int i = p+off;
       System.out.println("\tGETTING FRAME AT PAGE TABLE INDEX: " + i);
       try {
         pageTable[i].getValid();
         return pageTable[i].getFrameNumber();
       } catch (PageFault pf) {
          updateTableEntry(i, OSDriver.MemManager.getPage(i), true);
          //OSDriver.MemManager.printRam();
          return pageTable[i].getFrameNumber();
        }
    }

    /**
     * Searches the page table for the given page
     *
     * @param page page number
     * @return frame number associated with page
     */
    public int searchForPage(int page) {
      for (pageTableEntry pte : pageTable) {
        if (pte.getPageNumber() == page) {
          return pte.getFrameNumber();
        }
      }
        return -1;
    }

   /**
    *  Mutator method
    * @param s
    */
    public void setDataSize(int s) {
        pcb_e.setDataSize(s);
    }
    
    public synchronized int getJobCount() {
        return count;
    }

    /**
     *  Accessor method
     * @param i
     * @return
     */
    public synchronized PCB_block getJob(int i) {
        //System.out.println("JOB_COUNT: " + getJobCount());
//        if (i > LongTermScheduler.readyQueue.size())
//            System.exit(1);

        return jobQueue.get(i);
    }
    
    //****************************************************
    //  Returns the next job in the queue.
    //****************************************************
//    public PCB_block getNextJob() {
//        //PCB_block tmp = jobQueue.get(0);
//        return jobQueue.pop();
////        if (tmp != null)
////            return tmp;
////        jobQueue.remove(jobQueue.get(0));
////        System.out.println("ct." + count);
////        return tmp;
//    }

    /**
     * Removes a job from the job queue
     * @param j jobID of job to remove
     */
    public synchronized void removeJob(int j) {
        jobQueue.remove(j);
    }

    /**
     * Prints the contents of the PCB
     */
    public void printPCB() {

        //System.out.println(jobQueue.toString());
        for (PCB_block v : jobQueue) {
            System.out.println("JobID: " + v.getJobID() + "\tJobPriority: " +
                    v.getJobPriority() + "\tJobSize: " + v.getJobSize());
        }
    }
}

