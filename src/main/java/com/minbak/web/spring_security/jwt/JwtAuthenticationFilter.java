package com.minbak.web.spring_security.jwt;


import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.users.UsersMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
//OncePerRequestFilter는 모든 요청에대해 실행되는 코드라 따로 설정해줘야함
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    UsersMapper usersMapper;

    @Override
    //OncePerRequestFilter 설정 변경
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();

        // ✅ "/admin/**" 경로에서는 JWT 필터 실행하지 않음
        return requestURI.startsWith("/admin/");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        // 요청 쿠키에서 jwtToken 값 가져오기
        String jwtToken = jwtUtil.getJwtTokenFromCookies(request);

        // Authorization 헤더가 존재하고 "Bearer "로 시작하는지 확인
        if (jwtToken == null || !jwtUtil.validateToken(jwtToken)) {

            // Access Token이 유효하지 않다면 Refresh Token 확인
            // 클라이언트가 보낸 쿠키에서 refreshToken 추출
            String refreshToken = jwtUtil.getRefreshTokenFromCookies(request);

            // Refresh Token이 없거나 유효하지 않으면 필터 넘기기
            if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
                // 다음 필터로 요청 전달
                chain.doFilter(request, response);
                return;
            }

            // refreshToken에서 username 추출
            String username = jwtUtil.extractUsername(refreshToken);

            //username으로 인증
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //해당 인증객체의 roles가져와서
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // 새로운 Access Token 생성
            String newAccessToken = jwtUtil.generateAccessToken(username, roles);

            //엑세스토큰 쿠키로 전달
            response.addCookie(jwtUtil.createAccessCookie("jwtToken",newAccessToken));

            // SecurityContextHolder에 새로운 인증 정보 저장
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } else {
            // 기존 Access Token이 유효하면 사용자 인증 처리
            String username = jwtUtil.extractUsername(jwtToken);
            List<String> roles = jwtUtil.extractRoles(jwtToken);

            //user,HOST이렇게 오는 role을 ROLE_USER 이런식으로 만들어줌 UserRole 이눔?클래스
            roles.replaceAll(UserRole::toRoleName);

//            for(int i=0;i<roles.size();i++){
//                roles.set(i,UserRole.fromRoleName(roles.get(i)));
//            }

            UserDetails userDetails = new CustomUserDetails(usersMapper.findUserByEmail(username), roles);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 다음 필터로 요청 전달
        chain.doFilter(request, response);

    }

}
