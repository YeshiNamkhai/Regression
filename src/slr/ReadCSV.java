package slr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is a utility class to read a CVS file (Comma Separated Values)
 * line by line and split by token (,)
 * then fill an array of strings
 * to be printed or returned as the content
 */
public class ReadCSV {
    
    private static final String COMMA_DELIMITER = ",";
    private List<List<String>> records;

    /**
     * initializes an array of strings
     * for file content
     */
    public ReadCSV() {
        records = new ArrayList<>();
    }

    /**
     * Prints the array of strings with file content
     */
    public void printCSV() {
        for (int i=0; i<records.size(); i++) {
            System.out.print(i+":\t");
            for(int j=0;j<records.get(i).size();j++) {
                System.out.print(records.get(i).get(j)+"\t");
            }
            System.out.println();
        }
    }

    /**
     * Opens a CSV file and loads the content 
     * line by line into an array of strings
     * 
     * @param path 
     * @throws IOException
     */
    public void loadCSV(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
    }

    /**
     * Gives back an array of strings
     * @return array of strings
     */
    public List<List<String>> getCSV() {
        return records;
    }
    
    public static void main(String[] args) throws IOException {
        ReadCSV data = new ReadCSV();
        data.loadCSV("data.csv");
        data.printCSV();
        System.out.println(data.getCSV());
    }
}