package com.battybuilds.retryspike;

import com.battybuilds.retryspike.model.MyMessageBody;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(MyMessageChannels.class)
public class MyPublisher {

    private final MyMessageChannels messageChannels;

    public MyPublisher(MyMessageChannels messageChannels) {
        this.messageChannels = messageChannels;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody MyMessageBody messageBody) {
        messageChannels.initiate().send(new GenericMessage<>(messageBody));
        return "Done";
    }
}
