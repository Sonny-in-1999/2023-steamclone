package com.neurotoxin.steamclone.service;

import com.neurotoxin.steamclone.Entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TagServiceTest {

    @Autowired
    TagService tagService;

    @Test
    @DisplayName("Create Tag")
    @Transactional
    public void createTag() throws Exception {
        //given
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();

        tag1.setName("adult");
        tag1.setName("nude");

        // when
        tagService.create(tag1);
        tagService.create(tag2);

        // then
        assertThat(tag1).isEqualTo(tagService.findTagById(tag1.getId()));
        assertThat(tag2).isEqualTo(tagService.findTagById(tag2.getId()));
    }

    @Test
    @DisplayName("Delete Tag")
    public void deleteTag() throws Exception {
        //given
        Tag tag1 = new Tag();
        tag1.setName("adult");
        Tag tag2 = new Tag();
        tag2.setName("nude");

        tagService.create(tag1);
        tagService.create(tag2);

        List<Tag> allTags = tagService.findAllTags();
        assertThat(allTags.size()).isEqualTo(2);

        tagService.delete(tag2.getId());
        List<Tag> allTagsAfDelete = tagService.findAllTags();
        assertThat(allTagsAfDelete.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Duplicate Tag")
    public void validateDupTag() throws Exception {
        //given
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();

        tag1.setName("adult");
        tag2.setName("adult");

        //when
        tagService.create(tag1);

        //then
        assertThrows(IllegalStateException.class, () ->
                tagService.create(tag2));
    }

    @Test
    @DisplayName("Unknown Tag")
    public void unknownTag() throws Exception {
        Tag tag1 = new Tag();
        tag1.setName("adult");

        assertThrows(NullPointerException.class, () ->
                tagService.delete(999L));
    }

    @Test
    @DisplayName("Tag Update")
    public void updatedTag() throws Exception {
        //given
        Tag tag1 = new Tag();
        Tag newTag = new Tag();

        tag1.setName("adult");
        newTag.setName("nude");
        tagService.create(tag1);
        // when
        tagService.update(tag1.getId(),newTag);
        Tag updatedTag = tagService.findTagById(tag1.getId());
        // then
        assertThat(updatedTag.getName()).isEqualTo(newTag.getName());
    }
}
