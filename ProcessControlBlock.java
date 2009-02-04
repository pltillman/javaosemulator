
import java.util.Queue;

//****************************************************
//  File                 ProcessControlBlock.java
//  Purpose:             Contains all information related
//                      to each process.
//****************************************************
public class ProcessControlBlock {

    private static Queue jobQueue;
    private static String [][] jobQ;
    private PCB_block pcb_e;
    private static int count;

    //****************************************************
    //  Default constructor
    //****************************************************
    public ProcessControlBlock() {
        //jobQ = new String[];
        count = 0;
    }

    //****************************************************
    //  Creates a new PCB_block object and it will be
    //  added to the queue once the data related data
    //  to the object.
    //****************************************************
    public void createJob(String i, String s, String p) {

        //Integer.parseInt(s,16)
        pcb_e = new PCB_block(Integer.parseInt(i), Integer.parseInt(s), Integer.parseInt(s,16));

    }

    //****************************************************
    //  Adds the data metadata to the object and then adds
    //  the object to the queue.
    //****************************************************
    public void addMeta(String i, String o, String t) {

        pcb_e.addMetadata(Integer.parseInt(i,16), Integer.parseInt(o,16), Integer.parseInt(t,16));
        jobQueue.add(pcb_e);
        count++;

    }

    //****************************************************
    //  Returns the next job in the queue.
    //****************************************************
    public Object getNextJob() {
        return jobQueue.peek();
    }

    //****************************************************
    //  Removes a job from the queue.
    //****************************************************
    public void removeJob(int j) {
        jobQueue.remove(j);
    }

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
        public void terminate(Boolean b) {
            this.status = b;
        }
    }
}
