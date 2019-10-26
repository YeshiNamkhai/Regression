package slr;

import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;

public class Draw {

    XYChart chart;

    public Draw(String title, String xTitle, String yTitle) {
        chart = new XYChartBuilder()
        .width(800)
        .height(600)
        .title(title)
        .xAxisTitle(xTitle)
        .yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2)
        .build();
    }
    public void scatterPlot(List<Double> xData, List<Double> yData) {
        chart.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        new SwingWrapper(chart).displayChart();
    }
    public void scatterPlot(List<Double> xData, List<Double> yData, List<Double> zData) {
        chart.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chart.addSeries("Reg. line", xData, zData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line);
        new SwingWrapper(chart).displayChart();
    }

}
