
public class shortTermScheduler{



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



