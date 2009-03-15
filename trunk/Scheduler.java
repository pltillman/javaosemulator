import java.io.IOException;
import java.lang.Runnable;

public class Scheduler implements Runnable {

            public static LongTermScheduler LTS;
            public static shortTermScheduler STS;
             public static boolean DONE = false;

          //private List list;

          public Scheduler() {
              //list = pList;
          }

          public void run() {

        LTS = new LongTermScheduler();


        int numberOfProcess = LongTermScheduler.readyQueue.size();

        int[] jMeta = new int[6];
        CPU cpu1 = null;

        try {
           cpu1 = new CPU();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        STS = new shortTermScheduler();


         while(!DONE) {
             if (!LongTermScheduler.readyQueue.isEmpty()) {

                for(int i=0; i<numberOfProcess; i++){

                    jMeta = STS.Store(i);
               //     STS.Remove(i);
                    try {
                        cpu1.load(jMeta);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    //System.out.println("Assign job to CPU: " + cpu1);
                }

              //  System.out.println("Size of ArrayList before removing elements : "+ LongTermScheduler.readyQueue.size());
                LongTermScheduler.readyQueue.clear();
               // System.out.println("Size of ArrayList after removing elements : "+ LongTermScheduler.readyQueue.size());
                LTS.start();

                System.out.println("ADDING MORE JOBS");
             //   numberOfProcess = LongTermScheduler.readyQueue.size();

          }
      }

          }
      }