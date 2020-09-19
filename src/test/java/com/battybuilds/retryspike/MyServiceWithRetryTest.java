package com.battybuilds.retryspike;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MyServiceWithRetryTest {

    @Test
    public void checksFifteenMinutesAgo() {
        MyServiceWithRetry service = new MyServiceWithRetry(null);
        long currentTimestamp = System.currentTimeMillis();
        int fifteenMinutesInMs = 900000;
        int seconds = fifteenMinutesInMs / 1000;
        int minutes = seconds / 60;
        assertThat(minutes).isEqualTo(15);
        boolean hasNotBeen15min = service.itHasBeen(fifteenMinutesInMs, currentTimestamp, currentTimestamp);
        assertThat(hasNotBeen15min).isFalse();

        long fifteenMinutesAgo = currentTimestamp - fifteenMinutesInMs - 1;
        boolean hasBeen15min = service.itHasBeen(fifteenMinutesInMs, fifteenMinutesAgo, currentTimestamp);
        assertThat(hasBeen15min).isTrue();
    }


}