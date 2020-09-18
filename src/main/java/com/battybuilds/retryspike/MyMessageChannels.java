package com.battybuilds.retryspike;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyMessageChannels {

    String INITIAL_INPUT = "my-initial-input";
    String INITIATE = "initiate";

    @Input(INITIAL_INPUT)
    MessageChannel initialInbound();

    @Output(INITIATE)
    MessageChannel initiate();

    // public interface TripChannel {
    //
    //    String INPUT = "loyalty-trip-in";
    //
    //    @Input(INPUT)
    //    MessageChannel inbound();
    //}

}
