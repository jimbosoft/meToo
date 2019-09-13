package au.com.candidate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TickTock
{
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static LocalDateTime convert(String input)
    {
        LocalDateTime returnVal = null;
        try
        {
            returnVal = LocalDateTime.parse(input, formatter);
        }catch (Exception ex)
        {
            System.out.println("DateTime conversion failed for: " + input);
        }
        return returnVal;
    }

    public static boolean inRange(LocalDateTime start, LocalDateTime end, LocalDateTime input)
    {
        return input.compareTo(start) == 0 || input.compareTo(end) == 0
                || (input.isAfter(start) && input.isBefore(end));
    }
}
