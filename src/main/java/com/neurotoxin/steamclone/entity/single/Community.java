package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
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

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private CommunityType communityType;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Media> media;

    @OneToMany(mappedBy = "community", fetch = EAGER)
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
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

    public void setHub(Hub hub) {
        this.hub = hub;
    }
}
