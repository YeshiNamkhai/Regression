import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import slr.DataCSV;
import slr.Draw;

class SimpleLinearRegression {

    private static void demoMiu() throws IOException {
        DataCSV data = new DataCSV();
        data.load("ageMiu.csv");
        data.show();
        data.compute(0,true,1);

        System.out.println();
        System.out.format("%10s %10s %10s\n","Age","1/age","Height");
        System.out.format("sum %6s %10s %10s\n",data.getSumX(0),data.getSumInvX(4),data.getSumY(0));
        System.out.format("avg %6s %10s %10s\n",data.getAvgX(1),data.getAvgInvX(4),data.getAvgY(1));
        System.out.format("ssd %17s %10s\n",data.getSsdX(4),data.getSsdY(4));
        System.out.println();
        System.out.format("rss %17s\n",data.getRss(4));
        System.out.format("a %19s\n",data.getA(1));
        System.out.format("b %19s\n",data.getB(1));
        System.out.println();
        System.out.format("y = %sx + %s\n",data.getA(1),data.getB(1));        

        System.out.println();
        System.out.format("%20s\n","Hat");
        System.out.format("avg %17s\n",data.getAvgHat(1));
        System.out.format("ssd %17s\n",data.getSsdHat(1));
        System.out.format("sum prod %12s\n",data.getSpHat(1));

        System.out.println();
        System.out.format("%20s\n","Regression");
        System.out.format("r %19s\n",data.getR(4));
        System.out.format("r2 %18s\n",data.getR2(4));

        Draw scat = new Draw();
        scat.scatterPlot(data.getX(),data.getY(), new String[] {"Dispersion Plot","Age","Height"});
     }
     private static void demoTea() throws IOException {
        DataCSV data = new DataCSV();
        data.load("teaHouse.csv");
        data.show();
        data.compute(1, false, 2);

        System.out.println();
        System.out.format("%10s %10s\n","Temp.","IceTea");
        System.out.format("sum %6s %10s\n",data.getSumX(0),data.getSumY(0));
        System.out.format("avg %6s %10s\n",data.getAvgX(1),data.getAvgY(1));
        System.out.format("ssd %6s %10s\n",data.getSsdX(1),data.getSsdY(1));
        System.out.println();
        System.out.format("rss %17s\n",data.getRss(1));
        System.out.format("a %19s\n",data.getA(1));
        System.out.format("b %19s\n",data.getB(1));
        System.out.println();
        System.out.format("y = %sx + %s\n",data.getA(1),data.getB(1));

        System.out.println();
        System.out.format("%20s\n","Hat");
        System.out.format("avg %17s\n",data.getAvgHat(1));
        System.out.format("ssd %17s\n",data.getSsdHat(1));
        System.out.format("sum prod %12s\n",data.getSpHat(1));

        System.out.println();
        System.out.format("%20s\n","Regression");
        System.out.format("r %19s\n",data.getR(4));
        System.out.format("r2 %18s\n",data.getR2(4));

        Draw scat = new Draw();
        scat.scatterPlot(data.getX(),data.getY(),new String[] {"Dispersion Plot","Temperature","Icetea orders"});
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
