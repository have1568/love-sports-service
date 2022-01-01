package com.love.sports.user.utils;

import com.love.sports.user.entity.model.SysUserInfo;
import com.love.sports.utils.ObjectUtils;
import org.junit.jupiter.api.Test;

public class ObjectUtilsTest {

    @Test
    void test(){
        SysUserInfo userInfo = new SysUserInfo();
        System.out.println(ObjectUtils.isAllFieldNull(userInfo));

    }
}
