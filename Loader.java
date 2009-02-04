
import java.io.*;
import java.io.File;

//****************************************************
//
//****************************************************
public class Loader {

    //String file_1 = "DataFile1.txt";
    //String file_2 = "DataFile2.txt";

    File file_1;
    File file_2;
    
    public Loader(File f1, File f2) {

        this.file_1 = f1;
        this.file_2 = f2;

    }
    //****************************************************
    //
    //****************************************************
    protected void load() throws IOException {

        try {
            BufferedReader input =  new BufferedReader(new FileReader(file_1));

            String str = input.readLine();
            String id;

            while ( str != null || !str.contains("End") ) {
                //processData(str);

                if ( str.contains("JOB") ) {

                    //System.out.println(str);
                    str = str.substring(7,str.length());
                    //System.out.println(str);
                    
                    addJob(str, 0);
                    str = input.readLine();

                    while ( !str.contains("//") ) {
                        addData(str, 0);
                        str = input.readLine();
                    }

                } else if ( str.contains("Data") ) {

                    str = str.substring(8, str.length());
                    addJob(str, 1);
                    str = input.readLine();
                    
                    while ( !str.contains("//") ) {
                        addData(str, 1);
                        str = input.readLine();
                    }
                }
            }

            input.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 

    }

    //****************************************************
    //
    //****************************************************
    protected void addJob(String s, int o) {
        // o=0 indicates job metadata
        // o=1 indicates jobdata metadata

        if (o == 0) {
            //add job to PCB

        } else if (o == 1) {
            //add job data to PCB

        }
    }

    //****************************************************
    //
    //****************************************************
    protected void addData(String s, int o) {

        if (o == 0) {
            //add data to disk
            OSDriver.MemManager.writeDiskData(o, data);
        } else if (o == 1) {
            //add data to input buffer
            OSDriver.MemManager.writeRamData(o, data);
        }
    }
}
