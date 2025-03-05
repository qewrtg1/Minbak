package com.minbak.web.spring_security.OAuth2;

import com.minbak.web.spring_security.jwt.JwtUtil;
import com.minbak.web.users.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    public final JwtUtil jwtUtil;

    public final UsersService usersService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customOAuth2UserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String email = customOAuth2UserDetails.getEmail();

        List<String> roles = customOAuth2UserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // 권한 이름(String) 추출
                .toList();

        String accessToken = jwtUtil.generateAccessToken(email, roles);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        //토큰을 createRefreshCookie메서드(아래정의됨)로 쿠키에 추가
        response.addCookie(jwtUtil.createRefreshCookie("refreshToken", refreshToken));

        //리프레시 토큰 db저장
        usersService.createRefreshTokenData(email,refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);

        //엑세스토큰도 생성해서 쿠키로 전달
        response.addCookie(jwtUtil.createAccessCookie("jwtToken",accessToken));

        response.sendRedirect("/oauth2/redirect");
    }
}
