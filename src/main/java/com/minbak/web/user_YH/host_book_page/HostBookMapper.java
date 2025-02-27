package com.minbak.web.user_YH.host_book_page;

import com.minbak.web.user_YH.payment_page.PaymentBookDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface HostBookMapper {

    List<HostBooksResponse> findBooksByUserId(int userId);

}
