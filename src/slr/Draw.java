package slr;

import com.panayotis.gnuplot.JavaPlot;

public class Draw {
    public static void main(String[] args) {

        JavaPlot p = new JavaPlot();

        p.addPlot("sin(x)");

        p.plot();

    }
}
