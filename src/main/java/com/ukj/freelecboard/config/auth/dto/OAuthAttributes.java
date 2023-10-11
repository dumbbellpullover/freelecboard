package com.ukj.freelecboard.config.auth.dto;

import com.ukj.freelecboard.domain.user.Role;
import com.ukj.freelecboard.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * OAuth2UserService 통해 가져온 OAuth2User attribute 담음
 * dto
 */
@Getter
@Setter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String password;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String username, String email, String picture, String password) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("naver")) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .username((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * --문제점-- <br>
     * QueryDsl 기술 추가 시에, "uses unchecked or unsafe operations" 이슈로, 컴파일이 되지 않음 <br>
     * <br>
     * --자세한 문제-- <br>
     * 스프링 시큐리티에서는 하위 필드를 명시할 수 없어, 최상위 필드만 user_name 으로 지정 가능 <br>
     * 구글 API는 최상위 필드만 있어 Map&lt;String, Object> 만을 받음. <br>
     * of() 도 그에 맞게 attributes 를 Map&lt;String, Object> 만 받음. <br>
     * 네이버는 받아야 하는 네이버 API는 "response" 라는 최상위 필드 아래 하위 필드에 있음. <br>
     * 따라서, "response"의 필드를 Map<String, Object>로 변환해야 함. <br>
     * 하지만, QueryDsl을 위한 컴파일시, 앞서 말한 문제점이 터짐. <br>
     * <br>
     * --해결책-- <br>
     * 네이버 API는 반드시 "response" 안에 유저 정보가 포함된다는 사실 확인 <br>
     * attributes로 받은 Map에 "response"를 꺼내 Map&lt;String, Object>로 만든 것이 컴파일 되도록
     * 메소드에 @SuppressWarnings("unchecked") 추가
     */
    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .username((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
