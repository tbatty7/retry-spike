package com.battybuilds.retryspike;

import com.battybuilds.retryspike.model.MyMessageBody;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;

@EnableBinding(MyMessageChannels.class)
public class MyConsumer {

    private MyServiceWithRetry serviceWithRetry;

    public MyConsumer(MyServiceWithRetry serviceWithRetry) {

        this.serviceWithRetry = serviceWithRetry;
    }

    @StreamListener(MyMessageChannels.INITIAL_INPUT)
    public void receiveInitialMessage(Message<MyMessageBody> messageRequest, @Headers MessageHeaders headers) {
        System.out.println("-------------------------------------------");
        MyMessageBody payload = messageRequest.getPayload();
        System.out.println(payload);
        System.out.println(headers);
        System.out.println("-------------------------------------------");
        serviceWithRetry.makeInitialExternalCall(payload);
    }
}