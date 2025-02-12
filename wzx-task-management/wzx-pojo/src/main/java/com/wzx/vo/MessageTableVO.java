package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageTableVO
{
    private Long id;
    private String content;
    private LocalDateTime createdTime;
    private String title;
    private String sendUserName;
    private String type;
    private Long teamID;
}
