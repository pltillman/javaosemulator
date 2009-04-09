
public class PageFault extends Exception {


    public PageFault() {
        System.err.println("Page Fault Detected.... recovering now...");
    }

}
