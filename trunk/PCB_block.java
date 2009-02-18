//****************************************************
//  This is a template for an individual PCB entry
//****************************************************
public class PCB_block {
    private int jobID;
    private int jobSize;
    private int address;
    private int jobPriority;
    private int input_buffer;
    private int output_buffer;
    private int tmp_buffer;
    private int status;
    private final int READY = 0;
    private final int FINISHED = 1;
    private int mem_loc; //added by the schedule once the job is
                         //placed in memory. indicates what
                         //physical address in ram the job begins.
    private int mem_start;
    private int mem_end;
    

    PCB_block(int i, int s, int p, int a) {
        this.jobID = i;
        this.jobSize = s;
        this.jobPriority = p;
        this.address = a;
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
    public int getStatus() {
        return this.status;
    }
    public int getJobAddress() {
        return this.address;
    }
    public void setJobAddress(int a) {
        this.address = a;
    }
    public Boolean setStatus(int s) {
        if (s == READY || s == FINISHED) {
            this.status = s;
            return true;
        }
        return false;
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
    public void terminate(int s) {
        this.status = s;
    }
    public void set_mem_start(int k) {
        this.mem_start = k;
    }
    public int get_mem_start(int jID) {
        return this.mem_start;
    }
    public void set_mem_end(int k) {
        this.mem_end = k;
    }
    public int get_mem_end(int jID) {
        return this.mem_end;
    }

}