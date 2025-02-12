package com.wzx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMessage
{
    private Long id;
    private List<Long> userID;
    private String content;
    private String title;
    private int type;
    private Long teamID;
    private LocalDate crateTime;
    private Long createUserID;
    private String createUserName;
}
