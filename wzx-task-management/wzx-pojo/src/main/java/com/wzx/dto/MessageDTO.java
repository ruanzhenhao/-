package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO
{
    private String title;
    private String content;
    private List<Long> userIds;
    private LocalDate crateTime;
    private Long CREATE_USER_ID;
//    1.提醒任务，任务退回，任务完成，任务待批改 2.需要按钮的 加入 团队
    private Long type;
}
