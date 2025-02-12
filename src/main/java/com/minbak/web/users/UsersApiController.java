package com.minbak.web.users;

//import com.minbak.web.spring_security.CustomUserDetails;
//import com.minbak.web.spring_security.CustomUserDetailsService;
//import com.minbak.web.spring_security.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsersApiController {
//    @Value("${jwt.refresh-token-expiration-time}")
//    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private final UsersService usersService;
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDTO) {
        usersService.createUser(userDTO);
        return ResponseEntity.ok("Signup successful");
    }

//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> user, HttpServletResponse response) {
//        try {
//            // AuthenticationManager.authenticate()가 호출될 때 CustomUserDetailService 실행
//            //AuthenticationManager는 Spring Sequrity 인증관리 객체
//            //UsernamePasswordAuthenticationToken는 username과 password를 받아서 인증확인
//            //authenticate 메서드가 CustomUserDetailService를 username으로 실행시킴 SecurityConfig를 보면 login경로설정을 주석화해놓음
//            //그렇게 생성한 UserDetails와 어려것들을 이용해 Authentication(인증객체)생성
//            //SecurityContext(api의 토큰인증 정보)에 해당토큰의 인증정보도 추가함
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.get("username"), user.get("password"))
//            );
//
//            String username = authentication.getName();
//            List<String> roles = authentication.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toList());
//
//            //accessToken은 로컬스토리지, refreshToken은 쿠키에 저장하고 평소엔 accessToken으로 인증하다
//            //accessToken이 만료돼서 에러메시지(401)를 반환하면 클라이언트서버에서 refreshToken를 갖고 @PostMapping("/refresh")를 요청한다
//            String accessToken = jwtUtil.generateAccessToken(username, roles);
//            String refreshToken = jwtUtil.generateRefreshToken(username);
//
//            // HttpOnly(JS로 수정 불가능) 쿠키 설정 (Refresh Token) 바로 클라이언트서버의 쿠키에 저장함
//
//            //토큰을 createRefreshCookie메서드(아래정의됨)로 쿠키에 추가
//            response.addCookie(createRefreshCookie("refreshToken", refreshToken));
//
//            //리턴받은 accessToken을 로컬에 저장하고 api요청시마다 헤더에 요청
//            return ResponseEntity.ok(Map.of("accessToken", accessToken));
//
//        } catch (AuthenticationException e) {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//
//    @PostMapping("/refresh")
//    //HttpServletRequest 서블릿은 클라이언트의 요청을 받아오는 자바객체
//    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request,HttpServletResponse response) {
//
//        String refreshToken = jwtUtil.getRefreshTokenFromCookies(request);
//
//        if (!jwtUtil.validateToken(refreshToken)) {
//            //401에러 전달 로그인페이지로 이동시켜야함
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Refresh Token"));
//        }
//
//        //리프레시토큰의 유저네임 받아와서
//        String username = jwtUtil.extractUsername(refreshToken);
//        //유저네임으로 UserDetails생성
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        //Refresh Rotate(리프레시토큰 재발급)
//        String newRefreshToken = jwtUtil.generateRefreshToken(username);
//        response.addCookie(createRefreshCookie("refreshToken", newRefreshToken));
//
//        //accessToken 전달(클라이언트서버에서 로컬에 저장)
//        String newAccessToken = jwtUtil.generateAccessToken(username, roles);
//        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
//    }
//
//    @GetMapping("/user/{username}")
//    public ResponseEntity<String> user(@PathVariable String username,
//                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
//        // 인증된 사용자 확인
//        String authenticatedUsername = userDetails.getUsername();
//
//        // 요청한 username과 인증된 사용자가 일치하는지 확인
//        if (!authenticatedUsername.equals(username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
//        }
//
//        return ResponseEntity.ok("사용자 페이지: " + username);
//    }

//    private Cookie createRefreshCookie(String key, String value) {
//
//        Cookie cookie = new Cookie(key, value);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge((int) REFRESH_TOKEN_EXPIRATION_TIME / 1000);
//
//        return cookie;
//    }

    @GetMapping("/users")
    public UserPageDto<UserDto> searchUsers(
            @RequestParam("search") String search,    // 검색어
            @RequestParam("page") int page,           // 현재 페이지
            @RequestParam("size") int size            // 페이지 크기
    ) {

        return usersService.findUsersByLimitAndOffsetAndString(page, size, search);

    }

}