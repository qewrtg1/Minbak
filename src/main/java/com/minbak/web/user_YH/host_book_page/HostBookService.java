package com.minbak.web.user_YH.host_book_page;


import com.minbak.web.user_YH.dto.DetailUserResponse;
import com.minbak.web.user_YH.payment_page.PaymentBookDto;
import com.minbak.web.user_YH.payment_page.PaymentRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostBookService {

    private final HostBookMapper hostBookMapper;

    List<HostBooksResponse> findBooksByUserId(int userId){
        return hostBookMapper.findBooksByUserId(userId);
    }

    DetailUserResponse findUserDetailByUserId(int userId){
        return hostBookMapper.findUserDetailByUserId(userId);
    }

    public List<Map<String, String>> getHostingRooms(Integer userId) {
        return hostBookMapper.fineHostingRoomByUserId(userId);
    }

    public void approveReservation(Integer bookId) {
        int updatedRows = hostBookMapper.approveReservation(bookId);
        if (updatedRows == 0) {
            throw new IllegalStateException("예약 승인 실패: 해당 bookId가 존재하지 않거나 이미 승인됨.");
        }
    }

    public void declineReservation(Integer bookId) {
        int updatedRows = hostBookMapper.declineReservation(bookId);
        if (updatedRows == 0) {
            throw new IllegalStateException("예약 거절 실패: 해당 bookId가 존재하지 않거나 이미 거절됨.");
        }
    }
}
