package com.minbak.web.user_YH.host_book_page;


import com.minbak.web.user_YH.payment_page.PaymentBookDto;
import com.minbak.web.user_YH.payment_page.PaymentRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostBookService {

    private final HostBookMapper hostBookMapper;

    List<HostBooksResponse> findBooksByUserId(int userId){
        return hostBookMapper.findBooksByUserId(userId);
    }
}
