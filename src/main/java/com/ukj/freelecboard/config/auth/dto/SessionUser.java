package com.ukj.freelecboard.config.auth.dto;

import com.ukj.freelecboard.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 세션에 사용자 정보를 저장하기 위한 dto 클래스
 * 인증된 사용자 정보
 */
@Getter
public class SessionUser implements Serializable {

    private String username;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
