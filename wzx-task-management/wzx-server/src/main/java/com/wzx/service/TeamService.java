package com.wzx.service;

import com.wzx.vo.CreateTeamVO;
import com.wzx.dto.TeamListDataDTO;
import com.wzx.dto.TeamPageDTO;
import com.wzx.result.PageResult;
import com.wzx.vo.TeamListVO;
import com.wzx.vo.TeamReportVO;

import java.util.List;

public interface TeamService {


    List<TeamListVO> getTeamList(int id);

    Integer getCount(Integer id);

    TeamReportVO getTop5(int id);

    PageResult getTeamData(TeamPageDTO teamPageDTO);

    PageResult getTeamListData(TeamListDataDTO teamListDataDTO);

    void privilege(Long loginUserId, List<Long> teamIdList);

    void selectUser(String userName,Long loginUserId);

    Long createTeam(CreateTeamVO createTeam);

    void addTeam(Long invitationCode, Long loginUserId);
}
