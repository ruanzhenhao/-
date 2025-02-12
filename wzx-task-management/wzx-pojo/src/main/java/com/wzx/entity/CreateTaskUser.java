package com.wzx.entity;

import com.wzx.vo.CreateTaskVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTaskUser {
    private Long taskId;
    private Long loginUserId;
    private Long teamId;
    private List<Long> userIds;
    private CreateTaskVO createTaskVO;
}
