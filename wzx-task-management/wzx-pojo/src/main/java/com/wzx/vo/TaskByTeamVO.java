package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskByTeamVO
{
    private Long taskId;
    private String taskName;
    private String taskType;
    private String demand;
    private String createUserName;
    private LocalDate createTime;
    private LocalDate endTime;

                                                                                                                                                                                                                                                                                                                                                                                                                                                            

}
