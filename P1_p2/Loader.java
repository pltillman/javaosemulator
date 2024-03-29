
import java.io.*;
import java.util.StringTokenizer;

/**
 * Reads the data file and adds the hex
 * entries to the disk.
 * The job metadata read creates a PCB object
 * and once the associated data metadata is read,
 * the PCB is updated and placed in the PCB array.
 */
public class Loader {

    //String file_1 = "DataFile1.txt";
    //String file_2 = "DataFile2.txt";

    FileReader file_1;
    FileReader file_2;
    private static int count=0;
    protected int addr;
   
    /**
     * Initializes files
     *
     * @param f1    file 1 ("DataFile2.txt")
     * @param f2    file 2 ("DataFile1.txt")
     */
    public Loader(FileReader f1, FileReader f2) {

        this.file_1 = f1;
        this.file_2 = f2;

    }
    
    /**
     * Opens the files to be read 
     *
     * @throws java.io.IOException  If an input or output
     *                               exception occurs
     */
    protected void load() throws IOException {

        try {
            BufferedReader input =  new BufferedReader(file_1);
            BufferedReader input2 = new BufferedReader(file_2);

            if (readDataFile(input2) ) {
                input2.close();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 

    }
    /**
     * Reads data from the file
     *
     * @param in  BufferedReader to read from file
     * @return    true if successful
     */
    private Boolean readDataFile(BufferedReader in) {

        System.out.println("\tLOADING DATA FILE...\n\tADDING HEX DATA TO DISK...");
        addr = 0;

        try {
            String str = in.readLine();

            while ( str.length() > 0 ) {
                //processData(str);
                //System.out.println("trying to load datafile");
                if ( str.contains("JOB") ) {

                    //System.out.println(str);
                    str = str.substring(7,str.length());
                    //System.out.println(str);

                    addJob(str, 0);
                    str = in.readLine();

                    while ( !str.contains("//") ) {
                       // System.out.println("adding " + str + " to location " + count);
                        addData(str, 0, count++);
                        str = in.readLine();
                    }

                } else if ( str.contains("Data") ) {
                    //System.out.println(str);
                    str = str.substring(8, str.length());
                    addJob(str, 1);
                    str = in.readLine();

                    while ( !str.contains("//") ) {
                        addData(str, 0, count++);
                        str = in.readLine();
                    }
                } else {
                    str = in.readLine();
                    //System.out.println("Read data: " + str);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
            System.out.println("\tLOADING DATA FILE...COMPLETE");
            return true;
    }
   
    /**
     * Adds a job or job data to the PCB
     *
     * @param s string read from data file
     * @param o value that determines metadata type
     *             o=0 indicates job metadata
     *             o=1 indicates jobdata metadata
     */
    protected void addJob(String s, int o) {
        

        StringTokenizer token = new StringTokenizer(s);


        if (o == 0) {
            //add job to PCB
            int id = Integer.parseInt(token.nextToken(),16);
            int size = Integer.parseInt(token.nextToken(),16);
            int priority = Integer.parseInt(token.nextToken(),16);
            OSDriver.PCB.createJob(id, size, priority, addr);
            //addr ++;
        } else if (o == 1) {
            //add job data to PCB
            int input = Integer.parseInt(token.nextToken(),16);
            int output = Integer.parseInt(token.nextToken(),16);
            int temp = Integer.parseInt(token.nextToken(),16);
            OSDriver.PCB.addMeta(input, output, temp);
            OSDriver.PCB.setDataSize(input + output + temp);
            //addr++;
        }
        //System.out.println("job added");
    }

    /**
     * Writes data to the disk
     *
     * @param s     string read from data file
     * @param o     value that determines data type
     *                o=0 indicates disk data. format is string
     *                o=1 indicates ram data. format is string
     * @param loc   where to write the data
     */
    protected void addData(String s, int o, int loc) {

        
        
        if (o == 0) {
            //add data to disk
            OSDriver.MemManager.writeDiskData(loc, s);
            addr++;

        } else if (o == 1) {
            
            //OSDriver.MemManager.writeRamData(loc, s);
        }
        //System.out.println("data added");
    }
}
