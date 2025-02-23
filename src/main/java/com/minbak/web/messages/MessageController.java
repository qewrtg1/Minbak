package com.minbak.web.messages;


import com.minbak.web.users.UserDto;
import jakarta.validation.Valid;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin/message")
public class MessageController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @Autowired
    MessageService messageService;
//---------------------------------------메세지 조회 관련---------------------------------------------
//    유저조회
    @GetMapping("/user")
    public String userMessages(Model model){

        //유저 정보를 받아서 UserDto객체에 넣고 MessageDto 전달.

        //유저 목록 받아서 model로 전달
        List<UserDto> users= messageService.findAllUsers();
        model.addAttribute("users",users);
        return "/message/userMessage";
    }

//    유저아이디로  메세지 확인
    @GetMapping("/user/{user_id}")
    public String userMessagesById(@PathVariable("user_id") int user_id,Model model){
        model.addAttribute("user_id",user_id);
        model.addAttribute("messages", messageService.findMessagesById(user_id)  );

    return "/message/userMessageDetail";
    }

//    메인페이지, 오늘자 메세지 개수 조회
    @GetMapping("")
    public String mainMessage(Model model){
        model.addAttribute("countMessagesToday", messageService.countMessagesToday());
        return "/message/messageMain";
    }
// 메세지 리스트 페이지
    @GetMapping("/list")
    public String messagesList2(@ModelAttribute RequestMessageFilterDto requestMessageFilterDto ,
                                @RequestParam(name ="page", defaultValue = "1")int page,
                                @RequestParam(name ="size", defaultValue = "10")int size,
                                Model model){

        MessagePageDto<ResponseMessageDto> filteredResponseMessageDto=
                messageService.findMessagesWithUser(requestMessageFilterDto,page,size);

        model.addAttribute("messagePageDto",filteredResponseMessageDto);

        return"/message/messageList";
    }
//메세지 검색,필터링 요청
    @PostMapping("/list")
    public String filterMessageList(@ModelAttribute RequestMessageFilterDto requestMessageFilterDto,
                                    @RequestParam(name ="page", defaultValue = "1")int page,
                                    @RequestParam(name ="size", defaultValue = "10")int size,
                                    Model model){

        MessagePageDto<ResponseMessageDto> filteredResponseMessageDto=
                messageService.findMessagesWithUser(requestMessageFilterDto,page,size);

        model.addAttribute("messagePageDto",filteredResponseMessageDto);

        return "/message/messageList";
    }

//   ------------------------------------메세지 생성 관련-----------------------------------------

//    메세지 생성페이지
    @GetMapping("/create")
    public String createMessage(){
        return "/message/messageList";
    }
//    메세지 생성 요청(이메일로 보냄)
    @PostMapping("/create")
    public String postCreateMessage(@RequestParam(required = false) String receiverEmail,@RequestParam(required = false, defaultValue = "") Integer receiverId, @ModelAttribute MessageDto messageDto,RedirectAttributes redirectAttributes,BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage", "백앤드 유효성검사 통과 실패!" );
            return "redirect:/admin/message/list";
        }
        try {
        // 서비스에서 메시지 생성
            if (receiverId == null){
        messageService.createMessageByEmail(receiverEmail, messageDto);
            redirectAttributes.addFlashAttribute("createMessageOk", "메세지 보내기 완료");
                return "redirect:/admin/message/list";
        }
            messageService.createMessageById(receiverId, messageDto);
            redirectAttributes.addFlashAttribute("createMessageOk", "메세지 보내기 완료");

        } catch (Exception e) {
        model.addAttribute("errorMessage", "메시지 생성 중 오류 발생!");
        return "redirect:/admin/message/list";
        }

    return "redirect:/admin/message/list";
}
    //---------------------------------------메세지 삭제 관련---------------------------------------------
    //    메세지 삭제
    @GetMapping("/list/delete/{message_id}")
    public String deleteMessage(@PathVariable("message_id") int message_id,RedirectAttributes redirectAttributes){
        messageService.deleteMessage(message_id);
        redirectAttributes.addFlashAttribute("deleteOk","메세지 아이디 "+message_id +" 삭제 완료");


        return "redirect:/admin/message/list";
    }

}
