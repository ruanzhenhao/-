package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserinfoChangeDTO
{
    private Long id;

    private String img;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

    private String email;

    private String hobby;

    private LocalDateTime updateTime;

}
