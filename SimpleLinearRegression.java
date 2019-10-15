import java.io.IOException;

import slr.ReadCSV;

class SimpleLinearRegression {
 
    public static void main(String[] args) throws IOException{

        ReadCSV data = new ReadCSV();
        data.loadCSV("data.csv");
        data.printCSV();
        System.out.println(data.getCSV());
    }
}