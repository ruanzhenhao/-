package com.wzx.service.impl;

import com.wzx.dto.*;
import com.wzx.entity.CreateTaskUser;
import com.wzx.entity.InvitationCode;
import com.wzx.exception.PrivilegeException;
import com.wzx.mapper.TeamMapper;
import com.wzx.mapper.TeamReportMapper;
import com.wzx.service.TeamReportService;
import com.wzx.service.TeamService;
import com.wzx.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j

public class TeamReportServiceImpl implements TeamReportService {
    @Autowired
    private TeamReportMapper teamReportMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Override
    public List<Top10> getTop10(String teamName, Long loginUserId) {
        Long teamId = teamReportMapper.selectTeamID(teamName);
        List<String> top10Name = teamReportMapper.getTop10Name(loginUserId,teamId);
        List<Integer> top10Id = teamReportMapper.getTop10Id(loginUserId,teamId);
        List<Top10> top10List = new ArrayList<>();
        for (int i = 0; i < top10Name.size(); i++) {
            int value = teamReportMapper.getTop10Value(top10Id.get(i));
            Top10 top10 = new Top10(value,top10Name.get(i));
            top10List.add(top10);
        }


        return top10List;
    }

    @Override
    public List<TaskListDTO> getTaskList(String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        log.info("teamId:"+teamId);
        List<Integer> taskListId = teamReportMapper.getTaskList(teamId);

        List<TaskListDTO> taskListDTOList = new ArrayList<>();
        for (Integer id : taskListId) {
            String taskName = teamReportMapper.getTaskName(id);
            taskListDTOList.add(new TaskListDTO(taskName,id));
        }

        return taskListDTOList;
    }

    @Override
    public List<CompleteTaskDTO> getCompleteTask(int taskId) {
        List<CompleteTaskDTO> completeTaskDTOS = new ArrayList<>();
        int type1= teamReportMapper.getCount(taskId,1);
        int type2= teamReportMapper.getCount(taskId,2);
        int type3= teamReportMapper.getCount(taskId,3);
        int type4= teamReportMapper.getCount(taskId,4);

        completeTaskDTOS.add(new CompleteTaskDTO(TaskTypeVO.NO_COMPLETED,type1));
        completeTaskDTOS.add(new CompleteTaskDTO(TaskTypeVO.COMPLETED,type2));
        completeTaskDTOS.add(new CompleteTaskDTO(TaskTypeVO.ONGOING,type3));
        completeTaskDTOS.add(new CompleteTaskDTO(TaskTypeVO.TO_BE_REVIEWED,type4));
        return completeTaskDTOS;
    }

    @Override
    public Map<String, Integer> getPercentage(String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        log.info("teamId:"+teamId);
        int male = teamReportMapper.getHumanCount(teamId,"男");
        int female = teamReportMapper.getHumanCount(teamId,"女");
        Map<String, Integer> map = new HashMap<>();
        map.put("男",male);
        map.put("女",female);
        return map;
    }

