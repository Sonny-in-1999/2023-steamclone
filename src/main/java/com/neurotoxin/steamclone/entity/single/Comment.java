package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private LocalDate postDate;

    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
