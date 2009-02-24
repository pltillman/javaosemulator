/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rebes926
 */


public class LongTermScheduler{
CircularArray jobQ;
PCB_block pcbq;
private final int RAMSIZE = 1024;
private static int Memleft = 1024;
private int loc= 0;

public LongTermScheduler() {
    jobQ = new CircularArray(10);
    start();
}


   
public void start()
{
    
    PCB_block job = OSDriver.PCB.getNextJob();
    int start = job.getDiskAddress();
    int size = job.getJobSize();
  while(Memleft>size)
  {
      job.set_mem_start(loc);
   for (int s=start; s<size*4; s++) {
        String hexString = OSDriver.MemManager.readDiskData(s);
        hexString = hexString.substring(2);  // so we need to strip of the prefix 0x
        System.out.println("hexString: " + hexString);  // then print again to see that it's just 0000dd99
        String binaryBits = Integer.toBinaryString(Integer.parseInt(hexString, 16));
        System.out.println(binaryBits);// then convert it to a string of bits
        
//        byte binaryBits1 = Byte.valueOf(binaryBits.substring(0,7), 2);
//        OSDriver.MemManager.writeRamData(loc, binaryBits1);
//        byte binaryBits2 = Byte.valueOf(binaryBits.substring(8,15), 2);
//        OSDriver.MemManager.writeRamData(loc, binaryBits2);
//        byte binaryBits3 = Byte.valueOf(binaryBits.substring(16,23), 2);
//        OSDriver.MemManager.writeRamData(loc, binaryBits3);
//        byte binaryBits4 = Byte.valueOf(binaryBits.substring(24,31), 2);
//        OSDriver.MemManager.writeRamData(loc, binaryBits4);
//
//        loc++;
//        Memleft--;
    }


     job.set_mem_end(loc+1);
     jobQ.AddQueue(job);
     job = OSDriver.PCB.getNextJob();
    start = job.getDiskAddress();
    size = job.getJobSize();
   
  }
    
    OSDriver.MemManager.printRam();



 /* for (int j=0; j<1; j++)
  {
   String hexString = OSDriver.MemManager.readDiskData(5);  // this will be a loop where 3 is some variable i
   System.out.println("hexString: " + hexString);   // this just shows the string as 0x0000dd99


   System.out.println("binaryBits: " + binaryBits1 + "\n" + binaryBits2 +
           "\n" + binaryBits3 + "\n" + binaryBits4);   // print it to verify
   //byte it = Byte.parseByte(binaryBits, 2);

    //System.out.println(it);
   }

   jobQueue.AddQueue(pcbq);*/



}

 

//}

public class CircularArray {
    private int front, back;
    private Object data[];

// Constructor

public CircularArray (int size) {
    front = 0;
    back = 0;
    data = new Object [size];

}

public boolean EmptyQueue ()
   { return (front == back); }

public Object PopQueue ()
    { Object z;

      if (EmptyQueue()) return null;
      else { z = data[front];
             front = (front + 1) % data.length;

             return(z);
           }
    }

public void AddQueue (Object z)
    { data[back] = z;
      back = (back + 1) % data.length;
    }

public Object FirstQueue()
{ return(data[front]); }


public Object LastQueue()
{ return(data[back-1]); }

}
}
     

