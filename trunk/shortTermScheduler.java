import java.util.ArrayList;

public class shortTermScheduler{

        private PCB_block pcb_e;
        public static ArrayList<Short> regQueue;
        int jID;

        public shortTermScheduler(){

        }

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

        
        public int[] Store(int jID){

            int[] value = new int[6];
            PCB_block tmp = LongTermScheduler.readyQueue.get(jID);

            value[0] = tmp.getJobID();
            value[1] = tmp.get_mem_start(jID);
            value[2] = tmp.get_mem_end(jID);
            value[3] = tmp.get_Input_buffer_size(jID);
            value[4] = tmp.get_Output_buffer_size(jID);
            value[5] = tmp.get_tmp_buffer_size(jID);

            return value;
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


