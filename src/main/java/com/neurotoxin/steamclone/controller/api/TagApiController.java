package com.neurotoxin.steamclone.controller.api;

import com.neurotoxin.steamclone.entity.single.Tag;
import com.neurotoxin.steamclone.service.single.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagApiController {

    private final TagService tagService;

    @GetMapping("/api/test/tag")
    public List<Tag> tagList() {
        return tagService.findAllTags();
    }

    @GetMapping("/api/test/tag/{tagId}")
    public Tag findTagById(@PathVariable Long tagId) {
        return tagService.findTagById(tagId);
    }

    @GetMapping("/api/test/tag/search/{tagName}")
    public Tag findTagByName(@PathVariable String tagName) {
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