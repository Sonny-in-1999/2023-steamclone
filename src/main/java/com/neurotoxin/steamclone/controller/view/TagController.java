package com.neurotoxin.steamclone.controller.view;

import com.neurotoxin.steamclone.entity.single.Tag;
import com.neurotoxin.steamclone.service.connect.GameTagService;
import com.neurotoxin.steamclone.service.single.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final GameTagService gameTagService;

    // 태그 추가 페이지
    @GetMapping("/tags/add")
    public String addTagPage(Model model) {
        model.addAttribute("tag", new Tag());
        return "tag/tag_add";
    }

    // 태그 추가
    @PostMapping("/tags/add")
    public String addTag(@ModelAttribute Tag tag) {
        tagService.create(tag);
        return "redirect:/tags/list";
    }

    // 태그 목록 페이지
    @GetMapping("/tags/list")
    public String tagListPage(Model model) {
        List<Tag> tags = tagService.findAllTags();
        model.addAttribute("tags", tags);
        return "tag/tag_list";
    }

    // 태그 수정 페이지
    @GetMapping("/tags/edit/{tagId}")
    public String tagEditPage(@PathVariable Long tagId, Model model) {
        Tag tag = tagService.findTagById(tagId);
        model.addAttribute("tag", tag);
        return "tag/tag_edit";
    }

    // 태그 수정
    @PostMapping("/tags/edit/{tagId}")
    public String tagEdit(@PathVariable Long tagId, @ModelAttribute Tag tag) {
        tagService.update(tagId, tag);
        return "redirect:/tags/list";
    }
}
