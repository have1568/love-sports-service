package com.love.sports.play.service;

import com.love.sports.dubbo.dto.UserDTO;
import com.love.sports.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author WangXinzhu
 * @date 2022/2/16 20:31
 * @since 1.0
 */
@Service
public class PlayUserService {

    @DubboReference
    private UserService userService;

    public UserDTO getUserInfo(String uuid) {
        UserDTO one = userService.getOne(uuid);
        return one;
    }
}
