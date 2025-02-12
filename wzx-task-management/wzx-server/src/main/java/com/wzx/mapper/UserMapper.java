package com.wzx.mapper;

import com.wzx.dto.ChangePasswordDTO;
import com.wzx.dto.TeamPageDTO;
import com.wzx.dto.UserHeadshotDTO;
import com.wzx.dto.UserinfoChangeDTO;
import com.wzx.entity.User;
import com.wzx.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void changeHeadshot(UserHeadshotDTO userHeadshotDTO);

    UserInfoVO getUserInfo(int id);

    void changeInfo(UserinfoChangeDTO userInfoVO);

    UserinfoChangeDTO findUser(Long userinfoChangeDTO);

    String getPassword(int password);

    void changePassword(ChangePasswordDTO password);

    User getUser(TeamPageDTO userId);

    int selectUser(String userName);

    String getLoginUser(Long loginUserId);
}
