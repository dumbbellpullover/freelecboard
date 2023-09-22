package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.service.CommentsService;
import com.ukj.freelecboard.service.PostsService;
import com.ukj.freelecboard.web.dto.comments.CommentsSaveRequestDto;
import com.ukj.freelecboard.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;
    private final PostsService postsService;

    @PostMapping("/new/{postId}")
    public String create(Model model,
                         @PathVariable Long postId,
                         @Validated @ModelAttribute(name = "requestDto") CommentsSaveRequestDto requestDto,
                         BindingResult bindingResult) {

        PostsResponseDto responseDto = postsService.findById(postId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", responseDto);
            return "postDetail";
        }

        CommentsSaveRequestDto saveRequestDto = CommentsSaveRequestDto.builder()
                .author(requestDto.getAuthor())
                .content(requestDto.getContent())
                .build();

        commentsService.save(postId, saveRequestDto);
        return "redirect:/posts/{postId}";
    }
}

