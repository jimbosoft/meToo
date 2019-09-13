package au.com.candidate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * I always wanted to be a banker ...
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MeBank bank = new MeBank();
        GetCsvInputFile input = new GetCsvInputFile(bank);
        System.out.println("Bank detail: " + bank);

         for(int i = 0; i < args.length; i++)
        {
            System.out.println("Args: " + args[i]);
        }
         while (true) {
             processInput(bank);
         }
    }

    /**
     * expected input
     * accountId: ACC334455
     * from: 20/10/2018 12:00:00
     * to: 20/10/2018 19:00:00
     */
    private static void processInput(MeBank bank)
    {
        String accountNr;
        LocalDateTime from;
        LocalDateTime to;

        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);

        try {
            System.out.print("accountId: ");
            accountNr = br.readLine();
            System.out.print("from (20/10/2018 12:00:00): ");
            from = TickTock.convert(br.readLine().trim());
            System.out.print("to (20/10/2018 12:00:00): ");
            to = TickTock.convert(br.readLine().trim());

            if (accountNr.length() > 0 && from != null && to != null)
            {
                MeBank.FoundResult fr =  bank.getRelBalance(accountNr, from, to);
                String displayAmount = Integer.toString(fr.amount);
                if (Math.abs(fr.amount) > 0)
                {
                    displayAmount = displayAmount.substring(0, displayAmount.length() - 3)
                            + "." + displayAmount.substring(displayAmount.length() - 3, displayAmount.length() - 1);
                }
                System.out.println("Relative balance for the period is: " + fr.amount);
                System.out.println("Number of transactions included is: " + fr.nrOfTransactions);
            }
            else
            {
                System.out.println("Input error, please try again");
            }
            System.out.println();
        }catch (Exception ex)
        {
            System.out.println("Encountered an exception, try again. Error: " + ex.getMessage());
        }
     }
}
