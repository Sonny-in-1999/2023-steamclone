package com.neurotoxin.steamclone.security;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 토큰 정보 DTO
 */
@Builder
@Data
@AllArgsConstructor
public class JwtToken {

    private String grantType; // Bearer(Jwt or OAuth에 대한 토큰을 사용)
    private String accessToken;
    private String refreshToken;
}
