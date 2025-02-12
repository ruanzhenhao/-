package com.wzx.controller.admin;

import com.wzx.result.Result;
import com.wzx.service.MessageService;
import com.wzx.vo.MessageTableVO;
import com.wzx.vo.TaskTableVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/home/message")
@Slf4j
public class MessageController
{
    @Autowired
    private MessageService messageService;

    @PostMapping("messageTable")
    public Result<List<MessageTableVO>> messageTable(@RequestParam Long loginUserId)
    {
        return Result.success(messageService.messageTable(loginUserId));
    }

    @DeleteMapping("clearAllMessage")
    public Result clearAllMessage(@RequestParam Long loginUserId)
    {
        messageService.clearAllMessage(loginUserId);
        return Result.success();
    }
    @GetMapping("yes")
    public Result yes(@RequestParam Long loginUserId,Long teamId)
    {
        messageService.yes(loginUserId,teamId);
        return Result.success();
    }
    @GetMapping("no")
    public Result no(@RequestParam Long loginUserId,Long id)
    {
        messageService.no(loginUserId,id);
        return Result.success();
    }


}
