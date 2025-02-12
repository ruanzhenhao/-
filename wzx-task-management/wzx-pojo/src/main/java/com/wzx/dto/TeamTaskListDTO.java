package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamTaskListDTO {
    private int id;
    private String taskName;
    private String demand;
    private String status;
}
