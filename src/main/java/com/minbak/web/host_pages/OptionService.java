package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.OptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    @Autowired
    private OptionMapper optionMapper;

    public List<OptionDto> getAllOptions(){
        return optionMapper.getAllOptions();
    }

    public List<OptionDto> getOptionByCategory(String category){
        return optionMapper.getOptionsByCategory(category);
    }

}
