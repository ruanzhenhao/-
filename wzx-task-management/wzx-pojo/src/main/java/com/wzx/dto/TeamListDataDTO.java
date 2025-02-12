package com.wzx.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamListDataDTO {
    private Long LoginUserId;
    private int page;
    private int pageSize;
    private int start;
}
