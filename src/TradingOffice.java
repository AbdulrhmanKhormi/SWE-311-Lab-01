import java.io.File;
import java.util.Scanner;

public class TradingOffice {
    public static void main(String[] args) throws Exception{
        Scanner kb = new Scanner(System.in);
        Scanner Excel = new Scanner(new File("AAPL.csv"));
        // Date,Open,High,Low,Close,Adj Close,Volume
        Excel.nextLine();
        Excel.useDelimiter(",");
        while (Excel.hasNext())
        {
            String Data = Excel.next();
            Double Open = Excel.nextDouble();
            Double High = Excel.nextDouble();
            Double Low = Excel.nextDouble();
            Double Close = Excel.nextDouble();
            Double AdjClose = Excel.nextDouble();
            Double Volume = Double.valueOf((Excel.nextLine().split("\n")[0].substring(1)));
        }
        Excel.close();

}
}
