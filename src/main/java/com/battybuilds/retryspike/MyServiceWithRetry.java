package com.battybuilds.retryspike;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;

@Service
@EnableBinding(MyMessageChannels.class)
public class MyServiceWithRetry {

    private final int fifteenMinutes = 900000;
    private final int fiveSeconds = 5000;
    private final int tenSeconds = 10000;
    private MyMessageChannels messageChannels;

    public MyServiceWithRetry(MyMessageChannels messageChannels) {
        this.messageChannels = messageChannels;
    }

    public void makeInitialExternalCall(byte[] messageRequest) {
        callWithHeaders(messageRequest, buildInitialMessageHeaders());
    }

    public void makeRetryCall(byte[] messageRequest, MessageHeaders messageHeaders) {
        Long initial_timestamp = getInitial_timestamp(messageHeaders);
        Integer times_called = getTimes_called(messageHeaders);
        long currentTimestamp = System.currentTimeMillis();
        if (times_called > 16) {
            //publish to mongo
            return;
        }
        if (itHasBeen(tenSeconds, initial_timestamp, currentTimestamp)) {
            System.out.println(new Date(initial_timestamp).toString());
            callWithHeaders(messageRequest, messageHeaders);
        } else {
            pauseForMilliseconds(1000);
            messageChannels.retry().send(MessageBuilder.createMessage(messageRequest, messageHeaders));
        }
    }

    private void pauseForMilliseconds(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    boolean itHasBeen(int tenSeconds, Long initial_timestamp, long currentTimestamp) {
        return initial_timestamp + tenSeconds < currentTimestamp;
    }

    private Integer getTimes_called(MessageHeaders messageHeaders) {
        return (Integer) messageHeaders.get("times_called");
    }

    private Long getInitial_timestamp(MessageHeaders messageHeaders) {
        return (Long) messageHeaders.get("initial_timestamp");
    }

    private void callWithHeaders(byte[] messageRequest, MessageHeaders messageHeaders) {
        MessageHeaders updatedMessageHeaders = resetHeaders(messageHeaders);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8099/endpoint", String.class);
            System.out.println("***************Call worked with " + responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody());
        } catch (HttpServerErrorException e) {
            System.out.println("-----------Call Failed--------");
            System.out.println("ResponseBody____________" + e.getResponseBodyAsString());
            e.printStackTrace();
            messageChannels.retry().send(MessageBuilder.createMessage(messageRequest, updatedMessageHeaders));
        }
    }

    private MessageHeaders resetHeaders(MessageHeaders messageHeaders) {
        Integer times_called = getTimes_called(messageHeaders);
        times_called++;
        System.out.println("Times called: " + times_called);
        return buildMessageHeaders(times_called);
    }

    private MessageHeaders buildInitialMessageHeaders() {
        int timesCalled = 0;
        return buildMessageHeaders(timesCalled);
    }

    private MessageHeaders buildMessageHeaders(int timesCalled) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("times_called", timesCalled);
        headers.put("initial_timestamp", System.currentTimeMillis());
        return new MessageHeaders(headers);
    }
}
