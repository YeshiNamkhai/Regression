import java.io.IOException;

import slr.DataCSV;
import slr.Draw;

class SimpleLinearRegression {
    /**
     * Runs simple linear regression
     * @param fileName
     * @param x
     * @param y
     * @param inv
     * @throws IOException
     */
    private static void execRegression(String fileName, int x, int y, boolean inv) throws IOException {
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
            System.out.format("ssd %17s %10s\n",data.getSsdX(4),data.getSsdY(4));

        } else {
            System.out.println();
            System.out.format("%10s %10s\n",data.getFieldName(x),data.getFieldName(y));
            System.out.format("sum %6s %10s\n",data.getSumX(0),data.getSumY(0));
            System.out.format("avg %6s %10s\n",data.getAvgX(1),data.getAvgY(1));
            System.out.format("min %6s %10s\n",data.getMinX(),data.getMinY());
            System.out.format("max %6s %10s\n",data.getMaxX(),data.getMaxY());
            System.out.format("med %6s %10s\n",data.getMedX(1),data.getMedY(1));
            System.out.format("ssd %6s %10s\n",data.getSsdX(1),data.getSsdY(1));
        }
        
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
        
        Draw chart = new Draw("Plot",data.getFieldName(x),data.getFieldName(y));
        if(inv)
            chart.scatterPlot(data.getInvX(),data.getY(),data.getYhat());
        else
            chart.scatterPlot(data.getX(),data.getY(),data.getYhat());
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
