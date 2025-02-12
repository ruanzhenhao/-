package com.wzx.service.impl;

import com.wzx.constant.MessageConstant;
import com.wzx.dto.ChangePasswordDTO;
import com.wzx.dto.UserHeadshotDTO;
import com.wzx.dto.UserinfoChangeDTO;
import com.wzx.exception.AccountRepeatException;
import com.wzx.exception.BaseException;
import com.wzx.exception.PasswordEditFailedException;
import com.wzx.mapper.UserMapper;
import com.wzx.service.UserService;
import com.wzx.vo.UserInfoChangeVO;
import com.wzx.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void changeHeadshot(UserHeadshotDTO userHeadshotDTO) {
        userMapper.changeHeadshot(userHeadshotDTO);
    }

    @Override
    public UserInfoVO getUserInfo(int id) {
        UserInfoVO userInfoVO = userMapper.getUserInfo(id);
        log.info("获取用户信息，userInfoVO={}", userInfoVO);
        return userInfoVO;
    }

    @Override
    public void changeInfo(UserInfoChangeVO userInfoVO) {
        UserinfoChangeDTO userinfoChangeDTO = new UserinfoChangeDTO();
        BeanUtils.copyProperties(userInfoVO, userinfoChangeDTO);
        UserinfoChangeDTO rawUser= userMapper.findUser(userinfoChangeDTO.getId());
        log.info("Raw ID Number: {}", rawUser.getIdNumber());
        log.info("New ID Number: {}", userinfoChangeDTO.getIdNumber());
        if (rawUser.getPhone().equals(userinfoChangeDTO.getPhone()))
        {
            userinfoChangeDTO.setPhone(null);
        }if (rawUser.getIdNumber().equals(userinfoChangeDTO.getIdNumber())) {

            userinfoChangeDTO.setIdNumber(null);
        }
        log.info("修改用户信息，userinfoChangeDTO={}", userinfoChangeDTO);
        userinfoChangeDTO.setUpdateTime(LocalDateTime.now());
        userMapper.changeInfo(userinfoChangeDTO);


    }

    @Override
    public void changePassword(ChangePasswordDTO password) {
//        查询是否和原密码相同
        String oldPassword= userMapper.getPassword(password.getId());
        if (!oldPassword.equals(password.getOldPassword())){
            throw new PasswordEditFailedException(MessageConstant.PASSWORD_ERROR);
        }if (oldPassword.equals(password.getNewPassword()))
        {
            throw new PasswordEditFailedException(MessageConstant.PASSWORD_REPEAT);
        }
        userMapper.changePassword(password);
    }
}
