package com.wzx.service;

import com.wzx.dto.ChangePasswordDTO;
import com.wzx.dto.UserHeadshotDTO;
import com.wzx.vo.UserInfoChangeVO;
import com.wzx.vo.UserInfoVO;

public interface UserService {
    void changeHeadshot(UserHeadshotDTO userHeadshotDTO);

    UserInfoVO getUserInfo(int id);

    void changeInfo(UserInfoChangeVO userInfoVO);

    void changePassword(ChangePasswordDTO password);
}
