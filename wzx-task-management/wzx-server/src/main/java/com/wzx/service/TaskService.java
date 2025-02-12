package com.wzx.service;

import com.wzx.dto.SubmitMissionDTO;
import com.wzx.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {

    Integer getNoTask(int id);

    List<TaskTypeVO> getTaskType(int id,Long loginUser);

    TaskReportVO getComplete(int id);

    List<TeamByTaskVO> getTaskByTeam(Long loginUserId,String teamName);

    List<TaskByTeamVO> getTaskByID(Long teamId,Long loginUserId);

    TaskInfoVO getTaskInfo(Long loginUserId, Long taskId);

    List<FileVO> uploadFile(MultipartFile[] file);


    void submitMission(SubmitMissionDTO submitMissionDTO, Long loginUserId);

    List<TaskListVO> taskList(Long loginUserId);

    List<TaskTableVO> getTaskTable(Long loginUserId, Long taskId);

    void type(Long loginUserId, Long taskId ,int type);

    SubmitTaskInfoVO getSubmitInfo(Long loginUserId, Long id);

    List<String> getFilePath(Long loginUserId, List<Long> ids);


}
