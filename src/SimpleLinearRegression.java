import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import slr.Compute;
import slr.DataCSV;
import slr.Draw;

class SimpleLinearRegression {
 
    private static void demoMiu() throws IOException {
       DataCSV data = new DataCSV();
        data.loadCSV("ageMiu.csv");
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
        double ssdInvX = Compute.ssdList(xInv);
        double ssdY = Compute.ssdList(y);
        double rss = Compute.rssList(xInv,y,avgInvX,avgY);

        double a = rss/ssdInvX;
        double b = avgY-avgInvX*a;

        System.out.println();
        System.out.format("%10s %10s %10s\n","Age","1/age","Height");
        System.out.format("sum %6s %10s %10s\n",sumX,Compute.round(sumInvX,4),sumY);
        System.out.format("avg %6s %10s %10s\n",Compute.round(avgX,1),Compute.round(avgInvX,4),Compute.round(avgY,1));
        System.out.format("ssd %17s %10s\n",Compute.round(ssdInvX,4),Compute.round(ssdY,4));
        System.out.println();
        System.out.format("rss %17s\n",Compute.round(rss,4));
        System.out.format("a %19s\n",Compute.round(a,1));
        System.out.format("b %19s\n",Compute.round(b,1));
        System.out.println();
        System.out.format("y = %sx + %s\n",Compute.round(a,1),Compute.round(b,1));        

        Draw scat = new Draw();
        scat.scatterPlot(x,y, new String[] {"Dispersion Plot","Age","Height"});
    }

    private static void demoTea() throws IOException {
        DataCSV data = new DataCSV();
         data.loadCSV("teaHouse.csv");
         data.printCSV();
         List<Double> x= new ArrayList<>(data.getIndexValues(1));
         List<Double> y= new ArrayList<>(data.getIndexValues(2));
 
         double sumX = Compute.sumList(x);
         double sumY = Compute.sumList(y);
         double avgX = Compute.avgList(x);
         double avgY = Compute.avgList(y);
         double ssdX = Compute.ssdList(x);
         double ssdY = Compute.ssdList(y);
         double rss = Compute.rssList(x,y,avgX,avgY);
 
         double a = rss/ssdX;
         double b = avgY-avgX*a;

         System.out.println();
         System.out.format("%10s %10s\n","Temp.","Icetea ord.s");
         System.out.format("sum %6s %10s\n",sumX,sumY);
         System.out.format("avg %6s %10s\n",Compute.round(avgX,1),Compute.round(avgY,1));
         System.out.format("ssd %6s %10s\n",Compute.round(ssdX,1),Compute.round(ssdY,1));
         System.out.println();
         System.out.format("rss %17s\n",Compute.round(rss,1));
         System.out.format("a %19s\n",Compute.round(a,1));
         System.out.format("b %19s\n",Compute.round(b,1));
         System.out.println();
         System.out.format("y = %sx + %s\n",Compute.round(a,1),Compute.round(b,1));
         
 
         Draw scat = new Draw();
         scat.scatterPlot(x,y,new String[] {"Dispersion Plot","Temperature","Icetea orders"});
     }
 
    public static void main(String[] args) throws IOException{
        
        System.out.println("--Miu--");
        demoMiu();
        System.out.println();
        /*
        System.out.println("--Tea House--");
        demoTea();
        System.out.println();
        */
    }
    
}
