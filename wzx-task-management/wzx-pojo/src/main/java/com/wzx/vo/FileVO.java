package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileVO
{
    private Long taskUserId;
    private String fileName;
    private String filePath;

}
