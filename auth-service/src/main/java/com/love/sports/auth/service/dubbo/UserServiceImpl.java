package com.love.sports.auth.service.dubbo;

import com.love.sports.auth.entity.model.SysUserInfo;
import com.love.sports.auth.service.SysUserInfoService;
import com.love.sports.dubbo.dto.UserDTO;
import com.love.sports.dubbo.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;

import javax.annotation.Resource;

/**
 * @author WangXinzhu
 * @date 2022/2/16 20:17
 * @since 1.0
 */
@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserInfoService sysUserInfoService;

    @Override
    public UserDTO getOne(String uuid) {
        SysUserInfo sysUserInfo = sysUserInfoService.findById(uuid);
        return UserDTO.builder()
                .id(sysUserInfo.getId())
                .username(sysUserInfo.getUsername())
                .nickName(sysUserInfo.getNickName())
                .email(sysUserInfo.getEmail())
                .phoneNumber(sysUserInfo.getPhoneNumber())
                .sex(sysUserInfo.getSex().name())
                .avatarPath(sysUserInfo.getAvatarPath())
                .build();
    }
}
