import java.util.ArrayList;

/**
 * 
 * @author Patrick Tillman
 */
public class LongTermScheduler {
    //create ready queue
    public static ArrayList<PCB_block> readyQueue;
    //create PCB_block object
    PCB_block pcbq;
    private final int RAMSIZE = 1024;
    //initially all RAM is available
    // -- value decremented as jobs are added
    private static int Memleft = 1024;
    private int loc;
    private int pageNum;
    private int frameNum=0, frameCount;
    private int CURRJOB;
    private int jobStart, v;
    private int jobSize;
    private int dataSize;
    private int jobIBSize;
    private int jobOBSize;
    private int jobTBSize;
    private int cJobStart;
    private int cJobEnd;
    private final int ready = 0;
    private int jobCount=0;
    PCB_block job;
    double percRam = 0;
    public static  double average;
    public static  double percent;
    

    /**
     *
     */
    public LongTermScheduler() {
        
        readyQueue= new ArrayList<PCB_block>();
        CURRJOB = 0;
        start();
        //printQ();
    }

<<<<<<< .mine

=======
    /**
     *
     */
>>>>>>> .r104
    public synchronized void start() {

        while (Memleft>=64 && (CURRJOB<OSDriver.PCB.getJobCount())) {

            job = OSDriver.PCB.getJob(CURRJOB);
            jobStart = job.getDiskAddress();
            jobSize = job.getJobSize();
            jobIBSize = job.get_Input_buffer_size();
            jobOBSize = job.get_Output_buffer_size();
            jobTBSize = job.get_tmp_buffer_size();
            dataSize = job.getDataSize();
            
            System.out.println("CURRENT JOB: " + CURRJOB + "\nStart: " + jobStart + "\nSize: " + jobSize);
            System.out.println("\nIs there enough memory left for the next job? " + (Memleft>=((jobSize*4)+(jobIBSize*4))));
            System.out.println("Have we reached the end of the job list? " + (CURRJOB<OSDriver.PCB.getJobCount()+1));

            frameCount = 0;
            
            while (frameCount < 4) {

                frameNum=OSDriver.MemManager.getNextFrame();
                pageNum = jobStart/4;
                System.out.println("**** frame num "+ frameNum + "****");
                loc=frameNum*16;
                v = jobStart+4;
                job.set_mem_start(loc);
                
                for (int p=jobStart; p<v; p++) {

                    String binaryBits = OSDriver.tools.getBinaryData(p);
                    //System.out.println("BINARY STRING AFTER " + binaryBits);// then convert it to a string of bits
                    System.out.println("Binary bits: " + binaryBits);

<<<<<<< .mine
       while (Memleft>=(jobSize*4) && (CURRJOB<OSDriver.PCB.getJobCount())) {
        
         frameNum=OSDriver.MemManager.getNextFrame();
         System.out.println("**** frame num "+ frameNum + "****");
         loc=frameNum*16;
         
         
            job.set_mem_start(loc);
            int v = jobStart+4;
=======
                    short binaryBits1 = Short.valueOf(binaryBits.substring(0,8), 2);
                    System.out.println(binaryBits1);
                    System.out.println("Decimal: " + binaryBits1 + "\t added at location: " + loc);
                    OSDriver.MemManager.writeRamData(loc++, binaryBits1);
                    short binaryBits2 = Short.valueOf(binaryBits.substring(8,16), 2);
                    System.out.println(binaryBits2);
                    System.out.println("Decimal: " + binaryBits2 + "\t added at location: " + loc);
                    OSDriver.MemManager.writeRamData(loc++, binaryBits2);
                    short binaryBits3 = Short.valueOf(binaryBits.substring(16,24), 2);
                    System.out.println(binaryBits3);
                    System.out.println("Decimal: " + binaryBits3 + "\t added at location: " + loc);
                    OSDriver.MemManager.writeRamData(loc++, binaryBits3);
                    short binaryBits4 = Short.valueOf(binaryBits.substring(24,32), 2);
                    System.out.println(binaryBits4);
                    System.out.println("Decimal: " + binaryBits4 + "\t added at location: " + loc);
                    OSDriver.MemManager.writeRamData(loc++, binaryBits4);
>>>>>>> .r104

                    Memleft -= 4;
                }
                
//                // add the job's input buffer into paging system
//                int k = jobStart+jobSize+jobIBSize;
//                for (int d=jobStart+jobSize; d<k; d++) {
//                    String binaryBits = OSDriver.tools.getBinaryData(d);
//
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(0,8), 2));
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(8,16), 2));
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(16,24), 2));
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(24,32), 2));
//
//
//                }
//
//                // add the job's output buffer into paging system
//                int o = jobStart+jobSize+jobOBSize;
//                for (int d=jobStart+jobSize+jobIBSize; d<o; d++) {
//                    String binaryBits = OSDriver.tools.getBinaryData(d);
//
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(0,8), 2));
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(8,16), 2));
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(16,24), 2));
//                    OSDriver.MemManager.writeRamData(loc++, Short.valueOf(binaryBits.substring(24,32), 2));
//                }

                if (frameCount == 0) {
                    job.setPTBR(pageNum);
                }

                jobStart += 4;
                OSDriver.MemManager.updateFrameTable(frameNum, pageNum, true, job.getJobID());
                System.out.println("****** Page Num "+ pageNum+ "******");
                OSDriver.PCB.updateTableEntry(pageNum, frameNum, true);
                frameCount++;
            }
            System.out.println("job "+ job.getJobID() +
                    " added to frame " + frameNum + " and page " + pageNum);

            
