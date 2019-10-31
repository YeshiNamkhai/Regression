package slr;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;

public class Draw {

    List<XYChart> charts;
    String title;
    String xTitle;
    String yTitle;

    public Draw(String title, String xTitle, String yTitle) {
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        charts = new ArrayList<XYChart>();
    }

    public void scatterPlot(List<Double> xData, List<Double> yData, List<Double> zData) {
        XYChart chartDis = new XYChartBuilder()
        .width(800).height(600).title(title).xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartDis.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        charts.add(chartDis);

        XYChart chartReg = new XYChartBuilder()
        .width(800).height(600).title(title).xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartReg.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartReg.addSeries("Reg. line", xData, zData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line);
        charts.add(chartReg);

        new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }

    public void scatterPlot(List<Double> xData, List<Double> yData, List<Double> zData, List<Double> invData) {
        XYChart chartDis = new XYChartBuilder()
        .width(800).height(600).title(title).xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartDis.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
//        chartDis.getStyler().setYAxisMin(0.0);
        charts.add(chartDis);

        XYChart chartReg = new XYChartBuilder()
        .width(800).height(600).title(title+" with Regression").xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartReg.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartReg.addSeries("Reg. line", xData, zData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line);
//        chartReg.getStyler().setYAxisMin(0.0);
        charts.add(chartReg);

        XYChart chartInv = new XYChartBuilder()
        .width(800).height(600).title(title+" considering 1/X").xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartInv.addSeries("Inverted Data", invData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartInv.addSeries("Reg. line", invData, zData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line);
//        chartInv.getStyler().setYAxisMin(0.0);

        charts.add(chartInv);


        new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }

}
