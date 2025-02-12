package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskTypeVO {
    private int value;
    private String name;
    public static final String NO_COMPLETED = "未完成";
    public static final String COMPLETED = "已完成";
    public static final String TO_BE_REVIEWED = "待审核";
    public static final String ONGOING = "进行中";

}
