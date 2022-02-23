package com.love.sports.play.service;

import com.love.sports.dubbo.dto.UserDTO;
import com.love.sports.play.LoveSportsPlayApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangXinzhu
 * @date 2022/2/16 20:36
 * @since 1.0
 */
class PlayUserServiceTest extends LoveSportsPlayApplicationTests {

    @Resource
    private PlayUserService playUserService;

    @Test
    void getUserInfo() {
        UserDTO userInfo = playUserService.getUserInfo("1");
        System.out.println(userInfo);
    }

    @Commit
    @Test
    void test1() {
        playUserService.test();
    }
}
