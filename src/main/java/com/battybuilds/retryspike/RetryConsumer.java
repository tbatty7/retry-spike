package com.battybuilds.retryspike;

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

    @StreamListener(target = MyMessageChannels.PULL_RETRY, condition = "headers['times_called'] < 3")
    public void initiateRetry(byte[] message, @Headers MessageHeaders headers) {
        System.out.println("-------------counter----------- " + counter++);
        retryService.makeRetryCall(message, headers);
    }

    @StreamListener(target = MyMessageChannels.PULL_RETRY, condition = "headers['times_called'] > 2")
    public void sendToMongo(byte[] message) {
        System.out.println("Message sent to Mongo!");
        //send to Mongo
    }
}
