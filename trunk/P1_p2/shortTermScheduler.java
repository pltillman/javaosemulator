import java.util.ArrayList;


/*************************************************************
 *
 * @author Patrick Tillman, Bin Zhou, Rebecca Andrews
 ************************************************************/
public class shortTermScheduler{

        private PCB_block pcb_e;
        public static ArrayList<Short> regQueue;
        int jID;
        private final int LOADED = 1;

        public shortTermScheduler(){

        }


        /**
         * 
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

        /************************************************************
         *
         * @param jID
         * @return
         ************************************************************/
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


