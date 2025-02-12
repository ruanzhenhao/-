package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamReportDTO {
    private LocalDate beginDate;
    private LocalDate endDate;
    private Integer teamId;
}
