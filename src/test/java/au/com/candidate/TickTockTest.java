package au.com.candidate;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TickTockTest {

    @Test
    public void convert() {
        LocalDateTime ldt = TickTock.convert("20/10/2018 12:47:55");
        assertTrue(ldt != null);
    }

    @Test
    public void convertFail() {
        LocalDateTime ldt = TickTock.convert("20/102018 12:47:55");
        assertTrue(ldt == null);
    }

    @Test
    public void inRangeStart() {
        LocalDateTime start = TickTock.convert("20/10/2018 12:47:55");
        LocalDateTime end = TickTock.convert("21/10/2018 12:47:55");
        LocalDateTime input = TickTock.convert("20/10/2018 12:47:55");

        assertTrue(TickTock.inRange(start, end, input));
    }
    @Test
    public void inRangeEnd() {
        LocalDateTime start = TickTock.convert("20/10/2018 12:47:55");
        LocalDateTime end = TickTock.convert("21/10/2018 12:47:55");
        LocalDateTime input = TickTock.convert("21/10/2018 12:47:55");

        assertTrue(TickTock.inRange(start, end, input));
    }
    @Test
    public void inRange() {
        LocalDateTime start = TickTock.convert("20/10/2018 12:47:55");
        LocalDateTime end = TickTock.convert("21/10/2018 12:47:55");
        LocalDateTime input = TickTock.convert("20/10/2018 12:47:56");

        assertTrue(TickTock.inRange(start, end, input));
    }
    @Test
    public void notInRange() {
        LocalDateTime start = TickTock.convert("20/10/2018 12:47:55");
        LocalDateTime end = TickTock.convert("21/10/2018 12:47:55");
        LocalDateTime input = TickTock.convert("21/10/2018 12:47:56");

        assertTrue(!TickTock.inRange(start, end, input));
    }
    @Test
    public void endBeforeStart() {
        LocalDateTime start = TickTock.convert("20/10/2019 12:47:55");
        LocalDateTime end = TickTock.convert("21/10/2018 12:47:55");
        LocalDateTime input = TickTock.convert("21/10/2018 12:47:54");

        assertTrue(!TickTock.inRange(start, end, input));
    }

}