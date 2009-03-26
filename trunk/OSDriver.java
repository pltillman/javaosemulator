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

      private static CPU cpu0;
      private static CPU cpu1;
      private static CPU cpu2;
      private static CPU cpu3;

      private static CPU[] cpu_Array;

      private static Thread t;
//      public static ShortestJobFirst SJF;

//     private static int currentProcess;

      //****************************************************
      //  Begin main()
      //****************************************************
      public static void main(String[] args) {

          cpu_Array = new CPU[4];
          
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
        
        cpu0 = new CPU();
        cpu1 = new CPU();
        cpu2 = new CPU();
        cpu3 = new CPU();

        cpu_Array[0] = cpu0;
        cpu_Array[1] = cpu1;
        cpu_Array[2] = cpu2;
        cpu_Array[3] = cpu3;



        do {

            boolean ready = (cpu0.status == 0) || (cpu1.status == 0) || (cpu2.status == 0) || (cpu3.status == 0);


            while (ready) {

                sumPercent += LongTermScheduler.percent;
                counter++;

                for (int y=0; y<4; y++) {

                    System.err.println("CPU status:::::: Y " + y + " status " + cpu_Array[y].status);

                    switch (cpu_Array[y].status) {

                        case 0:
                            try {
                                
                                if (!LongTermScheduler.readyQueue.isEmpty()){
                                    cpu_Array[y].loadJob(STS.Store(0));
                                    new Thread(cpu_Array[y]).start();
                                }

                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }

                        default:
                            //System.out.println("DEFAULT REACHED");
                    }

                    //System.err.println("READY QUEUE SIZE....................... " + LongTermScheduler.readyQueue.size());
                    if (LongTermScheduler.readyQueue.size() == 0 && !DONE) {
                        System.out.println("\nADDING MORE JOBS........\n");
                        LTS.start();
                        STS.SJF();
                    }

                    ready = (cpu0.status == 0) || (cpu1.status == 0) || (cpu2.status == 0) || (cpu3.status == 0);

                }



//                if (!LongTermScheduler.readyQueue.isEmpty()) {
//
//                    System.out.println("NUMBER OF JOBS: " + LongTermScheduler.readyQueue.size());
//
//                    while (LongTermScheduler.readyQueue.size() > 0){
//
//                        //jMeta = STS.Store(0);
//
//                        try {
//                            for (int k=0; k<LongTermScheduler.readyQueue.size(); k++) {
//
//                                new Thread(new CPU(STS.Store(0))).start();
//                                new Thread(new CPU(STS.Store(0)));
//                                new Thread(new CPU(STS.Store(0)));
//                                new Thread(new CPU(STS.Store(0)));
//
//                            }
//                            System.out.println("*******************************************************************");
//
//
//                        } catch (IOException ioe) {
//                            ioe.printStackTrace();
//                        }
//                        //System.out.println("Assign job to CPU: " + cpu1);
//                    }
//                    System.out.println("\nADDING MORE JOBS........\n");
//                    LTS.start();
//                    STS.SJF();
//                    //numberOfProcess = LongTermScheduler.readyQueue.size();
//
//                }

//                if (tools.hasLoadedAllJobs()) {
//                    return;
//                } else {
//                    System.out.println("EFF THIS APPLICATION");
//                }

            }

        } while (!LongTermScheduler.readyQueue.isEmpty());

    
    }
 }