

public class FrameTableEntry {

    private int pageNum;
    private Boolean allocated;
    private int jobID;

    public FrameTableEntry() {

        this.pageNum = 0;
        this.allocated = false;
    }

    /**
     * Adds a new entry to the frame table
     *
     * @param page   page number
     * @param job    job ID
     * @param allct  true if frame is allocated, otherwise false
     */
    public void updateFrameEntry(int page, int job, Boolean allct) {
        this.pageNum = page;
        this.jobID = job;
        this.allocated = allct;

    }

    /**
     * Sets page number
     *
     * @param p  page number
     */
    public void setPageNum(int p) {
        this.pageNum = p;
    }

    /**
     * sets job ID
     *
     * @param j job ID
     */
    public void setJobID(int j) {
        this.jobID = j;
    }

    /**
     * Sets associated frame to allocated
     *
     * @param a true if allocated, otherwise false
     */
    public void setAllocated(Boolean a) {
        this.allocated = a;
    }

    /**
     * Returns the page number associated with a frame
     *
     * @return associated page number
     */
    public int getPageNum() {
        return this.pageNum;
    }

    /**
     * Returns the job ID associated with a frame
     *
     * @return associated job ID
     */
    public int getJobID() {
        return this.jobID;
    }

    /**
     * Checks if a fram is allocated
     * @return true if associated frame is allocated
     *           false otherwise
     */
    public Boolean isAllocated() {
        return this.allocated;
    }




}
