
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
    private static frameTableEntry[] frameTable;
    //private static String [][] jobQ;
    private PCB_block pcb_e;
    private pageTableEntry pageTable_e;
    private frameTableEntry frameTable_e;
    private static int count;
    
    //****************************************************
    //  Default constructor
    //****************************************************
    public ProcessControlBlock() {
        //jobQ = new String[];
        count = 0;
        jobQueue = new Stack<PCB_block>();
        pageTable = new pageTableEntry[128];
        frameTable = new frameTableEntry[128];

        for (int j=0; j<128; j++) {
            createPage(j);
        }
    }

    //****************************************************
    //  Creates a new PCB_block object and it will be
    //  added to the queue once the data related data
    //  to the object.
    //****************************************************
    public void createJob(int i, int s, int p, int a) {

        //Integer.parseInt(s,16)
        pcb_e = new PCB_block(i, s, p, a);

    }

     public void createPage(int pageIndex) {

        pageTable[pageIndex] = new pageTableEntry();
    }

     public void createFrame(int f, boolean a) {

        frameTable_e = new frameTableEntry(f, a);

    }
    //****************************************************
    //  Adds the data metadata to the object and then adds
    //  the object to the queue.
    //****************************************************
    public void addMeta(int i, int o, int t) {

        pcb_e.addMetadata(i, o, t);
        jobQueue.add(pcb_e);
        count++;

    }

    public void updateTableEntry(int pIndex, int frameNumber, Boolean validFlag) {
        pageTable[pIndex].updatePageEntry(frameNumber, validFlag);
    }

//    public void addFrameTableEntry (int f, boolean a){
//
//        frameTable_e.addFrameTable(f, a);
//        frameTable.add(frameTable_e);
//
//    }

    public int getFrame(int a){

        try {
            pageTable[a].getValid();
            return pageTable[a].getFrameNumber();
        } catch (PageFault p) {
            OSDriver.MemManager.getPage(a);
            return pageTable[a].getFrameNumber();
        }

    }

//    public void searchFrameTable(int index) {
//
//         for (int i = 0; i < pageTable.length; i++){
//                    boolean a = false;
//                   // boolean b = true;
//                    pageTable_e.setValid(a);
//        }
//
//        Boolean b = true;
//
//        for (int i = 0; i < pageTable.length; i++){
//            if (index == (pageTable[index].getPageJobID()%index) +   )
//                    pageTable_e.setValid(b);
//        }
//    }


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
//    public PCB_block findJob(int i) {
//
//        for (PCB_block tmp : jobQueue) {
//            if (tmp.getJobID() == i) {
//                return tmp;
//            } else {
//                break;
//            }
//        }
//
//    }
//    public Object getShortestJob() {
//        int target = 0;
//
//        for (int i=0; i<jobQueue.size(); i++) {
//            if ( jobQueue(i).getJobSize() > target)
//                target = jobQueue[i].getJobSize();
//        }
//        return jobQueue.offer(target);
//    }



}
