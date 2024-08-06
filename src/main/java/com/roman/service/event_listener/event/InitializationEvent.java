package com.roman.service.event_listener.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class InitializationEvent extends ApplicationEvent {

    public InitializationEvent(LocalDateTime time) {
        super(time);
    }
}
