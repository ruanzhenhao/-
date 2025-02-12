package com.wzx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitMission {
    private Long taskId;
    private Long teamId;
    private Long userId;
    private String text;
    private List<File> files;
}
