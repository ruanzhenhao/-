package com.wzx.service.impl;

import com.wzx.entity.SendMessage;
import com.wzx.mapper.MessageMapper;
import com.wzx.result.Result;
import com.wzx.service.MessageService;
import com.wzx.vo.MessageTableVO;
import com.wzx.vo.TaskTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService
{
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<MessageTableVO> messageTable(Long loginUserId) {

        return messageMapper.messageTable(loginUserId);
    }

    @Override
    public void clearAllMessage(Long loginUserId) {

    }

    @Override
    public void yes(Long loginUserId, Long teamID)
    {

        messageMapper.yes(loginUserId, teamID);
    }

    @Override
    public void no(Long loginUserId, Long id) {
        messageMapper.no(loginUserId,id);
    }


}
