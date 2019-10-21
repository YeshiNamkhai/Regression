package slr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility class to read a CVS file (Comma Separated Values) line by
 * line and split by token (,) then fill an array of strings to be printed or
 * returned as the content
 */
public class DataCSV {

    private static final String COMMA_DELIMITER = ",";
    private List<List<String>> fields;
    private List<List<Double>> values;

    /**
     * initializes array of fields and values
     * for file content
     */
    public DataCSV() {
        fields = new ArrayList<>();
        values = new ArrayList<>();
    }

    /**
     * Prints the array of strings with file content
     */
    public void printCSV() {
        for (int i = 0; i < fields.size(); i++) {
            System.out.print("\t");
            for (int j = 0; j < fields.get(i).size(); j++) {
                System.out.print(fields.get(i).get(j) + "\t");
            }
            System.out.println();
        }
        for (int i = 0; i < values.size(); i++) {
            System.out.print(i + ":\t");
            for (int j = 0; j < values.get(i).size(); j++) {
                System.out.print(values.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 
     * @param path
     * @return count of records
     * @throws IOException
     */
    public int loadCSV(String path) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(COMMA_DELIMITER);
                List<String> s = new ArrayList<>();
                List<Double> d = new ArrayList<>();
                for (int i=0; i<parts.length; i++) {
                    try
                    {
                        d.add(Double.parseDouble(parts[i]));
                    }
                    catch(NumberFormatException e)
                    {
                        s.add(parts[i]);
                    }
                }
                if(s.size()>0) fields.add(s);
                if(d.size()>0) values.add(d);
                }
                count++;
            }
        return count;
    }

    /**
     * Gives back field names
     * @return fields
     */
    public List<List<String>> getFields() {
        return fields;
    }
    /**
     * Gives back data values
     * @return values
     */
    public List<List<Double>> getValues() {
        return values;
    }

    public List<Double> getIndexValues(int i) {
        List<Double> d = new ArrayList<>();
        for(int j=0;j<values.size();j++)
            d.add(values.get(j).get(i));
        return d;
    }


    public static void main(String[] args) throws IOException {
        DataCSV data = new DataCSV();
        data.loadCSV("data.csv");
        data.printCSV();
        System.out.println(data.getFields());
        System.out.println("X="+data.getIndexValues(0));
        System.out.println("Y="+data.getIndexValues(1));
    }
}