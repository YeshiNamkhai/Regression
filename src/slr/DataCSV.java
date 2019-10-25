package slr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class to load data from CVS file (Comma Separated Values) line by
 * line and split by token (,) then fill an array of strings. This class provides 
 * methods to print the content and execute Simple Linear Regression through the
 * method compute() that fills internal vars accebiles by getters.
 */
public class DataCSV {

    private static final String COMMA_DELIMITER = ",";
    private List<List<String>> fields;
    private List<List<Double>> values;
    private List<Double> x;
    private List<Double> invX;
    private List<Double> y;
    private double sumX;
    private double sumInvX;
    private double sumY;
    private double avgX;
    private double avgInvX;
    private double avgY;
    private double rss;
    private double ssdX;
    private double ssdY;
    private double a;
    private double b;

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
    public void show() {
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
    public int load(String path) throws IOException {
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
     * Gives back a single set of values
     * @param i index
     * @return only specific column
     */
    private List<Double> getIndexValues(int i) {
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
    private List<Double> getIndexValuesInverted(int i) {
        List<Double> d = new ArrayList<>();
        for(int j=0;j<values.size();j++) {
        try {
            double v = 1/values.get(j).get(i);  
            d.add(v);
        }
        catch(Exception e) {}
        }
        return d;
    }
    
    private double sumList(List<Double> values) {
        double s = 0;
        for(int i=0;i<values.size();i++)
            s+=values.get(i);
        return s;
    }
    private double avgList(List<Double> values) {
        return sumList(values)/values.size();
    } 
    private double ssdList(List<Double> values, double avg) {
        double t = 0;
        for(int i=0;i<values.size();i++) {
            double v = values.get(i);
            t+=(v-avg)*(v-avg);
        }
        return t;
    }
    private double rssList(List<Double> x,List<Double> y,double aX, double aY) {
        double r = 0;
        for(int i=0;i<x.size();i++)
            r+=(x.get(i)-aX)*(y.get(i)-aY);
        return r;
    }
    private double round(double value, int precision) {
        if(precision>0) {
            int scale = (int) Math.pow(10, precision);
            return (double) Math.round(value * scale) / scale;
        } else return value;
    }
    public List<Double> getX() { return x;}
    public List<Double> getInvX() { return invX;}
    public List<Double> getY() { return y;}
    public double getSumX(int p) { return round(sumX,p);}
    public double getSumY(int p) { return round(sumY,p);}
    public double getSumInvX(int p) { return round(sumInvX,p);}
    public double getAvgX(int p) { return round(avgX,p);}
    public double getAvgY(int p) { return round(avgY,p);}
    public double getAvgInvX(int p) { return round(avgInvX,p);}
    public double getSsdX(int p) { return round(ssdX,p);}
    public double getSsdY(int p) { return round(ssdY,p);}
    public double getRss(int p) { return round(rss,p);}
    public double getA(int p) { return round(a,p);}
    public double getB(int p) { return round(b,p);}
    /**
     * Calculate linear regression and assign internal variables
     * @param iX index of independent variable
     * @param inv true if 1/X is required
     * @param iY index of dependent variable
     */
    public void compute(int iX, boolean inv, int iY) {
        x = new ArrayList<>();
        invX = new ArrayList<>();
        y = new ArrayList<>();
        try {
        if(inv) invX = getIndexValuesInverted(iX);
        x = getIndexValues(iX);
        y = getIndexValues(iY);
        } catch (Exception e) {
            System.out.println("Wrong parameters: index for x or y not assigned properly.");
            System.out.println("Please review your choice or your CSV file.");
            System.exit(1);
        }
        sumX = sumList(x);
        sumY = sumList(y);
        avgX = avgList(x);
        avgY = avgList(y);
        ssdY = ssdList(y,avgY);
        if(inv) {
            avgInvX = avgList(invX);
            sumInvX = sumList(invX);
            ssdX = ssdList(invX,avgInvX);
            rss = rssList(invX,y,avgInvX,avgY);
        } else {
            ssdX = ssdList(x,avgX);
            rss = rssList(x,y,avgInvX,avgY);
        }
        a = rss/ssdX;
        b = avgY-avgInvX*a;
    }
}