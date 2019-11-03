import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import slr.DataCSV;
import slr.Draw;

class SimpleLinearRegression {

    /**
     * Runs simple linear regression
     * @param fileName
     * @param x
     * @param y
     * @param inv
     * @return
     * @throws IOException
     */
    private static boolean execRegression(String fileName, int x, int y, boolean inv) throws IOException {
        DataCSV data = new DataCSV();
        data.load(fileName);
        data.show();
        data.compute(x, inv, y);

        System.out.println();
        if(inv) {
            System.out.println();
            System.out.format("%10s %10s %10s\n",data.getFieldName(x),"1/"+data.getFieldName(x),data.getFieldName(y));
            System.out.format("sum %6s %10s %10s\n",data.getSumX(0),data.getSumInvX(4),data.getSumY(0));
            System.out.format("avg %6s %10s %10s\n",data.getAvgX(1),data.getAvgInvX(4),data.getAvgY(1));
            System.out.format("min %6s %10s %10s\n",data.getMinX(),data.getMinInvX(4),data.getMinY());
            System.out.format("max %6s %10s %10s\n",data.getMaxX(),data.getMaxInvX(4),data.getMaxY());
            System.out.format("med %6s %10s %10s\n",data.getMedX(1),data.getMedInvX(4),data.getMedY(1));
            System.out.format("ssd %17s %10s\n",data.getSsdX(1),data.getSsdY(1));
            System.out.format("usv %17s %10s\n",data.getUsvX(1),data.getUsvY(1));
        } else {
            System.out.println();
            System.out.format("%10s %10s\n",data.getFieldName(x),data.getFieldName(y));
            System.out.format("sum %6s %10s\n",data.getSumX(0),data.getSumY(0));
            System.out.format("avg %6s %10s\n",data.getAvgX(1),data.getAvgY(1));
            System.out.format("min %6s %10s\n",data.getMinX(),data.getMinY());
            System.out.format("max %6s %10s\n",data.getMaxX(),data.getMaxY());
            System.out.format("med %6s %10s\n",data.getMedX(1),data.getMedY(1));
            System.out.format("ssd %6s %10s\n",data.getSsdX(1),data.getSsdY(1));
            System.out.format("usv %6s %10s\n",data.getUsvX(1),data.getUsvY(1));
        }
        
        System.out.println();
        System.out.format("rss %17s\n",data.getRss(1));
        System.out.format("a %19s\n",data.getA(1));
        System.out.format("b %19s\n",data.getB(1));

        System.out.println();
        if (data.getB(1)>0)
            System.out.format("y = %sx +%s\n",data.getA(1),data.getB(1));
        else
            System.out.format("y = %sx %s\n",data.getA(1),data.getB(1));    
        System.out.println();
        System.out.format("%20s\n","Hat");
        System.out.format("avg %17s\n",data.getAvgHat(1));
        System.out.format("ssd %17s\n",data.getSsdHat(1));
        System.out.format("Syŷ %17s\n",data.getSyHat(1));

        System.out.println();
        System.out.format("%20s\n","Regression");
        System.out.format("R %19s\n",data.getR(4));
        System.out.format("R2 %18s\n",data.getR2(4));

        System.out.println();
        System.out.format("%20s\n","Confidence");
        System.out.format("Se %18s\n",data.getEsum(1));
        System.out.format("sigma %15s\n",data.getSigma(1));
        System.out.format("anova %15s\n",data.getAnova(1));
        System.out.format("F .05 %15s\n",data.getFval());
        System.out.println();

        Draw chart = new Draw("Plot",data.getFieldName(x),data.getFieldName(y));
        if(inv)
            chart.scatterPlot(data.getX(),data.getY(),data.getYhat(),data.getInvX());
        else
            chart.scatterPlot(data.getX(),data.getY(),data.getYhat());
        
        boolean nullHypothesis = data.getFval()>data.getAnova(4);

        if(nullHypothesis) {
            System.out.println("Sorry predictions are not reliable, programs ends.");
            System.exit(0);
        }
        Scanner input = new Scanner(System.in);
        input.useLocale(Locale.US);
        double testX=0;  //x value to predict
        while(true) {
            System.out.print("What "+data.getFieldName(x)+"? ");
            try {
                testX = input.nextDouble();
                if(inv) testX=1/testX;
                if (data.getB(1)>0)
                System.out.format("%s = %s*%s +%s\n",data.getPredY(testX,1)[1],data.getA(1),testX,data.getB(1));
            else
                System.out.format("%s = %s*%s %s\n",data.getPredY(testX,1)[1],data.getA(1),testX,data.getB(1));        
                double[] intY = data.getPredY(testX,1);
                System.out.format("%s <-- %s --> %s\n",intY[0],intY[1],intY[2]);
            }
            catch (Exception e) { 
                String s = input.next();
                if(s.equalsIgnoreCase("stop") || s.equalsIgnoreCase("end"))
                    System.exit(0);
                else {
                System.out.print("Invalid "+data.getFieldName(x)+" input. ");
                System.out.println("Type 'stop' or 'end' to finish.");
                }
            }
        }
        //input.close();
    }
    /**
     * Usage:  specify data file (*.csv), 
     *  column X (0 default), 
     *  column Y (1 default), 
     *  true for 1/X (when X is not linear)
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        if(args.length==0) {
            System.out.println("Usage: specify data file (*.csv), column X (0 default), column Y (1 default), and true for 1/X (when X is not linear).");
            System.exit(1);
        }
        // check column number X
        int x=0;
        if(args.length>1) {
            try { x = Integer.parseInt(args[1]);}
            catch (NumberFormatException e) { x=0;}
        }
        // check column number Y
        int y=1;
        if(args.length>2) {
            try { y = Integer.parseInt(args[2]);}
            catch (NumberFormatException e) { y=1;}
        }
        // check if 1/X is needed
        boolean inv=false;
        if(args.length>3) {
            try { inv = Boolean.parseBoolean(args[3]); }
            catch (Exception e) { inv=false;}
        }
        //execute linear regression
        System.out.println();   
        System.out.println("--"+args[0]+"--");
        execRegression(args[0],x,y,inv);
        System.out.println();
    }    
}
