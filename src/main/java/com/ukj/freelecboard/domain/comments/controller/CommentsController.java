package com.ukj.freelecboard.domain.comments.controller;

import com.ukj.freelecboard.domain.comments.service.CommentsService;
import com.ukj.freelecboard.domain.comments.dto.CommentsResponseDto;
import com.ukj.freelecboard.domain.comments.dto.CommentsSaveRequestDto;
import com.ukj.freelecboard.domain.comments.dto.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/new")
    public String create(@PathVariable Long postId,
                         @Validated @ModelAttribute(name = "comment") CommentsSaveRequestDto requestDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Principal principal) {

        redirectAttributes.addAttribute("postId", postId);

//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.CommentsSaveRequestDto", bindingResult);
//            redirectAttributes.addFlashAttribute("newComment", requestDto);
//            return "redirect:/posts/{postId}";
//        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorComment", "내용은 필수 항목입니다.");
            return "redirect:/posts/{postId}";
        }

        requestDto.setAuthorName(principal.getName());

        commentsService.save(postId, requestDto);
        return "redirect:/posts/{postId}";
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
                         @Validated @ModelAttribute(name = "comments") CommentsUpdateRequestDto requestDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "editCommentsForm";
        }

        redirectAttributes.addAttribute("postId", postId);

        commentsService.update(id, requestDto);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/vote/{id}")
    public String vote(@PathVariable Long postId, @PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        commentsService.vote(id, principal.getName());
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long postId, @PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        CommentsResponseDto responseDto = commentsService.findById(id);
        if (!responseDto.getAuthorName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        redirectAttributes.addAttribute("postId", postId);

        commentsService.delete(id);
        return "redirect:/posts/{postId}";
    }
}

