package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.*;
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
    private final CommentService commentService;

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
    public void post(Community community, List<Media> media) {
        create(community);
        if (!media.isEmpty()) {
            community.setMedia(media);
        }
        community.getMember().getCommunities().add(community);
        community.setPostDate(LocalDate.now());
        community.setUpdateDate(null);
    }
    // (유저) 댓글 삭제 메소드
    @Transactional
    public void delete(Long communityId, Long memberId) {
        Community findCommunity = communityRepository.findCommunityById(communityId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findMember.getId().equals(findCommunity.getMember().getId())) {
            disconnect(findCommunity);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    @Transactional
    public void update(Long communityId, Long memberId, Community newCommunity) {
        Community findCommunity = communityRepository.findCommunityById(communityId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findMember.getId().equals(findCommunity.getMember().getId())) {
            findCommunity.setTitle(newCommunity.getTitle());
            findCommunity.setContent(newCommunity.getContent());
            findCommunity.setUpdateDate(LocalDate.now());
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    // Entity 내 단일 글 삭제
    @Transactional
    public void removeCommunityFromEntity(Community community, List<Community> communities) {
        int findIndex = communities.indexOf(community);
        if (findIndex != -1) {
            communities.remove(findIndex);
        }
    }

    // 해당 글에 대해 연결된 모든 Entity에서, 글을 삭제
    @Transactional
    public void disconnect(Community findCommunity) {
        List<Community> memberCommunities = findCommunity.getMember().getCommunities();
        List<Community> hubCommunities = findCommunity.getHub().getCommunities();

        removeCommunityFromEntity(findCommunity, memberCommunities);
        removeCommunityFromEntity(findCommunity, hubCommunities);

        mediaService.disconnect(findCommunity);
        commentService.disconnectFromEntity(findCommunity);

        communityRepository.delete(findCommunity);
    }

    // Entity를 삭제할 때, 해당 Entity에 대해 연결된 모든 Entity에서 글을 삭제
    @Transactional
    public void disconnectFromEntity(Object param) {
        if (param instanceof Member || param instanceof Hub) {
            List<Community> communities;
            if (param instanceof Member) {
                communities = ((Member) param).getCommunities();
            } else {
                communities = ((Hub) param).getCommunities();
            }

            if (communities != null) {
                for (Community community : communities) {
                    disconnect(community);
                }
            }
        } else {
            System.out.println(param);
            throw new IllegalStateException("잘못된 패러미터를 전달받았습니다.");
        }
    }
}
