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
      private static boolean DONE = false;
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



          } catch ( FileNotFoundException fnfe ) {
              fnfe.printStackTrace();

          } catch ( IOException ioe ) {
              ioe.printStackTrace();
          }

        //call scheduler
        //SJF = new ShortestJobFirst();
        LTS = new LongTermScheduler();
        STS = new shortTermScheduler();


        int numberOfProcess = LongTermScheduler.readyQueue.size();

        int[] jMeta = new int[6];
        CPU cpu1 = null;
        try {
            cpu1 = new CPU();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        PCB_block job;
        disk = new DiskMemory(2048);
        ram = new RamMemory(1024);
        RamJobSize=0;

        LTS.start();
        STS.SJF();
         

     //    while(!DONE || !LongTermScheduler.readyQueue.isEmpty()){

            for(int i=0; i<numberOfProcess; i++){
                jMeta = STS.Store(i);
                System.out.println("Job: " + i);
                try {
                    cpu1.load(jMeta);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                //System.out.println("Assign job to CPU: " + cpu1);

            

 //           LTS.start();
  //          STS.SJF();

//            doMore = true;
//
//            while(doMore){

//                numberOfProcess--;
//                doMore = false;
//
//                for(int k = 0; k < numberOfProcess; k++){
//
//                job = PCB.getJob(++k);
//                jobSize = job.getJobSize();
//                System.out.println("Current Job Ram size" + 4*jobSize);
//                totalJobSize += 4*jobSize;
//                System.out.println("increased Ram size: " + totalJobSize);
//                doMore = true;
//                }
//            }
         }
    //  }

  }
  }