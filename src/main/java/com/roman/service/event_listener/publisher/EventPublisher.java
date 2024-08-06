package com.roman.service.event_listener.publisher;

import com.roman.service.event_listener.event.InitializationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    public void initializationPublish(){
        publisher.publishEvent(new InitializationEvent(LocalDateTime.now()));
    }
}
