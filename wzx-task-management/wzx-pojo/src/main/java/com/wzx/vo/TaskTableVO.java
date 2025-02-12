package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskTableVO
{
    private Long id;
    private String teamName;
    private String userName;
    private String taskName;
    private LocalDate submitTime;

}
