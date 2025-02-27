package com.minbak.web.check_books;


import com.minbak.web.books.BooksDto;
import com.minbak.web.books.BooksPageDto;
import com.minbak.web.check_books.dto.CheckBookDto;
import com.minbak.web.check_books.dto.RoomImgUrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckBookService {



        @Autowired
        CheckBookMapper checkBookMapper;
// 유저 본인 예약목록 보기
        // 예약 목록을 페이지네이션과 필터링을 통해 가져오는 메서드
        public BooksPageDto<CheckBookDto> getBooks(int page, int size, int userId) {
            // 페이지 네이션을 위한 시작과 끝 인덱스 계산
            int offset = (page - 1) * size;

            // 예약 목록을 가져옵니다.
            List<CheckBookDto> booksList = checkBookMapper.selectBooks(offset, size, userId);
//            room dto 들고와서 북스dto에 넣습니다


            // 총 예약 항목 수를 가져옵니다.
            int totalItems = checkBookMapper.countBooks(userId);

            // 페이지 정보 계산 후 BooksPageDto 반환
            return new BooksPageDto<>(page, size, totalItems, booksList);
        }

        public List<RoomImgUrlDto> findRoomsImgByRoomId(List<Integer> roomIds){
            return checkBookMapper.findRoomsImgById(roomIds);
        }

        // 특정 예약을 가져오는 메서드 (필요시)
//        public BooksDto getBookById(int bookId) {
//            return checkBookMapper.selectBookById(bookId);
//        }



}
