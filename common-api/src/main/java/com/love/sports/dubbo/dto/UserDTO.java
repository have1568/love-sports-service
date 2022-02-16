package com.love.sports.dubbo.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WangXinzhu
 * @date 2022/2/16 19:46
 * @since 1.0
 */
@Data
@Builder
public class UserDTO implements Serializable {

    private String id;

    private String username;

    private String nickName;

    private String email;

    private String phoneNumber;

    private String sex;

    private String avatarPath;
}
