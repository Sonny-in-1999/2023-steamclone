package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.GameTag;
import com.neurotoxin.steamclone.Entity.Tag;
import com.neurotoxin.steamclone.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    // 태그 등록
    @Transactional
    public Tag create(Tag tag){
        validateDupTag(tag);
        return tagRepository.save(tag);
    }

    // 태그 전건 조회
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    // 태그 검색
    public List<Tag> findTagByName(String tagName) {
        return tagRepository.findTagByName(tagName);
    }

    // 태그 단일 조회
    public Tag findTagById(Long tagId) {
        return tagRepository.findTagById(tagId);
    }

    // 태그 삭제
    @Transactional
    public void delete(Long tagId) {
        Tag findTag = tagRepository.findTagById(tagId);

        validateTag(findTag);
        tagRepository.delete(findTag);
    }

    // 태그 수정
    @Transactional
    public void update(Long tagId, Tag newTag) {

        Tag oldTag = tagRepository.findTagById(tagId);

        oldTag.setName(newTag.getName());

        tagRepository.save(oldTag);
    }

    // 중복, NULL 예외
    private void validateTag(Tag givenTag) {
        List<Tag> findTag = tagRepository.findTagByName(givenTag.getName());
        if (findTag.isEmpty()) {
            throw new NullPointerException("존재하지 않는 태그입니다.");
        }
    }
    private void validateDupTag(Tag givenTag) {
        List<Tag> findTag = tagRepository.findTagByName(givenTag.getName());
        if (!findTag.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 태그입니다.");
        }
    }
}
