package com.battybuilds.retryspike;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

@EnableBinding(MyMessageChannels.class)
public class MyConsumer {

    @StreamListener(MyMessageChannels.INITIAL_INPUT)
    public void receiveInitialMessage(byte[] messageRequest, @Headers MessageHeaders headers) {
        System.out.println("-------------------------------------------");
        System.out.println(new String(messageRequest));
        System.out.println(headers);
        System.out.println("-------------------------------------------");
    }
}
