import java.io.IOException;

import slr.Draw;
import slr.ReadCSV;

class SimpleLinearRegression {
 
    public static void main(String[] args) throws IOException{

        ReadCSV data = new ReadCSV();
        data.loadCSV("data.csv");
        data.printCSV();
        System.out.println(data.getCSV());
        
        Draw a = new Draw();

        double[] age = new double[] { 4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19 };
        double[] height = new double[] { 100.1,107.2,114.1,121.7,126.8,130.90,137.5,143.2,149.4,151.6,154.0,154.6,155.0,155.1,155.3,155.7 };

        a.render(age,height);
    }
}