package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTaskVO
{
    private Long id;
    private Long loginUserId;
    private Long TeamId;
    private String title;
    private String contend;
    private String teamName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isRemind;
    private String remindContent;
}
