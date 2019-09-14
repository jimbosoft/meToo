package au.com.candidate;

import org.junit.Test;
import static org.junit.Assert.*;

/***
 * Testing the core functionality
 */
public class MeBankTest {

    @Test
    public void testInAndOutgoings() {
        MeBank mb = new MeBank();
        mb.addTransaction("1", "2", "a",
                TickTock.convert("20/10/2018 12:47:55"), 1025, "whatever", "");
        mb.addTransaction("2", "1", "b",
                TickTock.convert("20/10/2018 12:47:55"), 1025, "whatever", "");
        mb.addTransaction("1", "2", "c",
                TickTock.convert("20/10/2018 12:47:55"), 1234, "whatever", "");
        mb.addTransaction("2", "1", "d",
                TickTock.convert("20/10/2018 12:47:55"), 1234, "whatever", "");

        MeBank.FoundResult fr1 = mb.getRelBalance("1", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 12:47:55"));
        MeBank.FoundResult fr2 = mb.getRelBalance("2", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 12:47:55"));
        assertEquals(fr1.amount, 0);
        assertEquals(4, fr1.nrOfTransactions);
        assertEquals(fr2.amount, 0);
        assertEquals(4, fr2.nrOfTransactions);
    }

    @Test
    public void testReversalsInTimeframe() {
        MeBank mb = new MeBank();
        mb.addTransaction("1", "2", "a",
                TickTock.convert("20/10/2018 12:47:55"), 1025, "whatever", "");
        mb.addTransaction("2", "1", "b",
                TickTock.convert("20/10/2018 12:47:55"), 1025, "whatever", "");
        mb.addTransaction("1", "2", "c",
                TickTock.convert("20/10/2018 12:47:55"), 1025, "whatever", "a");
        mb.addTransaction("2", "1", "d",
                TickTock.convert("20/10/2018 12:47:55"), 1000, "whatever", "");

        MeBank.FoundResult fr1 = mb.getRelBalance("1", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 12:47:55"));
        MeBank.FoundResult fr2 = mb.getRelBalance("2", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 12:47:55"));
        assertEquals(fr1.amount, 2025);
        assertEquals(2, fr1.nrOfTransactions);
        assertEquals(fr2.amount, -2025);
        assertEquals(2, fr2.nrOfTransactions);
    }
    @Test
    public void testReversalsOutTimeframe() {
        MeBank mb = new MeBank();
        mb.addTransaction("1", "2", "a",
                TickTock.convert("20/10/2018 12:47:55"), 1025, "whatever", "");
        mb.addTransaction("2", "1", "b",
                TickTock.convert("20/10/2018 12:47:55"), 1025, "whatever", "");
        mb.addTransaction("1", "2", "c",
                TickTock.convert("20/10/2019 12:47:55"), 1025, "whatever", "a");
        mb.addTransaction("2", "1", "d",
                TickTock.convert("20/10/2018 12:47:55"), 1000, "whatever", "");

        MeBank.FoundResult fr1 = mb.getRelBalance("1", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 12:47:55"));
        MeBank.FoundResult fr2 = mb.getRelBalance("2", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 12:47:55"));
        assertEquals(fr1.amount, 2025);
        assertEquals(2, fr1.nrOfTransactions);
        assertEquals(fr2.amount, -2025);
        assertEquals(2, fr2.nrOfTransactions);
    }
    @Test
    public void testTimeFrame() {
        MeBank mb = new MeBank();
        mb.addTransaction("1", "2", "a",
                TickTock.convert("20/10/2018 12:47:55"), 1000, "whatever", "");
        mb.addTransaction("1", "2", "b",
                TickTock.convert("20/10/2018 13:47:55"), 100, "whatever", "");
        mb.addTransaction("1", "2", "c",
                TickTock.convert("20/10/2018 10:47:55"), 10, "whatever", "");
        mb.addTransaction("1", "2", "d",
                TickTock.convert("20/10/2018 14:47:55"), 1, "whatever", "");

        MeBank.FoundResult fr1 = mb.getRelBalance("1", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 13:47:55"));
        MeBank.FoundResult fr2 = mb.getRelBalance("2", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 13:47:55"));
        assertEquals(fr1.amount, -1100);
        assertEquals(2, fr1.nrOfTransactions);
        assertEquals(fr2.amount, 1100);
        assertEquals(2, fr2.nrOfTransactions);
    }
    @Test
    public void testOutsideTimeFrame() {
        MeBank mb = new MeBank();
        mb.addTransaction("1", "2", "a",
                TickTock.convert("20/10/2018 12:47:55"), 1000, "whatever", "");
        mb.addTransaction("1", "2", "b",
                TickTock.convert("20/10/2018 13:47:55"), 100, "whatever", "");
        mb.addTransaction("1", "2", "c",
                TickTock.convert("20/10/2018 10:47:55"), 10, "whatever", "");
        mb.addTransaction("1", "2", "d",
                TickTock.convert("20/10/2018 14:47:55"), 1, "whatever", "");

        MeBank.FoundResult fr1 = mb.getRelBalance("1", TickTock.convert("20/10/2019 12:47:55"),TickTock.convert("20/10/2019 13:47:55"));
        MeBank.FoundResult fr2 = mb.getRelBalance("2", TickTock.convert("20/10/2018 14:47:56"),TickTock.convert("20/10/2019 13:47:55"));
        assertEquals(fr1.amount, 0);
        assertEquals(0, fr1.nrOfTransactions);
        assertEquals(fr2.amount, 0);
        assertEquals(0, fr2.nrOfTransactions);
    }
    @Test
    public void testInvalidAccount() {
        MeBank mb = new MeBank();
        mb.addTransaction("1", "2", "a",
                TickTock.convert("20/10/2018 12:47:55"), 1000, "whatever", "");
        mb.addTransaction("1", "2", "b",
                TickTock.convert("20/10/2018 13:47:55"), 100, "whatever", "");
        mb.addTransaction("1", "2", "c",
                TickTock.convert("20/10/2018 10:47:55"), 10, "whatever", "");
        mb.addTransaction("1", "2", "d",
                TickTock.convert("20/10/2018 14:47:55"), 1, "whatever", "");

        MeBank.FoundResult fr1 = mb.getRelBalance("3", TickTock.convert("20/10/2018 12:47:55"),TickTock.convert("20/10/2018 13:47:55"));
        assertEquals(fr1.amount, 0);
        assertEquals(0, fr1.nrOfTransactions);
    }
    @Test
    public void testStartTimeAfterEndTime() {
        MeBank mb = new MeBank();
        mb.addTransaction("1", "2", "a",
                TickTock.convert("20/10/2018 12:47:55"), 1000, "whatever", "");

        MeBank.FoundResult fr1 = mb.getRelBalance("1", TickTock.convert("20/10/2019 12:47:55"),TickTock.convert("20/10/2018 13:47:55"));
        assertEquals(fr1.amount, 0);
        assertEquals(0, fr1.nrOfTransactions);
    }

}