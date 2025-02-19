package com.minbak.web.messages;


import com.minbak.web.board.posts.BoardPostDto;
import com.minbak.web.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;
    // 유저조회
    public List<UserDto> findAllUsers(){
        return messageMapper.findAllUsers();
    }

    // 유저 아이디에 따른 메세지 내용 조회
    public List<MessageDto> findMessagesById(int user_id) {
        return  messageMapper.findMessagesById(user_id);
    }
    // 오늘자 메세지 조회
    MessagePageDto<MessageDto> findMessagesToday(int page, int size){

        //        오늘 메세지 조회
        LocalDate currentDate = LocalDate.now();
        Date formattedDate = Date.valueOf(currentDate);

        int offset = (page-1)*size;
        int totalItems = messageMapper.countMessagesToday();
        List<MessageDto> messageDtos =messageMapper.findMessagesToday(size, offset,formattedDate);
        MessagePageDto<MessageDto> messagePageDto = new MessagePageDto<>(page,size,totalItems,messageDtos);
        return messagePageDto;
    }
    // 오늘자 메세지 개수 조회
    public int countMessagesToday(){
        return messageMapper.countMessagesToday();
    }
    // 모든 메세지 카운팅
    public int countAllMessages(){
        return messageMapper.countAllMessages();
    }
    // 메세지 조회 ,페이지네이션
    public MessagePageDto<MessageDto> findMessagesByLimitAndOffset(int page, int size){

        int offset = (page-1)*size;
        int totalItems = messageMapper.countAllMessages();
        List<MessageDto> messageDtos =messageMapper.findMessagesByLimitAndOffset(size, offset);
        MessagePageDto<MessageDto> messagePageDto = new MessagePageDto<>(page,size,totalItems,messageDtos);
        return messagePageDto;
    }
    // 메세지 삭제
    public void deleteMessage(int message_id){
        messageMapper.deleteMessage(message_id);
    }
//    아이디 입력하여 메세지 생성
    public void  createMessage(String receiverEmail,MessageDto messageDto){
//        이메일로 id조회해서 바꿈
        int receiverId=messageMapper.findUserIdByEmail(receiverEmail);
        messageDto.setReceiverId(receiverId);
//        샌더 아이디 임시 입력 *수정필요*
        messageDto.setSenderId(8);
//        메세지 생성
        messageMapper.createMessage(messageDto);
    }
}
