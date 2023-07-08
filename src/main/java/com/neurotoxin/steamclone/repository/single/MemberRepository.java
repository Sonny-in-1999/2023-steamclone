package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberById(Long memberId);

    Member findByLoginName(String loginName);

}
