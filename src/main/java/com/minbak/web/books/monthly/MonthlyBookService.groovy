package com.minbak.web.books.monthly

import org.springframework.stereotype.Service

@Service
class MonthlyBookService {
    private MonthlyBookMapper monthlyBookMapper;

    public List<Map<String, Object>> findMonthlyBooks(int year, int month) {
        return monthlyBookMapper.findMonthlyBooks(year, month);
    }
}
