package com.wzx.service;

import com.wzx.dto.*;
import com.wzx.vo.CreateTaskVO;
import com.wzx.vo.ManagementVO;

import java.util.List;
import java.util.Map;

public interface TeamReportService {
    List<Top10> getTop10(String teamName, Long loginUserId);

    List<TaskListDTO> getTaskList(String teamName);


    List<CompleteTaskDTO> getCompleteTask(int taskId);

    Map<String, Integer> getPercentage(String teamName);

    Map<String, Integer> getNoTask(String teamName,Long loginUserId);

    String getIntroduction(String teamName);

    List<TeamUserListDTO> getTeamUserList(String teamName);

    List<TeamTaskListDTO> getTeamTaskList(String teamName, Long loginUserId);
    void createTask(CreateTaskVO createTask, Long loginUserId);

    void updateTeamName(UpdateTeamNameDTO updateTeamNameDTO);

    void deleteTeam(Long loginUserId, String teamName);

    void quitTeam(Long loginUserId, String teamName);

    void deleteTeamUser(DeleteTeamUserDTO deleteTeamUserDTO);


    Long getInvitationCode(Long loginUserId, String teamName);

    void changeIntroduction(ChangeIntroductionDTO changeIntroductionDTO);

    ManagementVO managementUser(Long loginUserId, String teamName);

    void transferBoss(TransferBossDTO transferBossDTO);

    void setAdmin(SetAdminDTO setAdminDTO);

    void removeAdmin(RemoveAdminDTO removeAdminDTO);
}
