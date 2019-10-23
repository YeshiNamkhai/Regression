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
     * @return headers
     */
    public List<List<String>> getFields() {
        return fields;
    }
    /**
     * Gives back data values
     * @return values from all columns
     */
    public List<List<Double>> getValues() {
        return values;
    }

    /**
     * Gives back a single set of values
     * @param i index
     * @return only specific column
     */
    public List<Double> getIndexValues(int i) {
        List<Double> d = new ArrayList<>();
        for(int j=0;j<values.size();j++) {
        try { d.add(values.get(j).get(i));}
        catch(Exception e) {}
        }
        return d;
    }

    /**
     * Gives back a single set of values
     * @param i index
     * @param p precision
     * @return only specific column as 1/i
     */
    public List<Double> getIndexValuesInverted(int i,int p) {
        List<Double> d = new ArrayList<>();
        for(int j=0;j<values.size();j++) {
        try {
            double v = Compute.invert(values.get(j).get(i),p);  
            d.add(v);
        }
        catch(Exception e) {}
        }
        return d;
    }
}