package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamListDataVO
{
//    团队名
    private String teamName;
//  团队简介
    private String introduction;
}
