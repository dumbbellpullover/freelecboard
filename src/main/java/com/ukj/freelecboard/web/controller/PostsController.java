package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.service.PostsService;
import com.ukj.freelecboard.web.dto.comments.CommentsSaveRequestDto;
import com.ukj.freelecboard.web.dto.posts.PostsResponseDto;
import com.ukj.freelecboard.web.dto.posts.PostsSaveRequestDto;
import com.ukj.freelecboard.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @GetMapping()
    public String postsList(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "postsList";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, @ModelAttribute(name = "requestDto") CommentsSaveRequestDto requestDto) {
        model.addAttribute("post", postsService.findById(id));
        return "postDetail";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute(name = "requestDto") PostsSaveRequestDto requestDto) {
        return "createPostsForm";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute(name = "requestDto") PostsSaveRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createPostsForm";
        }
        PostsSaveRequestDto saveRequestDto = PostsSaveRequestDto.builder()
                .author(requestDto.getAuthor())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        postsService.save(saveRequestDto);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "editPostsForm";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute PostsUpdateRequestDto requestDto) {
        postsService.update(id, requestDto);
        return "redirect:/posts";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postsService.delete(id);
        return "redirect:/posts";
    }
}
