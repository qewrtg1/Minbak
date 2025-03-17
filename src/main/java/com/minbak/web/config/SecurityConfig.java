package com.minbak.web.config;

import com.minbak.web.spring_security.OAuth2.CustomOAuth2UserService;
import com.minbak.web.spring_security.OAuth2.CustomSuccessHandler;
import com.minbak.web.spring_security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration  //해당 클래스가 Config(설정) 클래스라는 걸 정의하는 어노테이션.
@EnableWebSecurity  // Spring Security설정을 한다는 어노테이션.
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomOAuth2UserService customOAuth2UserService,CustomSuccessHandler customSuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;

    }


    @Bean  // 해당 메서드가 빈에 등록된다는 어노테이션.
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // http(HttpSecurity) 객체는 보안 관련 설정을 담당하는 객체
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth  // HTTP 요청에 대한 접근 권한을 설정합니다.
                        .requestMatchers("/admin/login", "/admin/file/**","/admin/api/**","/admin/signup","/admin/test").permitAll()
                        .requestMatchers("/admin/user/{id}").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 그 외는 인증을 요구
                        .anyRequest().authenticated()
                )

                .formLogin(auth -> auth
                        .loginPage("/admin/login") //로그인 페이지를 설정한다는 매서드(GetMapping)
                        //loginProcessingUrl은 UserDetailsService를 상속받은 클래스를 실행시키는 중요한 설정.
                        .loginProcessingUrl("/admin/login") //로그인 버튼을(PostMapping) /login으로 연결한다는 의미
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .permitAll() // 위 경로를 누구나 접근하게 허용
                )

                //매 인증마다 인증정보를 http헤더에 넣어보내는 설정 비활성화
                .httpBasic(basic -> basic.disable())

                .logout(auth -> auth
                        .logoutUrl("/admin/logout") //get메서드로 로그아웃 할 수 있게 함
                        .logoutSuccessUrl("/")
                );

        http
                //로그인 할 때마다 해당 유저의 세션 정보를 변경함(보안)
                .sessionManagement(auth -> auth
                        .sessionFixation().changeSessionId());

        //csrf는 Cross-Site Request Forgery의 약자로 도메인 요청 위조라는 뜻이다.
        //활성화하면 세션을 해킹해서 다른 도메인에 세션정보를 넣고 API서버로 요청했을 때 spring security의존성이 막아준다.
        //근데 개발환경에서는 disable해놓고 사용해야함 아래 코드를 삭제하면 enable상태로 됨.
        //get을 제외한 요청시 위조검사함. get을 제외한 요청에 _csrf.token 데이터를 줘야함.
        http
                .csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository())  // CSRF 토큰 저장소 적용
        );
//        http.csrf(csrf -> csrf.disable());

//        http
//                .sessionManagement(auth -> auth
//                        .maximumSessions(1) //최대 몇 개의 세션을 만들 수 있는지.
//                        .maxSessionsPreventsLogin(true) //true는 새로운 로그인 차단, false는 기존 로그인 세션 삭제
//                );

        return http.build();  // SecurityFilterChain으로 HttpSecurity설정 후 반환.
    }

    @Bean
    @Order(2)
    // USER 시큐리티 설정 (JWT 기반)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/user/**","/host/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 또는 Stateless 환경
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll() // 공개 API
                        .requestMatchers("/login","/api/login", "/api/refresh", "/api/signup","/","/room/**","/uploads/**").permitAll()
                        .requestMatchers("/host/**").hasRole("HOST")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(auth -> auth
                        .authenticationEntryPoint((request, response, authException) -> {
                            // JWT 토큰이 없거나 유효하지 않은 경우 로그인 페이지로 리디렉션
                            response.sendRedirect("/login");
                        })
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //UsernamePasswordAuthenticationFilter 전에 jwtAuthenticationFilter 실행
                .logout(AbstractHttpConfigurer::disable //로그아웃 비활성화
                );


        return http.build();
    }

    @Bean
    @Order(3)
    // OAuth2 시큐리티 설정 (JWT 기반)
    public SecurityFilterChain OAuth2SecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/login/**", "/oauth2/**")

                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        //jwt 발급필터
                        .successHandler(customSuccessHandler))

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())


                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    //인증을 처리하는 핵심 컴포넌트 UsernamePasswordAuthenticationToken와 같은 인증요청객체 사용시 필요
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");  // 클라이언트가 사용할 CSRF 헤더 이름
        return repository;
    }

}