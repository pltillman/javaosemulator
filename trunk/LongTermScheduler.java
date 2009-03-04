/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rebes926
 */
import java.util.ArrayList;

public class LongTermScheduler {
    public static ArrayList<PCB_block> readyQueue;
    //CircularArray jobQ;
    PCB_block pcbq;
    private final int RAMSIZE = 1024;
    private static int Memleft = 1024;
    private int loc= 0;
    private int CURRJOB;
    private int jobStart;
    private int jobSize;
    private int cJobStart;
    private int cJobEnd;
    private final int loaded = 2;
    PCB_block job;
    public LongTermScheduler() {
       //jobQ = new CircularArray(10);
        readyQueue= new ArrayList<PCB_block>();
        CURRJOB = 0;
        start();
        printQ();
    }

    public void start() {
       if(CURRJOB<OSDriver.PCB.getJobCount()){
         job = OSDriver.PCB.getJob(++CURRJOB);
        System.out.println("job count: " + OSDriver.PCB.getJobCount());

        jobStart = job.getDiskAddress();
        jobSize = job.getJobSize();

        System.out.println("Start: " + jobStart);
        System.out.println("Size: " + jobSize);
        }
       else
          System.out.println("There are no more jobs ");
        while ((Memleft>=(jobSize*4)) && (CURRJOB<OSDriver.PCB.getJobCount())) {
                job.set_mem_start(loc);
           // int s = jobStart;
            int v = jobStart+jobSize;
             //while( jobStart < (v)) {
            System.out.println("Threshold: " + (jobSize*4) + " of " + v);

            for (int p=jobStart; p<v;p++){
                String hexString = OSDriver.MemManager.readDiskData(p);
                hexString = hexString.substring(2);  // so we need to strip of the prefix 0x
                //System.out.println("hexString: " + hexString);  // then print again to see that it's just 0000dd99
                long t = Long.parseLong(hexString, 16);
                //System.out.println(t);
                String binaryBits = Long.toBinaryString(t);
                //System.out.println(binaryBits);// then convert it to a string of bits
                if (binaryBits.length() < 32) {
                    int diff = 32 - binaryBits.length();
                    for (int i=0; i<diff; i++) {
                        binaryBits = 0 + binaryBits;
                    }
                }
                //System.out.println("Binary bits: " + binaryBits);

                short binaryBits1 = Short.valueOf(binaryBits.substring(0,7), 2);
                //System.out.println(binaryBits1);
                System.out.println("Location: " + loc);
                OSDriver.MemManager.writeRamData(loc++, binaryBits1);
                short binaryBits2 = Short.valueOf(binaryBits.substring(8,15), 2);
                //System.out.println(binaryBits2);
                System.out.println("Location: " + loc);
                OSDriver.MemManager.writeRamData(loc++, binaryBits2);
                short binaryBits3 = Short.valueOf(binaryBits.substring(16,23), 2);
                //System.out.println(binaryBits3);
                System.out.println("Location: " + loc);
                OSDriver.MemManager.writeRamData(loc++, binaryBits3);
                short binaryBits4 = Short.valueOf(binaryBits.substring(24,31), 2);
                //System.out.println(binaryBits4);
                System.out.println("Location: " + loc);
                OSDriver.MemManager.writeRamData(loc++, binaryBits4);
                //loc += 4;

                Memleft -= 4;
                //s++;
            }
             
            System.out.println("Added job: " + CURRJOB + " at address: " +
                    jobStart + "-" + loc);
            CURRJOB++;
            job.set_mem_end(loc);
            job.setStatus(loaded);
            System.out.println("job end: " + job.get_mem_end(CURRJOB-1));
            readyQueue.add(job);

            job = OSDriver.PCB.getJob(CURRJOB);
            jobStart = job.getDiskAddress();
            jobSize = job.getJobSize();
            System.out.println("new job size: " + jobSize);


        }

        //System.out.println(OSDriver.MemManager.printRam());

//      for (int j=0; j<1; j++)
//      {
//       String hexString = OSDriver.MemManager.readDiskData(5);  // this will be a loop where 3 is some variable i
//       System.out.println("hexString: " + hexString);   // this just shows the string as 0x0000dd99
//
//
//       System.out.println("binaryBits: " + binaryBits1 + "\n" + binaryBits2 +
//               "\n" + binaryBits3 + "\n" + binaryBits4);   // print it to verify
//       //byte it = Byte.parseByte(binaryBits, 2);
//
//        //System.out.println(it);
//       }
//
//       jobQueue.AddQueue(pcbq);


    }
    public void checkJob(int j)
    {
       PCB_block cJob= OSDriver.PCB.getJob(j);
       if (cJob.getStatus() == loaded)
       {
           cJobStart = cJob.get_mem_start(j);
           cJobEnd= cJob.get_mem_end(j);

       }
    }

    public void printQ()
    {
       for (PCB_block t: readyQueue)
         System.out.println("id: " +t.getJobID());
     
    }

    public class CircularArray {
        
        private int front, back;
        private Object data[];


        public CircularArray (int size) {
            front = 0;
            back = 0;
            data = new Object [size];
        }

        public boolean EmptyQueue () {
            return (front == back);
        }


        public Object PopQueue () {
            Object z;

            if (EmptyQueue())
                return null;
            else {
                z = data[front];
                front = (front + 1) % data.length;
                return(z);
            }
        }

        public void AddQueue (Object z) {
            data[back] = z;
            back = (back + 1) % data.length;
        }

        public Object FirstQueue(){
            return(data[front]);
        }


        public Object LastQueue() {
            return(data[back-1]); }

        }
}