package com.neurotoxin.steamclone.controller;

import com.neurotoxin.steamclone.Entity.Tag;
import com.neurotoxin.steamclone.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagApiTestController {

    private final TagService tagService;

    @GetMapping("/api/test/tag")
    public List<Tag> tagList() {
        return tagService.findAllTags();
    }

    // 이상한거 입력할 때도, Request 200 ex) DB: {"name": "adult"} - url: tag/adul
    @GetMapping("/api/test/tag/{tagId}")
    public Tag findTagById(@PathVariable Long tagId) {
        return tagService.findTagById(tagId);
    }

    @GetMapping("/api/test/tag/search/{tagName}")
    public List<Tag> findTagByName(@PathVariable String tagName) {
        return tagService.findTagByName(tagName);
    }

    @PostMapping("/api/test/tag/add")
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @DeleteMapping("/api/test/tag/{tagId}")
    public void deleteTag(@PathVariable Long tagId) {
        tagService.delete(tagId);
    }

    @PutMapping("/api/test/tag/{tagId}")
    public void updateTag(@PathVariable Long tagId, @RequestBody Tag newTag) {
        tagService.update(tagId, newTag);
    }

}