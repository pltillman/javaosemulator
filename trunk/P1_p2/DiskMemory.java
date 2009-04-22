
    //****************************************************
    //  Default constructor
    //****************************************************
public class DiskMemory {

    private String[] disk;


    //****************************************************
    //  Default constructor
    //****************************************************
    public DiskMemory(int s) {

        disk = new String[s];

    }

    //****************************************************
    //  Default constructor
    //****************************************************
    public void writeData(int loc, String data) {
        
        disk[loc] = data;
    }


    //****************************************************
    //  Default constructor
    //****************************************************
    public String readData(int i) {
        
        return disk[i];
        
    }

    public String toString() {

        System.out.println( disk.toString() );
        return disk.toString();
    }
}
