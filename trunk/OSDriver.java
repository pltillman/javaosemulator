import java.io.File;
import java.io.IOException;


public class OSDriver {

    private static Loader loader;
    public static MemoryManager MemManager;
    private static ProcessControlBlock PCB;

    public static void main(String[] args) throws SystemError {

        //Create Memory Manager -- disk, ram,
        MemManager = new MemoryManager();

        //Create the Process Control Block
        PCB = new ProcessControlBlock;

        //Create a new CPU
        CPU cpu1 = new CPU();

        File pFile = new File("DataFile1.txt");
        File pFile2 = new File("DataFile2.txt");

        //load the program files
        loader = new Loader(pFile, pFile2);

        try {
            loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }



        //call scheduler
        



    }

}
