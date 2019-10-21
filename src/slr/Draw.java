package slr;

import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;

public class Draw {

    public static void scatterPlot(List<Double> xData, List<Double> yData,String[] title) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title[0]).xAxisTitle(title[1]).yAxisTitle(title[2]).build();
       // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
       // Series
        chart.addSeries("single point (1,1)", xData, yData); 
       // Show it
       new SwingWrapper(chart).displayChart();
    }
}
