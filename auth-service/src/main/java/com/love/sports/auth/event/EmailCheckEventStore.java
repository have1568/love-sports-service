package com.love.sports.auth.event;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class EmailCheckEventStore implements EventStore<EmailCheckEvent> {

    private final String STORE_PREFIX = "check:email:";

    @Resource
    private RedisTemplate<String, EmailCheckEvent> redisTemplate;

    @Override
    public void store(EmailCheckEvent event) {
        log.info(event.getCode());
        redisTemplate.opsForValue().set(STORE_PREFIX + event.getEventId(), event, 10, TimeUnit.MINUTES);
    }

    @Override
    public EmailCheckEvent readEvent(String eventId) {
        return redisTemplate.opsForValue().get(STORE_PREFIX + eventId);
    }

    @Override
    public void removeEvent(String eventId) {
        redisTemplate.delete(STORE_PREFIX + eventId);
    }
}
