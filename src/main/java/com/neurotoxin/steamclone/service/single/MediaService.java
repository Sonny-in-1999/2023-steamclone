package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Community;
import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.repository.single.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaService {

    private final MediaRepository mediaRepository;

    @Transactional
    public Media create(Media media) {
        return mediaRepository.save(media);
    }

    @Transactional
    public void delete(Media media) {
        mediaRepository.delete(media);
    }

    /* API */
    @Transactional
    public void attach(Media media) {
        create(media);
        media.getCommunity().getMedia().add(media);
    }
    // 글에서 해당 미디어를 제거합니다.
    @Transactional
    public void remove(Media media, List<Media> communityMedia) {
        int findIndex = communityMedia.indexOf(media);
        if (findIndex != -1) {
            communityMedia.remove(findIndex);
        }
        delete(media);
    }
    // 글을 삭제할 때, 해당 글의 미디어를 모두 삭제합니다.
    @Transactional
    public void disconnect(Community community) {
        List<Media> media = community.getMedia();
        for (Media medium : media) {
            delete(medium);
        }
    }
}
