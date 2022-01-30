import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TradingOffice {
    public static void main(String[] args) throws Exception{
    Scanner kb = new Scanner(System.in);
    Scanner Excel = new Scanner(new File("AAPL.csv"));
    Scanner Excel2 = new Scanner(new File("AAPL-Dividends.csv"));
    // Date,Open,High,Low,Close,Adj Close,Volume

    ArrayList<String> Date = new ArrayList<>();
    ArrayList<Double> Open = new ArrayList<>();
    ArrayList<Double> High = new ArrayList<>();
    ArrayList<Double> Low = new ArrayList<>();
    ArrayList<Double> Close = new ArrayList<>();
    ArrayList<Double> AdjClose = new ArrayList<>();
    ArrayList<Double> Volume = new ArrayList<>();

    ArrayList<Double> Dividends = new ArrayList<>();
    ArrayList<String> Dividends_Data = new ArrayList<>();

    //loading all the data from the AAPL.csv and AAPL-Dividends.csv
    Excel.nextLine();
    Excel.useDelimiter(",");
    Excel2.nextLine();
    Excel2.useDelimiter(",");

    while (Excel.hasNext()) {

    Date.add(Excel.next());
    Open.add(Excel.nextDouble());
    High.add(Excel.nextDouble());
    Low.add(Excel.nextDouble());
    Close.add(Excel.nextDouble());
    AdjClose.add(Excel.nextDouble());
    Volume.add(Double.valueOf((Excel.nextLine().split("\n")[0].substring(1))));

    }
    while (Excel2.hasNext()){
        Dividends_Data.add(Excel2.next());
        Dividends.add(Double.valueOf((Excel2.nextLine().split("\n")[0].substring(1))));
    }

    Excel.close();
    Excel2.close();

    Boolean exit = false;

    while (!exit){
        int Choose = 0;
        Double days = -1.0;
        ArrayList<Double> AdjClose2 = (ArrayList<Double>) AdjClose.clone();

        //giving the user to choose form SMA and EMA with specific number of days
        //and the price of specific day

        System.out.print("For the price of specific day enter 1 for SMA enter 2 For EMA enter 3 for exit enter 4: ");
        try {
            Choose = Integer.parseInt(kb.next());
        }catch (NumberFormatException e){
            System.out.println("Error pleas enter an Integer");
        }

        if (Choose==1){
            System.out.print("Enter the date in this format YYYY-MM-DD:");
            String User_date = kb.next();
            if (Date.contains(User_date))
                System.out.println("The closing price of the date "+User_date+" is "+Close.get(Date.indexOf(User_date)));
            else
                System.out.println("the date does not exist");
        }
        else if (Choose==2){
            System.out.print("Enter the number of days:");
            try {
                days = Double.valueOf(kb.next());
            }catch (NumberFormatException e){
                System.out.println("Error pleas enter an Integer");
            }
            if (days!=-1){
                ArrayList<Double> SMAs = SMA(new ArrayList<>(),days,AdjClose2);
                while (!SMAs.isEmpty()){
                    System.out.println(SMAs.remove(0));
                }
            }
        }
        else if (Choose==3){
            System.out.print("Enter the number of days:");
            try {
                days = Double.valueOf(kb.next());
            }catch (NumberFormatException e){
                System.out.println("Error pleas enter an Integer");
            }
            if (days!=-1){
                ArrayList<Double> EMAs = EMA(AdjClose2,Date,days,Dividends,Dividends_Data,0.0);
                while (!EMAs.isEmpty()){
                    System.out.println(EMAs.remove(0));
                }
            }
        }
        else if (Choose==4)
            exit = true;
    }
}
    public static ArrayList<Double> SMA(ArrayList<Double> SMA, Double days, ArrayList<Double> Values){
        // recursive method to find SMA using the values of each day
        if (Values.isEmpty())
            return SMA;
        Double PeriodSum = 0.0;
        for (int i = 0; i < days; i++) {
            if (Values.isEmpty())
                break;
            PeriodSum+=Values.remove(0);
        }
        SMA.add(Math.abs((days-PeriodSum)/days));
        return SMA(SMA,days,Values);
    }
    public static ArrayList<Double> EMA(ArrayList<Double> Values,ArrayList<String> Dates, Double days, ArrayList<Double> Dividends,ArrayList<String> Dividends_Data, Double PreEMA){
        //EMA method with smoothing the dividends yield
        ArrayList<Double> EMA = new ArrayList<>();
        Double Smoothing = Dividends.get(0);
        int i = 0;
        while (!Values.isEmpty()){
            if (Dividends_Data.contains(Dates.get(i)))
                Smoothing = Dividends.get(Dividends_Data.indexOf(Dates.get(i)));// changing the Smoothing factor to fit the date
            EMA.add(Values.remove(0)*(Smoothing/(1+days))+PreEMA*(1-(Smoothing/(1+days))));//EMA formula
            PreEMA = EMA.get(EMA.size()-1);
            i++;
        }
        return EMA;
    }
}