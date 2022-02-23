package com.love.sports.play.service;

import com.love.sports.dubbo.dto.UserDTO;
import com.love.sports.dubbo.service.UserService;
import com.love.sports.play.entity.PlayUser;
import com.love.sports.play.entity.PlayUserRepository;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author WangXinzhu
 * @date 2022/2/16 20:31
 * @since 1.0
 */
@Service
public class PlayUserService {

    @DubboReference(check = false)
    private UserService userService;

    @Resource
    private PlayUserRepository playUserRepository;

    public UserDTO getUserInfo(String uuid) {
        UserDTO one = userService.getOne(uuid);
        return one;
    }

    @GlobalTransactional
    public void test(){
        PlayUser playUser = new PlayUser();
        playUser.setId(UUID.randomUUID().toString());

        userService.testSeata();

        playUserRepository.save(playUser);
        Assert.hasText("","test");
    }
}
