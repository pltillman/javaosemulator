/******************************************
 *
 *
 ******************************************/
public class OSToolkit {

    protected int effective_addr;
    protected int mem;
    protected int INDIRECT;
    protected int DIRECT;


    /**
     * This class is just a set of tools used by
     * other components in the OS.
     */
    public OSToolkit() {

        effective_addr = 0;
        INDIRECT = 0;
        DIRECT = 1;
    }

    public short effective_address(short i, short b) {

        return (short)(i + b);

    }
    

    /**
     * Used to calculate the effective address for a given
     *  instruction using either direct or indirect methods.
     *
     * @param flag Denotes which type of addressing method
     *         0=Indirect, 1= Direct
     * @param b byte array containing the data
     * @param D
     * @return calculated effective address
     */
    private int effective_addr(int flag, byte[] b, String D) {

        int offset = Integer.parseInt(D, 2);

        if (flag == INDIRECT) {
            //effective_addr = content(b_reg) + address;
            effective_addr = content(b[0]) + content(b[1]) + offset;
        } else if (flag == DIRECT) {

            effective_addr = content(b[0]) + offset;
        }
        
        return effective_addr;
    }

/**
 *
 * @param h
 * @return
 */
    protected String hexToByte(String h) {

        byte[] hexByte = new byte[4];
        String tmp = h.substring(2);
        byte tbyte;
        String bin = "";
        for (int i=0; i<tmp.length();i++) {
            System.out.println("bin: " + bin);
            bin += Integer.toBinaryString(Character.digit(h.charAt(i),16));
            System.out.println(i);

        }
        System.out.println(bin);
        return bin;
    }

    /*****************************************************
     *
     * @param index
     * @return
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

<<<<<<< .mine
/**
 * Loops through all of the jobs checking their status
 *  If a job's status is less than 0, it is either ready and has not
 *   been loaded, or is not yet ready or loaded.
 *
 * @return true if all jobs have been loaded, false otherwise
 */
=======
    
    /*****************************************************
     *
     * @return
     ****************************************************/
>>>>>>> .r104
    public Boolean hasLoadedAllJobs() {

        for (int i=0; i<OSDriver.PCB.getJobCount(); i++) {
            PCB_block tmp = OSDriver.PCB.getJob(i);
            if (tmp.getStatus() < 0) {
                System.out.println("JOB " + tmp.getJobID() + " is not finished");
                return false;

            }
        }
        return true;

    }

    /**
     * Get data from RAM at a given index.
     *
     * @param b Index in RAM to read data from
     * @return the content of index b in RAM
     */
    protected int content(short b) {

        return OSDriver.MemManager.readRamData(b);
        
    }
}
