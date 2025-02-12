package com.wzx.controller.admin;


import com.wzx.dto.ChangePasswordDTO;
import com.wzx.dto.UserHeadshotDTO;
import com.wzx.result.Result;
import com.wzx.service.UserService;
import com.wzx.vo.UserInfoChangeVO;
import com.wzx.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/home/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/headshot")
    public Result changeHeadshot(@RequestBody UserHeadshotDTO userHeadshotDTO) {
        log.info("修改用户头像，userHeadshotDTO={}", userHeadshotDTO);
        userService.changeHeadshot(userHeadshotDTO);
        return Result.success();
    }

    @GetMapping
    public Result<UserInfoVO> getUserInfo(int id)
    {
        return Result.success(userService.getUserInfo(id));

    }

    @PutMapping("/changeInfo")
    public Result changeInfo(@RequestBody UserInfoChangeVO userInfoVO)
    {
        log.info("修改用户信息，userInfoVO={}", userInfoVO);
        userService.changeInfo(userInfoVO);
        return Result.success();
    }
    @PutMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePasswordDTO password)
    {
        log.info(password.toString());
        userService.changePassword(password);
        return Result.success();
    }


}
