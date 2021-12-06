package com.love.sports.user.web;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BaseController<T> {

    default ResponseEntity<Void> ok(){
        return ResponseEntity.ok().build();
    }

    default ResponseEntity<Page<T>> ok(Page<T> page){
        return ResponseEntity.ok(page);
    }

    default ResponseEntity<Boolean> ok(Boolean bool){
        return ResponseEntity.ok(bool);
    }

    default ResponseEntity<T> ok(T t){
        return ResponseEntity.ok(t);
    }


}
