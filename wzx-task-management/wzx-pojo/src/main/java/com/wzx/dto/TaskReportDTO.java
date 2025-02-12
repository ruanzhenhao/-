package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskReportDTO {
    private Integer userId;
    private LocalDateTime begin;
    private LocalDateTime end;
}
