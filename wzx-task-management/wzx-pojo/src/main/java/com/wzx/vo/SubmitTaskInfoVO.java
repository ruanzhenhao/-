package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTaskInfoVO {
//    正文
    private String content;
//    下载文件id
    private List<Long> fileIds;
}
