import java.util.ArrayList;

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
    private int frameNum=0;
    private int CURRJOB;
    private int jobStart;
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
    

    public LongTermScheduler() {
        
        readyQueue= new ArrayList<PCB_block>();
        CURRJOB = 0;
        start();
        //printQ();
    }


    public synchronized void start() {


        System.out.println("CURRJOB: " + CURRJOB);
        job = OSDriver.PCB.getJob(CURRJOB);
        //System.out.println("job count: " + OSDriver.PCB.getJobCount());

        jobStart = job.getDiskAddress();
        jobSize = job.getJobSize();
        jobIBSize = job.get_Input_buffer_size();
        jobOBSize = job.get_Output_buffer_size();
        jobTBSize = job.get_tmp_buffer_size();
        dataSize = job.getDataSize();

        System.out.println("Start: " + jobStart);
        System.out.println("Size: " + jobSize);
        System.out.println("IP Buffer Size: " + jobIBSize);
        System.out.println("OP Buffer Size: " + jobOBSize);
        System.out.println("TMP Buffer Size: " + jobTBSize);

        System.out.println("\nIs there enough memory left for the next job? " + (Memleft>=((jobSize*4)+(jobIBSize*4))));
        System.out.println("Have we reached the end of the job list? " + (CURRJOB<OSDriver.PCB.getJobCount()+1));

       while (Memleft>=(jobSize*4) && (CURRJOB<OSDriver.PCB.getJobCount())) {
        
         frameNum=OSDriver.MemManager.getNextFrame();
         System.out.println("**** frame num "+ frameNum + "****");
         loc=frameNum*16;
         
         
            job.set_mem_start(loc);
            int v = jobStart+4;

            //System.out.println("Threshold: " + (jobSize*4) + " of " + v);
       
            for (int p=jobStart; p<v; p++) {

                String binaryBits = getBinaryData(p);
                //System.out.println("BINARY STRING AFTER " + binaryBits);// then convert it to a string of bits
                System.out.println("Binary bits: " + binaryBits);

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
                
                
                
                
                Memleft -= 4;
                
            if((p%3)==0){
                pageNum=p/4;
               OSDriver.MemManager.updateFrameTable(frameNum, pageNum, true, job.getJobID());
               System.out.println("****** Page Num "+ pageNum+ "******");
            
           }
            
            }
          
            System.out.println("job "+ job.getJobID() +
                    " added to frame " + frameNum + " and page " + pageNum);

            
            

            
            OSDriver.PCB.updateTableEntry(pageNum, frameNum, true);
            //OSDriver.MemManager.getNextFrame();
         
            System.out.println("Data Size: " + dataSize + " and location is " + loc);
            int z = 0;
            short[] tmp = new short[dataSize*4];

            String binaryDataBits;
            //Get input buffer from datafile and save it in PCB
            while ( z < job.getDataSize()*4 ) {
                System.out.println("GETTING JOB DATA...");
                binaryDataBits = getBinaryData(v++);
                
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(0,4), 2);
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(4,8), 2);
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(8,12), 2);
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(12,16), 2);
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(16,20), 2);
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(20,24), 2);
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(24,28), 2);
//                tmp[z++] = Short.valueOf(binaryDataBits.substring(28,32), 2);

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


            

            System.out.println("Is the current job < total jobs " + (CURRJOB < OSDriver.PCB.getJobCount()));
            System.out.println("Current job: " + CURRJOB + "\tTotal jobs: " + OSDriver.PCB.getJobCount());
            
            if (!OSDriver.tools.hasLoadedAllJobs() ) {

                System.out.println("CURRENT JOB: " + CURRJOB);
                
                //System.out.println("job end: " + job.get_mem_end(CURRJOB-1));
                //System.out.println("job count: "+ jobCount);
                //System.out.println("CURRJOB=" + CURRJOB);

                job = OSDriver.PCB.getJob(CURRJOB);
                jobStart = job.getDiskAddress();
                jobSize = job.getJobSize();
                dataSize = job.getDataSize();
                jobIBSize = job.get_Input_buffer_size();
                
            } else {
                percentRam(1);
                OSDriver.DONE = true;
                return;
            }
            
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


    /*****************************************************
     * Gets a hex string from disk and converts it into a 
     * binary string and adds leading zeros if converted 
     * string is less than 32-bits
     *
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

