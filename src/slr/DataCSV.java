package slr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/**
 * This is the main class to load data from CVS file (Comma Separated Values) line by
 * line and split by token (,) then fill an array of strings. This class provides 
 * methods to print the content and execute Simple Linear Regression through the
 * method compute() that fills internal vars accebiles by getters.
 */
public class DataCSV {
    // constants
    private static final String COMMA_DELIMITER = ",";
    // loaded csv organized
    private List<List<String>> fields;
    private List<List<Double>> values;
    // columns X, 1/X and Y
    private List<Double> x;
    private List<Double> invX;
    private List<Double> y;
    // Column sums
    private double sumX;
    private double sumInvX;
    private double sumY;
    // Column averages
    private double avgX;
    private double avgInvX;
    private double avgY;
    // Regression calculations
    private double rss;
    private double ssdX;
    private double ssdY;
    // Regression equation coefficents
    private double a;
    private double b;
    private double r;
    // predictions
    private List<Double> yHat;
    private double avgHat;
    private double spHat;
    private double ssdHat;

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
    /**
     * Calculates the sum
     * @param values
     * @return column sum
     */
    private double sumList(List<Double> values) {
        double s = 0;
        for(int i=0;i<values.size();i++)
            s+=values.get(i);
        return s;
    }
    /**
     * Calculates the average
     * @param values
     * @return column average
     */
    private double avgList(List<Double> values) {
        return sumList(values)/values.size();
    }
    /**
     * Calculate the sum of the squared deviations
     * @param values
     * @param avg
     * @return SSD
     */ 
    private double ssdList(List<Double> values, double avg) {
        double t = 0;
        for(int i=0;i<values.size();i++) {
            double v = values.get(i);
            t+=(v-avg)*(v-avg);
        }
        return t;
    }
    /**
     * Calculates the residual sum of squares
     * @param vX column X
     * @param vY column Y
     * @param aX average of X
     * @param aY average of Y
     * @return RSS
     */
    private double rssList(List<Double> vX,List<Double> vY,double aX, double aY) {
        double r = 0;
        for(int i=0;i<x.size();i++)
            r+=(vX.get(i)-aX)*(vY.get(i)-aY);
        return r;
    }
    /**
     * Gives back ŷ 
     * @param values column Y
     * @param aC coefficent
     * @param bC coefficent
     * @return column y=ax+b
     */
    private List<Double> hatList(List<Double> values, double aC, double bC) {
        List<Double> h = new ArrayList<>();
        for(int i=0;i<y.size();i++)
            h.add(aC*values.get(i)+bC);
        return h;
    }
    /**
     * Calculates the sum of two lists
     * @param values
     * @return difference 
     */
    private double hatSumProd(List<Double> aL,List<Double> bL, double aA, double bA) {
        double s = 0;
        for(int i=0;i<aL.size();i++)
            s+=(aL.get(i)-aA)*(bL.get(i)-bA);
        return s;
    }
    /**
     * Helper method for rounding values
     * @param value
     * @param precision
     * @return rounded value
     */
    private double round(double value, int precision) {
        if(precision>0) {
            int scale = (int) Math.pow(10, precision);
            return (double) Math.round(value * scale) / scale;
        } else return value;
    }
    /**
     * Getter for X
     * @return column
     */
    public List<Double> getX() { return x;}
    /**
     * Getter for 1/X
     * @return column
     */
    public List<Double> getInvX() { return invX;}
    /**
     * Getter for Y
     * @return column
     */
    public List<Double> getY() { return y;}
    /**
     * Getter for ŷ
     * @return column
     */
    public List<Double> getYhat() { return yHat;}    
    /**
     * Gives back a single value from column X
     * @param i index
     * @return value
     */
    public double getItemX(int i) { return x.get(i);}
    /**
     * Gives back a single value from column Y
     * @param i index
     * @return value
     */
    public double getItemY(int i) { return y.get(i);}
   /**
     * Gives back a single value from column 1/X
     * @param i index
     * @return value
     */
    public double getItemInvX(int i, int p) { return round(invX.get(i),p);}
    /**
     * Gives back a single value from ŷ
     * @param i index
     * @param p decimals
     * @return value
     */
    public double getItemYhat(int i, int p) { return round(yHat.get(i),p);}
    /**
     * Getter for Sum of X
     * @param p decimals
     * @return sum of column X
     */
    public double getSumX(int p) { return round(sumX,p);}
    /**
     * Getter for Sum of Y
     * @param p decimals
     * @return sum of column Y
     */
    public double getSumY(int p) { return round(sumY,p);}
    /**
     * Getter of Sum of 1/X
     * @param p decimals
     * @return sum of column X
     */
    public double getSumInvX(int p) { return round(sumInvX,p);}
    /**
     * Getter for Average of X
     * @param p decimals
     * @return average of column X
     */
    public double getAvgX(int p) { return round(avgX,p);}
    /**
     * Getter for Average of Y
     * @param p decimals
     * @return average of column Y
     */
    public double getAvgY(int p) { return round(avgY,p);}
    /**
     * Getter of Average of 1/X
     * @param p decimals
     * @return average of column 1/X
     */
    public double getAvgInvX(int p) { return round(avgInvX,p);}
    /**
     * Getter of Sum of Squared Deviations
     * @param p decimals
     * @return SSD of column X
     */
    public double getSsdX(int p) { return round(ssdX,p);}
    /**
     * Getter of Sum of Squared Deviations
     * @param p decimals
     * @return SSD of column Y
     */
    public double getSsdY(int p) { return round(ssdY,p);}
    /**
     * Getter of Residual Sum of Squares
     * @param p decimals
     * @return RSS
     */
    public double getRss(int p) { return round(rss,p);}
    /**
     * Getter for coefficent A
     * @param p decimals
     * @return A
     */
    public double getA(int p) { return round(a,p);}
    /**
     * Getter for coefficent B
     * @param p decimals
     * @return B
     */
    public double getB(int p) { return round(b,p);}
    /**
     * Getter for sum of products
     * @param p decimals
     * @return (y-yHat)(yHat-avgYhat)
     */
    public double getSpHat(int p) { return round(spHat,p);}
    /**
     * Getter for average of Hat
     * @param p decimals
     * @return average of column yHat
     */
    public double getAvgHat(int p) { return round(avgHat,p);}
    /**
     * Getter of Sum of Squared Deviations
     * @param p decimals
     * @return SSD of column Hat
     */
    public double getSsdHat(int p) { return round(ssdHat,p);}
    /**
     * Getter for coefficent R
     * @param p decimals
     * @return Regression coefficent
     */
    public double getR(int p) { return round(r,p);}
   /**
     * Getter for coefficent R2
     * @param p decimals
     * @return Regression coefficent
     */
    public double getR2(int p) { return round(r*r,p);}
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
            a = rss/ssdX;
            b = avgY-avgInvX*a;
            yHat = hatList(invX,a,b);
        } else {
            ssdX = ssdList(x,avgX);
            rss = rssList(x,y,avgX,avgY);
            a = rss/ssdX;
            b = avgY-avgX*a;
            yHat = hatList(x,a,b);
        }
        avgHat = avgList(yHat);
        // the average of regression function has to be same as Y's average
        if(round(avgHat,15)!=round(avgY,15)) {
            System.out.println("Error: something went wrong.");
            System.exit(1);
        }
        spHat = hatSumProd(y,yHat,avgY,avgHat);
        ssdHat = ssdList(yHat,avgHat);
        r = spHat/Math.sqrt(ssdY*ssdHat);
    }
}