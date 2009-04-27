/**
 * Defines a page fault exception
 */
public class PageFault extends Exception {

    /**
     * Outputs if a page fault is to occur
     */
    public PageFault() {
        System.out.println("||||||||PAGE FAULT DETECTED.... attempting recovery||||||||");
        //OSDriver.MemManager.printRam();
    }

}