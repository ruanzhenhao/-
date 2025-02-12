package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamPageDTO
{
    private int id;
    private String username;
    private String sex;
    private int teamId;
    private String phone;
    private int role;
    private String address;
    private int page;
    private int pageSize;
    private int start;


}
