package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Community;
import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.repository.single.CommunityRepository;
import com.neurotoxin.steamclone.repository.single.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final MediaService mediaService;

    @Transactional
    public Community create(Community community) {
        return communityRepository.save(community);
    }

    public Community findCommunityById(Long communityId) {
        return communityRepository.findCommunityById(communityId);
    }

    public List<Community> findAllCommunities() {
        return communityRepository.findAll();
    }

    @Transactional
    public void post(Community community) {
        create(community);
        community.getMember().getCommunities().add(community);
        community.setPostDate(LocalDate.now());
        community.setUpdateDate(null);
    }

    @Transactional
    public void delete(Long communityId, Long memberId) {
        Community findCommunity = communityRepository.findCommunityById(communityId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findMember.getId() == findCommunity.getMember().getId()) {
            communityRepository.delete(findCommunity);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    @Transactional
    public void update(Long communityId, Long memberId, Community newCommunity) {
        Community findCommunity = communityRepository.findCommunityById(communityId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findMember.getId() == findCommunity.getMember().getId()) {
            findCommunity.setTitle(newCommunity.getTitle());
            findCommunity.setContent(newCommunity.getContent());
            findCommunity.setUpdateDate(LocalDate.now());
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
}
