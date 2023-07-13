package com.neurotoxin.steamclone.config.auth;

import com.neurotoxin.steamclone.entity.single.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 * 로그인의 진행이 완료 되면 session을 만든다. (Security ContextHolder)
 * 오브젝트 => Authentication(인증) 타입 객체
 * Authentication(인증 객체) 안에 Member의 정보가 존재해야 함.
 * Member 오브젝트 타입 => UserDetails 타입 객체
 *
 * Security Session => Authentication => UserDetails(PrincipalDetail)
 */
public class PrincipalDetails implements UserDetails {

    private Member member;  //Composition

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // 해당 Member의 권한을 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //==사이트에서 1년 이상 접속 기록이 없을경우 휴면 계정으로 전환하는 로직==//
        // 현재시간 - 최근 로그인 시간(ex- member.getLoginDate) => 1년 이상일 경우 return false;
        return true;
    }
}
