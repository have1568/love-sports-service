package com.love.sports.file.web;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface BaseController<T> {

    default ResponseEntity<Void> ok() {
        return ResponseEntity.ok().build();
    }

    default ResponseEntity<Page<T>> ok(Page<T> page) {
        return ResponseEntity.ok(page);
    }

    default ResponseEntity<Collection<T>> ok(Collection<T> collection) {
        return ResponseEntity.ok(collection);
    }

    default ResponseEntity<Boolean> ok(Boolean bool) {
        return ResponseEntity.ok(bool);
    }

    default ResponseEntity<T> ok(T t) {
        return ResponseEntity.ok(t);
    }


}
