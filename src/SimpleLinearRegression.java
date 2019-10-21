import java.io.IOException;

import slr.DataCSV;
import slr.Draw;

class SimpleLinearRegression {
 
    public static void main(String[] args) throws IOException{

        DataCSV data = new DataCSV();
        data.loadCSV("data.csv");
        data.printCSV();
        System.out.println(data.getFields());
        System.out.println(data.getValues());
        System.out.println("X="+data.getIndexValues(0));
        System.out.println("Y="+data.getIndexValues(1));

        
        Draw a = new Draw();

        String[] title = new String[] {"Dispersion Plot","Age","Height"};
        a.scatterPlot(data.getIndexValues(0),data.getIndexValues(1), title);
    }
}