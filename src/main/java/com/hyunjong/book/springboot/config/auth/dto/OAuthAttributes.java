package com.hyunjong.book.springboot.config.auth.dto;

import com.hyunjong.book.springboot.domain.user.Role;
import com.hyunjong.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.jar.Attributes;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributekey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributekey, String name,String email, String picture){
        this.attributes = attributes;
        this.nameAttributekey = nameAttributekey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id",attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }
    //OAuth2User에서 반환하는 사용자 정보는 Map값이기 때문에 값 하나하나를 변환해야만 한다.

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributekey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributekey(userNameAttributeName)
                .build();

    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
    /*
    toEntitiy()
    User엔티티를 생성합니다.
    OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 떄입니다.
    가입할 떄의 기본권한을 Guest로 주기 위해서 role빌더값에는 Role.GUEST를 사용한다.
    OAuthAttributes클래스 생성이 끝났으면 같은 패키지에 SessionUser클래스를 생성한다.
     */
}
