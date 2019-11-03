package slr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    // Column min/max/median
    private double minX;
    private double minInvX;
    private double minY;
    private double maxX;
    private double maxInvX;
    private double maxY;
    private double medX;
    private double medInvX;
    private double medY;
    // Regression calculations
    private double rss;
    private double ssdX; // sum of squared deviations
    private double ssdY; // sum of squared deviations
    private double eSum;
    private double usvX; //umbiased sample variance
    private double usvY; //umbiased sample variance
    // Regression equation coefficents
    private double a;  //y=ax+b
    private double b;  //y=ax+b
    private double r;  //R
    // check and predictions
    private List<Double> yHat; //ŷ
    private double avgHat; //avgŷ
    private double syHat;  //Syŷ
    private double ssdHat;
    private double sigma;  
    private double anova;
    private double f; //critical value
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
     * Gives back the middle value
     * @param values
     * @return median
     */    
    private double medList(List<Double> values) {
        int l=values.size();
        double[] v = new double[l];
        for(int i=0;i<l;i++)
            v[i]=values.get(i);
        Arrays.sort(v);
        if(l%2==0) { //even
            return (v[l/2]+v[l/2-1])/2;
        } else { //odd
            return v[l/2];
        }
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
     * Gives back y-ŷ 
     * @param values column Y
     * @param aC coefficent
     * @param bC coefficent
     * @return column y-ŷ
     */
    private double eSumList(List<Double> vY, List<Double> vHat) {
        double e = 0;
        for(int i=0;i<vY.size();i++) {
            e+=(vY.get(i)-vHat.get(i))*(vY.get(i)-vHat.get(i));
        }
        return e;
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
     * Gives back a value from F-Distriution 
     * @param n number of samples
     * @param p %
     * @return critical value
     */
    private double getF(int n,double p){
        double[] f;
        if(p==0.10)  //10%
            f = new double[]{39.86346,8.52632,5.53832,4.54477,4.06042,3.77595,3.58943,3.45792,3.3603,3.28502,3.2252,3.17655,3.13621,3.10221,3.07319,3.04811,3.02623,3.00698,2.9899,2.97465,2.96096,2.94858,2.93736,2.92712,2.91774,2.90913,2.90119,2.89385,2.88703,2.88069,2.83535,2.79107,2.74781,2.70554};
        else if(p==0.025) //2.5%
            f = new double[]{647.789,38.5063,17.4434,12.2179,10.007,8.8131,8.0727,7.5709,7.2093,6.9367,6.7241,6.5538,6.4143,6.2979,6.1995,6.1151,6.042,5.9781,5.9216,5.8715,5.8266,5.7863,5.7498,5.7166,5.6864,5.6586,5.6331,5.6096,5.5878,5.5675,5.4239,5.2856,5.1523,5.0239};
        else if(p==0.01) //1%
            f = new double[]{4052.181,98.503,34.116,21.198,16.258,13.745,12.246,11.259,10.561,10.044,9.646,9.33,9.074,8.862,8.683,8.531,8.4,8.285,8.185,8.096,8.017,7.945,7.881,7.823,7.77,7.721,7.677,7.636,7.598,7.562,7.314,7.077,6.851,6.635};
        else if(p==0.001) //0.1%
            f = new double[]{405284.0679,998.5002501,167.0292238,74.13729332,47.18077922,35.50749025,29.24519336,25.41476047,22.85712515,21.03959527,19.68678565,18.64332157,17.81542047,17.14336026,16.58741634,16.12019551,15.72222635,15.37930598,15.08084102,14.81877555,14.58687806,14.38025503,14.19501174,14.02801082,13.87669745,13.73897062,13.61308701,13.49758824,13.3912451,13.29301437,12.60935783,11.97298729,11.38019033,10.82756617};
        else  //5%
            f = new double[]{161.4476,18.5128,10.128,7.7086,6.6079,5.9874,5.5914,5.3177,5.1174,4.9646,4.8443,4.7472,4.6672,4.6001,4.5431,4.494,4.4513,4.4139,4.3807,4.3512,4.3248,4.3009,4.2793,4.2597,4.2417,4.2252,4.21,4.196,4.183,4.1709,4.0847,4.0012,3.9201,3.8415};
        return f[n-1];
    }   
    /**
     * Getter for fields name
     * @return column
     */
    public String getFieldName(int i) { return fields.get(0).get(i);}
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
     * Getter for Min of X
     * @param p decimals
     * @return min of column X
     */
    public double getMinX() { return minX;}
    /**
     * Getter for Max of X
     * @param p decimals
     * @return max of column X
     */
    public double getMaxX() { return maxX;}
    /**
     * Getter for Median of X
     * @param p decimals
     * @return median of column X
     */
    public double getMedX(int p) { return round(medX,p);}
    /**
     * Getter for Min of Y
     * @param p decimals
     * @return min of column Y
     */
    public double getMinY() { return minY;}
    /**
     * Getter for Max of Y
     * @param p decimals
     * @return max of column Y
     */
    public double getMaxY() { return maxY;}
    /**
     * Getter for Average of Y
     * @param p decimals
     * @return average of column Y
     */
    public double getAvgY(int p) { return round(avgY,p);}
    /**
     * Getter for Median of Y
     * @param p decimals
     * @return median of column Y
     */
    public double getMedY(int p) { return round(medY,p);}
    /**
     * Getter of Average of 1/X
     * @param p decimals
     * @return average of column 1/X
     */
    public double getAvgInvX(int p) { return round(avgInvX,p);}
    /**
     * Getter of Min of 1/X
     * @return min of column 1/X
     */
    public double getMinInvX(int p) { return round(minInvX,p);}
    /**
     * Getter of Max of 1/X
     * @return min of column 1/X
     */
    public double getMaxInvX(int p) { return round(maxInvX,4);}
    /**
     * Getter of Median of 1/X
     * @param p decimals
     * @return median of column 1/X
     */
    public double getMedInvX(int p) { return round(medInvX,p);}
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
     * Getter of Umbiased Sample Variance
     * @param p decimals
     * @return usv of column X
     */
    public double getUsvX(int p) { return round(usvX,p);}
    /**
     * Getter of Umbiased Sample Variance
     * @param p decimals
     * @return usv of column Y
     */
    public double getUsvY(int p) { return round(usvY,p);}
    /**
     * Getter of Residual Sum of Squares
     * @param p decimals
     * @return RSS
     */
    public double getRss(int p) { return round(rss,p);}
    /**
     * Getter of Sum E
     * @param p decimals
     * @return Se
     */
    public double getEsum(int p) { return round(eSum,p);}
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
    public double getSyHat(int p) { return round(syHat,p);}
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
     * Getter of Variance Test
     * @param p decimals
     * @return varTest
     */
    public double getSigma(int p) { return round(sigma,p);}
    /**
     * Getter of ANOVA
     * @param p decimals
     * @return statistic test value
     */
    public double getAnova(int p) { return round(anova,p);}
    /**
     * Getter of F-Distribution's critical value
     */
    public double getFval() {return f;}
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
        minX = Collections.min(x);
        minY = Collections.min(y);
        maxX = Collections.max(x);
        maxY = Collections.max(y);
        medX = medList(x);
        medY = medList(y);
        ssdY = ssdList(y,avgY);
        if(inv) {
            sumInvX = sumList(invX);
            avgInvX = avgList(invX);
            minInvX = Collections.min(invX);
            maxInvX = Collections.max(invX);
            medInvX = medList(invX);
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
        usvX = ssdX/(x.size()-1);
        usvY = ssdY/(y.size()-1);
        avgHat = avgList(yHat);
        // the average of regression function has to be same as Y's average
        if(round(avgHat,15)!=round(avgY,15)) {
            System.out.println("Error: something went wrong.");
            System.exit(1);
        }
        syHat = hatSumProd(y,yHat,avgY,avgHat);
        ssdHat = ssdList(yHat,avgHat);
        r = syHat/Math.sqrt(ssdY*ssdHat);
        eSum = eSumList(y,yHat);
        sigma = Math.sqrt(eSum/(y.size()-2));
        anova = ((a*a)/(1/ssdX))/(eSum/(y.size()-2));
        f=getF(y.size()-2, 0.05);
    }
    /**
     * Gives back a prediction
     * @param vX x value
     * @param p precision
     * @return min, ŷ, max
     */
    public double[] getPredY(double vX, int p, boolean inv) {
        double vY;  //predicted value
        double dX;
        double dY; //delta interval 
        if(inv) {
            vY = a*1/vX+b; 
            dX= (1/vX-avgInvX)*(1/vX-avgInvX);
        } else {
            vY = a*vX+b;
            dX= (vX-avgX)*(vX-avgX);
        }
        if(x.contains(vX))
            dY = Math.sqrt(f*(1/y.size()+dX/ssdX)*(eSum/(y.size()-2)));
        else
            dY = Math.sqrt(f*(1+1/y.size()+dX/ssdX)*(eSum/(y.size()-2)));    
        double[] yP = new double[3]; 
        yP[1]=round(vY,p);  //predicted value
        yP[0]=round(vY-dY,p); //lower bound
        yP[2]=round(vY+dY,p); //higher bound
        return yP;
    }
}