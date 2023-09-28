package com.ukj.freelecboard.domain.user.controller;

import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.service.UserService;
import com.ukj.freelecboard.domain.user.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/new")
    public String signup(@ModelAttribute(name = "user") UserSaveRequestDto user) {
        return "createUserForm";
    }

    @PostMapping("/new")
    public String signup(@Validated @ModelAttribute(name = "user") UserSaveRequestDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createUserForm";
        }

        if (!user.getPassword1().equals(user.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "입력한 비밀번호와 일치하지 않습니다.");
            return "createUserForm";
        }

        user.setRole(Role.USER);
        try {
            userService.save(user);

        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "createUserForm";

        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "createUserForm";
        }

        return "redirect:/";
    }

    /**
     * 실제 로그인을 진행하는 @PostMapping 방식의 메서드는 스프링 시큐리티가 대신 처리
     * 로그인 실패시 파라미터로 error 전달; 스프링 시큐리티 규칙
     * 시큐리티 없이 하려면 @Valid 적용, bindingResult 로 "error" 와 메세지 보내야 함
     */
    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }
}
