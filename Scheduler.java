
/*The Scheduler loads programs from the disk into RAM according to the given
scheduling policy. The scheduler must note in the PCB, which physical
addresses in RAM each program/job begins. This ‘start’ address must be
stored in the base-register of the process. It must also use the
Input/Output buffer size information (in the control cards) for
allocating space in RAM. The Scheduler method either loads one program
at a time (in a simple batching system) or multiple programs
(in a multiprogramming system) depending on passed parameter(s). Thus,
the Scheduler works closely with the Memory manager and the
Effective-Address method.*/

public class Scheduler {

    public Scheduler() {


    }

    
    
    /*
     * The Dispatcher method assigns a process to the CPU. It is also 
     responsible for context switching of jobs when necessary (more on this 
     later!). For now, the dispatcher will extract parameter data from the PCB 
     and accordingly set the CPU’s PC, and other registers, before the OS calls 
     the CPU to execute it.*/
    public void dispatchJob() {

    }
}
