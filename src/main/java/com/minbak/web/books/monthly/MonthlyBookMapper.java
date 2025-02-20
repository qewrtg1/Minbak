package com.minbak.web.books.monthly;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MonthlyBookMapper {
    List<Map<String, Object>> findMonthlyBooks(@Param("year") int year, @Param("moonth") int month);
}
