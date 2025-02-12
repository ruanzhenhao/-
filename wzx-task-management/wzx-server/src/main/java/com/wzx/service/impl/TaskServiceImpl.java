package com.wzx.service.impl;

import com.wzx.dto.SubmitMissionDTO;
import com.wzx.dto.TaskReportDTO;
import com.wzx.dto.TaskTypeDTO;
import com.wzx.entity.SubmitMission;
import com.wzx.exception.PrivilegeException;
import com.wzx.exception.TeamException;
import com.wzx.mapper.TaskMapper;
import com.wzx.mapper.TeamMapper;
import com.wzx.service.MessageService;
import com.wzx.service.TaskService;
import com.wzx.utils.DateUtils;
import com.wzx.vo.*;
import io.lettuce.core.sentinel.api.StatefulRedisSentinelConnectionExtensionsKt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService
{
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private MessageService messageService;

    private final String uploadDir="uploads/";

    @Override
//    获取未完成任务数量
    public Integer getNoTask(int id) {
        TaskTypeDTO taskTypeDTO = new TaskTypeDTO();
        taskTypeDTO.setUserId(Long.valueOf(id));
        taskTypeDTO.setType(1);
        log.info("未完成任务"+taskTypeDTO.toString());

        return taskMapper.getNoTaskType(taskTypeDTO);

    }
    //    获取任务状态统计
    @Override
    public List<TaskTypeVO> getTaskType(int id,Long loginUser) {
        TaskTypeDTO taskTypeDTO = new TaskTypeDTO();
        taskTypeDTO.setTeamId(id);
        taskTypeDTO.setUserId(loginUser);
        log.info("获取用户id："+taskTypeDTO.toString());
        List<TaskTypeVO> taskTypeVOList =new ArrayList<>();
        for (int i = 1; i <= 4; i++)
        {
            taskTypeDTO.setType(i);
            taskTypeVOList.add(auto(taskTypeDTO));
        }

        return taskTypeVOList;
    }

    @Override
    public TaskReportVO getComplete(int id)
    {
        List<LocalDate> localDates = DateUtils.dateService( LocalDate.now().minusDays(7),LocalDate.now());
        List<String> strings = DateUtils.dateChangeString(localDates);
        TaskReportVO taskReportVO = new TaskReportVO();
        TaskReportDTO taskReportDTO = new TaskReportDTO();
        List<Integer> values = new ArrayList<>();
        taskReportVO.setDays(strings);
        for (LocalDate localDate : localDates) {
            taskReportDTO.setUserId(id);
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            taskReportDTO.setBegin(beginTime);
            taskReportDTO.setEnd(endTime);
            log.info("获取时间："+taskReportDTO.toString());
            Integer value = taskMapper.getComplete(taskReportDTO);
            log.info("获取用户id："+ value);
            if (value==null || value==0)
            {
                values.add(0);
            }else
            {
                values.add(value);
            }
            taskReportVO.setValues(values);

        }
        return taskReportVO;
    }

    @Override
    public List<TeamByTaskVO> getTaskByTeam(Long loginUserId,String teamName) {
//        找出用户的所有团队
        List<Long> teamIdList = taskMapper.getTeamId(loginUserId);
//        找出所有进行中的任务的任务的团队
        List<Long> validTeamList = new ArrayList<>();
        for (Long id : teamIdList) {
            Long num = taskMapper.getValidTeamID(id,loginUserId);
            if (num == 0)
            {
                continue;
            }
            validTeamList.add(id);
        }
        List<TeamByTaskVO> teamByTaskVOList = new ArrayList<>();
        for (Long id : validTeamList) {
            Long createTeamUserId = taskMapper.getBossID(id);
            String boss = teamMapper.getBoss(createTeamUserId);
            TeamByTaskVO teamByTaskVO = taskMapper.getCreateTeamUserId(id);
            teamByTaskVO.setBoss(boss);
            teamByTaskVO.setTeamId(id);
            List<String> nullList = new ArrayList<>();
            nullList.add("");
            teamByTaskVO.setTaskList(nullList);

            teamByTaskVOList.add(teamByTaskVO);

        }
        if (teamName != null && !teamName.isEmpty())
        {

            TeamByTaskVO selectTeam = null;
            for (TeamByTaskVO teamByTaskVO : teamByTaskVOList) {
                if (teamName.equals(teamByTaskVO.getTeamName()))
                {
                    selectTeam = teamByTaskVO;
                }
            }
            if (selectTeam == null)
            {
                teamByTaskVOList.clear();
            }
            teamByTaskVOList.clear();
            teamByTaskVOList.add(selectTeam);

        }

        return teamByTaskVOList;

    }

    @Override
    public List<TaskByTeamVO> getTaskByID(Long teamId,Long loginUserId) {
        List<Long> taskIDs= taskMapper.getTaskID(teamId,loginUserId);
        List<TaskByTeamVO> taskByTeamVOList = new ArrayList<>();
        for (Long taskID : taskIDs) {
            TaskByTeamVO taskVO = taskMapper.getTaskByTeam(taskID);
            Long createUserId = taskMapper.getCreateUserId(taskID);
            String createUser = taskMapper.getCreateUser(createUserId);
            taskVO.setCreateUserName(createUser);
            taskVO.setTaskType("进行中");
            taskVO.setTaskId(taskID);
            taskByTeamVOList.add(taskVO);


        }
        return taskByTeamVOList;
    }

    @Override
    public TaskInfoVO getTaskInfo(Long loginUserId, Long taskId) {

        TaskInfoVO taskInfoVO = taskMapper.getTaskInfo(taskId);
        taskInfoVO.setCreateUser(taskMapper.getUserName(taskInfoVO.getCreateUserId()));
        taskInfoVO.setTaskId(taskId);
        log.info("获取用户id："+taskInfoVO);


        return taskInfoVO;
    }

    @Override
    public List<FileVO> uploadFile(MultipartFile[] files) {
        List<FileVO> list = new ArrayList<>();
        try {
            // 检查文件数组是否为空
            if (files != null && files.length > 0) {
                // 创建上传目录
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs(); // 创建目录（包括父级目录）
                }

                // 遍历上传的文件
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        // 获取原始文件名和扩展名
                        String originalFilename = file.getOriginalFilename();
                        String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
                        String newFileName = UUID.randomUUID().toString() + extension; // 生成新的文件名
                        String fileUrl = uploadDir + newFileName; // 文件保存路径

                        // 保存文件
                        File newFile = new File(fileUrl);
                        try (FileOutputStream fos = new FileOutputStream(newFile)) {
                            fos.write(file.getBytes());
                        }

                        // 添加文件信息到返回列表
                        FileVO fileVO = new FileVO();
                        fileVO.setFileName(newFileName);
                        fileVO.setFilePath(fileUrl); // 可以根据需要调整为返回的访问 URL
                        list.add(fileVO);
                    }
                }
            }
        } catch (IOException e) {
            throw new TeamException("文件上传失败");
        }
        return list; // 返回上传的文件信息列表
    }

    @Override
    public void submitMission(SubmitMissionDTO submitMissionDTO, Long loginUserId) {
        submitMissionDTO.setUserId(loginUserId);
        taskMapper.submitMission(submitMissionDTO);
        List<FileVO> fileVOList = submitMissionDTO.getFiles();
        for (FileVO fileVO : fileVOList) {
            fileVO.setTaskUserId(loginUserId);
        }
        taskMapper.uploadFile(fileVOList);
        log.info(submitMissionDTO.toString());
    }

    @Override
    public List<TaskListVO> taskList(Long loginUserId) {
//        获取自己布置的任务
        List<TaskListVO> taskListVOList= taskMapper.getSetTask(loginUserId);
        return taskListVOList;
    }

    @Override
    public List<TaskTableVO> getTaskTable(Long loginUserId, Long taskId) {
        return taskMapper.getTaskTable(loginUserId,taskId);
    }

    @Override
    public void type(Long loginUserId, Long taskId ,int type) {
//        验证
        Long validID = taskMapper.getValidUserID(taskId);
        if (!Objects.equals(validID, loginUserId))
        {
            throw new PrivilegeException("没有权限,禁止访问");
        }
        else
        {
            taskMapper.changeType(taskId,type);
        }

    }

    @Override
    public SubmitTaskInfoVO getSubmitInfo(Long loginUserId, Long id) {
        String text = taskMapper.getSubmitInfo(loginUserId,id);
        List<Long> ids = taskMapper.getSubmitInfoIds(id);
        return new SubmitTaskInfoVO(text,ids);
    }

    @Override
    public List<String> getFilePath(Long loginUserId, List<Long> ids) {
        return taskMapper.getFilePath(ids);
    }




    private TaskTypeVO auto( TaskTypeDTO taskTypeDTO)
    {
        log.info("获取用户id："+taskTypeDTO.toString());
        if (taskTypeDTO.getType() == 1)
        {
            return new TaskTypeVO(taskMapper.getTaskType(taskTypeDTO),TaskTypeVO.NO_COMPLETED);
        }
        else if (taskTypeDTO.getType() == 2) {
            return new TaskTypeVO(taskMapper.getTaskType(taskTypeDTO),TaskTypeVO.COMPLETED);
        }
        else if (taskTypeDTO.getType() == 3) {
            return new TaskTypeVO(taskMapper.getTaskType(taskTypeDTO),TaskTypeVO.ONGOING);
        }
        else
        {
            return new TaskTypeVO(taskMapper.getTaskType(taskTypeDTO),TaskTypeVO.TO_BE_REVIEWED);
        }
    }

}
