package com.roman.service.event_listener.listener;

import com.roman.service.profile.init.InitializationNewsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("init")
@RequiredArgsConstructor
@Component
public class InitializationEventListener {

    private final Logger logger = LoggerFactory.getLogger("INIT_LOGGER");
    private final InitializationNewsService service;

    @EventListener(ContextRefreshedEvent.class)
    public void handleInitializationEvent(ContextRefreshedEvent initEvent){
        service.init();
        logger.info("Запуск приложения с заготовленной базой статей.");
    }

}
