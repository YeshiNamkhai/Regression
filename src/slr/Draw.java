package slr;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;

public class Draw {
    public static void render(double[] xData, double[] yData) {

        //double[] xData = new double[] { 4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19 };
        //double[] yData = new double[] { 100.1,107.2,114.1,121.7,126.8,130.90,137.5,143.2,149.4,151.6,154.0,154.6,155.0,155.1,155.3,155.7 };
 
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Dispersion Plot").xAxisTitle("Age").yAxisTitle("Height").build();

       // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);

       // Series
        chart.addSeries("single point (1,1)", xData, yData); 
       // Show it
       new SwingWrapper(chart).displayChart();
    }
}
