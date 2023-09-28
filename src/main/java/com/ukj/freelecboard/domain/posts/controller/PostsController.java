package com.ukj.freelecboard.domain.posts.controller;

import com.ukj.freelecboard.domain.posts.service.PostsService;
import com.ukj.freelecboard.domain.comments.dto.CommentsSaveRequestDto;
import com.ukj.freelecboard.domain.posts.dto.PostsResponseDto;
import com.ukj.freelecboard.domain.posts.dto.PostsSaveRequestDto;
import com.ukj.freelecboard.domain.posts.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping
    public String postsList(Model model, @RequestParam(value = "page", defaultValue = "0") int pageNumber) {
        model.addAttribute("posts", postsService.findPagingList(pageNumber));
        return "postsList";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, @ModelAttribute(name = "comment") CommentsSaveRequestDto requestDto) {
        model.addAttribute("posts", postsService.findById(id));
        return "postsDetail";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute(name = "posts") PostsSaveRequestDto requestDto) {
        return "createPostsForm";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute(name = "posts") PostsSaveRequestDto requestDto,
                         BindingResult bindingResult,
                         Principal principal) {

        if (bindingResult.hasErrors()) {
            return "createPostsForm";
        }

        PostsSaveRequestDto saveRequestDto = PostsSaveRequestDto.builder()
                .authorName(principal.getName())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        Long savedId = postsService.save(saveRequestDto);

        return "redirect:/posts/" + savedId;
    }

    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable Long id, Model model, Principal principal) {
        PostsResponseDto responseDto = postsService.findById(id);

        if (responseDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시물은 존재하지 않습니다.");
        }

        // 페치조인으로 연관관계를 전부 끌고 오는 문제
        if (!principal.getName().equals(responseDto.getAuthorName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        model.addAttribute("posts", responseDto);
        return "editPostsForm";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Validated @ModelAttribute(name = "posts") PostsUpdateRequestDto requestDto,
                         BindingResult bindingResult,
                         Principal principal) {

        // 권한 검사
        PostsResponseDto responseDto = postsService.findById(id);
        if (responseDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시물은 존재하지 않습니다.");
        }
        if (!principal.getName().equals(responseDto.getAuthorName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        log.info("bindingResult = {}", bindingResult);
        // 데이터 검사
        if (bindingResult.hasErrors()){
            return "editPostsForm";
        }

        postsService.update(id, requestDto);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        PostsResponseDto responseDto = postsService.findById(id);

        if (responseDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시물은 존재하지 않습니다.");
        }

        if (!principal.getName().equals(responseDto.getAuthorName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        postsService.delete(id);
        return "redirect:/posts";
    }
}
