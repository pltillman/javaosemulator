/******************************************
 *
 *
 ******************************************/
public class OSToolkit {

    protected int effective_addr;
    protected int mem;
    protected int INDIRECT;
    protected int DIRECT;

    /******************************************
     * This class is just a set of tools used by
     * other components in the OS.
     ******************************************/
    public OSToolkit() {

        effective_addr = 0;
        INDIRECT = 0;
        DIRECT = 1;
    }


    /******************************************
     * Used to calculate the effective address for a given
     * instruction using either direct or indirect methoods.
     ******************************************/
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


    protected int content(byte b) {

        return OSDriver.MemManager.readRamData(b);
        
    }
}
