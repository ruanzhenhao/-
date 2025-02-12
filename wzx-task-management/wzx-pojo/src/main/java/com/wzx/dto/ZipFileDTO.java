package com.wzx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZipFileDTO
{
    private List<String> fileNms; // 文件名称
    private List<ByteArrayOutputStream> streams; // 文件流
    private String zipFileNm; // 压缩文件名称
}