<<<<<<< .mine
            

            
            OSDriver.PCB.updateTableEntry(pageNum, frameNum, true);
            //OSDriver.MemManager.getNextFrame();
         
            System.out.println("Data Size: " + dataSize + " and location is " + loc);
            int z = 0;
=======
>>>>>>> .r104
            short[] tmp = new short[dataSize*4];

            String binaryDataBits;
            int dataStart = job.getDiskAddress()+job.getJobSize();
            
            //Get input buffer from disk and save it in PCB
            int z = 0;
            while ( z < job.getDataSize()*4 ) {
                System.out.println("GETTING JOB DATA...");
                binaryDataBits = OSDriver.tools.getBinaryData(dataStart++);
                
                tmp[z++] = Short.valueOf(binaryDataBits.substring(24,32), 2);
                tmp[z++] = Short.valueOf(binaryDataBits.substring(16,24), 2);
                tmp[z++] = Short.valueOf(binaryDataBits.substring(8,16), 2);
                tmp[z++] = Short.valueOf(binaryDataBits.substring(0,8), 2);
            }

            job.setCPUBuffer(tmp);
            job.set_mem_end(loc);
            job.setStatus(ready);

            //Add the job to the ready queue and record the time.
            readyQueue.add(job);
            job.setinQueueTime(System.nanoTime());

            System.out.println("Added job: " + CURRJOB + " at address: " +
                    job.get_mem_start() + "-" + job.get_mem_end() + "\n");

            CURRJOB++;            

<<<<<<< .mine

            CURRJOB++;


=======
//            System.out.println("Is the current job < total jobs " + (CURRJOB < OSDriver.PCB.getJobCount()));
//            System.out.println("Current job: " + CURRJOB + "\tTotal jobs: " + OSDriver.PCB.getJobCount());
>>>>>>> .r104
            
//            if (!OSDriver.tools.hasLoadedAllJobs() ) {
//
//                System.out.println("CURRENT JOB: " + CURRJOB);
//
//                //System.out.println("job end: " + job.get_mem_end(CURRJOB-1));
//                //System.out.println("job count: "+ jobCount);
//                //System.out.println("CURRJOB=" + CURRJOB);
//
//                job = OSDriver.PCB.getJob(CURRJOB);
//                jobStart = job.getDiskAddress();
//                jobSize = job.getJobSize();
//                dataSize = job.getDataSize();
//                jobIBSize = job.get_Input_buffer_size();
//
//            } else {
//                percentRam(1);
//                OSDriver.DONE = true;
//                return;
//            }
            
        }

        if (OSDriver.tools.hasLoadedAllJobs()) {
            System.out.println("ALL JOBS HAVE COMPLETED.");
            percentRam(1);
            OSDriver.DONE = true;
            return;
            //System.exit(1);
        } else {
            loc = 0;
            Memleft = 1024;
        }


        percentRam(0);
    }


    /**
     * Gets a hex string from disk and converts it into a 
     * binary string and adds leading zeros if converted 
     * string is less than 32-bits
     *
<<<<<<< .mine
     * @param index  location on disk to read data from
     * @return       String representation
     ****************************************************/
    public String getBinaryData(int index) {
        System.out.println("index:" + index);
        String hexString = OSDriver.MemManager.readDiskData(index);
        
        // so we need to strip of the prefix 0x
        hexString = hexString.substring(2,10);

        // then print again to see that it's just 0000dd99
        System.out.println("Adding hexString: " + hexString); 

        long t = Long.parseLong(hexString, 16);

        String binaryBits = Long.toBinaryString(t);

        // then convert it to a string of bits
        System.out.println("BINARY STRING " + binaryBits);

        int length = binaryBits.length();

        if (length < 32) {
            int diff = 32 - length;
            for (int i=0; i<diff; i++) {
                binaryBits = "0" + binaryBits;
            }
        }
        return binaryBits;
    }





 /**
  * Computes percentage of RAM used by jobs
  *
  * @param f  determines how percentage is calculated
  *             f=0 percentage is calculated for a single job
  *             f=1 total percentage is calculated via counters
  *                   in OSDriver class
  *
  */
=======
     * @param f -
     */
>>>>>>> .r104
    public void percentRam(int f){

        switch (f) {
            case 0:
                 average= (RAMSIZE-Memleft);
                 percent= average/RAMSIZE;

                System.out.println("Percentage of RAM used: " + percent*100);

                break;

            case 1:

                OSDriver.totalPercent = OSDriver.sumPercent/OSDriver.counter;
                System.out.println("Total average percent of RAM used:  " + OSDriver.totalPercent*100);

                break;
        }
   }


}

