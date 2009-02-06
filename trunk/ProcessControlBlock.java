
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

    //****************************************************
    //  Returns the next job in the queue.
    //****************************************************
    public Object getNextJob() {
        return jobQueue;
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


    //****************************************************
    //  This is a template for an individual PCB entry
    //****************************************************
    class PCB_block {
        private int jobID;
        private int jobSize;
        private int jobPriority;
        private int input_buffer;
        private int output_buffer;
        private int tmp_buffer;
        private Boolean status;
        private int mem_loc; //added by the schedule once the job is
                             //placed in memory. indicates what
                             //physical address in ram the job begins.

        PCB_block(int i, int s, int p) {
            this.jobID = i;
            this.jobSize = s;
            this.jobPriority = p;
        }
        public void addMetadata(int i, int o, int t) {
            this.input_buffer = i;
            this.output_buffer = o;
            this.tmp_buffer = t;
        }
        public int getJobID() {
            return this.jobID;
        }

        public int getJobPriority() {
            return this.jobPriority;
        }

        public int getJobSize() {
            return this.jobSize;
        }
        public Boolean getStatus() {
            return this.status;
        }
        public int get_Input_buffer(int jID) {
            return this.input_buffer;
        }
        public int get_Output_buffer(int jID) {
            return this.output_buffer;
        }
        public int get_tmp_buffer(int jID) {
            return this.tmp_buffer;
        }
        public void terminate(Boolean b) {
            this.status = b;
        }
        public void set_mem_loc(int k) {
            this.mem_loc = k;
        }
        public int get_mem_loc(int jID) {
            return this.mem_loc;
        }
    }
}
