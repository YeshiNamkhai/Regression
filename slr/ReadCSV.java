package slr;

import java.io.BufferedReader;
import java.io.File;

/**
 * This is a utility class to read CVS files
 *  and return arrays of data for 
 *  simple linear regression
 */
public class ReadCSV {

    File csvFile;
    BufferedReader csvReader;
    String[][] content;

    void printContent() {
        for(int i=0;i<content.length;i++) {
            System.out.print(i+": ");
            for(int j=0;j<content[0].length;j++) {
                System.out.print("["+content[i][j]+"]");

            }
            System.out.println();
        }
    }

    boolean openFile(String path) {
        csvFile = new File(path);
        if (csvFile.isFile()) 
            return true;
        return false;
    }

    void closeFile() {
        csvReader.close();
    }

    void readFile() {
        csvReader = new BufferedReader(csvFile);
        String row;
        while ((row = csvReader.readLine()) != null) {
            content = row.split(",");
        }
    }

    public static void main(String[] args){
        ReadCSV data = new ReadCSV();
        if(data.openFile("data.csv")) data.readFile();
        data.printContent();
        data.closeFile();
    }
}