package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.service.UserService;
import com.ukj.freelecboard.web.dto.user.UserSaveRequestDto;
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
}
