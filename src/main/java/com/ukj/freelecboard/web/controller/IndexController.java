package com.ukj.freelecboard.web.controller;

import com.ukj.freelecboard.config.auth.LoginUser;
import com.ukj.freelecboard.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getUsername());
        }
        return "index";
    }
}
