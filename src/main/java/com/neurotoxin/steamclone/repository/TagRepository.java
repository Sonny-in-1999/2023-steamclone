package com.neurotoxin.steamclone.repository;

import com.neurotoxin.steamclone.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagById(Long tagId);

    List<Tag> findTagByName(String tagName);
}
