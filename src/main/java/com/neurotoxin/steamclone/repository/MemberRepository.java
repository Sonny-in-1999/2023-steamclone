package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberById(Long memberId);
}
