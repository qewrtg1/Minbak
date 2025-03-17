package com.minbak.web.books;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksMapper booksMapper;

    public BooksPageDto<BooksDto> getBooks(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<BooksDto> books = booksMapper.selectBooksByPage(size,offset);
        int totalElements = booksMapper.countTotalBooks();
        return new BooksPageDto<>(page, size, totalElements, books);
    }

    public BooksPageDto<BooksDto> searchBooks(String searchType, String keyword, String dateType,
                                    LocalDate startDate, LocalDate endDate, String statusFilter,
                                    Integer page, Integer size) {
        int offset = (page - 1) * size;
        // 🔹 검색 조건이 없을 때 전체 리스트 조회 (status 또는 date만 있어도 검색 수행)
        boolean isEmptySearch = (searchType == null || searchType.isEmpty()) &&
                (keyword == null || keyword.isEmpty());

        if (isEmptySearch && (statusFilter == null || statusFilter.isEmpty()) && (dateType == null || dateType.isEmpty())) {
            return getBooks(page, size); // 전체 예약 리스트 반환
        }
        List<BooksDto> books = booksMapper.searchBooks(searchType, keyword, dateType, startDate, endDate, statusFilter, size, offset);
        int totalElements = booksMapper.countSearchedBooks(searchType, keyword, dateType, startDate, endDate, statusFilter);
        return new BooksPageDto<>(page, size, totalElements, books);
    }

    public void createBook(BooksDto booksDto) {
        booksMapper.createBook(booksDto);
    }

    public BooksDto selectBookById(Integer bookId) {
        return booksMapper.selectBookById(bookId);
    }

    public void editBook(BooksDto booksDto) {
        booksMapper.editBook(booksDto);
    }

    public void deleteBook(Integer bookId) {
        booksMapper.deleteBook(bookId);
    }

    public Map<String, Map<LocalDate, String>> findMonthlyBooks(int year, int month) {
        List<Map<String, Object>> bookings = booksMapper.findMonthlyBooks(year, month);
        Map<String, Map<LocalDate, String>> roomStatusMap = new HashMap<>();

        for (Map<String, Object> booking : bookings) {
            String roomName = (String) booking.get("roomName");
            LocalDate startDate = ((java.sql.Date) booking.get("start_date")).toLocalDate();
            LocalDate endDate = ((java.sql.Date) booking.get("end_date")).toLocalDate();
            String status = (String) booking.get("status");

            roomStatusMap.putIfAbsent(roomName, new HashMap<>());
            Map<LocalDate, String> roomStatus = roomStatusMap.get(roomName);

            // 체크인 날짜부터 체크아웃 전날까지 같은 상태 유지
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                roomStatus.putIfAbsent(date, status);
            }
        }
        return roomStatusMap;
    }

    public List<BooksDto> getWaitingBooks() {
        return booksMapper.selectWaitings();
    }

    public List<BooksDto> paidAndCheckIn() {
        return booksMapper.paidAndCheckIn();
    }

    public List<TopDayOfWeekDto> findTopBooks() {
        return booksMapper.findTopBooksDayOfWeek();
    }

    public List<TopBooksOfRegionDto> findTopBooksOfRegion() {
        return booksMapper.findTopBookOfRegion();
    }
}
