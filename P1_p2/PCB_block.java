
/**
 * This is a template for an individual PCB entry and
 * its associated attributes.
 */
public class PCB_block {

    private int jobID;
    private int jobSize;
    private int address;
    private int jobPriority;
    private int input_buffer_size;
    private int output_buffer_size;
    private int tmp_buffer_size;
    private int dataSize;
    private int status;

    private short[] IP_buffer;
    private short[] OP_buffer;
    private short[] TMP_buffer;

    private final int READY = 0;
    private final int LOADED = 1;
    private final int WAITING = 2;
    private final int FINISHED = 3;

    private long inqueueTime = 0;
    private long outqueueTime = 0;
    private long cpuStartTime = 0;
    private long cpuEndTime = 0;

    private int mem_start;
    private int mem_end;
    private int PTBR;

    /**
     * Default constructor
     *
     * @param i job ID
     * @param s job size
     * @param p job priority
     * @param a job address
     */
    PCB_block(int i, int s, int p, int a) {
        this.jobID = i;
        this.jobSize = s;
        this.jobPriority = p;
        this.address = a;
    }

    /**
     * Adds meta data to associated job and stores
     * the buffer data in an array
     *
     * @param i job's input buffer size
     * @param o job's output buffer size
     * @param t job's temporary buffer size
     */
    public void addMetadata(int i, int o, int t) {
        this.input_buffer_size = i;
        this.output_buffer_size = o;
        this.tmp_buffer_size = t;
        this.IP_buffer = new short[i];
        this.OP_buffer = new short[o];
        this.TMP_buffer = new short[t];
        this.status = -1;
    }
    
    /**
     * Mutator method to set the associated
     * job's page table base register
     *
     * @param i page table base register value to set
     */
    public void setPTBR(int i) {
        this.PTBR = i;
    }

    /**
     * Accessor method to get the associated job's
     * page table base register
     *
     * @return page table base register value
     */
    public int getPTBR() {
        return this.PTBR;
    }

    /**
     * Mutator method to set data size
     *
     * @param s  data size to be set
     */
    public void setDataSize(int s) {
        this.dataSize = s;
    }
    /**
     * Accessor method to get the data size
     *
     * @return data size
     */
    public int getDataSize() {
        return this.dataSize;
    }
    /**
     * Mutator method to set the CPU buffer
     *
     * @param i input buffer array
     */
    public void setCPUBuffer(short[] i) {
        this.IP_buffer = i;
    }
    /**
     * Mutator method to set output buffer
     *
     * @param o output buffer array
     */
    public void setOPBuffer(short[] o) {
        this.OP_buffer = o;
    }

    /**
     * Mutator method to set temporary buffer
     *
     * @param t temporary buffer array
     */
    public void setTMPBuffer(short[] t) {
        this.TMP_buffer = t;
    }

    /**
     * Accessor method for CPU buffer
     *
     * @return associated input buffer
     */
    public short[] getCPUBuffer() {
        return this.IP_buffer;
    }
    /**
     * Accessor method for output buffer
     *
     * @return associated output buffer
     */
    public short[] getOPBuffer() {
        return this.OP_buffer;
    }
    /**
     * Accessor method for temporary buffer
     *
     * @return associated temporary buffer
     */
    public short[] getTMPBuffer() {
        return this.TMP_buffer;
    }
    /**
     * Accessor method for job ID
     *
     * @return associated job ID
     */
    public int getJobID() {
        return this.jobID;
    }
    /**
     * Accessor method for job priority
     *
     * @return associated job priority
     */
    public int getJobPriority() {
        return this.jobPriority;
    }
    /**
     * Accessor method for job size
     *
     * @return associated job size
     */
    public int getJobSize() {
        return this.jobSize;
    }
    /**
     * Accessor method for job's status
     *
     * @return associated job's status
     */
    public int getStatus() {
        return this.status;
    }
    /**
     * Accessor method for disk address
     *
     * @return associated address on disk
     */
    public int getDiskAddress() {
        return this.address;
    }
    /**
     * Mutator method to set a job's disk address
     *
     * @param a address on disk to set
     */
    public void setDiskAddress(int a) {
        this.address = a;
    }
    /**
     * Sets the associated job's status
     *
     * @param s staus
     * @return true or false based on argument
     */
    public Boolean setStatus(int s) {
        if (s == READY || s == FINISHED || s == WAITING || s == LOADED) {
            this.status = s;
            return true;
        }
        return false;
    }
    /**
     * Accessor method for input buffer size
     *
     * @return size of associated input buffer
     */
    public int get_Input_buffer_size() {
        return this.input_buffer_size;
    }
    /**
     * Accessor mehtod for output buffer size
     *
     * @return size of associated output buffer
     */
    public int get_Output_buffer_size() {
        return this.output_buffer_size;
    }
    /**
     * Accessor method for temporary buffer size
     *
     * @return size of associated temporary buffer
     */
    public int get_tmp_buffer_size() {
        return this.tmp_buffer_size;
    }
    /**
     * Changes the status of associated job to
     * end the process
     *
     * @param s status 
     */
    public void terminate(int s) {
        this.status = s;
    }
    /**
     * Mutator method to set starting address in memory
     *
     * @param k address in memory to set as starting address
     */
    public void set_mem_start(int k) {
        this.mem_start = k;
    }
    /**
     * Accessor method for memory start
     *
     * @return address in memory where associated job starts
     */
    public int get_mem_start() {
        return this.mem_start;
    }
    /**
     * Mutator method to set ending address in memory
     *
     * @param k end address to set in memory
     */
    public void set_mem_end(int k) {
        this.mem_end = k;
    }
    /**
     * Accessor method for memory end address
     *
     * @return address in memory where associated job ends
     */
    public int get_mem_end() {
        return this.mem_end;
    }
    /**
     * Mutator method to set CPU end time
     * (to calculate execution time)
     *
     * @param t CPU end time to set for associated job
     */
    public void setCpuEndTime(long t) {
        this.cpuEndTime = t;
    }
    /**
     * Mutator method to set CPU start time
     * (to calculate execution time)
     *
     * @param t CPU start time to set for associated job
     */
    public void setCpuStartTime(long t) {
        this.cpuStartTime = t;
    }
    /**
     * Mutator method to set the time associated job enters
     * the ready queue
     * (to calculate waiting time)
     *
     * @param t time associated job enters ready queue
     */
    public void setinQueueTime(long t) {
        this.inqueueTime = t;
    }
    /**
     * Mutator method to set the time associated job
     * leaves the ready queue
     * (to calculate waiting time)
     *
     * @param t time associated job leaves the ready queue
     */
    public void setoutQueueTime(long t) {
        this.outqueueTime = t;
    }
    /**
     * Accessor method for CPU end time
     * (to calculate execution time)
     *
     * @return CPU end time for associated job
     */
    public long getCpuEndTime() {
        return this.cpuEndTime;
    }
    /**
     * Accessor method for CPU start time
     * (to calculate execution time)
     *
     * @return CPU start time for associated job
     */
    public long getCpuStartTime() {
        return this.cpuStartTime;
    }
    /**
     * Accessor method for the time a job entered
     * the ready queue (to calculate waiting time)
     *
     * @return time associated job entered ready queue
     */
    public long getinQueueTime() {
        return this.inqueueTime;
    }
    /**
     * Accessor method for the time a job left the ready
     * queue (to calculate waiting time)
     *
     * @return time associated job left the ready queue
     */
    public long getoutQueueTime() {
        return this.outqueueTime;
    }


}