package com.minbak.web.check_books;


import com.minbak.web.books.BooksDto;
import com.minbak.web.check_books.dto.CheckBookRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckBookMapper {


    List<BooksDto> selectBooks(int offset,int  size,int userId);
    CheckBookRoomDto findRoomsByRoomId(int room_id);

    int countBooks(int userId);

}
