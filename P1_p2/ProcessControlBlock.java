
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
    public synchronized void createJob(int i, int s, int p, int a) {

        //Integer.parseInt(s,16)
        pcb_e = new PCB_block(i, s, p, a);

    }

     public synchronized void createPage(int pageIndex) {

        pageTable[pageIndex] = new pageTableEntry(pageIndex);
    }


    //****************************************************
    //  Adds the data metadata to the object and then adds
    //  the object to the queue.
    //****************************************************
    public synchronized void addMeta(int i, int o, int t) {

        pcb_e.addMetadata(i, o, t);
        jobQueue.add(pcb_e);
        count++;

    }

    public synchronized void updateTableEntry(int pIndex, int frameNumber, Boolean validFlag) {
        pageTable[pIndex].updatePageEntry(frameNumber, validFlag);
    }

//    public void addFrameTableEntry (int f, boolean a){
//
//        frameTable_e.addFrameTable(f, a);
//        frameTable.add(frameTable_e);
//
//    }

    public void printPTBR() {
        System.out.println("\n\tPAGE TABLE BASE REGISTER");
        for (PCB_block p : jobQueue) {
            System.out.println("\tJOB: " + p.getJobID() + "\tPTBR: " + p.getPTBR());
        }
        System.out.println("");
    }
    public void printPageTable() {
        System.out.println("\n\tPAGE TABLE CONTENTS");
        for (pageTableEntry pte : pageTable) {
            System.out.println(pte.toString());
        }
        System.out.println("");
    }
    public synchronized int getFrame(int a) {
        System.out.println("\tGETTING FRAME AT INDEX: " + a);
        try {
            pageTable[a].getValid();
            return pageTable[a].getFrameNumber();
        } catch (PageFault p) {
            updateTableEntry(a, OSDriver.MemManager.getPage(a), true);
            //OSDriver.MemManager.printRam();
            return pageTable[a].getFrameNumber();
        }
    }


    public int searchForPage(int page) {
        for (pageTableEntry pte : pageTable) {
            if (pte.getPageNumber() == page) {
                return pte.getFrameNumber();
            }
        }
        return -1;
    }

    public void setDataSize(int s) {
        pcb_e.setDataSize(s);
    }
    
    public synchronized int getJobCount() {
        return count;
    }
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

    //****************************************************
    //  Removes a job from the queue.
    //****************************************************
    public synchronized void removeJob(int j) {
        jobQueue.remove(j);
    }

    public void printPCB() {

        //System.out.println(jobQueue.toString());
        for (PCB_block v : jobQueue) {
            System.out.println("JobID: " + v.getJobID() + "\tJobPriority: " +
                    v.getJobPriority() + "\tJobSize: " + v.getJobSize());
        }
    }

}
