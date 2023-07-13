package com.neurotoxin.steamclone.config.auth;


import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.repository.single.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 시큐리티 설정에서 loginProcessingUrl("/login");
 * /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행 (시큐리티에서 정해진 규칙임 ㅇㅇ)
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {


    private final MemberRepository memberRepository;

    // 시큐리티 session => Authentication => UserDetails
    // => 시큐리티 session(Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        System.out.println("Member Creation - loginName: " + loginName);
        Member member = memberRepository.findByLoginName(loginName);
        if (member != null) {
            return new PrincipalDetails(member);
        }
        return null;
    }
}
