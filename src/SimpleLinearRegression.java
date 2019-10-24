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
        List<Double> xInv= new ArrayList<>(data.getIndexValuesInverted(0));
        List<Double> y= new ArrayList<>(data.getIndexValues(1));

        double sumX = Compute.sumList(x);
        double sumInvX = Compute.sumList(xInv);
        double sumY = Compute.sumList(y);
        double avgX = Compute.avgList(x);
        double avgY = Compute.avgList(y);
        double avgInvX = Compute.avgList(xInv);
        double tssInvX = Compute.tssList(xInv);
        double tssY = Compute.tssList(y);
        double rss = Compute.rssList(xInv,y,avgInvX,avgY);

        double a = Compute.round(rss/tssInvX,1);
        double b = Compute.round(avgY-avgInvX*a,1);

        System.out.println();
        System.out.format("%10s %10s %10s\n","Age","1/age","Height");
        System.out.format("sum %6s %10s %10s\n",sumX,Compute.round(sumInvX,4),sumY);
        System.out.format("avg %6s %10s %10s\n",Compute.round(avgX,1),Compute.round(avgInvX,4),Compute.round(avgY,1));
        System.out.format("tss %17s %10s\n",Compute.round(tssInvX,4),Compute.round(tssY,4));
        System.out.println();
        System.out.format("rss %17s\n",Compute.round(rss,4));
        System.out.format("a %19s\n",a);
        System.out.format("b %19s\n",b);
        System.out.println();
        System.out.format("y = %sx + %s\n",a,b);
        

        Draw scat = new Draw();
        String[] title = new String[] {"Dispersion Plot","Age","Height"};
        scat.scatterPlot(data.getIndexValues(0),data.getIndexValues(1), title);
    }
}