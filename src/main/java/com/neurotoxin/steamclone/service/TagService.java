package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.Tag;
import com.neurotoxin.steamclone.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    // 태그 등록
    public Tag create(Tag tag){
        return tagRepository.save(tag);
    }

    // 태그 전건 조회
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    // 태그 단일 조회
    public Tag findTagById(Long tagId) {
        return tagRepository.findTagById(tagId);
    }

    // 태그 삭제
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }
}
