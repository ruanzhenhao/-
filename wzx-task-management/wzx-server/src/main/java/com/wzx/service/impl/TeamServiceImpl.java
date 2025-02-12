package com.wzx.service.impl;

import com.wzx.constant.MessageConstant;
import com.wzx.constant.UserConstant;
import com.wzx.dto.CreateTeamDTO;
import com.wzx.entity.SendMessage;
import com.wzx.mapper.MessageMapper;
import com.wzx.service.MessageService;
import com.wzx.vo.CreateTeamVO;
import com.wzx.dto.TeamListDataDTO;
import com.wzx.dto.TeamPageDTO;
import com.wzx.dto.TeamReportDTO;
import com.wzx.entity.User;
import com.wzx.exception.PrivilegeException;
import com.wzx.exception.TeamException;
import com.wzx.exception.UserException;
import com.wzx.mapper.TaskMapper;
import com.wzx.mapper.TeamMapper;
import com.wzx.mapper.UserMapper;
import com.wzx.result.PageResult;
import com.wzx.service.TeamService;
import com.wzx.vo.TeamListDataVO;
import com.wzx.vo.TeamListVO;
import com.wzx.vo.TeamReportVO;
import com.wzx.vo.TeamVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<TeamListVO> getTeamList(int id) {
        return teamMapper.getTeamList(id);

    }
    public void sendMessage(SendMessage sendMessage)
    {
        messageMapper.sendMessage(sendMessage);
        messageMapper.sendMessageUser(sendMessage);

    }
//    权限验证
    @Override
    public void privilege(Long loginUserId , List<Long> teamIdList)
    {
//        用户有权限查询的团队id
        List<Long> userTeamIdList = teamMapper.selectTeamID(loginUserId);
        for (Long teamId : teamIdList) {
            if (userTeamIdList.contains(teamId))
            {
                break;
            }else
            {
                throw new PrivilegeException("没有权限,禁止访问");
            }
        }

    }

    @Override
    public void selectUser(String userName,Long loginUserId) {
        log.info("获取用户名"+userName);
        String loginUser = userMapper.getLoginUser(loginUserId);
        if (userMapper.selectUser(userName)==0 && !Objects.equals(loginUser, userName))
        {
            throw new UserException(UserConstant.NOTFOUND);
        }
    }

    public List<Long> getUserIds(List<String> usernames)
    {
        return teamMapper.getUserIdsBylistName(usernames);
    }
    @Override
    public Long createTeam(CreateTeamVO createTeam)
    {
        CreateTeamDTO createTeamDTO = new CreateTeamDTO();
        try {

            BeanUtils.copyProperties(createTeam,createTeamDTO);
            createTeamDTO.setCreateTime(LocalDate.now());
            createTeamDTO.setUpdateTime(LocalDate.now());
            Long teamID = teamMapper.createTeam(createTeamDTO);
            if(createTeam.getInvitedUsers()!=null)
            {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setTeamID(teamID);
                sendMessage.setUserID(getUserIds(createTeam.getInvitedUsers()));
                sendMessage.setCreateUserName(teamMapper.getUserName(createTeam.getLoginUserId()));
                sendMessage.setContent(sendMessage.getCreateUserName()+"要请你加入团队");

                sendMessage.setCreateUserID(createTeam.getLoginUserId());
                sendMessage.setTitle("邀请加入团队");
                sendMessage.setType(2);
                sendMessage.setCrateTime(LocalDate.now());
                sendMessage(sendMessage);


            }

        }catch (Exception e)
        {
            throw new TeamException(MessageConstant.REPEAT_TEAM_NAME+e);
        }

//            设置组长
        teamMapper.createTeamUser(createTeamDTO);
//        设置邀请码
        long invitationCode = System.currentTimeMillis();
        createTeamDTO.setInvitationCode(invitationCode);
        teamMapper.insertCode(createTeamDTO);
//        发送邀请



        return invitationCode;
    }

    @Override
    public void addTeam(Long invitationCode, Long loginUserId) {
        Long teamID = teamMapper.selectIvitationCode(invitationCode);
        if (teamID==null)
            {
                throw new TeamException("邀请码无效或已过期");
            }
        if (teamMapper.isTrue(teamID,loginUserId)>0)
        {
            throw new TeamException("您已加入该团队");
        }
            teamMapper.insertUser(teamID,loginUserId);
    }

    //    获取团队人数
    @Override
    public Integer getCount(Integer id) {
        return teamMapper.getCount(id);
    }
//本月内获取团队成员完成任务量前5
    @Override
    public TeamReportVO getTop5(int id) {

        LocalDate begin = LocalDate.now();
        LocalDate firstDayOfMonth = begin.withDayOfMonth(1);
        TeamReportDTO teamReportDTO = new TeamReportDTO();
        teamReportDTO.setTeamId(id);
        teamReportDTO.setBeginDate(firstDayOfMonth);
        teamReportDTO.setEndDate(LocalDate.now());
        return new TeamReportVO(teamMapper.getTop5Name(teamReportDTO),teamMapper.getTop5Value(teamReportDTO));
    }

    @Override
    public PageResult getTeamData(TeamPageDTO teamPageDTO) {
        int teamId = teamPageDTO.getTeamId();
        int count = teamMapper.getCount(teamId);
        teamPageDTO.setStart((teamPageDTO.getPage()-1)*teamPageDTO.getPageSize());
        List<TeamVO> userIdList = teamMapper.getUserId(teamPageDTO);
        User user;
        List<TeamVO> teamVOList = new ArrayList<>();
        for (TeamVO teamVO : userIdList) {
//            通过用户id查询用户信息，后面用beanUtil装入teamVO中
            teamPageDTO.setId(teamVO.getUserId());

            teamVO.setRole(stringRole(teamMapper.getRole(teamId, teamVO.getUserId())));
            user = userMapper.getUser(teamPageDTO);
            log.info("teamPageDTO:"+teamPageDTO);

            if (user == null)
            {
                continue;
            }
            teamVO.setImg(user.getImg());
            teamVO.setUsername(user.getUsername());
            teamVO.setPhone(user.getPhone());
            teamVO.setEmail(user.getEmail());
            teamVO.setAddress(user.getAddress());
            teamVO.setSex(user.getSex());
            //再获取未完成任务数
            teamVO.setNoTask(taskMapper.getNoTeamTask(teamId, teamVO.getUserId()));
            //装入list中
            log.info("teamVO:"+teamVO);
            teamVOList.add(teamVO);
        }

        return new PageResult(count, teamVOList);
    }

    @Override
    public PageResult getTeamListData(TeamListDataDTO teamListDataDTO) {
        teamListDataDTO.setStart((teamListDataDTO.getPage()-1)*teamListDataDTO.getPageSize());
        List<Long> teamIds= teamMapper.getTeamID(teamListDataDTO);
        int count = teamMapper.getTeamCount(teamListDataDTO.getLoginUserId());
        List<TeamListDataVO> teamListDataVOList = new ArrayList<>();

        for (Long teamId : teamIds) {
            log.info("teamId:"+teamId);
            teamListDataVOList.add(teamMapper.getTeam(teamId));
            log.info("teamListDataVO:"+teamListDataVOList);
        }

        return new PageResult(count, teamListDataVOList);
    }

    private String stringRole(Integer role) {
        if (role == 3)
        {
            return "组长";
        }
        if (role == 2)
        {
            return "管理员";
        }
        return "组员";

    }
}

