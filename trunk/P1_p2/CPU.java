
import java.io.*;


public class CPU implements Runnable {


    private short opCode;
    private short type;
    private short s1_reg;
    private short s2_reg;
    private short d_reg;
    private short b_reg;
    private short reg1;
    private short reg2;

    private Boolean jumped;

    private final int data = 0;
    private int jobSize;

    private int oBufferSize;
    private int iBufferSize;
    private int tBufferSize;
    
    private short[] cpu_buffer;

    private long address;

    private int[] reg_Array;
    private int pc, logicalEnd, physical;

    private final int ACCUM = 0;
    private final int ZERO = 1;
    private final int FINISHED = 3;
    private final int WAITING = 3;

    private BufferedWriter out;

    private static int ioCount;
    private PCB_block j;

    // status 0 = ready; status 1 = busy
    public int status = 0;
    //NEED SOMETHING TO HOLD THE JOB SIZE IN RAM


    //************************************************
    //
    //} thrown into a big while (not 'halt' opcode).
    //************************************************
    public CPU () {
        status = 0;
    }



    public synchronized void loadJob(PCB_block job) throws IOException {

        status = 1;
        j = job;
        ioCount = 0;

        jumped = false;
        //Create registers and make the accumulator
        reg_Array = new int[16];
        reg_Array[ZERO] = 0;

        out = new BufferedWriter(new FileWriter("CPU_log.txt"));
        out.append("CPU LOG FILE");

    }

