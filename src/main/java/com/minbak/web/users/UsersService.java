package com.minbak.web.users;

import com.minbak.web.common.dto.PageDto;
import com.minbak.web.payments.PaymentDto;
import com.minbak.web.rooms.RoomsDto;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void deleteUserByUserId(int userId){
        usersMapper.deleteUserByUserId(userId);
    }

    public PageDto<UserResponseDto> searchUsersWithBookCount(int page, int size, String name, String email, Boolean enabled,
                                        LocalDate startDate, LocalDate endDate, Integer bookCount){
        int offset = (page-1)*size;
        int totalItems = usersMapper.countSearchUsers(name,email,enabled,startDate,endDate,bookCount);

        List<UserResponseDto> userDtos = usersMapper.searchUsersWithBookCount(size,offset,name,email,enabled,startDate,endDate,bookCount);

        return new PageDto<>(page,size,totalItems,userDtos);
    }

    public PageDto<HostResponseDto> searchHostsWithRoomCount(int page, int size, String name, String email, Boolean enabled,
                                                LocalDate startDate, LocalDate endDate, Integer bookCount){
        int offset = (page-1)*size;
        int totalItems = usersMapper.countHostsWithRoomCount(name,email,enabled,startDate,endDate,bookCount);

        List<HostResponseDto> hosts = usersMapper.searchHostsWithRoomCount(size,offset,name,email,enabled,startDate,endDate,bookCount);

        return new PageDto<>(page,size,totalItems,hosts);
    }

    public HostResponseDto findHostByUserId(int userId){
        return usersMapper.findHostByUserId(userId);
    }

    public void makeAdmin(String userId){
        usersMapper.makeAdmin(userId);
    }

    public PageDto<UserReportDto> searchUserReports(int page, int size,String reporterEmail,
                                                    String reportedUserEmail,
                                                    String reportReason,
                                                    String status,
                                                    LocalDate startReportDate,
                                                    LocalDate endReportDate,
                                                    LocalDate startProcessedAt,
                                                    LocalDate endProcessedAt){

        int offset = (page-1)*size;
        int totalItems = usersMapper.countUserReports(reporterEmail,reportedUserEmail,reportReason,status,startReportDate,endReportDate,startProcessedAt,endProcessedAt);

        List<UserReportDto> reportDtos = usersMapper.searchUserReports(size, offset,reporterEmail,reportedUserEmail,reportReason,status,startReportDate,endReportDate,startProcessedAt,endProcessedAt);

        return new PageDto<>(page,size,totalItems,reportDtos);
    };

    public UserReportDto getReportById(int reportId){
        return usersMapper.getReportById(reportId);
    }

    public void updateReportStatus(UserReportDto userReportDto){
        usersMapper.updateReportStatus(userReportDto);
    }


}
