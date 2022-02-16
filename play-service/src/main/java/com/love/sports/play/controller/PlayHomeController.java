package com.love.sports.play.controller;

import com.love.sports.controller.BaseController;
import com.love.sports.play.service.PlayUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author WangXinzhu
 * @date 2022/2/16 22:16
 * @since 1.0
 */
@RestController
@RequestMapping("/api/home")
public class PlayHomeController implements BaseController<Object> {


    @Resource
    private PlayUserService playUserService;

    @GetMapping("/userInfo")
    public ResponseEntity<Object> userInfo(@RequestParam final String userId) {
        return ok(playUserService.getUserInfo(userId));
    }
}
