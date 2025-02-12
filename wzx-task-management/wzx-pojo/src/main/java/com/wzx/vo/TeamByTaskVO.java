package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamByTaskVO
{
    private Long teamId;
    private String teamName;
    private String boss;
    private LocalDate createTime;
    private List<String> taskList;

}
