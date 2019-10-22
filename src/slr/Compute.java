package slr;

import java.util.List;

public class Compute {

    /**
     * Helper function to round double values to number of decimals
     * 
     * @param value what to round
     * @param precision decimals
     * @return rounded value
     */
    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /**
     * Helper function to invert value with precision
     * 
     * @param value what to invert
     * @param precision decimals
     * @return inverted value 1/value
     */
    public static double invert(double value, int precision) {
        return round(1/value,precision);
    }

    public static double sumList(List<Double> values) {
        double s = 0;
        for(int i=0;i<values.size();i++)
            s+=values.get(i);
        return s;
    }

    public static double avgList(List<Double> values,int precision ) {
        double a = 0;
        a = sumList(values)/values.size();
        return round(a,precision);
    } 

}