package com.neurotoxin.steamclone.controller.view;

import com.neurotoxin.steamclone.service.connect.FileStore;
import com.neurotoxin.steamclone.entity.single.Community;
import com.neurotoxin.steamclone.entity.single.Media;
import com.neurotoxin.steamclone.service.single.CommunityService;
import com.neurotoxin.steamclone.service.single.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final MediaService mediaService;
    private final FileStore fileStore;

    // 커뮤니티 홈페이지
    @GetMapping("/community")
    public String community(Model model) {
        List<Community> communities = communityService.findAllCommunities();
        model.addAttribute("community", communities);
        return "community/community_home";
    }
    // 커뮤니티 글 작성 페이지
    @GetMapping("/community/post")
//    @PreAuthorize("#loginName == authentication.principal.username")
    public String postCommunity(Model model) {
        model.addAttribute("community", new Community());
        return "community/community_add";
    }
    // 커뮤니티 글 작성
    @PostMapping("/community/post")
//    @PreAuthorize("#loginName == authentication.principal.username")
    public String post(@ModelAttribute Community community, @RequestParam List<MultipartFile> files) throws IOException {
        List<Media> media = mediaService.storeFiles(files, community);
        communityService.post(community, media);
        return "redirect:/community/" + community.getId();
    }
    // 커뮤니티 글 수정 페이지
    @GetMapping("/community/edit/{communityId}")
//    @PreAuthorize("#loginName == authentication.principal.username")
    public String editMemberAccountPage(@PathVariable Long communityId, Model model) {
        Community findCommunity = communityService.findCommunityById(communityId);
//        if (findCommunity.getMember().equals(CurrentLoginMember)) {
//            model.addAttribute("community", findCommunity);
//            return "community/community_edit";
//        } else {
//            throw new IllegalStateException("권한이 없습니다.");
//        }
        model.addAttribute("community", findCommunity);
        return "community/community_edit";
    }
    // 커뮤니티 글 수정
    @PostMapping("/community/edit/{communityId}")
//    @PreAuthorize("#loginName == authentication.principal.username")
    public String edit(@PathVariable Long communityId, @ModelAttribute Community updatedCommunity) {
//        communityService.update(communityId, LoginMemberId, updatedCommunity);
        return "redirect:/community/{communityId}";
    }
    // 커뮤니티 글 조회
    @GetMapping("/community/{communityId}")
    public String detail(@PathVariable Long communityId, Model model) {
        Community community = communityService.findCommunityById(communityId);
        model.addAttribute("community", community);
        return "community/community_detail";
    }

    // 파일 이름으로 경로를 설정해 UrlResource로, ResponseBody로 반환하여 다운로드를 허가
    @GetMapping("/media/{filename}")
    public ResponseEntity<Resource> processMedia(@PathVariable String filename, @RequestParam String originName, Long communityId) throws MalformedURLException {
        Community community = communityService.findCommunityById(communityId);
        UrlResource urlResource = new UrlResource("file:" + fileStore.setPath(filename, fileStore.setMediaType(filename), community));

        String encodedUploadFileName = UriUtils.encode(originName, StandardCharsets.UTF_8); // 한글 깨짐 방지를 위한 UTF-8 Encode
        String contentDisposition = "filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
