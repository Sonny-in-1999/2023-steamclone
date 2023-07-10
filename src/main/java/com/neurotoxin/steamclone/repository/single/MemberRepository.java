package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberById(Long memberId);

    Member findByLoginName(String loginName);

    List<Member> findByNickName(String nickName);

    List<Member> findByNickNameContaining(String nickName);

}
