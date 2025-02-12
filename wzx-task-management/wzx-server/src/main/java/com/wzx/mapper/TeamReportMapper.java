package com.wzx.mapper;

import com.wzx.dto.*;
import com.wzx.entity.CreateTaskUser;
import com.wzx.vo.AdminVO;
import com.wzx.vo.CreateTaskVO;
import com.wzx.vo.OrdinaryVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TeamReportMapper {
    int getHumanCount(Long teamId , String sex);

    List<String> getTop10Name(Long id, Long teamId);

    int getTop10Value(int id);

    Long selectTeamID(String teamName);

    List<Integer> getTop10Id(Long loginUserId, Long teamId);

    Long getTeamId(String teamName);

    List<Integer> getTaskList(Long teamId);

    String getTaskName(int id);

    int getCount(int taskId, int type);

    int getNoTask(Long teamId, Long loginUserId);

    int getCountTask(Long teamId, Long loginUserId);

    String getIntroduction(Long teamId);

    List<Long> getUserId(Long teamId);

    TeamUserListDTO getUser(Long id);

    List<Long> getTeamTaskList(Long teamId, Long loginUserId);

    TeamTaskListDTO getTaskInfo(Long taskId);

    String getTaskStatus(Long taskId,Long loginUserId);

    int getUserRole(Long loginUserId, Long teamId);

    void updateTeamName(UpdateTeamNameDTO updateTeamNameDTO);

    void deleteTeam(Long teamId);

    void deleteTeamUser(Long teamId);

    void deleteTeamTask(Long teamId);

    void quitTeam(Long loginUserId, Long teamId);

    void quitTeamTask(Long loginUserId, Long teamId);

    void deleteUser(Long deleteUserId, Long teamId);

    void deleteUserTask(Long deleteUserId, Long teamId);

    Long getInvitationCode(Long teamId, LocalDate now);

    void changeIntroduction(String introduction , Long teamId);



    List<Long> getAdmin(Long teamId);

    List<Long> getOrdinary(Long teamId);

    AdminVO getUserData(Long admin);

    OrdinaryVO getOrdinaryData(Long ordinary);

    void transferBoss(Long teamId, Long transferUserId);

    void changeOrdinary(Long teamId, Long loginUserId);

    void setAdmin(SetAdminDTO setAdminDTO);

    void removeAdmin(RemoveAdminDTO removeAdminDTO);

    long createTask(CreateTaskVO createTask);

    void createTaskUser(CreateTaskUser createTaskUser);
}
