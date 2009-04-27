import java.util.ArrayList;


/**
 * Reads jobs from the ready queue filled by the long-term scheduler
 * and sorts the queue by the jobs' priority.  The short-term scheduler
 * then waits until the CPU requests a job.  When the CPU sends its request
 * the short-term scheduler takes the next job in the ready queue and passes
 * it to the CPU along with its metadata to be processed
 *
 */
public class shortTermScheduler{

        private PCB_block pcb_e;
        public static ArrayList<Short> regQueue;
        int jID;
        private final int LOADED = 1;

        public shortTermScheduler(){

        }


        /**
         * Sorts the ready queue buy the containing jobs' priorities.
         * The job with the highest priority will be passed to the CPU
         * first.
         */
        public void SJF() {

            //Sort readyQueue in priority order

            int n = LongTermScheduler.readyQueue.size();
            //int p = LongTermScheduler.readyQueue
            boolean doMore = true;

            while (doMore) {
                n--;
                doMore = false;
                for (int i=0; i<n; i++) {

                    if (LongTermScheduler.readyQueue.get(i).getJobPriority() <
                            LongTermScheduler.readyQueue.get(i+1).getJobPriority()) {

                        pcb_e = LongTermScheduler.readyQueue.get(i);
                        LongTermScheduler.readyQueue.set(i, LongTermScheduler.readyQueue.get(i+1));
                        LongTermScheduler.readyQueue.set(i+1, pcb_e);
                        doMore = true;
                    }
                }
            }
        }

       /**
        * Removes a job from the ready queue and sets the
        * time it left the queue and the time it enters the CPU.
        *
        * @param jID ID of job to remove from the ready queue
        * @return job removed from the ready queue
        */
        public synchronized PCB_block Store(int jID){

            PCB_block tmp = LongTermScheduler.readyQueue.get(jID);
            tmp.setStatus(LOADED);
            LongTermScheduler.readyQueue.remove(tmp);
            tmp.setoutQueueTime(System.nanoTime());
            tmp.setCpuStartTime(System.nanoTime());

            System.out.println("\tJOB REMOVED FROM READY Q.. SIZE IS NOW " +
                    LongTermScheduler.readyQueue.size());

            return tmp;
        }

}


/*
    public static int currentProcess;
    public static int nextProcess=0;
    public int numberOfProcess;
    private final int READY=0;
    private final int RUN=1;
    CPU_register register;


    public shortTermScheduler(){

    }

    public int selectProcess(){

                 if(OSDriver.PCB.getJob(nextProcess).getStatus() == READY){

                         OSDriver.PCB.getJob(nextProcess).setStatus(RUN);
                         return nextProcess;
                    }
                 else{
                        return -1;
                 }
    }
    
    public void runProcess(int currentProcess){

      
      register.SaveToRegister();
    }
}
*/


