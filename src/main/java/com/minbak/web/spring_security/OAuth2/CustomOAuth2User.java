package com.minbak.web.spring_security.OAuth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
//API서버에서 받은 데이터를 담는 객체
//CustomUserDetails 클래스 대신 OAuth로그인은 이 클래스를 씀.
public class CustomOAuth2User implements OAuth2User {

    //아래 객체로 생성자 만들기
    private final OAuth2Response oAuth2Response;

    //나중에 유저 정보를 List나 Collection으로 받아야함
    private final List<String> roles;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        for (String role : roles) {
            collection.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "ROLE_"+role.toUpperCase();
                }
            });
        }

        return null;
    }

    @Override
    public String getName() {
        //oauth2_authorized_client 테이블에 저장될 primary키(principal_name)를 바꿔주기 위해 커스텀
        return oAuth2Response.getName()+","+oAuth2Response.getProviderId();
    }

    public String getUsername(){
        return oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    }
}
