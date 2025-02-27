package com.minbak.web.check_books;


import com.minbak.web.check_books.dto.CheckBookDto;
import com.minbak.web.check_books.dto.CheckBookRoomDto;
import com.minbak.web.users.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckBookMapper {


    List<CheckBookDto> selectBooks(int offset, int  size, int userId);
    CheckBookRoomDto findRoomsByRoomId(int room_id);
    int countBooks(int userId);
    String findRoomImageUrlByRoomId(int roomId);
    List<String> findRoomImageUrlsByRoomId(int roomId);
    UserDto findUserByUserId(int userId);
    CheckBookDto findBookByBookId(int bookId);
}
