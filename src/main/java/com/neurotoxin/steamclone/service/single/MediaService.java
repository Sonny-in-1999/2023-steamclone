package com.neurotoxin.steamclone.service.single;

import com.neurotoxin.steamclone.entity.connect.FileStore;
import com.neurotoxin.steamclone.entity.single.Community;
import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.repository.single.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaService {

    private final MediaRepository mediaRepository;
    private final FileStore fileStore;

    @Transactional
    public Media create(Media media) {
        return mediaRepository.save(media);
    }

    @Transactional
    public void delete(Media media) {
        mediaRepository.delete(media);
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

    // 여러 파일을 리스트로 반환
    public List<Media> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Media> media = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                media.add(fileStore.fileToMedia(multipartFile));
            }
        }

        return media;
    }

//    // 첨부된 여러 파일을 타입 상관 없이 평면화하여 리스트에 저장
//    public List<Media> saveMedia(Map<MediaType, List<MultipartFile>> multipartFileListMap) throws IOException{
//        List<Media> images = storeFiles(multipartFileListMap.get(MediaType.SCREENSHOT), MediaType.SCREENSHOT);
//        List<Media> vids = storeFiles(multipartFileListMap.get(MediaType.VIDEO), MediaType.VIDEO);
//
//        List<Media> result = Stream.of(images, vids)
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
//
//        return result;
//    }
}
