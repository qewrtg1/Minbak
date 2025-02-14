package com.minbak.web.users;

import com.minbak.web.payments.PaymentDto;
import com.minbak.web.rooms.RoomsDto;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UsersService {

    @Autowired
    UsersMapper usersMapper;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    public void createUser(UserDto userDto){

        //중복된 이메일이 있는지 확인
        if(usersMapper.findUserEmailByEmail(userDto.getEmail()) == null){
            //페스워드 암호화
//            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            //회원데이터 저장
            usersMapper.createUser(userDto);

            //저장된 객체를 참조하여 id값 추출
            Integer user_id = userDto.getUserId();
            //잘 추출되면 1(ROLE_USER) 권한 추가
            if(user_id == null){
                throw new IllegalStateException("회원가입이 제대로 되지 않았습니다.");
            }else {
                usersMapper.createUserRoleByUserIdAndRoleId(user_id,1);
            }
        }else {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

    }

    public List<UserResponseDto> findUsersByLimitAndOffset(int page, int size){
        int offset = (page-1)*size;
        List<UserDto> userDtos = usersMapper.findUsersByLimitAndOffset(size, offset);
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (UserDto userDto : userDtos) {
            UserResponseDto userResponseDto = new UserResponseDto(userDto);
            userResponseDtos.add(userResponseDto);
        }

        return userResponseDtos;
    }

    public UserPageDto<UserResponseDto> findUsersByLimitAndOffsetAndString(int page, int size, String search){

        int offset = (page-1)*size;
        
        //검색창에 적은 String을 email이나 name에 포함한 유저수 가져오기
        int totalItems = usersMapper.countUsersBySearch(search);
        
        //검색창에 적은 String을 이용해 보여줄 페이지 가져오기
        List<UserDto> userDtos = usersMapper.findUsersByLimitAndOffsetAndString(size, offset, search);

        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (UserDto userDto : userDtos) {
            UserResponseDto userResponseDto = new UserResponseDto(userDto);
            userResponseDtos.add(userResponseDto);
        }
        //위 정보로 UserPageDto만들고 리턴
        return new UserPageDto<>(page,size,totalItems,userResponseDtos);
    }

    public int countAllUsers(){
        return usersMapper.countAllUsers();
    }

    public int countUserRolesByRoleId(int roleId){
        return usersMapper.countUserRolesByRoleId(roleId);
    }

    public int countUsersJoinedToday(){
        return usersMapper.countUsersJoinedToday();
    }

    public List<Map<Integer, Integer>> countUsersJoinedByWeekday(){
        return usersMapper.countUsersJoinedByWeekday();
    }

    public UserDto findUserByUserId(int userId){
        return usersMapper.findUserByUserId(userId);
    }

    public void updateUserByIdWithoutPassword(UserDto userDto){
        usersMapper.updateUserByIdWithoutPassword(userDto);
    }

    public UserPageDto<PaymentDto> findPaymentsByLimitAndOffsetAndUserId(int page, int size, int userId){

        int offset = (page-1)*size;
        List<PaymentDto> paymentDtos = usersMapper.findUsersPaymentsCompactInfoByLimitAndOffsetAndUserId(size,offset,userId);

        int totalItems = usersMapper.countPaymentsByUserId(userId);

        //위 정보로 UserPageDto만들고 리턴
        return new UserPageDto<>(page,size,totalItems,paymentDtos);
    }

    public UserPageDto<RoomsDto> findRoomsByLimitAndOffsetAndUserId(int page, int size, int userId){

        int offset = (page-1)*size;
        List<RoomsDto> roomsDtos = usersMapper.findRoomsByLimitAndOffsetAndUserId(size,offset,userId);

        int totalItems = usersMapper.countRoomsByUserId(userId);

        //위 정보로 UserPageDto만들고 리턴
        return new UserPageDto<>(page,size,totalItems,roomsDtos);
    }

}
