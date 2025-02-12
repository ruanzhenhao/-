package com.wzx.mapper;

import com.wzx.dto.CreateTeamDTO;
import com.wzx.entity.InvitationCode;
import com.wzx.dto.TeamListDataDTO;
import com.wzx.dto.TeamPageDTO;
import com.wzx.dto.TeamReportDTO;
import com.wzx.vo.TeamListDataVO;
import com.wzx.vo.TeamListVO;
import com.wzx.vo.TeamVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMapper {



    List<TeamListVO> getTeamList(int id);

    Integer getCount(Integer id);

    List<String> getTop5Name(TeamReportDTO teamReportDTO);

    List<Integer> getTop5Value(TeamReportDTO teamReportDTO);

    List<TeamVO> getUserId(TeamPageDTO team);

    Integer getRole(int teamId, Integer userId);


    List<Long> getTeamID(TeamListDataDTO teamListDataDTO);

    TeamListDataVO getTeam(Long teamId);

    int getTeamCount(Long loginUserId);

    List<Long> selectTeamID(Long loginUserId);


    Long createTeam(CreateTeamDTO createTeamDTO);

    void createTeamUser(CreateTeamDTO createTeamDTO);

    void insertCode(CreateTeamDTO createTeamDTO);


    void addCode(InvitationCode invitationCodeData);

    Long selectIvitationCode(Long invitationCode);

    void insertUser(Long teamID, Long loginUserId);

    Integer isTrue(Long teamID, Long loginUserId);
    @Select("select username from `wyx-missions-management`.user where id = #{createTeamUserId}")
    String getBoss(Long createTeamUserId);
    @Select("select user_id from `wyx-missions-management`.team_user where team_id =#{teamID}")
    List<Long> getUserIds(long teamID);

    void sendInvitation(List<String> userNames);
    @Select("select id from `wyx-missions-management`.user where username =#{username}")
    Long getUserIdByname(String username);
    @Select({
            "<script>",
            "SELECT id FROM `wyx-missions-management`.user WHERE username IN",
            "<foreach item='username' collection='usernames' open='(' separator=',' close=')'>",
            "#{username}",
            "</foreach>",
            "</script>"
    })
    List<Long> getUserIdsBylistName(@Param("usernames") List<String> usernames);
    @Select("select username from `wyx-missions-management`.user where id = #{loginUserid}")
    String getUserName(Long loginUserId);
}
