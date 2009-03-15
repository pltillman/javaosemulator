import java.io.*;


  //****************************************************
  //  Default constructor
  //****************************************************
  public class OSDriver {

      private static Loader loader;
      public static MemoryManager MemManager;
      public static ProcessControlBlock PCB;
      public static OSToolkit tools;
     // public static Scheduler sched;
      public static RamMemory ram;
      public static int RamJobSize;
      public static DiskMemory disk;
      private static int jobSize;
      private static int totalJobSize=0;
     
//      public static ShortestJobFirst SJF;

//     private static int currentProcess;

      //****************************************************
      //  Begin main()
      //****************************************************
      public static void main(String[] args) {

          FileReader pFile;
          FileReader pFile2;
          //Create Memory Manager -- disk, ram,
          MemManager = new MemoryManager();

          //Create the Process Control Block
          PCB = new ProcessControlBlock();

          //Create an instance of the toolkit
          tools = new OSToolkit();


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

          } catch ( FileNotFoundException fnfe ) {
              fnfe.printStackTrace();
          } catch ( IOException ioe ) {
              ioe.printStackTrace();
          }

          //sched = new Scheduler();
          for (int i = 0; i < 10; i++) {
              Thread p1 = new Thread(new Scheduler());
              p1.start();
          }

             
         }
    

      }