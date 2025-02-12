package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateTeamNameDTO {
    private Long loginUserId;
    private String teamName;
    private String newTeamName;
}
