package com.wzx.mapper;

import com.wzx.entity.SendMessage;
import com.wzx.vo.MessageTableVO;
import com.wzx.vo.TaskTableVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    Long sendMessage(SendMessage sendMessage);

    void sendMessageUser(SendMessage sendMessage);

    List<MessageTableVO> messageTable(Long loginUserId);

    void yes(Long loginUserId, Long teamID);


    void no(Long loginUserId, Long id);
}
