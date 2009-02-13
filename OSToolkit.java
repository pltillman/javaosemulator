
public class OSToolkit {

    protected int effective_addr;
    protected int mem;
    protected int INDIRECT = 0;
    protected int DIRECT = 1;

    public OSToolkit() {


    }


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
        return mem;
    }
}
