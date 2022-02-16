package com.love.sports.dubbo.service;

import com.love.sports.dubbo.dto.UserDTO;

/**
 * @author WangXinzhu
 * @date 2022/2/16 19:47
 * @since 1.0
 */
public interface UserService {

    UserDTO getOne(String uuid);

}
