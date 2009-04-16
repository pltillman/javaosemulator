

public class FrameTableEntry {

    private int pageNum;
    private Boolean allocated;
    private int jobID;

    public FrameTableEntry() {

        this.pageNum = 0;
        this.allocated = false;
    }

    public void updateFrameEntry(int page, int job, Boolean allct) {
        this.pageNum = page;
        this.jobID = job;
        this.allocated = allct;

    }

    public void setPageNum(int p) {
        this.pageNum = p;
    }
    public void setJobID(int j) {
        this.jobID = j;
    }

    public void setAllocated(Boolean a) {
        this.allocated = a;
    }
    public int getPageNum() {
        return this.pageNum;
    }
    public int getJobID() {
        return this.jobID;
    }

    public Boolean isAllocated() {
        return this.allocated;
    }

    public String toString() {
        return "JobID: " + this.jobID + "\tPage Num:" + this.pageNum + "\tAlloc? " + this.allocated;
    }



}
