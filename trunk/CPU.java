
import java.io.*;

public class CPU {


    private short opCode;
    private short type;
    private short s1_reg;
    private short s2_reg;
    private short d_reg;
    private short b_reg;
    private int oBufferSize;
    private int iBufferSize;
    private int tBufferSize;
    private int out_buffer;
    private int in_buffer;
    private int tmp_buffer;

    private int address;

    private int[] reg_Array;
    private int pc;

    private int effective_addr;

    private int STATE;
    private final int WAITING = 1;
    private final int RUNNING = 2;
    private BufferedWriter out;

    private static int ioCount;

    //NEED SOMETHING TO HOLD THE JOB SIZE IN RAM


    //************************************************
    //
    //} thrown into a big while (not 'halt' opcode).
    //************************************************
    public CPU() throws IOException {
        ioCount = 0;

        //Create registers and make the accumulator
        reg_Array = new int[16];
        reg_Array[1] = 0;

        out = new BufferedWriter(new FileWriter("CPU_log.txt"));
        out.append("CPU LOG FILE");
    }

    public void load (int[] j) throws IOException {

        out.append("\n|||||||||||||||||");
        out.append("\nJob #" + j[0]);

        //set the pc counter & buffer sizes
        pc = j[1];
        oBufferSize = j[3]; //size in # of words
        iBufferSize = j[4];
        tBufferSize = j[5];

        //run the duration of the job
        while (pc < j[2]) {
            String instr = fetch(pc);
            try {
                execute(decode(instr));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            pc += 4;
        }
        System.out.println(j[1] + " " + j[3]);
        //out.close();
        
    }
    

    //************************************************
    //  FETCH() TAKES THE PC VALUE AND GRABS THE NEXT INSTRUCTION
    //  AND APPENDS THE BYTES TOGETHER INTO A 32 BIT BINARY
    //  STRING FOR PROCESSESING BY DECODE()
    //************************************************
    protected String fetch(int pc) {

        try {
            out.append("\nfetching instruction..");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        //create a new string to hold the instruction
        String instruction = new String();
        String returnedInst = new String();

        //loop 4 times to get all pieces of the word
        for (int i=0; i<4; i++) {

            //read data from ram
            short line = OSDriver.MemManager.readRamData(pc++);
            System.out.println(line);

            //get binary represenation of value.
            instruction = Integer.toBinaryString(line);
            System.out.println(instruction);

            //add any leading zeros that were left off by the previous operation
            int b = instruction.length();

            if (instruction.length() < 8) {
                for (int y = 0; y < 8-b; y++) {
                    instruction = "0" + instruction;
                }
                System.out.println(instruction);
                System.out.println("byte length: " + instruction.length());
                returnedInst += instruction;
            } else {
                returnedInst += instruction;
            }
            //System.out.println("ret length: " + returnedInst.length());
        }
        return returnedInst;
        
    }



    //************************************************
    // DECODE() TAKES THE BINARY STRING REPRESENTATION OF
    // THE INSTRUCTION SET AND EXTRACTS THE APPROPRIATE
    // COMPONENTS. RETURNS THE OPCODE TO BE USED BY EXECUTE
    //************************************************
    protected int decode(String instr_req) throws IOException {

        //CHECK HERE IF ANYTHING IS WRONG WITH CALCULATED RESULTS!
        //String binInstr = Integer.toBinaryString(instr_req);
        //Integer.toBinaryString(Character.digit(line.charAt(i),16))

        
        out.append("decoding instruction....");
       

        //EXTRACT THE TYPE AND OPCODE FROM THE INSTRUCTION
        this.type = Short.parseShort(instr_req.substring(0,1),2);
        this.opCode = Short.parseShort(instr_req.substring(2,6),2);

        System.out.println("instruction length: " + instr_req.length());
        //USE TYPE TO DETERMINE HOW TO EXTRACT THE REMAINING COMPONENTS
        out.append("\nInstruction type:");
        switch (type) {

            case 00:
                out.append(" arithmetic");
                s1_reg = Byte.parseByte(instr_req.substring(7,10),2);
                s2_reg = Byte.parseByte(instr_req.substring(11,14),2);
                d_reg = Byte.parseByte(instr_req.substring(15,18),2);
                break;
            case 01:
                out.append(" branch of immediate");
                b_reg = Byte.parseByte(instr_req.substring(7,10),2);
                d_reg = Byte.parseByte(instr_req.substring(11,14),2);
                address = Byte.parseByte(instr_req.substring(15,31),2);
                if (address > 0) {
                    b_reg = 0;
                }
                break;
            case 10:
                out.append(" jump");
                address = Integer.parseInt(instr_req.substring(7,31));
                break;
            case 11:
                out.append(" IO");
                ioCount++;
                break;
            default:
                System.out.println("hit default decode");
                break;
        }
        
        return opCode;
    }


    //************************************************
    //
    //************************************************
    protected void execute(int o) throws IOException {
        out.append("executing instruction....");

        if (opCode != 0) {
            out.append("\nOPCODE =" + opCode);
            switch (opCode) {

                case 0:
                    out.append("\nPutting input buffer contents into accumulator");
                    //Reads content of I/P buffer into a accumulator
                    reg_Array[0] = in_buffer;
                    
                    break;
                case 1:
                    out.append("\nWriting content of accumulator into output buffer");
                    //Writes the content of accumulator into O/P buffer
                    out_buffer = reg_Array[0];
                    break;
                case 2:
                    out.append("\nStoring register in address");
                    //Stores content of a reg.  into an address
                    //byte b = 0b0011;
                    break;
                case 3:
                    out.append("\nLoading address into register");
                    //Loads the content of an address into a reg.

                    break;
                case 4:
                    out.append("\nopCode " + opCode);
                    //Transfers the content of one register into another
                    calc_arith(6);
                    break;
                case 5:
                    out.append("\nopCode " + opCode);
                    //Adds content of two S-regs into D-reg
                    calc_arith(0);
                    break;
                case 6:
                    out.append("\nopCode " + opCode);
                    //Subtracts content of two S-regs into D-reg
                    calc_arith(1);
                    break;
                case 7:
                    out.append("\nopCode " + opCode);
                    //Multiplies content of two S-regs into D-reg
                    calc_arith(2);
                    break;
                case 8:
                    out.append("\nopCode " + opCode);
                    //Divides content of two S-regs into D-reg
                    calc_arith(3);
                    break;
                case 9:
                    out.append("\nopCode " + opCode);
                    //Logical AND of two S-regs into D-reg
                    calc_arith(4);
                    break;
                case 10:
                    out.append("\nopCode " + opCode);
                    //Logical OR of two S-regs into D-reg
                    calc_arith(5);
                    break;
                case 11:
                    out.append("\nopCode " + opCode);
                    //Transfers address/data directly into a register
                    //immediate - MOVI
                    break;
                case 12:
                    out.append("\nopCode " + opCode);
                    //Adds a data directly to the content of a register
                    //ADDI
                    break;
                case 13:
                    out.append("\nopCode " + opCode);
                    //Multiplies a data directly to the content of a register
                    //MULI
                    break;
                case 14:
                    out.append("\nopCode " + opCode);
                    //Divides a data directly to the content of a register
                    //DIVI
                    break;
                case 15:
                    out.append("\nopCode " + opCode);
                    //Loads a data/address directly to the content of a register
                    //immediate - LDI
                    break;
                case 16:
                    out.append("\nopCode " + opCode);
                    //Sets the D-reg to 1 if  first S-reg is less than second S-reg, and 0 otherwise
                    break;
                case 17:
                    out.append("\nopCode " + opCode);
                    //Sets the D-reg to 1 if  first S-reg is less than a data, and 0 otherwise
                    break;
                case 18:
                    out.append("\nopCode " + opCode);
                    //Logical end of program
                    //int job =0;
                    //pc = OSDriver.PCB.getJob(job).getJobSize();
                    return;
                case 19:
                    out.append("\nopCode " + opCode);
                    //Does nothing and moves to next instruction
                    //pc += 4;
                    break;
                case 20:
                    out.append("\nopCode " + opCode);
                    //Jumps to a specified location
                    pc = address;
                    //OSDriver.tools.effective_addr(address));
                    break;
                case 21:
                    out.append("\nopCode " + opCode);
                    //Branches to an address when content of B-reg = D-reg
                    if (d_reg == b_reg) {
                        pc = address;
                    }
                    break;
                case 22:
                    out.append("\nopCode " + opCode);
                    //Branches to an address when content of B-reg <> D-reg
                    if (b_reg != d_reg) {
                        pc = address;
                    }
                    break;
                case 23:
                    out.append("\nopCode " + opCode);
                    //Branches to an address when content of D-reg = 0
                    if (d_reg == 0) {
                        pc = address;
                    }
                    break;
                case 24:
                    out.append("\nopCode " + opCode);
                    //Branches to an address when content of B-reg <> 0
                    if (b_reg != 0) {
                        pc = address;
                    }
                    break;
                case 25:
                    out.append("\nopCode " + opCode);
                    //Branches to an address when content of B-reg > 0
                    if (b_reg > 0) {
                        pc = address;
                    }
                    break;
                case 26:
                    out.append("\nopCode " + opCode);
                    //Branches to an address when content of B-reg < 0
                    if (b_reg < 0) {
                        pc = address;
                    }
                    break;
            }
        }
    }



    private void calc_arith(int i) {

        // i=0 - ADD
        // i=1 - SUBTRACT
        // i=2 - MULTIPLY
        // i=3 - DIVIDE
        // i=4 - LOGICAL 'AND'
        // I=5 - LOGICAL 'OR'
        // i=6 - SWAP REGISTERS
        
        switch (i) {
            case 0:
                d_reg = (short)(s1_reg + s2_reg);
                break;
            case 1:
                d_reg = (short)(s1_reg - s2_reg);
                break;
            case 2:
                d_reg = (short)(s1_reg * s2_reg);
                break;
            case 3:
                if (s2_reg == 0)
                    return;
                else
                    d_reg = (short)(s1_reg / s2_reg);
                break;
            case 4:
                d_reg = (short)(s1_reg & s2_reg);
                break;
            case 5:
                d_reg = (short)(s1_reg | s2_reg);
                break;
            case 6:
                short tmp_reg = s1_reg;
                s1_reg = s2_reg;
                s2_reg = tmp_reg;
                break;
            default:
                break;

        }

    }

    //************************************************
    //
    //************************************************
    private int extract(int oc) {

        if (oc == 1) {

        }
        return 1;
    }

    private void terminate(int i) {
        
    }
    private void jump(int i) {
        
    }
    
}
