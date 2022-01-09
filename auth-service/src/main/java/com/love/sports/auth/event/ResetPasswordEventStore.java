package com.love.sports.auth.event;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class ResetPasswordEventStore implements EventStore<ResetPasswordEvent> {

    private final String RESET_PASSWORD = "reset_password:";

    @Resource
    private RedisTemplate<String, ResetPasswordEvent> redisTemplate;

    @Override
    public void store(ResetPasswordEvent event) {
        redisTemplate.opsForValue().set(RESET_PASSWORD + event.getEventId(), event, 10, TimeUnit.MINUTES);

    }

    @Override
    public ResetPasswordEvent readEvent(String eventId) {
        return redisTemplate.opsForValue().get(RESET_PASSWORD + eventId);
    }

    @Override
    public void removeEvent(String eventId) {
        redisTemplate.delete(RESET_PASSWORD + eventId);
    }
}
