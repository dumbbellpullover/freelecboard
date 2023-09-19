package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.service.CommentsService;
import com.ukj.freelecboard.service.PostsService;
import com.ukj.freelecboard.web.dto.comments.CommentsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/new/{postId}")
    public String create(@PathVariable Long postId, @ModelAttribute CommentsSaveRequestDto requestDto) {

        CommentsSaveRequestDto saveRequestDto = CommentsSaveRequestDto.builder()
                .author(requestDto.getAuthor())
                .content(requestDto.getContent())
                .postsId(postId)
                .build();

        commentsService.save(saveRequestDto);
        return "redirect:/posts/{postId}";
    }
}

