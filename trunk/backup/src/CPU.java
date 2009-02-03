


public class CPU {


    private int opCode;
    private int type;
    private byte s1_reg;
    private byte s2_reg;
    private byte d_reg;
    private byte b_reg;
    private int address;



    //************************************************
    //
    //} thrown into a big while (not 'halt' opcode).
    //************************************************
    public CPU(/*pass something here*/) {

        // and assign it to the vars above here!
        int pc = 0;

        while (pc != 0) {

            int instr = fetch(pc);
            execute(decode(instr));
            pc++;
        }

    }



    //************************************************
    //
    //************************************************
    protected int fetch(int r) {


        return r;
    }



    //************************************************
    // DECODE() TAKES THE BINARY STRING REPRESENTATION OF
    // THE INSTRUCTION SET AND EXTRACTS THE APPROPRIATE
    // COMPONENTS. RETURNS THE OPCODE TO BE USED BY EXECUTE
    //************************************************
    protected int decode(String instr_req) {

        //CHECK HERE IF ANYTHING IS WRONG WITH CALCULATED RESULTS!
        //String binInstr = Integer.toBinaryString(instr_req);
        //Integer.toBinaryString(Character.digit(line.charAt(i),16))



        //EXTRACT THE TYPE AND OPCODE FROM THE INSTRUCTION
        this.type = Integer.parseInt(instr_req.substring(0,1));
        this.opCode = Integer.parseInt(instr_req.substring(2,7));


        //USE TYPE TO DETERMINE HOW TO EXTRACT THE REMAINING COMPONENTS
        switch (type) {
            case 00:
                s1_reg = Byte.parseByte(instr_req.substring(8,11));
                s2_reg = Byte.parseByte(instr_req.substring(12,15));
                d_reg = Byte.parseByte(instr_req.substring(16,19));
                break;
            case 01:
                b_reg = Byte.parseByte(instr_req.substring(8,11));
                d_reg = Byte.parseByte(instr_req.substring(12,15));
                address = Integer.parseInt(instr_req.substring(16,31));
                break;
            case 10:
                address = Integer.parseInt(instr_req.substring(8,31));
                break;
            case 11:

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
    protected void execute(int opCode) {

        if (opCode != 0) {

            switch (opCode) {

                case 0:
                    //Reads content of I/P buffer into a accumulator

                    break;

                case 1:
                    //Writes the content of accumulator into O/P buffer

                    break;

                case 2:
                    //Stores content of a reg.  into an address

                    break;

                case 3:
                    //Loads the content of an address into a reg.


                    break;

                case 4:
                    //Transfers the content of one register into another

                    break;

                case 5:
                    //Adds content of two S-regs into D-reg
                    calc_arith(0);
                    break;

                case 6:
                    //Subtracts content of two S-regs into D-reg
                    calc_arith(1);
                    break;

                case 7:
                    //Multiplies content of two S-regs into D-reg
                    calc_arith(2);
                    break;

                case 8:
                    //Divides content of two S-regs into D-reg
                    calc_arith(3);
                    break;

                case 9:
                    //Logical AND of two S-regs into D-reg
                    calc_arith(4);
                    break;

                case 10:
                    //Logical OR of two S-regs into D-reg
                    calc_arith(5);
                    break;

                case 11:
                    //Transfers address/data directly into a register

                    break;

                case 12:
                    //Adds a data directly to the content of a register


                    break;

                case 13:
                    //Multiplies a data directly to the content of a register


                    break;

                case 14:
                    //Divides a data directly to the content of a register

                    break;

                case 15:
                    //Loads a data/address directly to the content of a register


                    break;

                case 16:
                    //Sets the D-reg to 1 if  first S-reg is less than second S-reg, and 0 otherwise

                    break;

                case 17:
                    //Sets the D-reg to 1 if  first S-reg is less than a data, and 0 otherwise


                    break;

                case 18:
                    //Logical end of program


                    break;

                case 19:
                    //Does nothing and moves to next instruction

                    break;

                case 20:
                    //Jumps to a specified location


                    break;

                case 21:
                    //Branches to an address when content of B-reg = D-reg


                    break;

                case 22:
                    //Branches to an address when content of B-reg <> D-reg


                    break;

                case 23:
                    //Branches to an address when content of D-reg = 0


                    break;

                case 24:
                    //Branches to an address when content of B-reg <> 0

                    break;

                case 25:
                    //Branches to an address when content of B-reg > 0

                    break;

                case 26:
                    //Branches to an address when content of B-reg < 0

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

        
        switch (i) {
            case 0:

                d_reg = (byte)(s1_reg + s2_reg);
                break;

            case 1:

                d_reg = (byte)(s1_reg - s2_reg);
                break;
                
            case 2:

                d_reg = (byte)(s1_reg * s2_reg);
                break;
                
            case 3:

                d_reg = (byte)(s1_reg / s2_reg);
                break;

            case 4:

                d_reg = (byte)(s1_reg & s2_reg);
                break;

            case 5:

                d_reg = (byte)(s1_reg | s2_reg);
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

    
}
