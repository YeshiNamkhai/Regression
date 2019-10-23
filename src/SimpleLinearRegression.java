import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import slr.Compute;
import slr.DataCSV;
import slr.Draw;

class SimpleLinearRegression {
 
    public static void main(String[] args) throws IOException{
       DataCSV data = new DataCSV();
        data.loadCSV("data.csv");
        data.printCSV();
        List<Double> x= new ArrayList<>(data.getIndexValues(0));
        List<Double> xInv= new ArrayList<>(data.getIndexValuesInverted(0,4));
        List<Double> y= new ArrayList<>(data.getIndexValues(1));

        System.out.println();
        System.out.format("%10s %10s %10s\n","Age","1/age","Height");
        System.out.format("sum %6s %10s %10s\n",Compute.sumList(x),Compute.sumList(xInv),Compute.sumList(y));
        System.out.format("avg %6s %10s %10s\n",Compute.avgList(x,4),Compute.avgList(xInv,4),Compute.avgList(y,4));
        System.out.format("tss %17s %10s\n",Compute.tssList(xInv,4),Compute.tssList(y,4));
        System.out.println();
        System.out.format("rss %17s\n",Compute.rssList(xInv,y,4));
        
        Draw a = new Draw();
        String[] title = new String[] {"Dispersion Plot","Age","Height"};
        a.scatterPlot(data.getIndexValues(0),data.getIndexValues(1), title);
    }
}