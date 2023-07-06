package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberById(Long memberId);

    Member findByLoginName(String loginName);

}
