package com.neurotoxin.steamclone.entity.single;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue
    @Column(name = "media_id")
    private Long id;
    private String fileName;
    private String storageName;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    public void setCommunity(Community community) {
        this.community = community;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }
}