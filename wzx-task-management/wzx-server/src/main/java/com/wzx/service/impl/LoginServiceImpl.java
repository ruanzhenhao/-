package com.wzx.service.impl;

import com.wzx.constant.MessageConstant;
import com.wzx.dto.UserLoginDTO;
import com.wzx.entity.User;
import com.wzx.exception.AccountRepeatException;
import com.wzx.exception.AccountNotFoundException;
import com.wzx.exception.PasswordErrorException;
import com.wzx.mapper.LoginMapper;
import com.wzx.service.loginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class LoginServiceImpl implements loginService {

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 员工登录
     *
     * @param userLoginDTO
     * @return
     */
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = loginMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }



        //3、返回实体对象
        return user;
    }

    @Override
    public void register(User user)
    {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        try {
            loginMapper.register(user);
        }catch (Exception e)
        {
            throw new AccountRepeatException(MessageConstant.ACCOUNT_REPEAT);
        }
        log.info("新增员工成功");
    }

}
