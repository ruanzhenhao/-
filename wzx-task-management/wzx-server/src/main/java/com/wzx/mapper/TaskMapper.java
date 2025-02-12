package com.wzx.mapper;

import com.wzx.dto.SubmitMissionDTO;
import com.wzx.dto.TaskReportDTO;
import com.wzx.dto.TaskTypeDTO;
import com.wzx.entity.File;
import com.wzx.entity.SubmitMission;
import com.wzx.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {

    Integer getNoTask(int id);

    int getTaskType(TaskTypeDTO taskTypeDTO);

    Integer getComplete(TaskReportDTO taskReportDTO);

    Integer getNoTeamTask(int teamId, Integer userId);

    Integer getNoTaskType(TaskTypeDTO taskTypeDTO);

    List<Long> getTeamId(Long loginUserId);

    Long getValidTeamID(Long id, Long loginUserId);


    Long getBossID(Long id);

    TeamByTaskVO getCreateTeamUserId(Long id);


    List<Long> getTaskID(Long teamId,Long loginUserId);

    TaskByTeamVO getTaskByTeam(Long taskID);

    @Select("select username from `wyx-missions-management`.user where id = #{id}")
    String getCreateUser(Long id);

    @Select("select create_user_id from `wyx-missions-management`.task where id = #{taskId}")
    Long getCreateUserId(Long taskId);

    TaskInfoVO getTaskInfo(Long taskId);

    String getUserName(Long createUserId);

    void submitMission(SubmitMissionDTO submitMission);

    void uploadFile(List<FileVO> files);

    List<TaskListVO> getSetTask(Long loginUserId);

    List<TaskTableVO> getTaskTable(Long loginUserId, Long taskId);

    Long getValidUserID(Long taskId);

    void changeType(Long taskId, int type);

    String getSubmitInfo(Long loginUserId, Long id);

    List<Long> getSubmitInfoIds(Long loginUserId);

    List<String> getFilePath(List<Long> ids);
}

