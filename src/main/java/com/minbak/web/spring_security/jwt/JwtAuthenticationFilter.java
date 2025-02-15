package com.minbak.web.spring_security.jwt;


import com.minbak.web.spring_security.CustomUserDetails;
import com.minbak.web.users.UsersController;
import com.minbak.web.users.UsersMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    UsersMapper usersMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 요청 헤더에서 Authorization 값 가져오기
        final String authHeader = request.getHeader("Authorization");

        String jwtToken = null;

        // Authorization 헤더가 존재하고 "Bearer "로 시작하는지 확인
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // "Bearer " 이후의 JWT 토큰 추출
            jwtToken = authHeader.substring(7);

            //jwtToken이 유효하지 않으면 401에러 반환해서 /refresh 메서드 실행
            if (!jwtUtil.validateToken(jwtToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token expired or invalid");

                return;  // 필터 체인 중단
            }

            // JWT에서 사용자명(username)과 역할(roles) 정보 추출
            String username = jwtUtil.extractUsername(jwtToken);
            List<String> roles = jwtUtil.extractRoles(jwtToken);

            // 사용자명과 역할이 존재하면 인증 객체 생성
            if (username != null && roles != null) {

                // DB 조회 없이 JWT 정보로 UserDetails 객체 생성
                UserDetails userDetails = new CustomUserDetails(usersMapper.findUserByEmail(username), roles);

                // JWT가 유효한 경우, SecurityContextHolder에 인증 정보 저장
                if (jwtUtil.validateToken(jwtToken)) {
                    // roles(List<String>) -> SimpleGrantedAuthority 리스트로 변환
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority(role)) // "USER" -> new SimpleGrantedAuthority("USER")
                            .collect(Collectors.toList());

                    // UsernamePasswordAuthenticationToken 생성 (인증된 사용자 객체)
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                    // SecurityContextHolder에 인증 객체 등록
                    // 등록된 후 이번 요청후에 삭제됨. 해당 요청이 끝날때까지 요청권한을 유지함
                    // 등록된 채로 다음필터로 전달되는 개념.

                    // 그럼 SecurityContextHolder에 인증정보가 저장된 후
                    // 다음 filter가 진행되고 api서버의 코드들이 진행되면서
                    // springsecurity가 권한이 필요할때마다 SecurityContextHolder에 인증정보를 확인하는 것 !
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // 다음 필터로 요청 전달
        chain.doFilter(request, response);


    }



}
