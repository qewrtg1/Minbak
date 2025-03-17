package com.minbak.web.user_YH.host_book_page;

import com.minbak.web.user_YH.dto.DetailUserResponse;
import com.minbak.web.user_YH.payment_page.PaymentBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface HostBookMapper {

    List<HostBooksResponse> findBooksByUserId(int userId);
    DetailUserResponse findUserDetailByUserId(Integer userId);
    List<Map<String, String>> fineHostingRoomByUserId(Integer userId);
    int approveReservation(Integer bookId);
    int declineReservation(Integer bookId);

}
