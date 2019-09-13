package au.com.candidate;

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
    }
}
