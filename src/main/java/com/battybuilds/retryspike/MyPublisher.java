package com.battybuilds.retryspike;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@EnableBinding(MyMessageChannels.class)
public class MyPublisher {

    private MyMessageChannels messageChannels;

    public MyPublisher(MyMessageChannels messageChannels) {
        this.messageChannels = messageChannels;
    }

    public String publishMessage(String message) {
        messageChannels.initiate().send(new GenericMessage<>(message));
        return "Done";
    }
}
