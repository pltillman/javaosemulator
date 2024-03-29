import java.io.*;

 /**
  * Core or main thread of the OS Simulator.  Calls the
  * loader to load the user programs and then calls the short-term scheduler
  * to assign a job to the CPU from the ready queue created by the
  * long-term scheduler.
  *
  */
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

      private static Boolean DEBUG = true;

      private static CPU cpu0;
      private static CPU cpu1;
      private static CPU cpu2;
      private static CPU cpu3;

      private static CPU[] cpu_Array;

      private static Thread t;

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

          System.out.println("KERNEL:::VIRTUAL SYSTEM INITIALIZING...\n\nKERNEL:::VIRTUAL SYSTEM INITIALIZING...COMPLETE");
          try {
              pFile = new FileReader("DataFile1.txt");
              pFile2 = new FileReader("DataFile2.txt");
               
			  System.out.println("\nKERNEL:::CALLING LOADER...");
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


            System.out.println("\nKERNEL:::CALLING LONG TERM SCHEDULER...\n");

            LTS = new LongTermScheduler();

            System.out.println("\nKERNEL:::CALLING SHORT TERM SCHEDULER...");
            STS = new shortTermScheduler();
            //STS.SJF();

            if (DEBUG) {
                MemManager.printFrameTable();
                PCB.printPageTable();
                PCB.printPTBR();
            }

            PCB_block jMeta;

            cpu0 = new CPU();
            cpu1 = new CPU();
            cpu2 = new CPU();
            cpu3 = new CPU();

            cpu_Array[0] = cpu0;
            cpu_Array[1] = cpu1;
            cpu_Array[2] = cpu2;
            cpu_Array[3] = cpu3;

            System.out.println("\nKERNEL: EXECUTING JOBS...");
            Thread t;

            do {

                //boolean ready = (cpu0.status == 0) || (cpu1.status == 0) || (cpu2.status == 0) || (cpu3.status == 0);
                boolean ready = (cpu0.status == 0);

                while (ready) {

                    sumPercent += LongTermScheduler.percent;
                    counter++;

                    for (int y=0; y<1; y++) {

                        //System.err.println("CPU status:::::: Y " + y + " status " + cpu_Array[y].status);

                        switch (cpu_Array[y].status) {

                            case 0:
                                try {
                                    if (!LongTermScheduler.readyQueue.isEmpty()) {
                                        PCB_block j = STS.Store(0);

                                        cpu_Array[y].loadJob(j);
                                        //cpu0.run();
                                        t = new Thread(cpu_Array[y]);
                                        t.start();
                                        MemManager.reclaimFrame(j.getFrameUsed());
                                    }
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }

                            default:
                                System.out.println("\t WAITING ON AN AVAILABLE CPU");
                        }
                    }
                    
                    if (LongTermScheduler.readyQueue.size() == 0 && !DONE && ready) {
                        System.out.println("\nADDING MORE JOBS........\n");
                        LTS.start();
                        STS.SJF();
                    }

                    ready = (cpu0.status == 0);
                    //ready = (cpu0.status == 0) || (cpu1.status == 0) || (cpu2.status == 0) || (cpu3.status == 0);
                    
                }
            } while (!LongTermScheduler.readyQueue.isEmpty());

    }
 }