package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteTeamUserDTO {
    private Long LoginUserId;
    private String teamName;
    private Long deleteUserId;
}
