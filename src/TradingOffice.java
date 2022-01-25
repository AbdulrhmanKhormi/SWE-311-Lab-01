import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TradingOffice {
    public static void main(String[] args) throws Exception{
    Scanner kb = new Scanner(System.in);
    Scanner Excel = new Scanner(new File("AAPL.csv"));
    // Date,Open,High,Low,Close,Adj Close,Volume

    ArrayList<String> Data = new ArrayList<>();
    ArrayList<Double> Open = new ArrayList<>();
    ArrayList<Double> High = new ArrayList<>();
    ArrayList<Double> Low = new ArrayList<>();
    ArrayList<Double> Close = new ArrayList<>();
    ArrayList<Double> AdjClose = new ArrayList<>();
    ArrayList<Double> Volume = new ArrayList<>();

        Excel.nextLine();
        Excel.useDelimiter(",");
        while (Excel.hasNext())
    {

        Data.add(Excel.next());
        Open.add(Excel.nextDouble());
        High.add(Excel.nextDouble());
        Low.add(Excel.nextDouble());
        Close.add(Excel.nextDouble());
        AdjClose.add(Excel.nextDouble());
        Volume.add(Double.valueOf((Excel.nextLine().split("\n")[0].substring(1))));

    }
        Excel.close();
        System.out.println("Enter the date in this format YYYY-MM-DD:");
    String User_date = kb.next();
        if (Data.contains(User_date))
            System.out.println("The closing price of the date "+User_date+" is "+Close.get(Data.indexOf(User_date)));
        else
                System.out.println("the date is not exist");
}
}