
import java.util.Stack;

public class CPU_register{

    private short register1, register2, register3, register4;
    private int r;
    public static Stack<CPU_register> regQueue;
    private static int count;

    public CPU_register(short reg1, short reg2, short reg3, short reg4){

        this.register1=reg1;
        this.register2=reg2;
        this.register3=reg3;
        this.register4=reg4;
        r=0;
        regQueue = new Stack<CPU_register>();
        SaveToRegister();
        count=0;
    }
    
    public void LoadFromRegister(short reg1, short reg2, short reg3, short reg4){
    
        this.register1=reg1;
        regQueue.add(register1);
        count++;
        this.register2=reg2;
        regQueue.add(register2);
        count++;
        this.register3=reg3;
        regQueue.add(register3);
        count++;
        this.register4=reg4;
        regQueue.add(register4);
        count++;
    }

    public void SaveToRegister(){


        this.register1= OSDriver.MemManager.readRamData(r);
        r++;
        this.register2= OSDriver.MemManager.readRamData(r);
        r++;
        this.register3= OSDriver.MemManager.readRamData(r);
        r++;
        this.register4= OSDriver.MemManager.readRamData(r);

    }
}