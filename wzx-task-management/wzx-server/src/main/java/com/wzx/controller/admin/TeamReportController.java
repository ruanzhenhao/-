package com.wzx.controller.admin;

import com.wzx.dto.*;
import com.wzx.result.Result;
import com.wzx.service.TeamReportService;
import com.wzx.vo.CreateTaskVO;
import com.wzx.vo.ManagementVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/home/teamReport")
@Slf4j
public class TeamReportController {
    @Autowired
    private TeamReportService teamReportService;

//    在团队中做任务数量最多的10人
    @PostMapping("/top10")
    public Result<List<Top10>> getTop10(String teamName, @RequestParam Long loginUserId)
    {
        log.info("获取团队名"+teamName);
        return Result.success(teamReportService.getTop10(teamName,loginUserId));

    }
    @GetMapping("/taskList")
    public Result<List<TaskListDTO>> getTaskList(String teamName)
    {
        return Result.success(teamReportService.getTaskList(teamName));
    }
    @GetMapping("/completeTask")
    public Result<List<CompleteTaskDTO>> getCompleteTask(int taskId)
    {
        return Result.success(teamReportService.getCompleteTask(taskId));
    }
    //    男女比例
    @GetMapping("/percentage")
    public Result<Map<String,Integer>> getPercentage(String teamName)
    {
        return Result.success(teamReportService.getPercentage(teamName));
    }
    @GetMapping("/noTask")
    public Result<Map<String,Integer>> getNoTask(String teamName,@RequestParam Long loginUserId)
    {
        return Result.success(teamReportService.getNoTask(teamName,loginUserId));
    }
    @GetMapping("/introduction")
    public Result<String> getIntroduction(String teamName)
    {
        return Result.success(teamReportService.getIntroduction(teamName));
    }

    @PostMapping("/getTeamUserList")
    public Result<List<TeamUserListDTO>> getTeamUserList(String teamName)
    {
        return Result.success(teamReportService.getTeamUserList(teamName));
    }
    @GetMapping("/getTeamTaskList")
    public Result<List<TeamTaskListDTO>> getTeamTaskList(String teamName,@RequestParam Long loginUserId)
    {
        log.info("获取团队名"+teamName);
        return Result.success(teamReportService.getTeamTaskList(teamName,loginUserId));
    }
    @PostMapping("/updateTeamName")
    public Result updateTeamName(@RequestBody UpdateTeamNameDTO updateTeamNameDTO,@RequestParam Long loginUserId)
    {
        updateTeamNameDTO.setLoginUserId(loginUserId);

        teamReportService.updateTeamName(updateTeamNameDTO);
        return Result.success();
    }
    @DeleteMapping("/deleteTeam")
    public Result deleteTeam(@RequestBody DeleteTeamDTO deleteTeamDTO, @RequestParam Long loginUserId)
    {
        String teamName=deleteTeamDTO.getTeamName();

        teamReportService.deleteTeam(loginUserId,teamName);
        return Result.success();
    }
    @PostMapping("/quitTeam")
    public Result quitTeam(@RequestParam Long loginUserId, String teamName)
    {
        teamReportService.quitTeam(loginUserId,teamName);
        return Result.success();
    }

    @PostMapping("/deleteTeamUser")

    public Result deleteTeamUser(@RequestBody DeleteTeamUserDTO deleteTeamUserDTO, @RequestParam Long loginUserId)
    {
        deleteTeamUserDTO.setLoginUserId(loginUserId);
        teamReportService.deleteTeamUser(deleteTeamUserDTO);
        return Result.success();
    }
    @GetMapping("/getInvitationCode")
    public Result<Long> getInvitationCode(Long loginUserId, String teamName)
    {

        return Result.success(teamReportService.getInvitationCode(loginUserId,teamName));
    }
    @PostMapping("/changeIntroduction")
    public Result changeIntroduction(@RequestBody ChangeIntroductionDTO changeIntroductionDTO , @RequestParam Long loginUserId)
    {
        changeIntroductionDTO.setLoginUserId(loginUserId);
        teamReportService.changeIntroduction(changeIntroductionDTO);
        return Result.success();
    }
    @GetMapping("/management")
    public Result<ManagementVO> managementUser (@RequestParam Long loginUserId,String teamName)
    {
        return Result.success(teamReportService.managementUser(loginUserId,teamName));
    }
    @PostMapping("/transferBoss")
    public Result transferBoss(@RequestBody TransferBossDTO transferBossDTO , @RequestParam Long loginUserId)
    {
        transferBossDTO.setLoginUserId(loginUserId);
        log.info("转职信息"+transferBossDTO);
        teamReportService.transferBoss(transferBossDTO);
        return Result.success();
    }
    @PostMapping("/setAdmin")
    public Result setAdmin(@RequestBody SetAdminDTO setAdminDTO , @RequestParam Long loginUserId)
    {
        setAdminDTO.setLoginUserId(loginUserId);
        teamReportService.setAdmin(setAdminDTO);
        return Result.success();
    }
    @PostMapping("/removeAdmin")
    public Result removeAdmin(@RequestBody RemoveAdminDTO removeAdminDTO, @RequestParam Long loginUserId)
    {
        removeAdminDTO.setLoginUserId(loginUserId);
        teamReportService.removeAdmin(removeAdminDTO);
        return Result.success();
    }
    @PostMapping("createTask")
    public Result createTask(@RequestBody CreateTaskVO createTask, @RequestParam Long loginUserId)
    {
        createTask.setLoginUserId(loginUserId);
        teamReportService.createTask(createTask,loginUserId);
        return Result.success();
    }

}
