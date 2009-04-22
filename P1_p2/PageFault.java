
public class PageFault extends Exception {


    public PageFault() {
        System.out.println("||||||||PAGE FAULT DETECTED.... attempting recovery||||||||");
        //OSDriver.MemManager.printRam();
    }

}
