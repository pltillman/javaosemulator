public class ShortestJobFirst{

private PCB_block pcb_e;
private final int READY=0;

  public ShortestJobFirst(){

              SJF();
  }

 //First come first serve
 public void SJF(){

         //Sort readyQueue in priority order
         for(int index=0; index < pcb_e.getJobSize(); index++){
                 if(OSDriver.PCB.getJob(index).getJobPriority() > OSDriver.PCB.getJob(index+1).getJobPriority()){

                         OSDriver.PCB.getJob(index+1).Change(OSDriver.PCB.getJob(index));
                         OSDriver.PCB.getJob(index).Change(pcb_e);
                 }
         }

         //set status READY
         for(int index=0; index < pcb_e.getJobSize(); index++){

             OSDriver.PCB.getJob(index).setStatus(READY);
         }
    }
}