package com.minbak.web.messages;


import com.minbak.web.board.posts.BoardPostDto;
import com.minbak.web.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    // 오늘자 메세지 개수 조회
    public int countMessagesToday(){
        return messageMapper.countMessagesToday();
    }
    // 모든 메세지 카운팅
    public int countAllMessages(){
        return messageMapper.countAllMessages();
    }
    // 메세지 조회 ,페이지네이션
    public List<MessageDto> findMessagesByLimitAndOffset(int page, int size){

        int offset = (page-1)*size;
        return messageMapper.findMessagesByLimitAndOffset(size, offset);
    }
    // 메세지 삭제
    public void deleteMessage(int message_id){
        messageMapper.deleteMessage(message_id);
    }
//    메세지 생성
    public void  createMessage(MessageDto messageDto){
        messageMapper.createMessage(messageDto);
//        메세지 sender_id 임시저장 1로 *수정필요*
        messageDto.setSenderId(1);
    }
// 이메일로 유저 찾고 유저id 반환
    public int getUserIdByEmail(String email) {
        // 이메일로 유저를 찾고, 유저 ID를 반환하는 로직
        int userId = messageMapper.findUserIdByEmail(email);

        return userId;
    }
}
