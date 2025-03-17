package com.minbak.web.users;

import com.minbak.web.categories.CategoryDto;
import com.minbak.web.categories.OptionDto;
import com.minbak.web.spring_security.jwt.RefreshTokenDto;
import com.minbak.web.common.dto.PageDto;
import com.minbak.web.payments.PaymentDto;
import com.minbak.web.user_YH.RoomDetailMapper;
import com.minbak.web.user_YH.dto.DetailUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RoomDetailMapper roomDetailMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public DetailUserResponse getUserInfo(int userId){
        if(userId > 0){
            DetailUserResponse user = usersMapper.getUserInfo(userId);
            user.setProfileImageUrl(roomDetailMapper.findImageUrlsByUserId(userId));
            return user;
        }else {
            return null;
        }
    }

    public UserDto findUserByUserId(Integer userId){
        return usersMapper.findUserByUserId(userId);
    }

    public void createUser(UserDto userDto){

        //중복된 이메일이 있는지 확인
        if(usersMapper.findUserEmailByEmail(userDto.getEmail()) == null){
            //페스워드 암호화
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
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

    public void createRefreshTokenData(String username,String refreshToken, Long expirationMs){

        //현 시간에 expirationMs 값 추가
        LocalDateTime expirationDate = LocalDateTime.now().plus(Duration.ofMillis(expirationMs));

        Timestamp timestamp = Timestamp.valueOf(expirationDate);

        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
        refreshTokenDto.setUsername(username);
        refreshTokenDto.setRefreshToken(refreshToken);
        refreshTokenDto.setExpiration(timestamp);

        usersMapper.createRefreshTokenData(refreshTokenDto);
    }

    public void deleteRefreshTokenDataByRefreshToken(String refreshToken){
        usersMapper.deleteRefreshTokenDataByRefreshToken(refreshToken);
    }

    public Boolean checkRefreshTokenIsExpired(String refreshToken){
        return usersMapper.checkRefreshTokenIsExpired(refreshToken);
    }

    public void deleteExpiredRefreshTokens(){
        LocalDateTime now = LocalDateTime.now();
        Timestamp currentTime = Timestamp.valueOf(now);
        usersMapper.deleteExpiredRefreshTokens(currentTime);
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

    public UserPageDto<UserRoomsDto> findRoomsByLimitAndOffsetAndUserId(int page, int size, int userId){

        int offset = (page-1)*size;
        List<UserRoomsDto> roomsDtos = usersMapper.findRoomsByLimitAndOffsetAndUserId(size,offset,userId);

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
                                                             LocalDate startDate, LocalDate endDate, Integer bookCount, String isVerified){
        int offset = (page-1)*size;
        int totalItems = usersMapper.countHostsWithRoomCount(name,email,enabled,startDate,endDate,bookCount);

        List<HostResponseDto> hosts = usersMapper.searchHostsWithRoomCount(size,offset,name,email,enabled,startDate,endDate,bookCount,isVerified);

        return new PageDto<>(page,size,totalItems,hosts);
    }

    public HostResponseDto findHostByUserId(int userId){
        return usersMapper.findHostByUserId(userId);
    }

    public void makeAdmin(String userId){
        usersMapper.makeAdmin(userId);
    }

    public void makeHost(Integer userId) {
        usersMapper.makeHost(userId);
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

    public void updateUser(UserDto userDto) {
        usersMapper.updateUser(userDto); // MyBatis XML 매퍼 호출
    }

    public void updateHost(HostDto hostDto) {
        usersMapper.updateHost(hostDto);
    }

    public Integer findUserIdByEmail(String email){
        if (email.equals("anonymousUser")){
            return null;
        }
        return usersMapper.findUserIdByEmail(email);
    }

    public void createHost(HostDto hostDto){
        usersMapper.insertHost(hostDto);
    }

    public void insertRoom(UserRoomsDto userRoomsDto){
        usersMapper.insertRoom(userRoomsDto);
    }

    public List<CategoryDto> getAllCategories(){
        return usersMapper.getAllCategories();
    }

    public Map<String, List<OptionDto>> getOptionsGroupedByCategory() {
        List<OptionDto> options = usersMapper.getOptionsGroupedByCategory();
        return options.stream()
                .collect(Collectors.groupingBy(OptionDto::getOptionsCategory)); // 카테고리별로 그룹화
    }

    public UserRoomsDto getRoomById(int roomId){
        return usersMapper.getRoomById(roomId);
    }

    // 숙소에 선택한 카테고리 업데이트
    public void updateRoomCategories(int roomId, List<Integer> categoryIds) {
        usersMapper.deleteRoomCategories(roomId); // 기존 카테고리 삭제
        if (categoryIds != null && !categoryIds.isEmpty()) {
            usersMapper.insertRoomCategories(roomId, categoryIds);
        }
    }

    // 숙소에 선택한 옵션 업데이트
    public void updateRoomOptions(int roomId, List<Integer> optionIds) {
        usersMapper.deleteRoomOptions(roomId); // 기존 옵션 삭제
        if (optionIds != null && !optionIds.isEmpty()) {
            usersMapper.insertRoomOptions(roomId, optionIds);
        }
    }

    public void createHostRoleByUserIdAndRoleId(int userId, int role){
        usersMapper.createUserRoleByUserIdAndRoleId(userId, role);
    }

    public List<RoleDto> findRolesByUserId(int userId){
        return usersMapper.findRolesByUserId(userId);
    }

    public String findUserEmailByUserId(int userId){
        return  usersMapper.findUserEmailByUserId(userId);
    }
}
