package com.wzx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private Long taskUserId;
    private String fileNames;
    private String fileUrls;

}
