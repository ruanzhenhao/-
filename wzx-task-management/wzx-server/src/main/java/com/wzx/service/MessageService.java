package com.wzx.service;

import com.wzx.entity.SendMessage;
import com.wzx.result.Result;
import com.wzx.vo.MessageTableVO;
import com.wzx.vo.TaskTableVO;

import java.util.List;

public interface MessageService {
    List<MessageTableVO> messageTable(Long loginUserId);

    void clearAllMessage(Long loginUserId);


    void yes(Long loginUserId, Long teamID);

    void no(Long loginUserId, Long id);
}
