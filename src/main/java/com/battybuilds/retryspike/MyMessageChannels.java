package com.battybuilds.retryspike;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MyMessageChannels {

    String INITIAL_INPUT = "my-initial-input";
    String INITIATE = "initiate";
    String SEND_RETRY = "outbound-retry";
    String PULL_RETRY = "inbound-retry";

    @Input(INITIAL_INPUT)
    MessageChannel initialInbound();

    @Output(INITIATE)
    MessageChannel initiate();

    @Output(SEND_RETRY)
    MessageChannel retry();

    @Input(PULL_RETRY)
    MessageChannel pullRetry();
}
