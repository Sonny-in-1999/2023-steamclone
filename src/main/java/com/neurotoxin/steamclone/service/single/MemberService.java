package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Grade;
import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.repository.single.MemberRepository;
import com.neurotoxin.steamclone.repository.single.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final LibraryService libraryService;
    private final WalletRepository walletRepository;
    private final CommunityService communityService;
    private final CommentService commentService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 멤버 객체 생성
    @Transactional
    public Member create(Member member, String role) {
        validateDuplicateMember(member);
        member.getWallet().setMember(member);
        member.getLibrary().setMember(member);
        member.setNickName(member.getLoginName());
        member.setRole(role);
        String encodedPassword = bCryptPasswordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        return memberRepository.save(member);
    }

    // 일반 유저 회원가입
    @Transactional
    public void registerUser(Member member) {
        create(member, "ROLE_USER");
    }

    // 개발자 회원가입
    @Transactional
    public void registerDev(Member member) {
        create(member, "ROLE_DEVELOPER");
    }

    // *어드민 계정 생성(어드민 회원 가입)
    @Transactional
    public void registerAdmin(Member member) {
        create(member, "ROLE_ADMIN");
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByLoginName(member.getLoginName());
        if (findMember != null) {
            throw new IllegalStateException("이미 등록된 아이디 입니다.");
        }
    }

    // 유저 전건 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    // 유저 단건 조회(id로 검색)
    public Member findMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }

    // 유저 단건 조회(이름으로 검색)
    public Member findMemberByLoginName(String loginName) {
        return memberRepository.findByLoginName(loginName);
    }

    // 닉네임을 통한 유저 검색(유저 검색에 이용)
    public List<Member> findMemberByNickName(String nickName) {
        return memberRepository.findByNickName(nickName);
    }

    // 닉네임에 키워드를 포함하는 유저 검색(유저 검색에 이용)
    public List<Member> findMemberByNickNameContaining(String nickName) {
        return memberRepository.findByNickNameContaining(nickName);
    }

    // 유저 삭제(회원 탈퇴)
    @Transactional
    public Member delete(Long memberId) {
        Member findMember = memberRepository.findMemberById(memberId);
        validateMember(findMember);
        libraryService.delete(findMember.getLibrary());
        walletRepository.delete(findMember.getWallet());
        commentService.disconnectFromEntity(findMember);
        communityService.disconnectFromEntity(findMember);

        memberRepository.delete(findMember);
        return findMember;
    }

    private void validateMember(Member member) {
        Member findMember = memberRepository.findMemberById(member.getId());
        if (findMember == null) {
            throw new NullPointerException("존재하지 않는 회원입니다.");
        }
    }

    // 유저 정보 수정
    // 패스워드 변경 구현 로직 필요
    @Transactional
    public void update(Long memberId, Member newMember) {
        Member oldMember = memberRepository.findMemberById(memberId);

        oldMember.setEmail(newMember.getEmail());
        oldMember.setNickName(newMember.getNickName());
        oldMember.setPhoneNumber(newMember.getPhoneNumber());

        memberRepository.save(oldMember);
    }
}
