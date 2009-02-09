
import java.util.ArrayList;

//****************************************************
//  File                 ProcessControlBlock.java
//  Purpose:             Contains all information related
//                      to each process.
//****************************************************
public class ProcessControlBlock {

    private static ArrayList<PCB_block> jobQueue;
    private static String [][] jobQ;
    private PCB_block pcb_e;
    private static int count;

    //****************************************************
    //  Default constructor
    //****************************************************
    public ProcessControlBlock() {
        //jobQ = new String[];
        count = 0;
        jobQueue = new ArrayList<PCB_block>();
    }

    //****************************************************
    //  Creates a new PCB_block object and it will be
    //  added to the queue once the data related data
    //  to the object.
    //****************************************************
    public void createJob(int i, int s, int p) {

        //Integer.parseInt(s,16)
        pcb_e = new PCB_block(i, s, p);

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

    public PCB_block get(int i) {
        return jobQueue.get(i);
    }
    //****************************************************
    //  Returns the next job in the queue.
    //****************************************************
    public PCB_block getNextJob() {
        PCB_block tmp = jobQueue.get(0);
        jobQueue.remove(jobQueue.get(0));
        return tmp;

    }

    //****************************************************
    //  Removes a job from the queue.
    //****************************************************
    public void removeJob(int j) {
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
