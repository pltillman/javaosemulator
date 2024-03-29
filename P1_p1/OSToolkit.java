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

    public short effective_address(short i, short b) {

        return (short)(i + b);

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



    
    protected int content(short b) {

        return OSDriver.MemManager.readRamData(b);
        
    }
}
