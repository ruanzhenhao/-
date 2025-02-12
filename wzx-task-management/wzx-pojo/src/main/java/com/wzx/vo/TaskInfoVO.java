package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskInfoVO {
    private Long taskId;
    private String createUser;
    private String taskName;
    private String demand;
    private LocalDate createTime;
    private LocalDate endTime;
    private Long createUserId;

}
