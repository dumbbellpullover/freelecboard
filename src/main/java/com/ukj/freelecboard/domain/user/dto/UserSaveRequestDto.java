package com.ukj.freelecboard.domain.user.dto;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class UserSaveRequestDto {

    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 이름은 필수항목입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "이메일의 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    private String picture;

    private Role role;

    @Builder
    public UserSaveRequestDto(String username, String email, String password1, String password2, String picture, Role role) {
        this.username = username;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.picture = picture;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .picture(picture)
                .password(password1)
                .role(role)
                .build();
    }
}
