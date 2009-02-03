import java.io.File;
import java.io.IOException;


public class OSDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //create ram, disk
        


        //create a new processor



        File pFile = new File("DataFile1.txt");
        Loader loader = new Loader();

        try {
            loader.load(pFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }



        //call scheduler




    }

}
