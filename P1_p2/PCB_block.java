//****************************************************
//  This is a template for an individual PCB entry
//****************************************************
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
     *
     * @param i
     * @param s
     * @param p
     * @param a
     */
    PCB_block(int i, int s, int p, int a) {
        this.jobID = i;
        this.jobSize = s;
        this.jobPriority = p;
        this.address = a;
    }

    /**
     *
     * @param i
     * @param o
     * @param t
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


    public void setPTBR(int i) {
        this.PTBR = i;
    }
    public int getPTBR() {
        return this.PTBR;
    }
    /**
     *
     * @param s - data size to be set
     */
    public void setDataSize(int s) {
        this.dataSize = s;
    }
    public int getDataSize() {
        return this.dataSize;
    }
    public void setCPUBuffer(short[] i) {
        this.IP_buffer = i;
    }
    public void setOPBuffer(short[] o) {
        this.OP_buffer = o;
    }
    public void setTMPBuffer(short[] t) {
        this.TMP_buffer = t;
    }
    public short[] getCPUBuffer() {
        return this.IP_buffer;
    }
    public short[] getOPBuffer() {
        return this.OP_buffer;
    }
    public short[] getTMPBuffer() {
        return this.TMP_buffer;
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
    public int getDiskAddress() {
        return this.address;
    }
    public void setDiskAddress(int a) {
        this.address = a;
    }
    public Boolean setStatus(int s) {
        if (s == READY || s == FINISHED || s == WAITING || s == LOADED) {
            this.status = s;
            return true;
        }
        return false;
    }
    public int get_Input_buffer_size() {
        return this.input_buffer_size;
    }
    public int get_Output_buffer_size() {
        return this.output_buffer_size;
    }
    public int get_tmp_buffer_size() {
        return this.tmp_buffer_size;
    }
    public void terminate(int s) {
        this.status = s;
    }
    public void set_mem_start(int k) {
        this.mem_start = k;
    }
    public int get_mem_start() {
        return this.mem_start;
    }
    public void set_mem_end(int k) {
        this.mem_end = k;
    }
    public int get_mem_end() {
        return this.mem_end;
    }
    public void setCpuEndTime(long t) {
        this.cpuEndTime = t;
    }
    public void setCpuStartTime(long t) {
        this.cpuStartTime = t;
    }
    public void setinQueueTime(long t) {
        this.inqueueTime = t;
    }
    public void setoutQueueTime(long t) {
        this.outqueueTime = t;
    }
    public long getCpuEndTime() {
        return this.cpuEndTime;
    }
    public long getCpuStartTime() {
        return this.cpuStartTime;
    }
    public long getinQueueTime() {
        return this.inqueueTime;
    }
    public long getoutQueueTime() {
        return this.outqueueTime;
    }


}