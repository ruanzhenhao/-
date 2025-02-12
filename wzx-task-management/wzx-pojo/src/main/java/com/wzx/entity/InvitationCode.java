package com.wzx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationCode {
    private Long invitationCode;
    private Long teamId;
    private LocalDate createTime;
    private LocalDate validityTime;
}
