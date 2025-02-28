package com.minbak.web.host_today;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HostTodayMapper {
    List<HostTodayDto> getCheckOut(@Param("hostId") int hostId);
    List<HostTodayDto> getOngoing(@Param("hostId") int hostId);
    List<HostTodayDto> getCheckIn(@Param("hostId") int hostId);
    List<HostTodayDto> getUpcoming(@Param("hostId") int hostId);
    List<HostTodayDto> getPendingReviews(@Param("hostId") int hostId);
}
