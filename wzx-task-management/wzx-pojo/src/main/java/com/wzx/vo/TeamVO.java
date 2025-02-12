package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamVO {
//    用户id
    private Integer userId;
//    头像
    private String img;
//    性别
    private String sex;
//    用户名
    private String username;
//    电话
    private String phone;
//    权限
    private String role;
//    未完成任务数
    private Integer noTask;
//    email
    private String email;
//    住址
    private String address;

}
