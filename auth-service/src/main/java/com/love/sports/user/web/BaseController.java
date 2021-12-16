package com.love.sports.user.web;

import com.love.sports.user.common.Res;
import org.springframework.data.domain.Page;

public interface BaseController<T> {

    default Res<Void> ok() {
        return Res.success();
    }

    default Res<Page<T>> ok(Page<T> page) {
        return Res.success(page);
    }

    default Res<Boolean> ok(Boolean bool) {
        return Res.success(bool);
    }

    default Res<T> ok(T t) {
        return Res.success(t);
    }


}