    public synchronized void run ()  {
        
        ioCount = 0;
        try {
            out.append("\n|||||||||||||||||");
            out.append("\nJob #" + j.getJobID());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("\nJob #" + j.getJobID() + " EXECUTING");

        //set the pc counter & buffer sizes
        pc = 0;
        System.out.println("Job starting at: " + pc);
        physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
        oBufferSize = j.get_Output_buffer_size(); //size in # of words
        iBufferSize = j.get_Input_buffer_size();
        tBufferSize = j.get_Output_buffer_size();
        cpu_buffer = j.getCPUBuffer();
        jobSize = j.getJobSize();
        //logicalEnd = j.get_mem_end();
        //logicalEnd = OSDriver.MemManager.getPhysicalAddress(logicalEnd);
//        for (int i=0; i<cpu_buffer.length; i++) {
//            System.out.println("INPUT BUFFER: " + i + " " + cpu_buffer[i]);
//        }

        System.out.println("\tProgram Counter starting at: " + pc);
        //run the duration of the frame
        while (status == 1) {
            System.out.println("Fetching instruction at PC=" + physical);
            String instr = fetch(physical);
            try {
                execute(decode(instr),j.getJobID());
            } catch (IOException ioe) {
                System.out.println("EXCEPTION: occurred while trying to execute the instruction");
                ioe.printStackTrace();
            }
            if (!jumped && status == 1) {
                System.out.println("--------------------------------");
                pc += 4;
                physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
            } else {
                System.out.println("--------------------------------");
                jumped = false;
            }

            try {
                out.append("\n\nPROGRAM COUNTER=" + pc);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            //System.out.println("\tPROGRAM COUNTER=" + pc);

            if (pc % 15 == 0) {
                //OSDriver.MemManager.reclaimFrame(pc/15);
                //OSDriver.PCB.searchForPage(data);
            }
        }

    }
    

    //************************************************
    //  FETCH() TAKES THE PC VALUE AND GRABS THE NEXT INSTRUCTION
    //  AND APPENDS THE BYTES TOGETHER INTO A 32 BIT BINARY
    //  STRING FOR PROCESSESING BY DECODE()
    //************************************************
    protected synchronized String fetch(int pc) {

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
            //short line = ;

            //get binary represenation of value.
            int s = OSDriver.MemManager.readRamData(pc++);

            instruction = Integer.toBinaryString(s);

            System.out.println("\tRAW: " + s + "\tAFTER CONVERSION " + instruction);

            //add any leading zeros that were left off by the previous operation
            
            while (instruction.length() < 8) {
                instruction = "0" + instruction;
            }
            returnedInst =  returnedInst + "" + instruction;
            //System.out.println("AFTER EXTRACTION & APPENDING " + returnedInst);
        }
        return returnedInst;
        
    }



    /************************************************
     * 
     * @param instr_req
     * @return
     * @throws java.io.IOException

     * DECODE() TAKES THE BINARY STRING REPRESENTATION OF
     * THE INSTRUCTION SET AND EXTRACTS THE APPROPRIATE
     * COMPONENTS. RETURNS THE OPCODE TO BE USED BY EXECUTE
     ************************************************/
    protected synchronized int decode(String instr_req) throws IOException {

        //CHECK HERE IF ANYTHING IS WRONG WITH CALCULATED RESULTS!
        //String binInstr = Integer.toBinaryString(instr_req);
        //Integer.toBinaryString(Character.digit(line.charAt(i),16))

        System.out.println("\tBinary instruction: " + instr_req );
        
        out.append("decoding instruction.... " + instr_req);
       
        //EXTRACT THE TYPE AND OPCODE FROM THE INSTRUCTION
        this.type = Short.parseShort(instr_req.substring(0,2),2);
        this.opCode = Short.parseShort(instr_req.substring(2,8),2);

        out.append("\nTYPE: " + type + "\tOPCODE: " + opCode);
        //USE TYPE TO DETERMINE HOW TO EXTRACT THE REMAINING COMPONENTS
        out.append("\nInstruction type:");
        switch (type) {

            case 0:
                out.append(" arithmetic");
                s1_reg = Short.parseShort(instr_req.substring(8,12),2);
                s2_reg = Short.parseShort(instr_req.substring(12,16),2);
                d_reg = Short.parseShort(instr_req.substring(16,20),2);
                System.out.println("\ts1_reg:" + s1_reg + " s2_reg:" + s2_reg + " d_reg:" + d_reg);
                long dataCHK = Long.parseLong(instr_req.substring(20,32),2);
                if (dataCHK > 0) {
                    System.out.println("INVALID INSTRUCTION DATA FIELD DETECTED.. EXITING");
                    System.exit(0);
                }
                break;

            case 1:
                out.append(" branch of immediate");
                b_reg = Short.parseShort(instr_req.substring(8,12),2);
                d_reg = Short.parseShort(instr_req.substring(12,16),2);
                address = Long.parseLong(instr_req.substring(16,32),2);
                System.out.println("\tb_reg:" + b_reg + " d_reg:" + d_reg + " address:" + address);
                break;

            case 2:
                out.append(" jump");
                address = Integer.parseInt(instr_req.substring(8,32));
                System.out.println("\tJUMP ADDRESS: " + address);
                break;

            case 3:
                out.append(" IO");
                reg1 = Short.parseShort(instr_req.substring(8,12),2);
                reg2 = Short.parseShort(instr_req.substring(12,16),2);
                address = Long.parseLong(instr_req.substring(16,32),2);
                System.out.println("\tReg1: " + reg1 + " Reg2: " + reg2 + " Address: " + address);
                ioCount++;
                break;

            default:
                System.err.println("\tERROR: HIT DEFAULT DECODE TYPE");
                out.append("\nERROR: HIT DEFAULT DECODE TYPE");
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
        out.append("\nExecuting instruction...." + " OPCODE = " + o);
        System.out.println("\n\tExecuting instruction...." + " OPCODE = " + o);
        
        if (!(opCode < 0) || (opCode > 26)) {
            //out.append("\nOPCODE =" + opCode);
            switch (opCode) {

                case 0:
                    out.append("\nPutting input buffer contents into accumulator");
                    if (address > 0) {
                        //Reads content of I/P buffer into a accumulator
                        //reg_Array[ACCUM] = (int)in_buffer[(int)address];
                        reg_Array[reg1] = cpu_buffer[buff_address((int)address)];
                    } else {
                        reg_Array[reg1] = (int)cpu_buffer[buff_address(reg_Array[reg2])];
                    }
                    System.out.println("\tRegister " + reg1 + " now contains " + reg_Array[reg1]);
                    break;

                case 1:
                    out.append("\nWriting content of accumulator " + reg_Array[ACCUM] + " into output buffer");
                    System.out.println("\tWriting content of accumulator into output buffer");
                    //Writes the content of accumulator into O/P buffer
                    cpu_buffer[buff_address((int)address)] = (short)reg_Array[reg2];
                    System.out.println("\tWriting " + reg_Array[ACCUM] + " into output buffer");
                    out.append("Writing " + reg_Array[ACCUM] + " into output buffer");
                    break;

                case 2:
                    out.append("\nStoring register in address");
                    System.out.println("\tStoring register in address: " + cpu_buffer.length);
                    //Stores content of a reg.  into an address
                    //reg_Array[(int)address] = reg_Array[(int)d_reg];

                    cpu_buffer[buff_address(reg_Array[d_reg])] = (short)reg_Array[b_reg];
                    System.out.println("\tCPU buffer: " + reg_Array[d_reg] + " now contains " + reg_Array[b_reg]);
                    break;

                case 3:
                    out.append("\nLoading address into register");
                    System.out.println("\tLoading address into register");
                    //Loads the content of an address into a reg.

                    reg_Array[d_reg] = cpu_buffer[buff_address(reg_Array[b_reg])];
                    System.out.println("\tr_index: " + d_reg + " now contains " + reg_Array[d_reg]);
                    break;

                case 4:
                    out.append("\nSwapping registers");
                    System.out.println("\tSwapping registers");
                    //Transfers the content of one register into another
                    calc_arith(6);
                    break;

                case 5:
                    out.append("\nAdding s_regs into d_reg");
                    System.out.println("\tAdding s_regs into d_reg");
                    //Adds content of two S-regs into D-reg
                    calc_arith(0);
                    break;

                case 6:
                    out.append("\nSubtracting s_regs into d_reg");
                    System.out.println("\tSubtracting s_regs into d_reg");
                    //Subtracts content of two S-regs into D-reg
                    calc_arith(1);
                    break;

                case 7:
                    out.append("\nMultiplying s_regs into d_reg");
                    System.out.println("\tMultiplying s_regs into d_reg");
                    //Multiplies content of two S-regs into D-reg
                    calc_arith(2);
                    break;

                case 8:
                    out.append("\nDividing s_regs into d_reg");
                    System.out.println("\tDividing s_regs into d_reg");
                    //Divides content of two S-regs into D-reg
                    calc_arith(3);
                    break;

                case 9:
                    out.append("\nLogical AND of s_regs");
                    System.out.println("\tLogical AND of s_regs");
                    //Logical AND of two S-regs into D-reg
                    calc_arith(4);
                    break;

                case 10:
                    out.append("\nLogical OR of s_regs");
                    System.out.println("\tLogical OR of s_regs");
                    //Logical OR of two S-regs into D-reg
                    calc_arith(5);
                    break;

                case 11:
                    out.append("\nTransferring data into register");
                    System.out.println("\tTransferring data into register");
                    reg_Array[d_reg] = (int)address;
                    //Transfers address/data directly into a register
                    System.out.println("\tr_index: " + d_reg + " now contains " + reg_Array[d_reg]);
                    //immediate - MOVI
                    break;

                case 12:
                    out.append("\nAdding data into register");
                    System.out.println("\tAdding data into register");
                    reg_Array[d_reg] += (int)address;
                    System.out.println("\tr_index: " + d_reg + " now contains " + reg_Array[d_reg]);
                    //Adds a data directly to the content of a register
                    //ADDI
                    break;

                case 13:
                    out.append("\nMultiplying data into register");
                    System.out.println("\tMultiplying data into register");
                    reg_Array[d_reg] *= (int)address;
                    //Multiplies a data directly to the content of a register
                    System.out.println("\tr_index: " + d_reg + " now contains " + reg_Array[d_reg]);
                    //MULI
                    break;

                case 14:
                    out.append("\nDividing data into register");
                    System.out.println("\tDividing data into register");
                    reg_Array[d_reg] /= (int)address;
                    System.out.println("\tr_index: " + d_reg + " now contains " + reg_Array[d_reg]);
                    //Divides a data directly to the content of a register
                    //DIVI
                    break;

                case 15:
                    out.append("\nLoading data/address into register");
                    System.out.println("\tLoading data/address into register");
                    reg_Array[d_reg] = (int)address;
                    System.out.println("\tr_index: " + d_reg + " now contains " + reg_Array[d_reg]);
                    //Loads a data/address directly to the content of a register
                    //immediate - LDI
                    break;

                case 16:
                    //Sets the D-reg to 1 if first S-reg is less than second S-reg, and 0 otherwise
                    out.append("\nChecking if " + reg_Array[s1_reg] + " < " + reg_Array[s2_reg]);
                    System.out.println("\tChecking if " + reg_Array[s1_reg] + " < " + reg_Array[s2_reg]);
                    if (reg_Array[s1_reg] < reg_Array[s2_reg]) {
                        reg_Array[d_reg] = 1;
                    } else {
                        reg_Array[d_reg] = 0;
                    }
                    out.append("\nr_index: " + d_reg + " is now " + reg_Array[d_reg]);
                    System.out.println("\tr_index: " + d_reg + " is now " + reg_Array[d_reg]);
                    break;

                case 17:
                    //Sets the D-reg to 1 if first S-reg is less than a data, and 0 otherwise
                    out.append("\nChecking if " + s1_reg + " < " + data);
                    System.out.println("\tChecking if " + s1_reg + " < " + data);
                    if (reg_Array[s1_reg] < (int)address) {
                        reg_Array[d_reg] = 1;
                    } else {
                        reg_Array[d_reg] = 0;
                    }
                    out.append("\nr_index: " + d_reg + " is now " + reg_Array[d_reg]);
                    System.out.println("\tr_index: " + d_reg + " is now " + reg_Array[d_reg]);
                    break;

                case 18:
                    out.append("\nEnd of program detected!");
                    //Logical end of program
                    //int job =0;
                    System.out.println("\tEND OF PROGRAM - OPCODE " + opCode);
                    PCB_block tmp = OSDriver.PCB.getJob(jID-1);
                    tmp.setCpuEndTime(System.nanoTime());
                    tmp.setStatus(FINISHED);

                    String avgs = "\tJOB #" + jID + " was waiting for " +
                            (double)(tmp.getoutQueueTime() - tmp.getinQueueTime())/1000000 +
                            " milliseconds and had a CPU time of " +
                            (double)(tmp.getCpuEndTime() - tmp.getCpuStartTime())/1000000 +
                            " milliseconds.";
                    System.out.println(avgs);
                    out.append("\n" + avgs);

                    String ios = "\tThere were " + ioCount + " IO requests in job # " + jID;
                    System.out.println(ios);
                    out.append("\n" + ios);
                    System.out.println("\tSETTING STATUS TO 0\n");
                    status = 0;
                    break;

                case 19:
                    out.append("\nMoving to the next instruction");
                    System.out.println("\tMoving to the next instruction");
                    //Does nothing and moves to next instruction
                    break;

                case 20:
                    out.append("\nJumping to another location");
                    System.out.println("\tJumping to another location");
                    //Jumps to a specified location
                    pc = (int)address;
                    physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
                    jumped = true;
                    out.append("\nProgram counter set to " + pc);
                    System.out.println("\tProgram counter set to " + pc);
                    //OSDriver.tools.effective_addr(address));
                    break;

                case 21:
                    out.append("\nChecking if b_reg = d_reg, then branch");
                    System.out.println("\tChecking if " + reg_Array[b_reg] + " = " + reg_Array[d_reg] + " , then branch");
                    //Branches to an address when content of B-reg = D-reg
                    if (reg_Array[d_reg] == reg_Array[b_reg]) {
                        pc = (int)address;
                        physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
                        //pc += j.get_mem_start();
                        jumped = true;
                        System.out.println("\tProgram counter set to " + pc);
                        out.append("\nProgram counter set to " + pc);
                    }
                    break;

                case 22:
                    out.append("\nChecking if b_reg != d_reg, then branch");
                    System.out.println("\tChecking if b_reg != d_reg, then branch");
                    //Branches to an address when content of B-reg <> D-reg
                    System.out.println("\tb_reg: " + reg_Array[b_reg] + " d_reg:" + reg_Array[d_reg]);
                    if (reg_Array[b_reg] != reg_Array[d_reg]) {
                        pc = (int)address;
                        physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
                        //pc += j.get_mem_start();
                        jumped = true;
                        System.out.println("\tProgram counter set to " + pc);
                        out.append("\nProgram counter set to " + pc);
                    }
                    break;

                case 23:
                    out.append("\nChecking if d_reg is 0, then branch");
                    System.out.println("\tChecking if d_reg is 0, then branch");
                    //Branches to an address when content of D-reg = 0
                    if (reg_Array[d_reg] == 0) {
                        pc = (int)address;
                        physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
                        //pc += j.get_mem_start();
                        jumped = true;
                        System.out.println("\tProgram counter set to " + pc);
                        out.append("\nProgram counter set to " + pc);
                    }
                    break;

                case 24:
                    out.append("\nChecking if b_reg != 0, then branch");
                    System.out.println("\tChecking if b_reg != 0, then branch");
                    //Branches to an address when content of B-reg <> 0
                    if (reg_Array[b_reg] != 0) {
                        pc = (int)address;
                        physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
                        //pc += j.get_mem_start();
                        out.append("\nProgram counter set to " + pc);
                        System.out.println("\tProgram counter set to " + pc);
                    }
                    break;

                case 25:
                    out.append("\nChecking if b_reg > 0, then branch");
                    System.out.println("\tChecking if b_reg > 0, then branch");
                    //Branches to an address when content of B-reg > 0
                    if (reg_Array[b_reg] > 0) {
                        pc = (int)address;
                        physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
                        //pc += j.get_mem_start();
                        System.out.println("\tProgram counter set to " + pc);
                        out.append("\nProgram counter set to " + pc);
                    }
                    break;

                case 26:
                    out.append("\nChecking if b_reg < 0, then branch");
                    System.out.println("\tChecking if b_reg < 0, then branch");
                    //Branches to an address when content of B-reg < 0
                    if (reg_Array[b_reg] < 0) {
                        pc = (int)address;
                        physical = OSDriver.MemManager.getPhysicalAddress(pc, j.getPTBR());
                        //pc += j.get_mem_start();
                        System.out.println("\tProgram counter set to " + pc);
                        out.append("\nProgram counter set to " + pc);
                    }
                    break;

                default:
                    System.err.println("UNKNOWN OPCODE");
                    break;
                    
            }
        } else {
            out.append("\nDIDN'T DECODE... OPCODE = " + opCode);
            System.err.println("DIDN'T DECODE... OPCDOE = " + opCode);
        }
    }



    private synchronized void calc_arith(int i) throws IOException {

        // i=0 - ADD
        // i=1 - SUBTRACT
        // i=2 - MULTIPLY
        // i=3 - DIVIDE
        // i=4 - LOGICAL 'AND'
        // I=5 - LOGICAL 'OR'
        // i=6 - SWAP REGISTERS
        
        switch (i) {
            case 0:
                reg_Array[d_reg] = (short)(reg_Array[s1_reg] + reg_Array[s2_reg]);
                out.append("\ns1_reg: " + reg_Array[s1_reg] + " + s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + (reg_Array[s1_reg] + reg_Array[s2_reg]));
                System.out.println("\ts1_reg: " + reg_Array[s1_reg] + " + s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                break;
            case 1:
                reg_Array[d_reg] = (short)(reg_Array[s1_reg] - reg_Array[s2_reg]);
                out.append("\ns1_reg: " + reg_Array[s1_reg] + " - s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                System.out.println("\ts1_reg: " + reg_Array[s1_reg] + " - s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                break;
            case 2:
                reg_Array[d_reg] = (short)(reg_Array[s1_reg] * reg_Array[s2_reg]);
                out.append("\ns1_reg: " + reg_Array[s1_reg] + " * s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                System.out.println("\ts1_reg: " + reg_Array[s1_reg] + " * s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                break;
            case 3:
                if (reg_Array[s2_reg] == 0)
                    return;
                else {
                    reg_Array[d_reg] = (short)(reg_Array[s1_reg] / reg_Array[s2_reg]);
                    out.append("\ns1_reg: " + reg_Array[s1_reg] + " / s2_reg: " + reg_Array[s2_reg] +
                            "\tmakes d_reg: " + reg_Array[d_reg]);
                    System.out.println("\ts1_reg: " + reg_Array[s1_reg] + " / s2_reg: " + reg_Array[s2_reg] +
                            "\tmakes d_reg: " + reg_Array[d_reg]);
                }
                break;
            case 4:
                reg_Array[d_reg] = (short)(reg_Array[s1_reg] & reg_Array[s2_reg]);
                out.append("\ns1_reg: " + reg_Array[s1_reg] + " LOGICAL AND'd with s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                System.out.println("\ts1_reg: " + reg_Array[s1_reg] + " LOGICAL AND'd with s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                break;
            case 5:
                reg_Array[d_reg] = (short)(reg_Array[s1_reg] | reg_Array[s2_reg]);
                out.append("\ns1_reg: " + reg_Array[s1_reg] + " LOGICAL OR'd with s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                System.out.println("\ts1_reg: " + reg_Array[s1_reg] + " LOGICAL OR'd with s2_reg: " + reg_Array[s2_reg] +
                        "\tmakes d_reg: " + reg_Array[d_reg]);
                break;
            case 6:
                int tmp_reg = reg_Array[s1_reg];
                reg_Array[s1_reg] = reg_Array[s2_reg];
                reg_Array[s2_reg] = tmp_reg;
                out.append("\ns1_reg is now: " + reg_Array[s1_reg]);
                out.append("\ns2_reg is now: " + reg_Array[s2_reg]);
                System.out.println("\ts1_reg is now: " + reg_Array[s1_reg]);
                System.out.println("\ts2_reg is now: " + reg_Array[s2_reg]);
                break;
            default:
                out.append("\nDEFAULT CALC_ARITH SWITCH REACHED");
                System.out.println("\tDEFAULT CALC_ARITH SWITCH REACHED");
                break;
        }
    }

    //************************************************
    //
    //************************************************


    private synchronized int buff_address(int a) {
        return Math.abs(a-jobSize*4);
    }
    private synchronized int effective_address(short i, long a) {
        return reg_Array[i] + (int)a;
    }
    
}
