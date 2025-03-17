package com.minbak.web.messages;


import com.minbak.web.board.posts.BoardPostDto;
import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.users.UserDto;
import com.minbak.web.users.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UsersMapper usersMapper;
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
//    페이지 필터 검색 조회

    //    필터링 메세지 조회
    public MessagePageDto<ResponseMessageDto> findMessagesWithUser(RequestMessageFilterDto requestMessageFilterDto,int page,int size){
//        입력값 공백시 null로 처리
        if (requestMessageFilterDto.getUserName() != null && requestMessageFilterDto.getUserName().isEmpty()) {
            requestMessageFilterDto.setUserName(null);
        }
        if (requestMessageFilterDto.getUserEmail() != null && requestMessageFilterDto.getUserEmail().isEmpty()) {
            requestMessageFilterDto.setUserEmail(null);
        }
        if (requestMessageFilterDto.getUserPhoneNumber() != null && requestMessageFilterDto.getUserPhoneNumber().isEmpty()) {
            requestMessageFilterDto.setUserPhoneNumber(null);
        }
        if (requestMessageFilterDto.getKeyword() != null && requestMessageFilterDto.getKeyword().isEmpty()) {
            requestMessageFilterDto.setKeyword(null);
        }
//        페이지처리, 검색,필터
        int offset = (page-1)*size;
        requestMessageFilterDto.setLimit(size);
        requestMessageFilterDto.setOffset(offset);
        int totalItems = messageMapper.countFilteredMessages(requestMessageFilterDto);
        List<ResponseMessageDto> responseMessageDtos= messageMapper.findMessagesWithUser(requestMessageFilterDto);
        MessagePageDto<ResponseMessageDto> filteredMessagePageDto= new MessagePageDto<>(page,size,totalItems,responseMessageDtos);

        return filteredMessagePageDto;
    }


    // 메세지 삭제
    public void deleteMessage(int message_id){
        messageMapper.deleteMessage(message_id);
    }
    // 메세지 비활성화
    public void blindMessage(int messageId){
        messageMapper.blindMessage(messageId);
    }
//    아이디 입력하여 메세지 생성
    public void  createMessageByEmail(String receiverEmail, MessageDto messageDto, CustomUserDetails userDetails){



//        유효성 검사
        if (usersMapper.findUserByEmail(receiverEmail) ==null){
            throw new IllegalArgumentException("받는이 유저 이메일을 확인하세요.");
        }
        //        이메일로 id조회해서 바꿈
        Integer receiverIdByEmail=usersMapper.findUserByEmail(receiverEmail).getUserId();

        messageDto.setReceiverId(receiverIdByEmail);
//        샌더 아이디 임시 입력 *수정필요*
        messageDto.setSenderId(userDetails.getUserId());
//        메세지 생성
        messageMapper.createMessage(messageDto);
    }
    public void createMessageById(Integer receiverId,MessageDto messageDto,CustomUserDetails userDetails){

        if (usersMapper.findUserByUserId(receiverId)==null){
            throw new IllegalArgumentException("받는이 유저 ID가 없습니다.");
        }
        messageDto.setReceiverId(receiverId);
//        샌더 아이디 임시 입력 *수정필요*
        messageDto.setSenderId(userDetails.getUserId());
        messageMapper.createMessage(messageDto);
    }
//--------------------유저 메세지 기능---------------------------------------
//    유저메세지 채팅방 목록
    public List<UserMessageListDto> showUserMessageList(int user_id){

        List<UserMessageListDto> userMessageLists=messageMapper.showUserMessageList(user_id);
        for (UserMessageListDto userMessageListDto : userMessageLists) {
            userMessageListDto.setIsUnRead(messageMapper.findMessageByMessageId(userMessageListDto.getLastMessageId()).getIsRead());// 예를 들어, 메시지를 읽은 것으로 표시
            userMessageListDto.setChatRoomUserName(usersMapper.findUserByUserId(userMessageListDto.getChatRoomId()).getName());
        }

        return userMessageLists;
    }
    public Integer countMessagesByIds(int userId,int chatRoomId){
        return messageMapper.countMessagesByIds(userId, chatRoomId);
    }
//    유저 메세지 읽음 표시 포스트 요청
    public void checkIsRead(int userId,int chatRoomId){
        messageMapper.updateMessageCheck(userId,chatRoomId);
    }

    public List<MessageDto> showUserMessageDetail(int userId, int chatRoomId){
        List<MessageDto> messageDtos=messageMapper.findMessagesByIds(userId,chatRoomId);

        return messageDtos;
    }
//  유저메세지 생성
    public void userCreateMessage(MessageDto messageDto){
        messageMapper.createMessage(messageDto);

    }



}
