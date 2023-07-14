package com.neurotoxin.steamclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록 된다.
// @secured 어노테이션 활성화 => 컨트롤러별 권한 설정 가능, @PreAuthorize/@PostAuthorize 어노테이션 활성화 => 컨트롤러 실행 전/후에 권한 체크가능
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        return http.authorizeRequests()
                .antMatchers("/account/**").authenticated()
                .antMatchers("/products/add", "/products/edit/**", "/products/delete/**").access("hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**", "/tags/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("loginName")
                .defaultSuccessUrl("/")
                .and().build();// /login 주소가 호출 되면 시큐리티가 낚아채서 대신 로그인을 진행
    }
}
