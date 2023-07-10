package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.repository.single.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void attach(Media media) {
        media.getCommunity().getMedia().add(media);
    }

    @Transactional
    public void delete(Media media) {
        mediaRepository.delete(media);
    }
}
