package com.neurotoxin.steamclone.entity.connect;

import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.entity.single.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FileStore {

    @Value("${spring.file.upload.location}")
    private String savedMediaPath;

    // 확장자 추출 메소드
    private String getExt(String fileName) {
        int idx = fileName.lastIndexOf(".");
        return fileName.substring(idx);
    }
    // 파일 이름 생성 메소드 (파일 원본 이름 -> 저장용 이름)
    private String generateStoreFileName(String fileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = getExt(fileName);

        return uuid + ext;
    }
    // 파일 타입 생성 메소드
    private MediaType setMediaType(String fileName) {
        String ext = getExt(fileName);
        MediaType mediaType;

        if (Arrays.asList("jpg", "jpeg", "png", "gif").contains(ext)) {
            mediaType = MediaType.SCREENSHOT;
        } else if (Arrays.asList("mp4", "avi", "mov").contains(ext)) {
            mediaType = MediaType.VIDEO;
        } else {
            throw new IllegalArgumentException("Unsupported file extension");
        }

        return mediaType;
    }
    // 파일 저장 경로 설정 메소드 - 혹시 모를 확장을 위해 VIDEO를 elif로 처리
    public String setPath(String storageName, MediaType mediaType) {
        if (mediaType == MediaType.SCREENSHOT) {
            return savedMediaPath + "images" + storageName;
        } else if (mediaType == MediaType.VIDEO) {
            return savedMediaPath + "vids" + storageName;
        } else {
            throw new IllegalStateException("Invalid File Type");
        }
    }
    // 파일을 Media 객체로 반환
    @Transactional
    public Media fileToMedia(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        String storageName = generateStoreFileName(fileName);
        MediaType mediaType = setMediaType(fileName);

        try { file.transferTo(new File(setPath(storageName, mediaType))); }
        catch (IllegalStateException e) {
            System.out.println("확인할 수 없는 확장자의 파일입니다.");
            return null;
        }

        Media media = new Media();
        media.setFileName(fileName);
        media.setStorageName(storageName);
        media.setMediaType(mediaType);

        return media;
    }
}
