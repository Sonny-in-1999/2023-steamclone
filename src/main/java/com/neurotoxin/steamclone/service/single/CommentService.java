package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Comment;
import com.neurotoxin.steamclone.entity.single.CommentType;
import com.neurotoxin.steamclone.entity.single.Community;
import com.neurotoxin.steamclone.entity.single.Member;
import com.neurotoxin.steamclone.repository.single.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findCommentById(commentId);
    }
    // 게시글로 모든 댓글 조회
    public List<Comment> findAllCommentsByCommunity(Long communityId) {
        Community findCommunity = communityRepository.findCommunityById(communityId);
        return findCommunity.getComments();
    }
    // 멤버로 모든 댓글 조회
    public List<Comment> findAllCommentsByMember(Long memberId) {
        Member findMember = memberRepository.findMemberById(memberId);
        return findMember.getComments();
    }

    @Transactional
    public void post(Comment comment) {
        create(comment);
        comment.getMember().getComments().add(comment);
        comment.setPostDate(LocalDate.now());
    }

    @Transactional
    public void delete(Long commentId, Long memberId) {
        Comment findComment = commentRepository.findCommentById(commentId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findComment.getMember().getId() == findMember.getId()) {
            commentRepository.delete(findComment);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    @Transactional
    public void update(Long commentId, Long memberId, Comment newComment) {
        Comment findComment = commentRepository.findCommentById(commentId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findComment.getMember().getId() == findMember.getId()) {
            findComment.setContent(newComment.getContent());
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }
}
