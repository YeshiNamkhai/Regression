import java.io.File;
import java.io.IOException;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.FileDataSet;

import slr.ReadCSV;

class SimpleLinearRegression {
 
    public static void main(String[] args) throws IOException{

        ReadCSV data = new ReadCSV();
        data.loadCSV("data.csv");
        data.printCSV();
        System.out.println(data.getCSV());
        try {
            JavaPlot p = new JavaPlot();
            //p.set("title","'Scatter Plot'");
            p.setTitle("Scatter Plot");
            p.addPlot(new FileDataSet(new File("test.csv")));
            System.out.println(p.getParameters());
            p.plot();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}