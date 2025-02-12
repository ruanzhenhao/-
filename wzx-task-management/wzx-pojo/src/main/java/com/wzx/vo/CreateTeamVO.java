package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamVO
{
    private String teamName;
    private List<String> invitedUsers;
    private String introduction;
    private LocalDate validityTime;
    private Long loginUserId;
}
