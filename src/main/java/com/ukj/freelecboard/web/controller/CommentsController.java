package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.service.CommentsService;
import com.ukj.freelecboard.service.PostsService;
import com.ukj.freelecboard.web.dto.comments.CommentsResponseDto;
import com.ukj.freelecboard.web.dto.comments.CommentsSaveRequestDto;
import com.ukj.freelecboard.web.dto.comments.CommentsUpdateRequestDto;
import com.ukj.freelecboard.web.dto.posts.PostsResponseDto;
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
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;
    private final PostsService postsService;

    @PostMapping("/new")
    public String create(@PathVariable Long postId,
                         Model model,
                         @Validated @ModelAttribute(name = "comment") CommentsSaveRequestDto requestDto,
                         BindingResult bindingResult,
                         Principal principal) {

        PostsResponseDto responseDto = postsService.findById(postId);

        log.info("bindResult = {}", bindingResult.getFieldError("content"));

        if (bindingResult.hasErrors()) {
            model.addAttribute("posts", responseDto);
            return "postsDetail";
        }

        requestDto.setAuthorName(principal.getName());

        commentsService.save(postId, requestDto);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable Long postId,
                         @PathVariable Long id,
                         Model model,
                         Principal principal) {
        CommentsResponseDto responseDto = commentsService.findById(id);
        if (!responseDto.getAuthorName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        model.addAttribute("comments", responseDto);
        return "editCommentsForm";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long postId,
                         @PathVariable Long id,
                         @ModelAttribute CommentsUpdateRequestDto requestDto) {
        commentsService.update(id, requestDto);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long postId, @PathVariable Long id, Principal principal) {
        CommentsResponseDto responseDto = commentsService.findById(id);
        if (!responseDto.getAuthorName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        commentsService.delete(id);
        return "redirect:/posts/" + postId;
    }
}

