package com.battybuilds.retryspike;

import org.junit.Test;
import java.util.Date;

public class CounterTest {

    @Test
    public void timestampExperiments() {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        System.out.println(new Date(timestamp));
        long fifteenMinutesAgo = timestamp - 900000;
        System.out.println(fifteenMinutesAgo);
        System.out.println(new Date(fifteenMinutesAgo));
        long fifteenSecondsAgo = timestamp - 15000;
        System.out.println(fifteenSecondsAgo);
        System.out.println(new Date(fifteenSecondsAgo));
    }

    @Test
    public void incrementingBy_15_Minutes() {
        long initialTimestamp = System.currentTimeMillis();
        System.out.println(initialTimestamp);
        for (int counter = 1; counter < 17; counter++) {
            long fifteenMinuteIncrement = initialTimestamp - (900000 * counter);
            System.out.println(new Date(fifteenMinuteIncrement));
            long difference = initialTimestamp - fifteenMinuteIncrement;
            long differenceInMinutes = (difference / 1000) / 60;
            System.out.println(differenceInMinutes);
            long differenceInHours = differenceInMinutes / 60;
            System.out.println(differenceInHours);
        }
    }
}