package com.minbak.web.users;

import com.minbak.web.payments.PaymentDto;
import com.minbak.web.rooms.RoomsDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/api")
@RequiredArgsConstructor
@ResponseBody
public class UsersAdminApiController {

    private final UsersService usersService;

//    @GetMapping("/users")
//    public UserPageDto<UserResponseDto> searchUsers(
//            @RequestParam("search") String search,    // 검색어
//            @RequestParam("page") int page,           // 현재 페이지
//            @RequestParam("size") int size            // 페이지 크기
//    ) {
//
//        return usersService.findUsersByLimitAndOffsetAndString(page, size, search);
//
//    }

    @GetMapping("/users/payments")
    public UserPageDto<PaymentDto> getPaymentByUserPageing(
            @RequestParam("userId") int userId,    // 검색어
            @RequestParam("page") int page,           // 현재 페이지
            @RequestParam("size") int size            // 페이지 크기
    ){
        return usersService.findPaymentsByLimitAndOffsetAndUserId(page, size, userId);
    }

    @GetMapping("/users/rooms")
    public UserPageDto<RoomsDto> getRoomsByUserPageing(
            @RequestParam("userId") int userId,    // 검색어
            @RequestParam("page") int page,           // 현재 페이지
            @RequestParam("size") int size            // 페이지 크기
    ){
        return usersService.findRoomsByLimitAndOffsetAndUserId(page, size, userId);
    }

    @PostMapping("/users/makeAdmin")
    public ResponseEntity<Map<String, Object>> makeAdmin(@RequestBody Map<String, Object> request){

        Map<String, Object> response = new HashMap<>();


        try {

            String userId = (String) request.get("userId");
            // 관리자 등록 처리 (예시)
            usersService.makeAdmin(userId);

            response.put("success", true);
            return ResponseEntity.ok(response); // HTTP 200 OK 응답
        }catch (Exception e){
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // HTTP 400 Bad Request 응답
        }
    }

    @PostMapping("/users/report/update-status")
    public ResponseEntity<Map<String, String>> updateReportStatus(@RequestBody UpdateReportStatusRequest request){

        Map<String, String> response = new HashMap<>();
        try {
            // 상태 일괄 수정 로직
            List<ReportStatusUpdate> updates = request.getUpdates();

            // 상태 수정하는 서비스 호출 (Service 클래스를 통해)
            for (ReportStatusUpdate update : updates) {
                Integer reportId = update.getReportId();
                String status = update.getStatus();

                // UserReportDto로 데이터 조회
                UserReportDto userReportDto = usersService.getReportById(reportId);
                if (userReportDto != null) {
                    userReportDto.setStatus(status);  // 상태 업데이트
                    usersService.updateReportStatus(userReportDto);  // 상태 수정된 리포트 저장
                }
            }
            response.put("message","상태가 성공적으로 수정되었습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외 처리
            response.put("message","에러가 발생하였습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/users/sendMessage")
    public ResponseEntity<Map<String, String>> sendMessageAtUserDetail(@RequestBody Integer userId, @RequestBody String message){

        //로그인한 사람 id받아서 메시지 보내는 기능 구현 예정 (관리자 유저 디테일 페이지 참고)
        
        Map<String, String> response = new HashMap<>();
        
        return ResponseEntity.ok(response);

    }

    @Setter
    @Getter
    public static class UpdateReportStatusRequest {
        // Getters and Setters
        private List<ReportStatusUpdate> updates; // 상태 업데이트할 리포트 목록

    }

}
