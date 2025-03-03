package com.minbak.web.users;

import com.minbak.web.spring_security.jwt.RefreshTokenDto;
import com.minbak.web.payments.PaymentDto;
import com.minbak.web.rooms.RoomsDto;
import com.minbak.web.user_YH.dto.DetailUserResponse;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface UsersMapper {

    //
    public DetailUserResponse getUserInfo(int userId);

    // 유저 아이디로 유저조회
    public UserDto findUserByUserId(Integer userId);

    // 회원가입
    public void createUser(UserDto userDto);

    // 역할 목록 조회 (예시: 제한된 수의 역할)
    public List<RoleDto> findRoles(int limit);

    // 사용자 역할 삽입 (userId와 roleId로 연결)
    public void createUserRoleByUserIdAndRoleId(int userId, int roleId);

    // 이메일로 사용자 조회 (이메일 중복 체크용)
    public String findUserEmailByEmail(String email);

    // 이메일로 사용자 전체 조회
    public UserDto findUserByEmail(String email);

    //username으로 사용자 조회
    int findUserIdByEmail(String email);

    // 사용자 ID로 역할 조회
    public List<RoleDto> findRolesByUserId(Integer userId);

    //리프레시 토큰 저장
    public void createRefreshTokenData(RefreshTokenDto RefreshTokenDto);
    // 페이지에 보여줄 유저 조회
    public List<UserDto> findUsersByLimitAndOffset(int limit, int offset);

    //limit와 offset값, 그리고 문자열값에 따라 게시글 조회
    public List<UserDto> findUsersByLimitAndOffsetAndString(int limit, int offset, String search);

    //모든 유저 수 조회
    public int countAllUsers();

    //모든 HOST(role_id = 2)조회
    public int countUserRolesByRoleId(int roleId);

    //오늘 가입한 유저 수 가져오기
    public int countUsersJoinedToday();

    //검색에 해당하는 유저 수 가져오기
    public int countUsersBySearch(String search);

    //지난 7일동안 가입한 유저 수 Map으로 가져오기
    public List<Map<Integer, Integer>> countUsersJoinedByWeekday();

    public UserDto findUserByUserId(int userId);

    public void updateUserByIdWithoutPassword(UserDto userDto);

    public List<PaymentDto> findUsersPaymentsCompactInfoByLimitAndOffsetAndUserId(int limit, int offset, int userId);

    public int countPaymentsByUserId(int userId);

    public int countRoomsByUserId(int userId);

    public List<RoomsDto> findRoomsByLimitAndOffsetAndUserId(int limit, int offset, int userId);

    public void deleteUserByUserId(int userId);

    public List<UserDto> searchUsers(int limit, int offset, String name, String email, Boolean enabled,
                                     LocalDate startDate, LocalDate endDate, Integer bookCount);

    public Integer countSearchUsers(String name, String email, Boolean enabled, LocalDate startDate, LocalDate endDate, Integer bookCount);

    public List<UserResponseDto> searchUsersWithBookCount(int limit, int offset, String name, String email, Boolean enabled,
                                                          LocalDate startDate, LocalDate endDate, Integer bookCount);

    public void deleteRefreshTokenDataByRefreshToken(String refreshToken);
    List<HostResponseDto> searchHostsWithRoomCount(
            int limit,
            int offset,
            String name,
            String email,
            Boolean enabled,
            LocalDate startDate,
            LocalDate endDate,
            Integer roomCount,
            String isVerified
    );

    public Integer countHostsWithRoomCount(String name,
                                       String email,
                                       Boolean enabled,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       Integer roomCount);

    public HostResponseDto findHostByUserId(int userId);

    public void makeAdmin(String userId);

    public List<UserReportDto> searchUserReports(int limit, int offset,String reporterEmail,
                                                 String reportedUserEmail,
                                                 String reportReason,
                                                 String status,
                                                 LocalDate startReportDate,
                                                 LocalDate endReportDate,
                                                 LocalDate startProcessedAt,
                                                 LocalDate endProcessedAt);

    public Integer countUserReports(String reporterEmail,
                                    String reportedUserEmail,
                                    String reportReason,
                                    String status,
                                    LocalDate startReportDate,
                                    LocalDate endReportDate,
                                    LocalDate startProcessedAt,
                                    LocalDate endProcessedAt);


    public UserReportDto getReportById(int reportId);

    public void updateReportStatus(UserReportDto userReportDto);

    void updateUser(UserDto userDto);

    void updateHost(HostDto hostDto);

    public Boolean checkRefreshTokenIsExpired(String refreshToken);

    public void deleteExpiredRefreshTokens(Timestamp timestamp);

    void insertHost(HostDto hostDto);

    void deleteHostByUserId(int userId);


}
