package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.OptionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OptionMapper {

    List<OptionDto> getAllOptions();

    List<OptionDto> getOptionsByCategory(@Param("category") String category);

}
