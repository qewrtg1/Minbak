package com.minbak.web.check_books;


import com.minbak.web.books.BooksDto;
import com.minbak.web.check_books.dto.CheckBookDto;
import com.minbak.web.check_books.dto.CheckBookRoomDto;
import com.minbak.web.check_books.dto.RoomImgUrlDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckBookMapper {


    List<CheckBookDto> selectBooks(int offset, int  size, int userId);
    CheckBookRoomDto findRoomsByRoomId(int room_id);
    List<RoomImgUrlDto> findRoomsImgById(List<Integer> roomIds);
    int countBooks(int userId);

}
