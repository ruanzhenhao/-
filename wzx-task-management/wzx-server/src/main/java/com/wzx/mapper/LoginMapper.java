package com.wzx.mapper;

import com.wzx.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */

    User getByUsername(String username);

    void register(User user);
}
