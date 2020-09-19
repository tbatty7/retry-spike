package com.battybuilds.retryspike;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

@EnableBinding(MyMessageChannels.class)
public class RetryConsumer {

    private int counter;
    private MyServiceWithRetry retryService;

    public RetryConsumer(MyServiceWithRetry retryService) {
        this.retryService = retryService;
    }

    @StreamListener(MyMessageChannels.PULL_RETRY)
    public void initiateRetry(byte[] message, @Headers MessageHeaders headers) {
//        System.out.println("-------------counter----------- " + counter++);
        retryService.retryCall(message, headers);
    }
}
