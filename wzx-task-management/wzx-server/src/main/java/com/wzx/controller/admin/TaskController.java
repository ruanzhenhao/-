package com.wzx.controller.admin;

import com.aliyuncs.http.HttpRequest;
import com.wzx.dto.FilesDTO;
import com.wzx.dto.SubmitMissionDTO;
import com.wzx.dto.ZipFileDTO;
import com.wzx.exception.TaskException;
import com.wzx.result.Result;
import com.wzx.service.TaskService;
import com.wzx.utils.JwtUtil;
import com.wzx.utils.ZipUtil;
import com.wzx.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/admin/home/task")
@Slf4j
public class TaskController
{

    @Autowired
    private TaskService taskService;
    @Autowired
    private ZipUtil zipUtil;


//    获取未完成任务数量
    @PostMapping("/noTask")
    public Result<Integer> getNoTask(int id)
    {
        return Result.success(taskService.getNoTask(id));
    }

//    获取任务状态统计
    @PostMapping("/taskTypeList")
    public Result<List<TaskTypeVO>> getTaskType(int id,@RequestParam Long loginUserId)
    {

        log.info("获取用户id："+id);
        return Result.success(taskService.getTaskType(id,loginUserId));
    }
//    7日内完成任务的统计折线图
    @PostMapping("/complete")
    public Result<TaskReportVO> getComplete(int id)
    {
        log.info("获取用户id："+id);
        return Result.success(taskService.getComplete(id));
    }
//    获取有任务的团队
    @PostMapping("/getTaskByTeam")
    public Result<List<TeamByTaskVO>> getTaskByTeam
    (@RequestParam Long loginUserId,String teamName)
    {
        return Result.success(taskService.getTaskByTeam(loginUserId,teamName));
    }
//    通过团队id找到任务
    @GetMapping("/getTaskByID")
    public Result<List<TaskByTeamVO>> getTaskByID(Long teamId,@RequestParam Long loginUserId)
    {
        return Result.success(taskService.getTaskByID(teamId,loginUserId));
    }
    @GetMapping("/getTaskInfo")
    public Result<TaskInfoVO> getTaskInfo(@RequestParam Long loginUserId , Long taskId )
    {
        return Result.success(taskService.getTaskInfo(loginUserId,taskId));
    }

    @PostMapping("/submitMission")
    public Result submitMission(@RequestBody SubmitMissionDTO submitMissionDTO, @RequestParam Long loginUserId)
    {
        log.info("提交任务："+submitMissionDTO);
        taskService.submitMission(submitMissionDTO,loginUserId);
        return Result.success();
    }
    @PostMapping("/upload")
    public Result<List<FileVO>> uploadFile(@ModelAttribute MultipartFile[] fileList)
    {

        return Result.success(taskService.uploadFile(fileList));
    }

    @GetMapping("/taskList")
    public Result<List<TaskListVO>> taskList(@RequestParam Long loginUserId)
    {
        return Result.success(taskService.taskList(loginUserId));
    }

    @PostMapping("/getTaskTable")
    public Result<List<TaskTableVO>> getTaskTable(@RequestParam Long loginUserId, Long taskId)
    {
        return Result.success(taskService.getTaskTable(loginUserId,taskId));
    }

    @PostMapping("/success")
    public Result success(@RequestParam Long loginUserId, Long taskId ,int type)
    {
        taskService.type(loginUserId,taskId,type);
        return Result.success();
    }

    @PostMapping("return")
    public Result returnTask(@RequestParam Long loginUserId, Long taskId ,int type)
    {
        taskService.type(loginUserId,taskId,type);
        return Result.success();
    }
    @PostMapping("getSubmitInfo")
    public Result<SubmitTaskInfoVO> getSubmitInfo(@RequestParam Long loginUserId, Long id)
    {
        return Result.success(taskService.getSubmitInfo(loginUserId,id));
    }
    @PostMapping("download")
    public void download(@RequestParam Long loginUserId, @RequestBody FilesDTO fileIds, HttpServletResponse response) throws TaskException, IOException {
        List<String> filePaths = taskService.getFilePath(loginUserId, fileIds.getFileIds());

        // 创建 ZipFileDTO
        ZipFileDTO zipFileDTO = new ZipFileDTO();
        List<String> fileNames = new ArrayList<>();
        List<ByteArrayOutputStream> streams = new ArrayList<>();
        List<String> missingFiles = new ArrayList<>();

        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("找到文件: " + file.getAbsolutePath());
                fileNames.add(file.getName());

                // 获取文件的字节流
                ByteArrayOutputStream byteArrayOutputStream = zipUtil.getByteArrayOutputStream(filePath);
                if (byteArrayOutputStream != null && byteArrayOutputStream.size() > 0) {
                    streams.add(byteArrayOutputStream);
                } else {
                    System.out.println("文件流为空: " + file.getAbsolutePath());
                    missingFiles.add(file.getName());
                }
            } else {
                System.out.println("未找到文件: " + file.getAbsolutePath());
                missingFiles.add(file.getName());
            }
        }

        if (!missingFiles.isEmpty()) {
            throw new FileNotFoundException("未找到文件: " + String.join(", ", missingFiles));
        }

        zipFileDTO.setFileNms(fileNames);
        zipFileDTO.setStreams(streams);
        zipFileDTO.setZipFileNm("files.zip");

        // 设置响应头，确保支持中文文件名
        String zipFileName = URLEncoder.encode(zipFileDTO.getZipFileNm(), "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");
        response.setContentType("application/zip");

        try {
            // 使用 ZipUtil 下载 ZIP 文件
            zipUtil.downloadZipFile(response, zipFileDTO);
        } catch (Exception e) {
            // 处理异常并记录详细信息
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "文件下载失败: " + e.getMessage());
        }
    }

}

