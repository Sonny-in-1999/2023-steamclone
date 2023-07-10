package com.neurotoxin.steamclone.controller.view;

import com.neurotoxin.steamclone.entity.single.Tag;
import com.neurotoxin.steamclone.service.single.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/add")
    public String addTagPage(Model model) {
        model.addAttribute("tag", new Tag());
        return "tag_add";
    }

    @PostMapping("/add")
    public String addTag(@ModelAttribute Tag tag) {
        tagService.create(tag);
        return "redirect:/tags/list";
    }

    @GetMapping("/list")
    public String tagList(Model model) {
        List<Tag> tags = tagService.findAllTags();
        model.addAttribute("tags", tags);
        return "tag_list";
    }
}
