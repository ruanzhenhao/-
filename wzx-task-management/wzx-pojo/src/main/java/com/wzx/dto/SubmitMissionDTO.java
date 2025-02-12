package com.wzx.dto;

import com.wzx.vo.FileVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitMissionDTO
{
    private Long TeamId;
    private Long taskId;
    private List<FileVO> files;
    private String text;
    private Long userId;
}
