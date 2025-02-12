package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDTO
{



    private String username;

    private String name;

    private String password;

    private String phone;


    private String idNumber;




}
