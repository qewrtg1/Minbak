package com.minbak.web.host_today;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HostTodayMapper {
    List<HostTodayDto> findMyRooms(@Param("userId") int userId);
    List<HostTodayDto> getCheckOut(@Param("userId") int userId);
    List<HostTodayDto> getOngoing(@Param("userId") int userId);
    List<HostTodayDto> getCheckIn(@Param("userId") int userId);
    List<HostTodayDto> getUpcoming(@Param("userId") int userId);
    List<HostTodayDto> getPendingReviews(@Param("userId") int userId);
}
