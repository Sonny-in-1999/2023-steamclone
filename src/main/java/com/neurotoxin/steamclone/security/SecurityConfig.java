package com.neurotoxin.steamclone.security;

import com.neurotoxin.steamclone.repository.single.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * HTTP Request이 들어온 이후의 설정을 관리합니다.
 * 기존에 사용하던 WebSecurityConfigurerAdapter의 지원이 종료되어 직접 전부 Bean을 등록하여 컨테이너에서 관리합니다.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 사용자 인증을 위한 UserDetailsService와 패스워드 인코더를 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 서버에 인증정보를 저장하지 않기 때문에 csrf가 필요없음(JWT를 쿠키에 저장하지 않음)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT를 사용하기 때문에 세션을 비활성화
                .and()
                .formLogin().disable() // Form Based 인증을 비활성화
                .httpBasic().disable() // HTTP Basic 인증을 비활성화
                .authorizeRequests()
                .antMatchers("/account/**", "/history/purchase/**").authenticated()
                .antMatchers("/products/add", "/products/edit/**", "/products/delete/**").hasAnyRole("DEVELOPER") // 개발자만 접근 가능
                .anyRequest().permitAll() // 이외에 요청은 모두 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
