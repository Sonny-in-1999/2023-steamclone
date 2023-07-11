package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Community {

    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    private String title;

    private String content;

    private LocalDate postDate;

    private LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    private CommunityType communityType;

    @OneToMany(mappedBy = "community")
    private List<Media> media;

    @OneToMany(mappedBy = "community")
    private List<Comment> comments;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hub_id")
    private Hub hub;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public void setCommunityType(CommunityType communityType) {
        this.communityType = communityType;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
