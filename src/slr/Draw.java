package slr;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Draw {

     List<XYChart> charts;
     SwingWrapper<XYChart> sw;
     String title;
     String xTitle;
     String yTitle;
     boolean initial=true;

    public Draw(String title, String xTitle, String yTitle) {
        this.title = title;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        charts = new ArrayList<XYChart>();
    }

    /**
     * Helper function to find the lowest value in the list
     * @param values
     * @return min
     */
    private double getMin(List<Double> values) {
        return Collections.min(values);
    }
    /**
     * Helper function to find the highest value in the list
     * @param values
     * @return max
     */
    private double getMax(List<Double> values) {
        return Collections.max(values);
    }

    public void scatterPlot(List<Double> xData, List<Double> yData, List<Double> zData) {
        double minY = Math.min(getMin(yData), getMin(zData));
        double maxY = Math.min(getMax(yData), getMax(zData));

        XYChart chartDis = new XYChartBuilder()
        .width(800).height(600).title(title).xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartDis.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartDis.getStyler().setYAxisMin(minY).setYAxisMax(maxY);
        charts.add(chartDis);

        XYChart chartReg = new XYChartBuilder()
        .width(800).height(600).title(title+" with Regression Line").xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartReg.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartReg.addSeries("Reg. line", xData, zData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chartReg.getStyler().setYAxisMin(minY).setYAxisMax(maxY);
        charts.add(chartReg);

        XYChart chartPred = new XYChartBuilder()
        .width(800).height(600).title(title+" with Predictions").xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();
        chartPred.addSeries("Reg. line", xData, yData)
               .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        charts.add(chartPred);
        
        sw = new SwingWrapper<XYChart>(charts);
        sw.displayChartMatrix();
    }

    public void scatterPlot(List<Double> xData, List<Double> yData, List<Double> zData, List<Double> invData) {
        double minY = Math.min(getMin(yData), getMin(zData));
        double maxY = Math.min(getMax(yData), getMax(zData));

        XYChart chartDis = new XYChartBuilder()
        .width(800).height(600).title(title).xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartDis.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartDis.getStyler().setYAxisMin(minY).setYAxisMax(maxY);
        charts.add(chartDis);

        XYChart chartReg = new XYChartBuilder()
        .width(800).height(600).title(title+" with Regression Line").xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartReg.addSeries("Data", xData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartReg.addSeries("Reg. line", xData, zData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chartReg.getStyler().setYAxisMin(minY).setYAxisMax(maxY);
        charts.add(chartReg);

        XYChart chartInv = new XYChartBuilder()
        .width(800).height(600).title(title+" considering 1/X").xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();

        chartInv.addSeries("Inverted Data", invData, yData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        chartInv.addSeries("Reg. line", invData, zData)
             .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chartInv.getStyler().setYAxisMin(minY).setYAxisMax(maxY);
        charts.add(chartInv);

        XYChart chartPred = new XYChartBuilder()
        .width(800).height(600).title(title+" with Predictions").xAxisTitle(xTitle).yAxisTitle(yTitle)
        .theme(ChartTheme.GGPlot2).build();
        chartPred.addSeries("Reg. line", xData, yData)
          .setXYSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
        charts.add(chartPred);

        sw = new SwingWrapper<XYChart>(charts);
        sw.displayChartMatrix();
    }
    public void addPoint(List<Double> xData, List<Double> yData, List<Double> errorBars, boolean inv){
         XYChart pred = charts.get(charts.size()-1);
         if(!initial)
              pred.updateXYSeries("Predictions", xData, yData, errorBars);
         else {
               initial=false;
               pred.addSeries("Predictions", xData, yData, errorBars)
                    .setXYSeriesRenderStyle(XYSeriesRenderStyle.Line)
                    .setMarkerColor(Color.ORANGE)
                    .setMarker(SeriesMarkers.SQUARE);
         }
         if(inv)
               sw.repaintChart(3);
         else
               sw.repaintChart(2);
    }
}
