import java.io.*;


  //****************************************************
  //  Default constructor
  //****************************************************
  public class OSDriver {

      private static Loader loader;
      public static MemoryManager MemManager;
      public static ProcessControlBlock PCB;
      public static OSToolkit tools;
      public static LongTermScheduler LTS;
      public static shortTermScheduler STS;
      public static RamMemory ram;
      public static int RamJobSize;
      public static DiskMemory disk;
      private static int jobSize;
      private static int totalJobSize=0;
      public static boolean DONE = false;
      public static double sumPercent;
      public static int counter=0;
      public static double totalPercent;
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

        LTS = new LongTermScheduler();
        STS = new shortTermScheduler();
        STS.SJF();

        //int numberOfProcess = LongTermScheduler.readyQueue.size();

        PCB_block jMeta;
        
        CPU cpu1 = null;

        try {
            cpu1 = new CPU();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        while (!DONE || !LongTermScheduler.readyQueue.isEmpty()) {

            if (!LongTermScheduler.readyQueue.isEmpty()) {

                System.out.println("NUMBER OF JOBS: " + LongTermScheduler.readyQueue.size());
                while (LongTermScheduler.readyQueue.size() > 0){
                    jMeta = STS.Store(0);
                    try {
                        cpu1.load(jMeta);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    //System.out.println("Assign job to CPU: " + cpu1);
                }
                System.out.println("\nADDING MORE JOBS........\n");
                LTS.start();
                STS.SJF();
                //numberOfProcess = LongTermScheduler.readyQueue.size();

            }

            sumPercent +=LongTermScheduler.percent;
            counter++;
        }

    
    }
 }