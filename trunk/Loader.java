
import java.io.*;
import java.util.StringTokenizer;

//****************************************************
//
//****************************************************
public class Loader {

    //String file_1 = "DataFile1.txt";
    //String file_2 = "DataFile2.txt";

    FileReader file_1;
    FileReader file_2;
    private static int count=0;
    protected int addr;

    public Loader(FileReader f1, FileReader f2) {

        this.file_1 = f1;
        this.file_2 = f2;

    }
    //****************************************************
    //
    //****************************************************
    protected void load() throws IOException {
        System.out.println("load() called");

        try {
            System.out.println("trying to load datafile");
            BufferedReader input =  new BufferedReader(file_1);
            BufferedReader input2 = new BufferedReader(file_2);

            if (readDataFile(input2) ) {
                input2.close();
                //readDataFile(input2);
                System.out.println("loading 2nd data file");
            }
            
            //System.out.println(str.toString());
            
            //input2.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 

    }

    private Boolean readDataFile(BufferedReader in) {

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
                        addData(str, 0, count++);
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
                    System.out.println("trying to load datafile");
                    System.out.println(str);
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
            OSDriver.PCB.createJob(id, size, priority, addr);
            addr += size-1;
        } else if (o == 1) {
            //add job data to PCB
            int input = Integer.parseInt(token.nextToken(),16);
            int output = Integer.parseInt(token.nextToken(),16);
            int temp = Integer.parseInt(token.nextToken(),16);
            OSDriver.PCB.addMeta(input, output, temp);
        }
        System.out.println("job added");
    }

    //****************************************************
    //
    //****************************************************
    protected void addData(String s, int o, int loc) {

        // o=0 indicates disk data. format is string
        // o=1 indicates ram data. format is string
        
        if (o == 0) {
            //add data to disk
            OSDriver.MemManager.writeDiskData(loc, s);

        } else if (o == 1) {
            
            //OSDriver.MemManager.writeRamData(loc, s);
        }
        System.out.println("data added");
    }
}
