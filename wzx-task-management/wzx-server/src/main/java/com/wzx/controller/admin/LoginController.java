package com.wzx.controller.admin;

import com.wzx.constant.JwtClaimsConstant;
import com.wzx.dto.UserLoginDTO;
import com.wzx.dto.UserRegisterDTO;
import com.wzx.entity.User;
import com.wzx.properties.JwtProperties;
import com.wzx.result.Result;
import com.wzx.service.loginService;
import com.wzx.utils.JwtUtil;
import com.wzx.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class LoginController {

    @Autowired
    private loginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("员工登录：{}", userLoginDTO);
        User user = loginService.login(userLoginDTO);

        if (user == null) {
            log.warn("登录失败：用户未找到");
            return Result.error("用户未找到或密码错误");
        }

        // 登录成功后，生成jwt令牌
        log.info("用户登录成功，开始生成jwt令牌"+user.getId());
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());

        String token = jwtUtil.createJWT(claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .img(user.getImg())
                .token(token)
                .build();

        log.info("用户登录成功：{}", userLoginVO);
        return Result.success(userLoginVO);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册：{}", userRegisterDTO);
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        loginService.register(user);
        return Result.success("注册成功");
    }
}