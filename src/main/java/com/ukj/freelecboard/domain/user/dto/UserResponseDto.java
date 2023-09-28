package com.ukj.freelecboard.domain.user.dto;

import com.ukj.freelecboard.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String picture;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
    }
}
