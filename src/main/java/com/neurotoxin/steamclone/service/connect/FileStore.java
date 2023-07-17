package com.neurotoxin.steamclone.service.connect;

import com.neurotoxin.steamclone.entity.single.Game;
import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.entity.single.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FileStore {

//    @Value("${spring.servlet.multipart.location}")
//    private String savedMediaPath;
    private String savedMediaPath = "D:\\Programming\\Team Project\\2023-steamclone\\src\\main\\resources\\media";

    // 확장자 추출 메소드
    private String getExt(String fileName) {
        int idx = fileName.lastIndexOf(".");
        if (idx > 0 && idx < fileName.length() - 1) {
            return fileName.substring(idx + 1).toLowerCase();
        } else {
            return ""; // Return an empty string for files without extensions.
        }
    }
    // 파일 이름 생성 메소드 (파일 원본 이름 -> 저장용 이름)
    public String generateStoreFileName(String fileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = "." + getExt(fileName);

        return uuid + ext;
    }
    // 파일 타입 생성 메소드
    public MediaType setMediaType(String fileName) {
        String ext = getExt(fileName);
        MediaType mediaType;

        if (Arrays.asList("jpg", "jpeg", "png", "gif").contains(ext)) {
            mediaType = MediaType.SCREENSHOT;
        } else if (Arrays.asList("mp4", "avi", "mov").contains(ext)) {
            mediaType = MediaType.VIDEO;
        } else {
            throw new UnsupportedOperationException("Unsupported file extension.");
        }

        return mediaType;
    }
    // 파일 저장 경로 설정 메소드 - 혹시 모를 확장을 위해 VIDEO를 elif로 처리
    public String setPath(String storageName, MediaType mediaType, Object entity) {
        StringBuilder subDir = new StringBuilder();

        if (entity instanceof Game) {       // 엔티티를 식별자로 받아, Game이면 디렉토리를 app/으로 설정합니다.
            subDir.append("app/");
        }
        if (mediaType == MediaType.SCREENSHOT) {
            subDir.append("images");
        } else if (mediaType == MediaType.VIDEO) {
            subDir.append("vids");
        } else {
            throw new IllegalStateException("Invalid File Type.");
        }

        String fullPath = Paths.get(savedMediaPath, subDir.toString(), storageName).toString();
        return fullPath;
    }

    // 파일을 Media 객체로 반환
    public Media fileToMedia(MultipartFile file, Object entity) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        String storageName = generateStoreFileName(fileName);
        MediaType mediaType = setMediaType(fileName);

        try { file.transferTo(new File(setPath(storageName, mediaType, entity))); }
        catch (IllegalStateException e) {
            throw new IllegalStateException("확인할 수 없는 확장자의 파일입니다.");
        }

        Media media = new Media();
        media.setFileName(fileName);
        media.setStorageName(storageName);
        media.setMediaType(mediaType);

        return media;
    }

}
