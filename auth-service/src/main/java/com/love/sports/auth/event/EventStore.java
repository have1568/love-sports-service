package com.love.sports.auth.event;

public interface EventStore<S extends Event> {

    void store(S event);

    S readEvent(String eventId);

    void removeEvent(String eventId);

}
