package com.wzx.service;

import com.wzx.dto.UserLoginDTO;
import com.wzx.entity.User;

public interface loginService {

    /**
     * 员工登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    void register(User user);
}
