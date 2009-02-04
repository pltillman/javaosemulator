

public class DiskMemory {

    private final String[] disk;

    public DiskMemory(int s) {

        disk = new String[s];

    }


    public void writeData(int loc, String data) {
        this.disk[loc] = data;
    }

    public String readData(int i) {
        
        return this.disk[i];
        
    }
}
