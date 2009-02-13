import java.io.*;


//****************************************************
//  Default constructor
//****************************************************
public class OSDriver {

    private static Loader loader;
    public static MemoryManager MemManager;
    public static ProcessControlBlock PCB;
    public static OSToolkit tools;



    //****************************************************
    //  Begin main() 
    //****************************************************
    public static void main(String[] args) throws SystemError {

        FileReader pFile;
        FileReader pFile2;
        //Create Memory Manager -- disk, ram,
        MemManager = new MemoryManager();

        //Create the Process Control Block
        PCB = new ProcessControlBlock();

        //Create an instance of the toolkit
        tools = new OSToolkit();

        //Create a new CPU
        //CPU cpu1 = new CPU();

        try {
            pFile = new FileReader("DataFile1.txt");
            pFile2 = new FileReader("DataFile2.txt");


        //load the program files
        loader = new Loader(pFile, pFile2);

        try {
            loader.load();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        for (int u=0; u<2048; u++) {
            System.out.println(MemManager.readDiskData(u));
        }

        System.out.println(MemManager.readRamData(2));
        System.out.println(MemManager.printDisk());
        PCB.printPCB();



        } catch ( FileNotFoundException fnfe ) {
            fnfe.printStackTrace();

        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }




        //call scheduler
        



    }

}
