package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.*;
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
    public void delete(Comment comment) {                                   // 관리자용 댓글 삭제 메소드
        commentRepository.delete(comment);
    }
    // 댓글 작성
    @Transactional
    public void post(Comment comment) {
        create(comment);
        comment.getMember().getComments().add(comment);
        comment.setPostDate(LocalDate.now());
    }
    // (유저) 댓글 삭제 메소드
    @Transactional
    public void delete(Long commentId, Long memberId) {
        Comment findComment = commentRepository.findCommentById(commentId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findComment.getMember().getId().equals(findMember.getId())) {
            disconnect(findComment);
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    @Transactional
    public void update(Long commentId, Long memberId, Comment newComment) {
        Comment findComment = commentRepository.findCommentById(commentId);
        Member findMember = memberRepository.findMemberById(memberId);

        if (findComment.getMember().getId().equals(findMember.getId())) {
            findComment.setContent(newComment.getContent());
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }

    // Entity 내 단일 댓글 삭제
    @Transactional
    public void removeCommentFromEntity(Comment comment, List<Comment> comments) {
        int findIndex = comments.indexOf(comment);
        if (findIndex != -1) {
            comments.remove(findIndex);
        }
    }

    // 해당 댓글에 대해 연결된 모든 Entity에서, 댓글을 삭제
    @Transactional
    public void disconnect(Comment findComment) {
        List<Comment> memberComments = findComment.getMember().getComments();
        List<Comment> gameComments = findComment.getGame().getComments();
        List<Comment> communityComments = findComment.getCommunity().getComments();

        removeCommentFromEntity(findComment, memberComments);
        removeCommentFromEntity(findComment, gameComments);
        removeCommentFromEntity(findComment, communityComments);

        commentRepository.delete(findComment);
    }

    // Entity를 삭제할 때, 해당 Entity에 대해 연결된 모든 Entity에서 댓글 삭제
    @Transactional
    public void disconnectFromEntity(Object param) {
        if (param instanceof Member || param instanceof Game || param instanceof Community) {
            List<Comment> comments;
            if (param instanceof Member) {
                comments = ((Member) param).getComments();
            } else if (param instanceof Game) {
                comments = ((Game) param).getComments();
            } else {
                comments = ((Community) param).getComments();
            }

            if (comments != null) {
                for (Comment comment : comments) {
                    disconnect(comment);
                }
            }
        } else {
            System.out.println(param);
            throw new IllegalStateException("잘못된 패러미터를 전달받았습니다.");
        }
    }
}
