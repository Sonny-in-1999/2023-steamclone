package com.neurotoxin.steamclone.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * JWT 인증을 위해 생성되는 토큰입니다.
 * 요청과 함께 바로 실행이 됩니다. (요청이 들어오면 헤더에서 토큰을 추출합니다.)
 * 토큰을 추출 후 유효성 검사를 실시합니다.
 */
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 1. Autorization이 Bearer임을 확인
     * 2. 엑세스토큰을 가져와 토큰을 다시 복호화
     * 3. 모든 검증이 끝나면 토큰을 인증받은 유저인 UsernamePasswordAuthenticationToken을 리턴
     * 4. 위 과정이 모두 끝나면 해당 유저는 토큰이 유효한 유저임이 증명이 되고 SecurityContext에 저장됨.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);

        // 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    // 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
