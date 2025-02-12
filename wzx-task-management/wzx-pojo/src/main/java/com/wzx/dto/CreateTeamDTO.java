package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTeamDTO
{
    private String teamName;
    private List<String> userNames;
    private String introduction;
    private LocalDate validityTime;
    private Long invitationCode;
    private Long loginUserId;
    private LocalDate createTime;
    private LocalDate updateTime;
    private Long teamId;

}
