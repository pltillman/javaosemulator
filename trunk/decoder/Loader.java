
import java.io.*;
import java.util.StringTokenizer;

//****************************************************
//
//****************************************************
public class Loader {

    //String file_1 = "DataFile1.txt";
    //String file_2 = "DataFile2.txt";

    FileReader file_1;
    private static int count=0;
    protected int addr;


    private short opCode;
    private short type;
    private short s1_reg;
    private short s2_reg;
    private short d_reg;
    private short b_reg;
    private short reg1;
    private short reg2;
    private long address;
    private static int ioCount;


    public Loader(FileReader f1) {

        this.file_1 = f1;

    }
    //****************************************************
    //
    //****************************************************
    protected void load() throws IOException {

        try {
            BufferedReader input =  new BufferedReader(file_1);

            if (readDataFile(input) ) {
                input.close();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private Boolean readDataFilol0e(BufferedReader in) {

        addr = 0;

        try {
            String str = in.readLine();

            while ( str.length() > 0 ) {
                //processData(str);
                //System.out.println("trying to load datafile");
                if ( str.contains("JOB") ) {

                    System.out.println(str);
                    str = str.substring(7,str.length());
                    //System.out.println(str);

                    addJob(str, 0);
                    str = in.readLine();

                    while ( !str.contains("//") ) {
                        System.out.println("adding " + str + " to location " + count);
                        addData(str, 0, count++);
                        //str = str.substring(2, 9);
                        String bin = getBinaryData(str);
                        decode(bin);
                        str = in.readLine();
                    }

                } else if ( str.contains("Data") ) {
                    System.out.println(str);
                    str = str.substring(8, str.length());
                    addJob(str, 1);
                    str = in.readLine();

                    while ( !str.contains("//") ) {
                        addData(str, 0, count++);
                        str = in.readLine();
                    }
                } else {
                    str = in.readLine();
                    System.out.println("Read data: " + str);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
            return true;
    }
    //****************************************************
    //
    //****************************************************
    protected void addJob(String s, int o) {
        // o=0 indicates job metadata
        // o=1 indicates jobdata metadata

        StringTokenizer token = new StringTokenizer(s);


        if (o == 0) {
            //add job to PCB
            int id = Integer.parseInt(token.nextToken(),16);
            int size = Integer.parseInt(token.nextToken(),16);
            int priority = Integer.parseInt(token.nextToken(),16);
            //addr ++;
        } else if (o == 1) {
            //add job data to PCB
            int input = Integer.parseInt(token.nextToken(),16);
            int output = Integer.parseInt(token.nextToken(),16);
            int temp = Integer.parseInt(token.nextToken(),16);

            //addr++;
        }
    }

    //****************************************************
    //
    //****************************************************
    protected void addData(String s, int o, int loc) {


    }



    protected synchronized int decode(String instr_req) throws IOException {

        //CHECK HERE IF ANYTHING IS WRONG WITH CALCULATED RESULTS!
        //String binInstr = Integer.toBinaryString(instr_req);
        //Integer.toBinaryString(Character.digit(line.charAt(i),16))

        System.out.println("\nBinary instruction: " + instr_req );


        //EXTRACT THE TYPE AND OPCODE FROM THE INSTRUCTION
        this.type = Short.parseShort(instr_req.substring(0,2),2);
        this.opCode = Short.parseShort(instr_req.substring(2,8),2);

        switch (type) {

            case 0:
                s1_reg = Short.parseShort(instr_req.substring(8,12),2);
                s2_reg = Short.parseShort(instr_req.substring(12,16),2);
                d_reg = Short.parseShort(instr_req.substring(16,20),2);
                System.out.println("s1_reg: " + s1_reg + "\ns2_reg:" + s2_reg + "\nd_reg:" + d_reg);
                long dataCHK = Long.parseLong(instr_req.substring(20,32),2);
                break;

            case 1:
                b_reg = Short.parseShort(instr_req.substring(8,12),2);
                d_reg = Short.parseShort(instr_req.substring(12,16),2);
                address = Long.parseLong(instr_req.substring(16,32),2);
                System.out.println("b_reg:" + b_reg + "\nd_reg:" + d_reg + "\naddress:" + address);
                break;

            case 2:
                address = Integer.parseInt(instr_req.substring(8,32));
                System.out.println("JUMP ADDRESS: " + address);
                break;

            case 3:
                reg1 = Short.parseShort(instr_req.substring(8,12),2);
                reg2 = Short.parseShort(instr_req.substring(12,16),2);
                address = Long.parseLong(instr_req.substring(16,32),2);
                System.out.println("Reg1: " + reg1 + "\nReg2: " + reg2 + "\nAddress: " + address);
                ioCount++;
                break;

            default:
                System.err.println("ERROR: HIT DEFAULT DECODE TYPE");
                break;

        }

        return opCode;
    }


    /************************************************
     *
     * @param o
     * @throws java.io.IOException
     ************************************************/
    protected synchronized void execute(int o, int jID) throws IOException {
        System.out.println("\nExecuting instruction...." + " OPCODE = " + o);

        if (!(opCode < 0) || (opCode > 26)) {
            //out.append("\nOPCODE =" + opCode);
            switch (opCode) {

                case 0:
                    System.out.println("Putting input buffer contents into accumulator");
                    break;

                case 1:
                    System.out.println("Writing content of accumulator into output buffer");
                    break;

                case 2:
                    System.out.println("Storing register in address");
                    break;

                case 3:
                    System.out.println("Loading address into register");
                    break;

                case 4:
                    System.out.println("Swapping registers");
                    break;

                case 5:
                    System.out.println("Adding s_regs into d_reg");
                    break;

                case 6:
                    System.out.println("Subtracting s_regs into d_reg");
                    break;

                case 7:
                    System.out.println("Multiplying s_regs into d_reg");
                    break;

                case 8:
                    System.out.println("Dividing s_regs into d_reg");
                    break;

                case 9:
                    System.out.println("Logical AND of s_regs");
                    break;

                case 10:
                    System.out.println("Logical OR of s_regs");
                    break;

                case 11:
                    System.out.println("Transferring data into register");
                    break;

                case 12:
                    System.out.println("Adding data into register");
                    break;

                case 13:
                    System.out.println("Multiplying data into register");
                    break;

                case 14:
                    System.out.println("Dividing data into register");
                    break;

                case 15:
                    System.out.println("Loading data/address into register");
                    break;

                case 16:
                    System.out.println("Checking if s1_reg < s2_reg");
                    break;

                case 17:
                    System.out.println("Checking if s1_reg <  data");
                    break;

                case 18:
                    System.out.println("END OF PROGRAM - OPCODE " + opCode);
                    System.out.println("IOCOUNT: " + ioCount);
                    break;

                case 19:
                    System.out.println("Moving to the next instruction");
                    //Does nothing and moves to next instruction
                    break;

                case 20:
                    System.out.println("Jumping to another location");
                    break;

                case 21:
                    System.out.println("Checking if b_reg = d_reg, then branch");
                    break;

                case 22:
                    System.out.println("Checking if b_reg != d_reg, then branch");
                    break;

                case 23:
                    System.out.println("Checking if d_reg is 0, then branch");
                    break;

                case 24:
                    System.out.println("Checking if b_reg != 0, then branch");
                    break;

                case 25:
                    System.out.println("Checking if b_reg > 0, then branch");
                    break;

                case 26:
                    System.out.println("Checking if b_reg < 0, then branch");
                    break;

                default:
                    System.err.println("UNKNOWN OPCODE");
                    break;

            }
        } else {
            System.err.println("DIDN'T DECODE... OPCDOE = " + opCode);
        }
    }


    public String getBinaryData(String h) {

        // so we need to strip of the prefix 0x
        String hexString = h.substring(2,10);
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

}
