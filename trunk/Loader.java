
import java.io.*;

//****************************************************
//
//****************************************************
public class Loader {

    String file_1 = "DataFile1.txt";
    String file_2 = "DataFile2.txt";
    
    //****************************************************
    //
    //****************************************************
    protected void load(File programFile) throws IOException {

        try {
            BufferedReader input =  new BufferedReader(new FileReader(programFile));

            String str = input.readLine();
            while ( str != null) {
                processData(str);


                if ( str.contains("//") ) {
                    //System.out.println(s);

                    if ( str.contains("JOB") ) {
                        System.out.println(str);
                        str = str.substring(7,str.length());
                        System.out.println(str);
                        addJob(str);
                        str = input.readLine();
                        while ( !str.contains("//") ) {
                            addData(str);
                        }
                    } else if ( str.contains("Data") ) {
                        str = str.substring(8, str.length());
                        addData(str);
                    } else if ( str.contains("End") ) {
                        str = input.readLine();
                    } else {
                        //do something
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
    protected void processData(String s) {



    }

    protected void addJob(String s) {

    }
    protected void addData(String s) {

    }
}
