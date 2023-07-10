package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagById(Long tagId);

    Tag findTagByName(String tagName);
}
