package slr;

import java.util.List;

public class Compute {

    /**
     * Helper function to round double values to number of decimals 
     * @param value what to round
     * @param precision decimals
     * @return rounded value
     */
    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
    /**
     * Calculate the total of a list
     * @param values
     * @return sum
     */
    public static double sumList(List<Double> values) {
        double s = 0;
        for(int i=0;i<values.size();i++)
            s+=values.get(i);
        return s;
    }
    /**
     * Calculate the average of a list
     * @param values
     * @param precision
     * @return average
     */
    public static double avgList(List<Double> values) {
        double a = 0;
        a = sumList(values)/values.size();
        return a;
    } 
    public static double rssList(List<Double> x,List<Double> y,double aX, double aY) {
        double r = 0;
        for(int i=0;i<x.size();i++)
            r+=(x.get(i)-aX)*(y.get(i)-aY);
        return r;
    }
    /**
     * Calculate the Total Sum of Squares of a List
     * @param values
     * @param precision
     * @return tss
     */
    public static double tssList(List<Double> values) {
        double a = avgList(values);
        double t = 0;
        for(int i=0;i<values.size();i++)
            t+=(values.get(i)-a)*(values.get(i)-a);
        return t;
    }

}