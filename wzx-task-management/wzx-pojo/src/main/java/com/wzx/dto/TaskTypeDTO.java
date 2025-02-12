package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskTypeDTO
{
    private int type;
    private Long userId;
    private int teamId;
}
