public class pageTableEntry {

    //private int jobID;
    private int pageNum;
    private int frameNum;
    private boolean valid;

   public pageTableEntry (){
            //this.jobID = j;
            this.pageNum = 0;
            this.frameNum = 0;
            this.valid = false;
    }

   public void updatePageEntry (int f, boolean v) {
            this.frameNum = f;
            this.valid = v;

    }

//    public int getPageJobID() {
//        return this.jobID;
//    }

//     public void setPageJobID(int p) {
//        this.pageNum = p;
//    }

    public int getPageNumber() {
        return this.pageNum;
    }

    public int getFrameNumber() {
        return this.frameNum;
    }
    public void setPageNumber(int p) {
        this.pageNum = p;
    }

    public void setFrameNumber(int f) {
        this.frameNum = f;
    }
    public boolean getValid() throws PageFault {
        if (this.valid == false) {
            throw new PageFault();
        }
        return this.valid;
    }

    public void setValid(Boolean a) {
        this.valid = a;

    }

//    public int getJobID() {
//        return this.jobID;
//    }
}