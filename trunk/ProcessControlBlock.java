
import java.util.Queue;

public class ProcessControlBlock {

    public int jobID;
    public int jobSize;
    public int jobPriority;
    public Queue PCB;


    protected void createJob(String i, String s, String p) {

        jobID = Integer.parseInt(i);
        jobSize = Integer.parseInt(s);
        jobPriority = Integer.parseInt(p);

       // PCB.add(p);
    }

}