    @Override
    public Map<String, Integer> getNoTask(String teamName, Long loginUserId) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        int completeTask = teamReportMapper.getNoTask(teamId, loginUserId);
        int countTask = teamReportMapper.getCountTask(teamId,loginUserId);
        int noTask = countTask - completeTask;
        Map<String, Integer> map = new HashMap<>();
        map.put("进行中或任务待审批",noTask);
        map.put("已完成任务",completeTask);
        return map;

    }
    private int getRole(Long loginUserId , Long teamId)
    {
        log.info("loginUserId:"+loginUserId+"teamId:"+teamId);
        return teamReportMapper.getUserRole(loginUserId,teamId);
    }
    private void checkRole(int userRole,int validRole)
    {
        if (userRole < validRole)
        {
            throw new PrivilegeException("没有权限,禁止访问");
        }
    }

    @Override
    public String getIntroduction(String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);

        return teamReportMapper.getIntroduction(teamId);
    }

    @Override
    public List<TeamUserListDTO> getTeamUserList(String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        List<Long> userIdList = teamReportMapper.getUserId(teamId);
        List<TeamUserListDTO> teamUserListDTOS = new ArrayList<>();
        for (Long id : userIdList) {
            TeamUserListDTO teamUserListDTO = teamReportMapper.getUser(id);
            teamUserListDTOS.add(teamUserListDTO);

        }
        return teamUserListDTOS;
    }

    @Override
    public List<TeamTaskListDTO> getTeamTaskList(String teamName, Long loginUserId) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        List<Long> taskIds = teamReportMapper.getTeamTaskList(teamId,loginUserId);
        List<TeamTaskListDTO> teamTaskListDTOS = new ArrayList<>();
        for (Long taskId : taskIds) {
            TeamTaskListDTO teamTaskListDTO = teamReportMapper.getTaskInfo(taskId);
            teamTaskListDTO.setStatus(teamReportMapper.getTaskStatus(taskId,loginUserId));
            teamTaskListDTOS.add(teamTaskListDTO);
        }
        return teamTaskListDTOS;
    }
    @Override
    public void createTask(CreateTaskVO createTask, Long loginUserId) {
        long teamID = teamReportMapper.getTeamId(createTask.getTeamName());
        createTask.setTeamId(teamID);
        checkRole(getRole(loginUserId, teamID),2);
        teamReportMapper.createTask(createTask);
        List<Long> userIDs = teamMapper.getUserIds(teamID);
        userIDs.remove(loginUserId); // 直接删除，若存在则会删除
        CreateTaskUser createTaskUser = new CreateTaskUser();
        createTaskUser.setCreateTaskVO(createTask);
        createTaskUser.setLoginUserId(loginUserId);
        createTaskUser.setUserIds(userIDs);
        createTaskUser.setTaskId(createTask.getId());
        createTaskUser.setTeamId(teamID);
        teamReportMapper.createTaskUser(createTaskUser);
        if (createTask.getIsRemind())
        {

        }
    }
    @Override
    public void updateTeamName(UpdateTeamNameDTO updateTeamNameDTO) {
        Long teamId = teamReportMapper.getTeamId(updateTeamNameDTO.getTeamName());
        int userRole= getRole(updateTeamNameDTO.getLoginUserId(), teamId);
        checkRole(userRole,2);
        teamReportMapper.updateTeamName(updateTeamNameDTO);
    }

    @Override
    public void deleteTeam(Long loginUserId, String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        int userRole= getRole(loginUserId, teamId);
        checkRole(userRole,3);
        teamReportMapper.deleteTeam(teamId);
        teamReportMapper.deleteTeamUser(teamId);
        teamReportMapper.deleteTeamTask(teamId);

    }

    @Override
    public void quitTeam(Long loginUserId, String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        int userRole= getRole(loginUserId, teamId);
        if (userRole==3)
        {
            throw new PrivilegeException("组长不能退出团队，请先转让组长");
        }
        teamReportMapper.quitTeam(loginUserId,teamId);
        teamReportMapper.quitTeamTask(loginUserId,teamId);

    }

    @Override
    public void deleteTeamUser(DeleteTeamUserDTO deleteTeamUserDTO) {
        Long teamId = teamReportMapper.getTeamId(deleteTeamUserDTO.getTeamName());
        int userRole= getRole(deleteTeamUserDTO.getLoginUserId(), teamId);
        int deleteUserRole = getRole(deleteTeamUserDTO.getDeleteUserId(), teamId);
        if (deleteUserRole == 2)
        {
            throw new PrivilegeException("管理员不能踢出管理员");
        }
        checkRole(userRole,2);
        teamReportMapper.deleteUser(deleteTeamUserDTO.getDeleteUserId(),teamId);
        teamReportMapper.deleteUserTask(deleteTeamUserDTO.getDeleteUserId(),teamId);
    }

    @Override
    public Long getInvitationCode(Long loginUserId, String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        Long invitationCode = teamReportMapper.getInvitationCode(teamId, LocalDate.now());
        if (invitationCode==null)
        {
            InvitationCode invitationCodeData = new InvitationCode();
            invitationCodeData.setInvitationCode(System.currentTimeMillis());
            invitationCodeData.setCreateTime(LocalDate.now());
            invitationCodeData.setValidityTime(LocalDate.now().plusDays(30));
            invitationCodeData.setTeamId(teamId);
            teamMapper.addCode(invitationCodeData);
            return invitationCodeData.getInvitationCode();
        }
        return invitationCode;
    }

    @Override
    public void changeIntroduction(ChangeIntroductionDTO changeIntroductionDTO) {
        Long teamId = teamReportMapper.getTeamId(changeIntroductionDTO.getTeamName());
        int userRole= getRole(changeIntroductionDTO.getLoginUserId(), teamId);
        checkRole(userRole,3);
        teamReportMapper.changeIntroduction(changeIntroductionDTO.getIntroduction(),teamId);
    }

    @Override
    public ManagementVO managementUser(Long loginUserId, String teamName) {
        Long teamId = teamReportMapper.getTeamId(teamName);
        int userRole= getRole(loginUserId, teamId);
//        checkRole(userRole,3);
        List<Long> adminId = teamReportMapper.getAdmin(teamId);
        List<Long> ordinaryId = teamReportMapper.getOrdinary(teamId);
        List<AdminVO> adminVOList = new ArrayList<>();
        List<OrdinaryVO> ordinaryVOList = new ArrayList<>();
        for (Long admin : adminId) {
             AdminVO adminVO = teamReportMapper.getUserData(admin);
             adminVO.setRole(getRole(admin,teamId));
             adminVO.setId(admin);
             adminVOList.add(adminVO);
        }
        for (Long ordinary : ordinaryId) {
            OrdinaryVO ordinaryVO = teamReportMapper.getOrdinaryData(ordinary);
            ordinaryVOList.add(ordinaryVO);
            ordinaryVO.setId(ordinary);
        }
        return new ManagementVO(adminVOList,ordinaryVOList);
    }

    @Override
    public void transferBoss(TransferBossDTO transferBossDTO) {
        Long teamId = teamReportMapper.getTeamId(transferBossDTO.getTeamName());
        int userRole= getRole(transferBossDTO.getLoginUserId(), teamId);
        checkRole(userRole,3);
        teamReportMapper.transferBoss(teamId,transferBossDTO.getTransferUserId());
        teamReportMapper.changeOrdinary(teamId,transferBossDTO.getLoginUserId());


    }

    @Override
    public void setAdmin(SetAdminDTO setAdminDTO) {
        Long teamId = teamReportMapper.getTeamId(setAdminDTO.getTeamName());
        int userRole= getRole(setAdminDTO.getLoginUserId(), teamId);
        checkRole(userRole,2);
        setAdminDTO.setTeamId(teamId);
        teamReportMapper.setAdmin(setAdminDTO);
    }

    @Override
    public void removeAdmin(RemoveAdminDTO removeAdminDTO) {
        Long teamId = teamReportMapper.getTeamId(removeAdminDTO.getTeamName());
        removeAdminDTO.setTeamId(teamId);
        int userRole= getRole(removeAdminDTO.getLoginUserId(), teamId);
        checkRole(userRole,3);
        teamReportMapper.removeAdmin(removeAdminDTO);
    }


}
