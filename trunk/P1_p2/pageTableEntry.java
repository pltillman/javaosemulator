

public class pageTableEntry {

    //private int jobID;
    private int pageNum;
    private int frameNum;
    private boolean valid;

<<<<<<< .mine
    /**
    * Default Constructor
    *   Set page number and frame number to 0.
    *   Set valid to false.
    */
   public pageTableEntry (){
            //this.jobID = j;
            this.pageNum = 0;
            this.frameNum = 0;
            this.valid = false;
=======

    public pageTableEntry (int p){
        //this.jobID = j;
        this.pageNum = p;
        this.frameNum = 0;
        this.valid = false;
>>>>>>> .r104
    }

<<<<<<< .mine
   /**
    * Updates a page table entry with the given
    *  frame number and valid or invalid
    *
    * @param f frame number
    * @param v valid or invalid bit
    */
   public void updatePageEntry (int f, boolean v) {
            this.frameNum = f;
            this.valid = v;
=======
    public void updatePageEntry (int f, boolean v) {
        this.frameNum = f;
        this.valid = v;
>>>>>>> .r104

    }

//    public int getPageJobID() {
//        return this.jobID;
//    }

//     public void setPageJobID(int p) {
//        this.pageNum = p;
//    }
 /**
  *Gets the page number
  * @return
  */
    
    public int getPageNumber() {
        return this.pageNum;
    }

    /**
     *Gets the frame number
     *
     * @return frame number 
     */
    public int getFrameNumber() {
        return this.frameNum;
    }
    /**
     *Sets the page number
     *
     * @param p page number to set to
     */
    public void setPageNumber(int p) {
        this.pageNum = p;
    }

    /**
     *Sets the frame number
     *
     * @param f frame number to set to
     */
    public void setFrameNumber(int f) {
        this.frameNum = f;
    }
    /**
     *Checks to see if a page is valid, throws a page fault
     *  if it is invalid.
     *
     * @return true if valid
     * @throws PageFault  If a page fault occurs
     */
    public boolean getValid() throws PageFault {
        if (this.valid == false) {
            throw new PageFault();
        }
        return this.valid;
    }

    /**
     *Sets a pages validity
     *
     * @param a true = valid
     *          false= invalid
     */
    public void setValid(Boolean a) {
        this.valid = a;

    }

    public String toString(){
        return "Page Index: " + this.pageNum + "\tFrame Num: " + this.frameNum +
                "\tvalid? " + this.valid;
    }
//    public int getJobID() {
//        return this.jobID;
//    }
}