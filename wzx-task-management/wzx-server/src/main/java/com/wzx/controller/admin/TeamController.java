package com.wzx.controller.admin;

import com.wzx.vo.CreateTeamVO;
import com.wzx.dto.TeamListDataDTO;
import com.wzx.dto.TeamPageDTO;
import com.wzx.result.PageResult;
import com.wzx.result.Result;
import com.wzx.service.TeamService;
import com.wzx.vo.TeamListVO;
import com.wzx.vo.TeamReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/admin/home/team")
@Slf4j
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/add")
    public Result addTeam(Long invitationCode ,@RequestParam Long loginUserId)
    {
        teamService.addTeam(invitationCode,loginUserId);
        return Result.success();
    }

    @GetMapping("/list/{id}")
//    获取用户id
    public Result<List<TeamListVO>> getTeamList(@PathVariable Long id, @RequestParam Long loginUserId)
    {
        log.info("获取用户id"+loginUserId);
        log.info("获取团队列表"+id);

        List<TeamListVO> teamList = teamService.getTeamList(Math.toIntExact(loginUserId));
        return Result.success(teamList);
    }
//    获取团队成员数量 通过teamId
    @PostMapping("/count")
    public Result<Integer> getCount(Integer id)
    {
        log.info("成功获取团队id："+id);
        return Result.success(teamService.getCount(id));
    }
//    获取团队人员完成任务的前5名
    @GetMapping("/top5")
    public Result<TeamReportVO> getTop5(int id)
    {
        log.info("获取团队前5名,团队id为："+id);
        return Result.success(teamService.getTop5(id));
    }
//    获取团队成员详细情况
    @PostMapping("/teamData")
    public Result<PageResult> getTeamData(@RequestBody TeamPageDTO teamPageDTO)
    {
        log.info("获取得查询信息"+ teamPageDTO.toString());
        return Result.success(teamService.getTeamData(teamPageDTO));
    }
    @PostMapping("/list")
    public Result<PageResult> getTeamListData(@RequestBody TeamListDataDTO teamListDataDTO ,@RequestParam Long loginUserId)
    {
        teamListDataDTO.setLoginUserId(loginUserId);
        log.info("获取用户id"+teamListDataDTO);
        log.info("获取团队列表数据"+teamService.getTeamListData(teamListDataDTO));
        return Result.success(teamService.getTeamListData(teamListDataDTO));
    }
//检验是否有此人
    @GetMapping("/selectUser")
    public Result selectUser(String userName ,@RequestParam Long loginUserId)
    {
        log.info("获取用户名"+userName);
        teamService.selectUser(userName,loginUserId);
        return Result.success();
    }
//    增加团队
    @PostMapping("/createTeam")
    public Result<Long> createTeam(@RequestBody CreateTeamVO createTeam,@RequestParam Long loginUserId)
    {
        createTeam.setLoginUserId(loginUserId);
        log.info("创建团队信息"+createTeam.toString());

        return Result.success(teamService.createTeam(createTeam));
    }

}
