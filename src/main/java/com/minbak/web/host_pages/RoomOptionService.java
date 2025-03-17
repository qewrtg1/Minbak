package com.minbak.web.host_pages;

import com.minbak.web.host_pages.dto.OptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomOptionService {
    @Autowired
    private RoomOptionMapper roomOptionMapper;

    @Transactional
    public void saveRoomOptions(Integer roomId, List<Integer> optionIds){
        for(Integer optionId : optionIds){
            roomOptionMapper.insertRoomOption(roomId,optionId);
        }
    }

    public List<OptionDto> getOptionsByRoomId(Integer roomId){
        return roomOptionMapper.getOptionsByRoomId(roomId);
    }

}
